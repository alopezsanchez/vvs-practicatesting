package es.udc.vvs.test.servidor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.udc.vvs.model.contenido.Contenido;
import es.udc.vvs.model.contenido.anuncioimpl.ImplementacionAnuncio;
import es.udc.vvs.model.contenido.cancionimpl.ImplementacionCancion;
import es.udc.vvs.model.contenido.emisoraimpl.ImplementacionEmisora;
import es.udc.vvs.model.servidor.servidorimpl.ImplementacionServidor;
import es.udc.vvs.model.servidor.servidorimpl.ImplementacionServidorRespaldo;
import es.udc.vvs.model.util.exceptions.TokenInvalidoException;
import es.udc.vvs.test.servidor.ServidorTest.CancionGenerator;
import es.udc.vvs.test.servidor.ServidorTest.UsuarioGenerator;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.iterable.Iterables;

import static es.udc.vvs.model.util.servidorutil.ModelConstants.*;


public class ServidorRespaldoTest {
	
	/** The servidor. */
	private ImplementacionServidor servidorRespaldo;
	
	private ImplementacionServidorRespaldo servidor;
	
	/** The token. */
	private String token;
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		servidorRespaldo = new ImplementacionServidor("servidor");
		String tk = servidorRespaldo.alta();
		ImplementacionAnuncio anuncio1 = new ImplementacionAnuncio("PUBLICIDAD",5);
		ImplementacionCancion cancion1 = new ImplementacionCancion("Cancion1",7);
		ImplementacionCancion cancion2 = new ImplementacionCancion("sdfsfsfd",3);
		ImplementacionCancion cancion3 = new ImplementacionCancion("cccccccc",75);
		ImplementacionCancion cancion4 = new ImplementacionCancion("Cancion23",56);
		ImplementacionCancion cancion5 = new ImplementacionCancion("Cancion3",37);
		ImplementacionAnuncio anuncio2 = new ImplementacionAnuncio("PUBLICIDAD",8);
		ImplementacionCancion cancion6 = new ImplementacionCancion("Cancion44",31);
		ImplementacionCancion cancion7 = new ImplementacionCancion("Cancion5",34);
		ImplementacionAnuncio anuncio3 = new ImplementacionAnuncio("PUBLICIDAD",12);
		ImplementacionAnuncio anuncio4 = new ImplementacionAnuncio("PUBLICIDAD",23);
		ImplementacionCancion cancion8 = new ImplementacionCancion("Cancion9",4);
		ImplementacionEmisora emisora = new ImplementacionEmisora("Emisora1",55);
		
		try {
			servidorRespaldo.agregar(anuncio1, MASTER_TOKEN);
			servidorRespaldo.agregar(cancion1, MASTER_TOKEN);
			servidorRespaldo.agregar(cancion2, MASTER_TOKEN);
			servidorRespaldo.agregar(cancion3, MASTER_TOKEN);
			servidorRespaldo.agregar(cancion4, MASTER_TOKEN);
			servidorRespaldo.agregar(cancion5, MASTER_TOKEN);
			servidorRespaldo.agregar(anuncio2, MASTER_TOKEN);
			servidorRespaldo.agregar(cancion6, MASTER_TOKEN);
			servidorRespaldo.agregar(cancion7, MASTER_TOKEN);
			servidorRespaldo.agregar(anuncio3, MASTER_TOKEN);
			servidorRespaldo.agregar(anuncio4, MASTER_TOKEN);
			servidorRespaldo.agregar(cancion8, MASTER_TOKEN);
			servidorRespaldo.agregar(emisora, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			e.printStackTrace();
		}
		try {
			servidorRespaldo.baja(tk);
		} catch (TokenInvalidoException e) {
			e.printStackTrace();
		}
		servidor = new ImplementacionServidorRespaldo(servidorRespaldo,"servidorConRespaldo");
		token = servidor.alta();
	}
	
	/**
	 * Test para obtener nombre de servidor.
	 */
	@Test
	public void obtenerNombreTest() 
	{
		for (String anyString : Iterables.toIterable(PrimitiveGenerators.printableStrings())) {
			servidor = new ImplementacionServidorRespaldo(servidorRespaldo,anyString);
			assertEquals(anyString,servidor.obtenerNombre());
		}
		
	}
	
