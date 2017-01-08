package de.hsnr.eal.ArtificialDispatcher.data.prolog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyTask;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyType;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment.EquipmentItem;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.Station;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.ConcreteVehicle;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;

public class PLDatabase {
	
	
	private String filepath;

	public PLDatabase(String path){
		//System.out.println(System.getProperties());
		this.filepath = path;
		Query data = new Query( "consult",new Term[] {new Atom(filepath)});	
		System.out.println( "consult " + (data.hasMoreElements() ? "succeeded" : "failed"));
	}

	String getVehicleTypeForEquipment(){
       // String tVehicleForEuipment = "child_of(joe, ralf)";
       // Query qVehicleForEuipment = new Query(tVehicleForEuipment);
        
        return "";
	}
	
	private Map<String, Term>[] getStations(){
		String tStations = "station(Number, Name, Type, Node)";
        Query qStations = new Query(tStations);
        
        Map<String, Term>[] stations = qStations.allSolutions();
       	
        return stations;
	}
	
	public ArrayList<Station> getStationObjects(){
		Map<String, Term>[] stations = getStations();
		ArrayList<Station> stationObjects = new ArrayList<Station>();

        for(int i = 0; i < stations.length; i++){
    		int number =  Integer.parseInt(stations[i].get("Number").toString());
    		String name = stations[i].get("Name").toString().replace("'", "");
    		String type = stations[i].get("Type").toString().replace("'", "");
    		long node = stations[i].get("Node").longValue();
    		stationObjects.add(new Station(number, name, type, node));
        }
        	
        return stationObjects;
	}
	
	private Map<String, Term>[] getVehiclesOfStation(int stationId){
		String tVehicles = "vehicle(Number, Type, Name, "+ stationId +", CrewStrength)";
        Query qVehicles = new Query(tVehicles);
        
        Map<String, Term>[] vehicles = qVehicles.allSolutions();
       	
        return vehicles;
	}
	
	
	public ArrayList<Vehicle> getVehiclesObjectsOfStation(int stationId) throws Exception{
		Map<String, Term>[] vehicles = getVehiclesOfStation(stationId);
		ArrayList<Vehicle> vehicleObjects = new ArrayList<Vehicle>();
		
        for(int i = 0; i < vehicles.length; i++){
        	Map<String, Term> vehicleMap = vehicles[i];
        	vehicleObjects.add(constructVehicle(stationId, vehicleMap));
        }		
		
		return vehicleObjects;
		
	}
	
	private Map<String, Term> getEquipment(int id){
		String tEquipment = "equipment(" + id + ", Name, SetupTime, NeededPeople)";
        Query qEquipmemt = new Query(tEquipment);
        
        return qEquipmemt.oneSolution();
	}
	
	private EquipmentItem getEquipmentObject(int id) {
		Map<String, Term> item = getEquipment(id);
		String name = item.get("Name").toString().replace("'", "");
		int setupTime = item.get("SetupTime").intValue();
		int neededPeople = item.get("NeededPeople").intValue();
		return new EquipmentItem(id, name, setupTime, neededPeople);
	}

	private Map<String, Term> getVehicleType(String typeTerm) throws Exception{
		String tVehicle = "vehicleType(" + typeTerm + ", Equipment, TankVolume, EmergencySpeed, NormSpeed)";
        Query qVehicle = new Query(tVehicle);
        
        if(!qVehicle.hasSolution())
        	throw new Exception("no vehicleType found. Prolog Error 10");
        return qVehicle.oneSolution();
	}
	
	

	private ConcreteVehicle constructVehicle(int stationId, Map<String, Term> vehicleMap) throws Exception {
		int id =  Integer.parseInt(vehicleMap.get("Number").toString());
		String name = vehicleMap.get("Name").toString().replace("'", "");
		String typeTerm = vehicleMap.get("Type").toString();
		String type = vehicleMap.get("Type").toString().replace("'", "");
		int station = stationId;
		int crewStrength = vehicleMap.get("CrewStrength").intValue();
		
		Map<String, Term> vehicleType = getVehicleType(typeTerm);
		List<EquipmentItem> equipmentItems = loadEquipment(vehicleType.get("Equipment").toTermArray());
		int emergencySpeed = vehicleType.get("EmergencySpeed").intValue();
		int normSpeed = vehicleType.get("NormSpeed").intValue();
		int tankVolume = vehicleType.get("TankVolume").intValue();
		
		return new ConcreteVehicle(id, type, name, station, crewStrength, equipmentItems, emergencySpeed, normSpeed, tankVolume);
	}

