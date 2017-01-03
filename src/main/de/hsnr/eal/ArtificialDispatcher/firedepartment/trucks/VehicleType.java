package de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.StationType;

public enum VehicleType {
	HLF_BF("HLF-BF"), 
	HLF("HLF"), 
	HLF_A("HLF-A"),
	LF8("LF8"),
	LF20("LF20"),
	ELW("ELW"),
	RW2_K("RW2-K"),
	GTLF("GTLF"),	
	DLK_23("DLK-23");
	
	
	private String name;
	VehicleType(String name){
		this.name = name;
	}
	
	@Override
	public String toString(){
		return this.name;
	}

	public static VehicleType parseType(String typeTerm) {
		if(typeTerm.equals(HLF_BF.toString()))
			return VehicleType.HLF;
		if(typeTerm.equals(HLF.toString()))
			return VehicleType.HLF;
		if(typeTerm.equals(HLF_A.toString()))
			return VehicleType.HLF_A;
		if(typeTerm.equals(LF8.toString()))
			return VehicleType.LF8;
		if(typeTerm.equals(LF20.toString()))
			return VehicleType.LF20;
		if(typeTerm.equals(ELW.toString()))
			return VehicleType.ELW;
		if(typeTerm.equals(RW2_K.toString()))
			return VehicleType.RW2_K;
		if(typeTerm.equals(GTLF.toString()))
			return VehicleType.GTLF;
		if(typeTerm.equals(DLK_23.toString()))
			return VehicleType.DLK_23;
		return null;
	}
}