	/**
	 * Test alta usuario.
	 */
	@Test
	public void altaUsuario(){
		for(String anyToken : Iterables.toIterable(new UsuarioGenerator())) {
			assertTrue(anyToken != null);
		}
	}
	

	/**
	 * Test baja usuario.
	 */
	@Test
	public void bajaUsuario() throws TokenInvalidoException{
		for(String anyToken : Iterables.toIterable(new UsuarioGenerator())) {
			servidor.baja(anyToken);
			assertFalse(servidor.existeUsuario(anyToken));
		}

	}
	
	/**
	 * Test baja usuario no existente.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void bajaUsuarioNoExiste() throws TokenInvalidoException {
		String tk ="notexist";
		servidor.baja(tk);
	}
	
	/**
	 * Test agregar Contenido.
	 */
	@Test
	public void agregarTest() {
		for (ImplementacionCancion anyCancion : Iterables.toIterable(new CancionGenerator())) {
			boolean agregado = true;
			try {
				servidor.agregar(anyCancion, MASTER_TOKEN);
			} catch (TokenInvalidoException e) {
				agregado = false;
			}
			assertTrue(agregado);
			
			List<Contenido> resultado = servidor.buscar(anyCancion.obtenerTitulo(), token);
			int i = resultado.size();
			//Comprueba si el contenido añadido es igual al ultimo elemento de la lista
			assertEquals(anyCancion,resultado.get(i-1));
		}
	}
	
	
	/**
	 * Test agregar Contenido con token inválido.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void agregarTokenInvalidoTest() throws TokenInvalidoException 
	{
		for (ImplementacionCancion anyCancion : Iterables.toIterable(new CancionGenerator())) {
			servidor.agregar(anyCancion, token);
		}
	}

	
	/**
	 * Test agregar Contenido con token nulo.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void agregarTokenNuloTest() throws TokenInvalidoException 
	{
		for (ImplementacionCancion anyCancion : Iterables.toIterable(new CancionGenerator())) {
			servidor.agregar(anyCancion, null);
		}
	}
	
	
	/**
	 * Test eliminar Contenido.
	 */
	@Test
	public void eliminarTest() 
	{
		boolean agregado = true;
		
		ImplementacionCancion cancion1 = new ImplementacionCancion("cancion1",5);
		ImplementacionCancion cancion2 = new ImplementacionCancion("Cancion2",7);

		try {
			servidor.agregar(cancion1, MASTER_TOKEN);
			servidor.agregar(cancion2, MASTER_TOKEN);
		} catch (TokenInvalidoException e1) {
			agregado = false;
		}
		assertTrue(agregado);
		
		List<Contenido> resultado = servidor.buscar("canci", token);
		assertEquals(2,resultado.size());
		
		boolean borrado = true;
		try {
			servidor.eliminar(cancion1, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			borrado = false;
		}
		assertTrue(borrado);
		
		resultado = servidor.buscar("canc", token);
		assertEquals(1,resultado.size());
	}
	
	
	/**
	 * Test eliminar Contenido con token inválido.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void eliminarTokenInvalidoTest() throws TokenInvalidoException {
		
		for (ImplementacionCancion anyCancion : Iterables.toIterable(new CancionGenerator())) {
			boolean agregado = true;	
			try {
				servidor.agregar(anyCancion, MASTER_TOKEN);
			} catch (TokenInvalidoException e) {
				agregado = false;
			}
			
			assertTrue(agregado);
			
			List<Contenido> resultado = servidor.buscar(anyCancion.obtenerTitulo(), token);
			assertEquals(1,resultado.size());
			
			servidor.eliminar(anyCancion, token);
		}
	}
	
	
	/**
	 * Test eliminar Contenido con token inválido.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void eliminarTokenNuloTest() throws TokenInvalidoException {
		
		for (ImplementacionCancion anyCancion : Iterables.toIterable(new CancionGenerator())) {
			boolean agregado = true;
						
			try {
				servidor.agregar(anyCancion, MASTER_TOKEN);
			} catch (TokenInvalidoException e) {
				agregado = false;
			}
			
			assertTrue(agregado);
			
			List<Contenido> resultado = servidor.buscar(anyCancion.obtenerTitulo(), token);
			assertEquals(1,resultado.size());
			
			servidor.eliminar(anyCancion, null);
		}	
	}
	
	
	/**
	 * Test para buscar contenidos.
	 */
	@Test
	public void buscarTest() {
		
		boolean agregado = true;
		
		ImplementacionCancion cancion1 = new ImplementacionCancion("yyyyyyyy",7);
		ImplementacionCancion cancion2 = new ImplementacionCancion("sdfsfsfd",3);
		ImplementacionCancion cancion3 = new ImplementacionCancion("dfsdfsdf",75);
		ImplementacionCancion cancion4 = new ImplementacionCancion("aaaaaaa",56);
		ImplementacionCancion cancion5 = new ImplementacionCancion("hhhhrrr",12);
		ImplementacionCancion cancion6 = new ImplementacionCancion("ggggggg",67);
		ImplementacionCancion cancion7 = new ImplementacionCancion("hhhhhhf",3);
		ImplementacionCancion cancion8 = new ImplementacionCancion("hhhhhh",6);
		
		try {
			servidor.agregar(cancion1, MASTER_TOKEN);
			servidor.agregar(cancion2, MASTER_TOKEN);
			servidor.agregar(cancion3, MASTER_TOKEN);
			servidor.agregar(cancion4, MASTER_TOKEN);
			servidor.agregar(cancion5, MASTER_TOKEN);
			servidor.agregar(cancion6, MASTER_TOKEN);
			servidor.agregar(cancion7, MASTER_TOKEN);
			servidor.agregar(cancion8, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			agregado = false;
		}
		assertTrue(agregado);
		
		// Primera búsqueda
		List<Contenido> resultado = servidor.buscar("aaaaa", token);
		assertEquals(1,resultado.size());
		assertEquals(cancion4.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		assertEquals(cancion4.obtenerDuracion(),resultado.get(0).obtenerDuracion());
		
		// Segunda búsqueda
		resultado = servidor.buscar("hhhh", token);
		assertEquals(3,resultado.size());
		assertEquals(cancion5.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		assertEquals(cancion5.obtenerDuracion(),resultado.get(0).obtenerDuracion());
		assertEquals(cancion7.obtenerTitulo(),resultado.get(1).obtenerTitulo());
		assertEquals(cancion7.obtenerDuracion(),resultado.get(1).obtenerDuracion());
		assertEquals(cancion8.obtenerTitulo(),resultado.get(2).obtenerTitulo());
		assertEquals(cancion8.obtenerDuracion(),resultado.get(2).obtenerDuracion());

		
		// Realizamos una búsqueda de un contenido que no está en el servidor principal
		// pero si en el de respaldo.
		resultado = servidor.buscar("cancio", token);
		assertEquals(9,resultado.size());
		
		// Verificamos que la estructura de anuncios y contenidos es correcta
		assertEquals(ImplementacionAnuncio.class,resultado.get(0).getClass());
		
		assertEquals(ImplementacionCancion.class,resultado.get(1).getClass());
		assertEquals(ImplementacionCancion.class,resultado.get(2).getClass());
		assertEquals(ImplementacionCancion.class,resultado.get(3).getClass());
		
		assertEquals(ImplementacionAnuncio.class,resultado.get(4).getClass());
		
		assertEquals(ImplementacionCancion.class,resultado.get(5).getClass());
		assertEquals(ImplementacionCancion.class,resultado.get(6).getClass());
		assertEquals(ImplementacionCancion.class,resultado.get(7).getClass());
		
		assertEquals(ImplementacionAnuncio.class,resultado.get(8).getClass());
	}

	
	class UsuarioGenerator implements Generator<String> {
		Generator<String> sGen = PrimitiveGenerators.printableStrings();
		
		public String next() {
			String anyToken = servidor.alta();
			
			return anyToken;
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
