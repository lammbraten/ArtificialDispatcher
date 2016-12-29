package de.hsnr.eal.ArtificialDispatcher.data.prolog;

import gnu.prolog.database.*;
import gnu.prolog.term.AtomTerm;
import gnu.prolog.vm.Environment;
import gnu.prolog.vm.Interpreter;


/**
 * A reader for the in prolog written databsae of firestations
 *
 */
public class StationAdapter {
	
	private static boolean issetup = false;
	private static Environment env;
	private static Interpreter interpreter;
	
	public StationAdapter(){
		setup();
	}
	
	/**
	 * Ensure that we have an environment and have loaded the prolog code and have
	 * an interpreter to use.
	 */
	private synchronized static void setup() {
		if (issetup)
			return;

		env = new Environment();
		env.ensureLoaded(AtomTerm.get("vehicles.pl"));
		interpreter = env.createInterpreter();
		env.runInitialization(interpreter);

		issetup = true;
	}
	
}
