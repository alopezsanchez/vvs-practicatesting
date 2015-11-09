package es.udc.vvs.test.emisora;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.udc.vvs.model.contenido.Cancion;
import es.udc.vvs.model.contenido.Emisora;
import es.udc.vvs.model.contenido.Contenido;

public class EmisoraTest {
	
	private Emisora emisora1;
	private Emisora emisora2;
	
	private Cancion cancion1;
	private Cancion cancion2;
	private Cancion cancion3;
	
	@Before
	public void setUp() {
		emisora1 = new Emisora("emisora1",0);
		emisora2 = new Emisora("emisora2",0);

		
		cancion1 = new Cancion("cancion1",4);
		cancion2 = new Cancion("cancion2",5);
		cancion3 = new Cancion("cancion3",6);
		
		emisora1.agregar(cancion1, null);
		emisora1.agregar(cancion2, cancion1);
		emisora2.agregar(cancion3, null);
		
	}
	
	@Test
	public void obtenerTituloTest() {
		assertTrue(emisora1.obtenerTitulo().equals("emisora1"));
		assertFalse(emisora1.obtenerTitulo().equals("emisora2"));
	}
	
	@Test
	public void agregarTest(){

		List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion1);
		lista.add(cancion2);
	
		assertTrue(emisora1.obtenerListaReproduccion().get(0).equals(lista.get(0)));
		assertTrue(emisora1.obtenerListaReproduccion().get(1).equals(lista.get(1)));
		
		assertTrue(cancion3.equals(emisora2.obtenerListaReproduccion().get(0)));
		
	}

	@Test
	public void obtenerDuracionTest() {
		assertEquals(emisora1.obtenerDuracion(),9);
		assertEquals(emisora2.obtenerDuracion(),6);
		assertNotEquals(emisora1.obtenerDuracion(),0);
		assertNotEquals(emisora2.obtenerDuracion(),0);
	}
	
	@Test
	public void obtenerListaReproduccionTest() {
		List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion1);
		lista.add(cancion2);
		
		assertTrue(lista.equals(emisora1.obtenerListaReproduccion()));
	}
	
	@Test
	public void buscarTest() {
		List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion3);

		assertTrue(lista.equals(emisora2.buscar("cancion3")));
		assertTrue(lista.equals(emisora2.buscar("can")));
		assertFalse(lista.equals(emisora2.buscar("cancion1")));

		
	}
	
}