	private List<EquipmentItem> loadEquipment(Term equipmentTerm[]) throws Exception {
		ArrayList<EquipmentItem> items = new ArrayList<EquipmentItem>();
		int[] equipmentIds = parsePLListToIntArray(equipmentTerm);
		
		for(int i = 0; i < equipmentIds.length; i++)
			items.add(getEquipmentObject(equipmentIds[i]));
		
		return items;
	}

	private int[] parsePLListToIntArray(Term listTerm[]) {
		int l = listTerm.length;
		int arr[] =  new int[l];
		for(int i = 0; i < l; i++)
			arr[i] = listTerm[i].intValue();
		return arr;
	}
	
	private List<Term[]> parsePLListToListIntArray(Term[] terms) {
		ArrayList<Term[]> list = new ArrayList<Term[]>();
		for(int i = 0; i < terms.length; i++)
			list.add(terms[i].toTermArray());
		return list;
	}

	private Map<String, Term>[] getEmergencyTypes(){
		String tEmergencyType = "emergencyType(CodeWordId, Name, TaskIds)";
        Query qEmergencyType = new Query(tEmergencyType);
        
        Map<String, Term>[] emergencyTypes = qEmergencyType.allSolutions();
       	
        return emergencyTypes;
		
	}
	
	public List<EmergencyType> getEmergencyTypeObjects() throws Exception{
		Map<String, Term>[] emergencyType = getEmergencyTypes();
		ArrayList<EmergencyType> emergemcyTypeObjects = new ArrayList<EmergencyType>();

        for(int i = 0; i < emergencyType.length; i++){
    		int codeWordId =  emergencyType[i].get("CodeWordId").intValue();
    		String codeWord = getCodeWord(codeWordId);
    		String name = emergencyType[i].get("Name").toString().replace("'", "");
    		List<EmergencyTask> tasks = loadTasks(emergencyType[i].get("TaskIds").toTermArray());

    		emergemcyTypeObjects.add(new EmergencyType(codeWord, name, tasks));
        }
        	
        return emergemcyTypeObjects;
	}
	
	private List<EmergencyTask> loadTasks(Term[] taskTerm) throws Exception {
		ArrayList<EmergencyTask> tasks = new ArrayList<EmergencyTask>();
		int[] taskIds = parsePLListToIntArray(taskTerm);
		
		for(int i = 0; i < taskIds.length; i++)
			tasks.add(getTaskObject(taskIds[i]));
		
		return tasks;

	}

	private EmergencyTask getTaskObject(int id) throws Exception {
		Map<String, Term> task = getTask(id);
		String name = task.get("Name").toString().replace("'", "");
		List<Term[]> equipmentTerms = parsePLListToListIntArray(task.get("EquipmentTerms").toTermArray());
		List<EquipmentItem> neededEquipment = loadEquipment(equipmentTerms.get(0));
		List<EquipmentItem> altEquipment = null;
		if(equipmentTerms.size() > 1)
			altEquipment = loadEquipment(equipmentTerms.get(1));

		int estimatedTime = task.get("EstimatedTime").intValue();
		
		return new EmergencyTask(id, name, neededEquipment, altEquipment, estimatedTime);
	}

	private Map<String, Term> getTask(int id) {
		String tTask = "task(" + id + ", Name, EquipmentTerms, EstimatedTime)";
        Query qTask= new Query(tTask);
        
        return qTask.oneSolution();
	}

	private String getCodeWord(int codeWordId) {
		String tCodeWord = "codeWord(" + codeWordId  + ", Name)";
        Query qCodeWord = new Query(tCodeWord);
		
        return qCodeWord.oneSolution().get("Name").toString().replace("'", "");
	}
}
