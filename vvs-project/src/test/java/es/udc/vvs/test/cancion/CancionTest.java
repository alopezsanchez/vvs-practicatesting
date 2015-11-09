
/*
 * Test de la clase Cancion
 * 
 * @author Alex
 */
package es.udc.vvs.test.cancion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.udc.vvs.model.contenido.Cancion;
import es.udc.vvs.model.contenido.Contenido;


public class CancionTest {

	private Cancion cancion;
	private Cancion cancion2;
	
	@Before
	public void setUp() {
		cancion = new Cancion("cancion1",3);
		cancion2 = new Cancion("cancion2",5);
		
	}
	
	/**
	 * ObtenerTitulo() test.
	 */
	@Test
	public void obtenerTituloTest() {
		assertTrue(cancion.obtenerTitulo().equals("cancion1"));
		assertFalse(cancion.obtenerTitulo().equals("cancion2"));
	}
	
	/**
	 * ObtenerDuracion() test.
	 */
	@Test
	public void obtenerDuracionTest() {
		assertEquals(cancion.obtenerDuracion(),3);
		assertEquals(cancion2.obtenerDuracion(),5);
		assertNotEquals(cancion.obtenerDuracion(),5);
		assertNotEquals(cancion2.obtenerDuracion(),3);
	}
	
	/**
	 * ObtenerListaReproduccion() test.
	 */
	@Test
	public void obtenerListaReproduccionTest() {
		List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion);
		List<Contenido> lista2 = new ArrayList<Contenido>();
		lista2.add(cancion2);
		
		assertTrue(lista.equals(cancion.obtenerListaReproduccion()));
		assertTrue(lista2.equals(cancion2.obtenerListaReproduccion()));
	}
	
	/**
	 * Buscar(subcadena) test.
	 */
	@Test
	public void buscarTest() {
		List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion);
		List<Contenido> lista2 = new ArrayList<Contenido>();
		lista2.add(cancion2);
		List<Contenido> lista3 = new ArrayList<Contenido>();
		
		assertTrue(lista.equals(cancion.buscar("cancion1")));
		assertFalse(lista2.equals(cancion.buscar("cancion1")));
		assertTrue(lista.equals(cancion.buscar("can")));
		assertFalse(lista3.equals(cancion.buscar("cancion1")));
		
	}

}
