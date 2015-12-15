package es.udc.vvs.test.emisora;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.udc.vvs.model.contenido.Contenido;
import es.udc.vvs.model.contenido.cancionimpl.ImplementacionCancion;
import es.udc.vvs.model.contenido.emisoraimpl.ImplementacionEmisora;
/**
* 
*
* @author Grupo 8
*/
public class EmisoraTest {	
	
	private ImplementacionEmisora emisora1;// NOPMD by adrian on 15/12/15 19:46
	private ImplementacionEmisora emisora2;// NOPMD by adrian on 15/12/15 19:46
	private ImplementacionCancion cancion1;// NOPMD by adrian on 15/12/15 19:46
	private ImplementacionCancion cancion2;// NOPMD by adrian on 15/12/15 19:46
	private ImplementacionCancion cancion3;// NOPMD by adrian on 15/12/15 19:46
	
	/**
	 * SetUp de las pruebas de Emisora
	 */
	@Before
	public void setUp() {
		
		emisora1 = new ImplementacionEmisora("emisora1",0);
		emisora2 = new ImplementacionEmisora("emisora2",0);
		
		cancion1 = new ImplementacionCancion("cancion1",4);
		cancion2 = new ImplementacionCancion("cancion2",5);
		cancion3 = new ImplementacionCancion("cancion3",6);

	}
	
	/**
	 * Test del metodo obtenerTitulo()
	 */
	@Test
	public void obtenerTituloTestTrue() {
		
		assertTrue(emisora1.obtenerTitulo().equals("emisora1"));
	}
	
	/**
	 * Test del metodo obtenerTitulo()
	 */
	@Test
	public void obtenerTituloTestFalse() {
		
		assertFalse(emisora1.obtenerTitulo().equals("emisora2"));
	}
	
	/**
	 * Test del metodo agregar() para agregar un contenido a una emisora
	 */
	@Test
	public void agregarTest(){

		final List<Contenido> lista = new ArrayList<Contenido>();
		
		lista.add(cancion1);
		lista.add(cancion2);
		
		emisora1.agregar(cancion1, null);
		emisora1.agregar(cancion2, cancion1);
		emisora2.agregar(cancion3, null);
	
		//assertTrue(lista.equals(emisora1.obtenerListaReproduccion()));
		assertEquals(lista,emisora1.obtenerListaReproduccion());
		
		lista.clear();
		
		//assertFalse(lista.equals(emisora1.obtenerListaReproduccion()));
		
	}

	/**
	 * Test del metodo obtenerDuracion() para obtener la duración de una emisora.
	 */
	@Test
	public void obtenerDuracionTest() {
		
		
		emisora1.agregar(cancion1, null);
		emisora1.agregar(cancion2, cancion1);
		emisora2.agregar(cancion3, null);
		
		assertEquals(emisora1.obtenerDuracion(),9);
		//assertEquals(emisora2.obtenerDuracion(),6);
		//assertNotEquals(emisora1.obtenerDuracion(),0);
		//assertNotEquals(emisora2.obtenerDuracion(),0);
	}
	
	/**
	 * Test del metodo obtenerListaReproduccion() para obtener una l. de reproduccion.
	 */
	@Test
	public void obtenerListaReproduccionTest() {
		final List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion1);
		lista.add(cancion2);
		
		emisora1.agregar(cancion1, null);
		emisora1.agregar(cancion2, cancion1);

		//assertTrue(lista.equals(emisora1.obtenerListaReproduccion()));
		assertEquals(lista, emisora1.obtenerListaReproduccion());
	}
	
	/**
	 * Test del metodo buscar() que de un resultado positivo
	 */
	@Test
	public void buscarTestTrue() {
		final List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion3);

		emisora2.agregar(cancion3, null);
		
		assertFalse(lista.equals(emisora2.buscar("cancion1")));
	}
	
	/**
	 * Test del metodo buscar() que de un resultado negativo
	 */
	
	@Test
	public void buscarTestFalse() {
		final List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion3);
		
		emisora2.agregar(cancion3, null);
		
		assertFalse(lista.equals(emisora2.buscar("cancion1")));
	}
	/**
	 * Test del metodo eliminar() para eliminar un contenido de una emisora
	 */
	@Test
	public void eliminar() {
		final List<Contenido> lista = new ArrayList<Contenido>();
		lista.add(cancion2);
		
		emisora1.agregar(cancion1, null);
		emisora1.agregar(cancion2, cancion1);

		//assertEquals(emisora1.obtenerDuracion(),9);
		
		emisora1.eliminar(cancion1);
		
		assertEquals(emisora1.obtenerDuracion(),cancion2.obtenerDuracion());

		//assertTrue(lista.equals(emisora1.obtenerListaReproduccion()));
		
		//assertEquals(lista, emisora1.obtenerListaReproduccion());

	}
	
}
