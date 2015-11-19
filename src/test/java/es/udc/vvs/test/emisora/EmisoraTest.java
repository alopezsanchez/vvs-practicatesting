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

public class EmisoraTest {
	
	private ImplementacionEmisora emisora1;
	private ImplementacionEmisora emisora2;
	
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
		
		emisora1.agregar(cancion1, null);
		emisora1.agregar(cancion2, cancion1);
		emisora2.agregar(cancion3, null);
	
		assertTrue(lista.equals(emisora1.obtenerListaReproduccion()));
		
		lista.clear();
		
		assertFalse(lista.equals(emisora1.obtenerListaReproduccion()));
		
	}

	@Test
	public void obtenerDuracionTest() {
		
		
		emisora1.agregar(cancion1, null);
		emisora1.agregar(cancion2, cancion1);
		emisora2.agregar(cancion3, null);
		
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
		
		emisora1.agregar(cancion1, null);
		emisora1.agregar(cancion2, cancion1);

		assertTrue(lista.equals(emisora1.obtenerListaReproduccion()));
	}
	
	@Test
	public void buscarTest() {
		List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion3);
		
		emisora1.agregar(cancion1, null);
		emisora1.agregar(cancion2, cancion1);
		emisora2.agregar(cancion3, null);

		assertTrue(lista.equals(emisora2.buscar("cancion3")));
		assertTrue(lista.equals(emisora2.buscar("can")));
		assertFalse(lista.equals(emisora2.buscar("cancion1")));
	}
	
	@Test
	public void eliminar() {
		List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion2);
		
		emisora1.agregar(cancion1, null);
		emisora1.agregar(cancion2, cancion1);

		assertEquals(emisora1.obtenerDuracion(),9);
		
		emisora1.eliminar(cancion1);
		
		assertEquals(emisora1.obtenerDuracion(),cancion2.obtenerDuracion());

		assertTrue(lista.equals(emisora1.obtenerListaReproduccion()));

	}
	
}
