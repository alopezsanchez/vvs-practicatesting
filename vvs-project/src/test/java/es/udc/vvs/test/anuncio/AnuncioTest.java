package es.udc.vvs.test.anuncio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.udc.vvs.model.contenido.Contenido;
import es.udc.vvs.model.contenido.anuncioimpl.ImplementacionAnuncio;

public class AnuncioTest {

	private ImplementacionAnuncio anuncio1, anuncio2, anuncio3;
	
	@Before
	public void setUp() {
		anuncio1 = new ImplementacionAnuncio("PUBLICIDAD",5);
		anuncio2 = new ImplementacionAnuncio("PUBLICIDAD",5);
		anuncio3 = new ImplementacionAnuncio("Error", 3);
	}
	
	@Test
	public void obtenerTituloTest() {
		assertTrue(anuncio1.obtenerTitulo().equals("PUBLICIDAD"));
		assertFalse(anuncio2.obtenerTitulo().equals("PUBLI"));
		assertTrue(anuncio2.obtenerTitulo().equals("PUBLICIDAD"));
		
		/*demuestro que no se puede poner error, que el titulo siempre es publicidad*/
		assertFalse(anuncio3.obtenerTitulo().equals("Error"));
		assertTrue(anuncio3.obtenerTitulo().equals("PUBLICIDAD"));
	}
	
	@Test
	public void obtenerDuracionTest() {
		assertEquals(anuncio1.obtenerDuracion(),5); /*compruebo que efectivamente es 5 segundos*/
		assertEquals(anuncio2.obtenerDuracion(),5);
		
		/*demuestro que aunque cambie la duracion no puede ser 3*/
		assertFalse(anuncio3.obtenerDuracion()==3);
	}
	
	@Test
	public void obtenerListaReproduccionTest() {
		List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(anuncio1);
		List<Contenido> lista2 = new ArrayList<Contenido>();
		lista2.add(anuncio2);
		
		/*creo una lista y compruebo que el anuncio existe*/
		assertTrue(lista.equals(anuncio1.obtenerListaReproduccion()));
		assertTrue(lista2.equals(anuncio2.obtenerListaReproduccion()));
	}
	
	@Test
	public void buscarTest() {
		List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(anuncio1);
		
		assertTrue(lista.equals(anuncio1.buscar("PUBLICIDAD")));
		assertFalse(lista.equals(anuncio1.buscar("publicidad")));
		assertFalse(lista.equals(anuncio3.buscar("Error")));
		
	}
	
	/*no hace falta hacer test de los metodos que no tienen efecto*/

}
