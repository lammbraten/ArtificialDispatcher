package de.hsnr.ArtificialDispatcher.data.prolog;

import static org.junit.Assert.*;

import java.util.Hashtable;
import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;
import org.junit.Test;

public class PlayinWithSWIPL {

	@Test
	public void test() {
		
		
		
		System.out.println(System.getProperties());
	    Query q1 = new Query( "consult",new Term[] {new Atom("C:\\Users\\lammbraten\\Dropbox\\Master\\1.Semester\\EAL\\Projekt\\Implementierung\\ArtificialDispatcher\\src\\test\\de\\hsnr\\ArtificialDispatcher\\data\\prolog\\test.pl")});

	 System.out.println( "consult " + (q1.hasMoreElements() ? "succeeded" : "failed"));
	 
	    Query q2 =
	            new Query(
	                "child_of",
	                new Term[] {new Atom("joe"),new Atom("ralf")}
	            );

	        System.out.println(
	            "child_of(joe,ralf) is " +
	            ( q2.hasSolution()  ? "provable" : "not provable" )
	        );
	        
	        
	      //---------
	        String t17 = "child_of(joe, ralf)";
	        Query q17 = new Query(t17);
	        
	        System.out.println(t17 + " is " + (q17.hasSolution() ? "provable" : "not provable"));
	        
	        //---
	        String t4 = "descendent_of(X, ralf)";
	        Query q4 = new Query(t4);
	        
	        System.out.println("first solution of " + t4 + ": X = " + q4.oneSolution().get("X"));
	        
	        //---
	        Map<String, Term>[] ss4 = q4.allSolutions();
	        
	        System.out.println("all solutions of " + t4);
	        for(int i = 0; i < ss4.length; i++)
	        	System.out.println("X = "  + ss4[i].get("X"));

            //---
	        System.out.println("each solution of " + t4);
	        while(q4.hasMoreSolutions()){
	        	Map<String, Term> s4 = q4.nextSolution();
	        	System.out.println("X = " + s4.get("X"));
	        }
	        
	        //...
	        String t5 = "descendent_of(X,Y)";
	        Query q5 = new Query(t5);
	        
	        System.out.println("each solution of " + t5);            
	        while(q5.hasMoreSolutions()){
	        	Map<String, Term> s5 = q5.nextSolution();
	        	System.out.println("X = " + s5.get("X") + ", Y = " + s5.get("Y"));
	        }       




	}

}
