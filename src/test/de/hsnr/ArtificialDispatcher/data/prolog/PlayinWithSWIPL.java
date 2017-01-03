package de.hsnr.ArtificialDispatcher.data.prolog;

import static org.junit.Assert.*;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.junit.Test;

public class PlayinWithSWIPL {

	@Test
	public void test() {
		
		
		
		System.out.println(System.getProperties());
	    Query q1 = new Query( "consult",new Term[] {new Atom("C:\\Users\\lammbraten\\workspace\\TestProlog4\\src\\test.pl")});

	 System.out.println( "consult " + (q1.hasMoreElements() ? "succeeded" : "failed"));
	 System.out.println( "consult ");
	}

}
