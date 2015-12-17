
/*
 * Test de la clase Cancion
 * 
 * @author Alex
 */
package es.udc.vvs.test.cancion;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import net.java.quickcheck.Generator;
import net.java.quickcheck.characteristic.Classification;
import net.java.quickcheck.collection.Pair;
import net.java.quickcheck.generator.CombinedGenerators;
import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.iterable.Iterables;

import org.junit.Before;
import org.junit.Test;

import es.udc.vvs.model.contenido.Contenido;
import es.udc.vvs.model.contenido.cancionimpl.ImplementacionCancion;
import es.udc.vvs.model.contenido.emisoraimpl.ImplementacionEmisora;


public class CancionTest {

	private ImplementacionCancion cancion;
	private ImplementacionCancion cancion2;
	private ImplementacionCancion cancion3;
	
	@Before
	public void setUp() {
		cancion = new ImplementacionCancion("cancion1",3);
		cancion2 = new ImplementacionCancion("cancion2",5);
		
	}
	
	/**
	 * ObtenerTitulo() test.
	 */
	@Test
	public void obtenerTituloTest() {
			
		for (String anyString : Iterables.toIterable(PrimitiveGenerators.printableStrings())) {
			cancion = new ImplementacionCancion(anyString,3);
			String otherString = PrimitiveGenerators.printableStrings().next();
			
			assertTrue(cancion.obtenerTitulo().equals(anyString));		
		}	
	}
	
	/**
	 * ObtenerDuracion() test.
	 */
	@Test
	public void obtenerDuracionTest() {
		
		Generator<Integer> generator = PrimitiveGenerators.integers(-50,50);
		
		for (Integer anyInteger : Iterables.toIterable(generator)) {
			cancion = new ImplementacionCancion("cancion1",anyInteger);
			assertTrue(anyInteger.equals(cancion.obtenerDuracion()));
			//System.out.println(anyInteger);
		}
	}
	
	/**
	 * ObtenerListaReproduccion() test.
	 */
	@Test
	public void obtenerListaReproduccionTest() {
		
		/*for (Pair<ImplementacionCancion, List<ImplementacionCancion>> pair : Iterables.toIterable(new CancionListGenerator())) {
			ImplementacionCancion anyCancion = pair.getFirst();
			List<ImplementacionCancion> anyList = pair.getSecond();
			
			assertTrue(anyList.get(0).equals(anyCancion.obtenerListaReproduccion().get(0)));
		}*/
		/*List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion);
		List<Contenido> lista2 = new ArrayList<Contenido>();
		lista2.add(cancion2);
		
		assertTrue(lista.equals(cancion.obtenerListaReproduccion()));
		assertTrue(lista2.equals(cancion2.obtenerListaReproduccion()));*/
		
		
		CancionGenerator cGen = new CancionGenerator();
		List<Contenido> listaReproduccion = new ArrayList<Contenido>();
		
		for (ImplementacionCancion anyCancion : Iterables.toIterable(cGen)) {
			//cancion3 = new ImplementacionCancion("cancion3",0);
			listaReproduccion.add(anyCancion);
			
			//assertEquals(listaReproduccion,anyCancion.obtenerListaReproduccion());
			System.out.println(listaReproduccion.size());
			System.out.println(anyCancion.obtenerListaReproduccion().size());
		}
	}
	
	
	class CancionGenerator implements Generator<ImplementacionCancion> {
		Generator<Integer> iGen = PrimitiveGenerators.integers(-50,50);
		Generator<String> sGen = PrimitiveGenerators.printableStrings();
		
		public ImplementacionCancion next() {
			String anyTitulo = sGen.next();
			int anyDuracion = iGen.next();
			
			return new ImplementacionCancion(anyTitulo,anyDuracion);
		}
		
	}
	
	
	class CancionListGenerator implements Generator<Pair<ImplementacionCancion,List<ImplementacionCancion>>> {
		Generator<ImplementacionCancion> cGen = new CancionGenerator();
		Generator<List<ImplementacionCancion>> lGen = CombinedGenerators.lists(new CancionGenerator());
		
		@SuppressWarnings("unchecked")
		public Pair<ImplementacionCancion, List<ImplementacionCancion>> next() {
			ImplementacionCancion anyCancion = cGen.next();
			List<ImplementacionCancion> l = lGen.next();
			
			l.add(anyCancion);
			
			return new Pair(anyCancion,l);
		}
		
	

		
		
	}
	
	/**
	 * Buscar(subcadena) test.
	 */
	@Test
	public void buscarTest() {
		
		Generator<ImplementacionCancion> cGen = new CancionGenerator();
		List<Contenido> lista = new ArrayList<Contenido>();
		
		for (ImplementacionCancion anyCancion : Iterables.toIterable(cGen)) {
			lista.add(anyCancion);
			ImplementacionCancion otherCancion = cGen.next();
			// COMO HACER QUE LA LISTA SEA IGUAL?
			assertEquals(anyCancion,anyCancion.buscar(anyCancion.obtenerTitulo()).get(0));
			assertNotEquals(lista,otherCancion.buscar(anyCancion.obtenerTitulo()));
		}
		
		/*List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion);
		List<Contenido> lista2 = new ArrayList<Contenido>();
		lista2.add(cancion2);
		List<Contenido> lista3 = new ArrayList<Contenido>();
		
		assertTrue(lista.equals(cancion.buscar("cancion1")));
		assertFalse(lista2.equals(cancion.buscar("cancion1")));
		assertTrue(lista.equals(cancion.buscar("can")));
		assertFalse(lista3.equals(cancion.buscar("cancion1")));*/
		
	}

}
