package es.udc.vvs.test.anuncio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.udc.vvs.model.contenido.Contenido;
import es.udc.vvs.model.contenido.anuncioimpl.ImplementacionAnuncio;
import es.udc.vvs.model.contenido.cancionimpl.ImplementacionCancion;
import es.udc.vvs.model.contenido.emisoraimpl.ImplementacionEmisora;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.iterable.Iterables;

public class AnuncioTest {

	private ImplementacionAnuncio anuncio1, anuncio2, anuncio3, anuncio4;
	
	@Before
	public void setUp() {
		anuncio1 = new ImplementacionAnuncio("PUBLICIDAD",5);
		anuncio2 = new ImplementacionAnuncio("PUBLICIDAD",5);
		anuncio3 = new ImplementacionAnuncio("Error", 3);
	}
	
	@Test
	public void obtenerTituloTest() {
		
		
		for (String anyString : Iterables.toIterable(PrimitiveGenerators.printableStrings())) {
			anuncio4 = new ImplementacionAnuncio(anyString,5);
			assertFalse(anyString.equals(anuncio4.obtenerTitulo()));
		}
		assertTrue(anuncio1.obtenerTitulo().equals("PUBLICIDAD"));
		/*assertFalse(anuncio2.obtenerTitulo().equals("PUBLI"));
		assertTrue(anuncio2.obtenerTitulo().equals("PUBLICIDAD"));*/
		
		/*demuestro que no se puede poner error, que el titulo siempre es publicidad*/
		assertFalse(anuncio3.obtenerTitulo().equals("Error"));
		assertTrue(anuncio3.obtenerTitulo().equals("PUBLICIDAD"));
	}
	
	@Test
	public void obtenerDuracionTest() {
		
		for (int anyDuracion : Iterables.toIterable(PrimitiveGenerators.integers())) {
			anuncio4 = new ImplementacionAnuncio("PUBLICIDAD",anyDuracion);
			assertNotEquals(anyDuracion,anuncio4.obtenerDuracion());
			
		}
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
		Generator<ImplementacionAnuncio> aGen = new AnuncioGenerator();
		List<Contenido> lista = new ArrayList<Contenido>();
		
		for (ImplementacionAnuncio anyAnuncio : Iterables.toIterable(aGen)) {
			lista.add(anyAnuncio);
			ImplementacionAnuncio otherAnuncio = aGen.next();
			// COMO HACER QUE LA LISTA SEA IGUAL?
			assertEquals(anyAnuncio,anyAnuncio.buscar(anyAnuncio.obtenerTitulo()).get(0));
			assertNotEquals(lista,otherAnuncio.buscar("sdfkj"));
		}
		
	}
	
	/*no hace falta hacer test de los metodos que no tienen efecto*/
	
	class AnuncioGenerator implements Generator<ImplementacionAnuncio> {
		Generator<Integer> iGen = PrimitiveGenerators.integers(-50,50);
		Generator<String> sGen = PrimitiveGenerators.printableStrings();
		
		public ImplementacionAnuncio next() {
			String anyTitulo = sGen.next();
			int anyDuracion = iGen.next();
			
			return new ImplementacionAnuncio(anyTitulo,anyDuracion);
		}
		
	}

}
