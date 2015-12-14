package es.udc.vvs.test.emisora;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.udc.vvs.model.contenido.Contenido;
import es.udc.vvs.model.contenido.cancionimpl.ImplementacionCancion;
import es.udc.vvs.model.contenido.emisoraimpl.ImplementacionEmisora;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.iterable.Iterables;

public class EmisoraTest {
	
	private ImplementacionEmisora emisora1;
	private ImplementacionEmisora emisora2;
	private ImplementacionEmisora emisora3;
	
	private ImplementacionCancion cancion1;
	private ImplementacionCancion cancion2;
	private ImplementacionCancion cancion3;
	
	@Before
	public void setUp() {
		emisora1 = new ImplementacionEmisora("emisora1",0);
		emisora2 = new ImplementacionEmisora("emisora2",0);

		
		cancion1 = new ImplementacionCancion("cancion1",4);
		cancion2 = new ImplementacionCancion("cancion2",5);
		cancion3 = new ImplementacionCancion("cancion3",6);
		
		emisora1.agregar(cancion1, null);
		emisora1.agregar(cancion2, cancion1);
		emisora2.agregar(cancion3, null);
		
	}
	
	@Test
	public void obtenerTituloTest() {
		
		for (String anyString : Iterables.toIterable(PrimitiveGenerators.printableStrings())) {
			emisora1 = new ImplementacionEmisora(anyString,0);
			String otherString = PrimitiveGenerators.printableStrings().next();
			
			assertTrue(emisora1.obtenerTitulo().equals(anyString));
		}
	}
	
	@Test
	public void agregarTest(){

		for (ImplementacionCancion anyCancion : Iterables.toIterable(new CancionGenerator())) {
			emisora1.agregar(anyCancion, null);
			
			assertEquals(anyCancion,emisora1.buscar(anyCancion.obtenerTitulo()).get(0));
		}
			
		/*List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion1);
		lista.add(cancion2);
	
		assertTrue(emisora1.obtenerListaReproduccion().get(0).equals(lista.get(0)));
		assertTrue(emisora1.obtenerListaReproduccion().get(1).equals(lista.get(1)));
		
		assertTrue(cancion3.equals(emisora2.obtenerListaReproduccion().get(0)));*/
		
	}
	
	@Test
	public void eliminarTest() {
		
		List<Contenido> listaReproduccion = new ArrayList<Contenido>();
		
		for (ImplementacionCancion anyCancion : Iterables.toIterable(new CancionGenerator())) {
			emisora1.agregar(anyCancion, null);
			ImplementacionCancion otherCancion = new CancionGenerator().next();
			listaReproduccion.add(anyCancion);
			assertEquals(anyCancion,emisora1.buscar(anyCancion.obtenerTitulo()).get(0));
			
			emisora1.eliminar(anyCancion);
			assertNotEquals(listaReproduccion,emisora1.buscar(anyCancion.obtenerTitulo()));
			emisora1.eliminar(otherCancion);
			assertNotEquals(listaReproduccion,emisora1.buscar(anyCancion.obtenerTitulo()));
		}
	}

	@Test
	public void obtenerDuracionTest() {
		
		for (int anyDuracion : Iterables.toIterable(PrimitiveGenerators.integers())) {
			emisora1 = new ImplementacionEmisora("emisora1",anyDuracion);
			
			assertEquals(emisora1.obtenerDuracion(),anyDuracion);
		}
		
		/*assertEquals(emisora1.obtenerDuracion(),9);
		assertEquals(emisora2.obtenerDuracion(),6);
		assertNotEquals(emisora1.obtenerDuracion(),0);
		assertNotEquals(emisora2.obtenerDuracion(),0);*/
	}
	
	@Test
	public void obtenerListaReproduccionTest() {
		
		CancionGenerator cGen = new CancionGenerator();
		List<Contenido> listaReproduccion = new ArrayList<Contenido>();
		
		for (ImplementacionCancion anyCancion : Iterables.toIterable(cGen)) {
			emisora3 = new ImplementacionEmisora("emisora3",0);
			emisora3.agregar(anyCancion, null);
			listaReproduccion.add(anyCancion);
			
			assertEquals(anyCancion,emisora3.obtenerListaReproduccion().get(0));
			
		}
	}
	
	@Test
	public void buscarTest() {
		Generator<ImplementacionCancion> cGen = new CancionGenerator();
		List<Contenido> lista = new ArrayList<Contenido>();
		
		for (ImplementacionCancion anyCancion : Iterables.toIterable(cGen)) {
			emisora3 = new ImplementacionEmisora("emisora3",0);
			emisora3.agregar(anyCancion, null);
			lista.add(anyCancion);
			
			// COMO HACER QUE LA LISTA SEA IGUAL?
			assertEquals(anyCancion,emisora3.buscar(anyCancion.obtenerTitulo()).get(0));
			
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
	
	
}
