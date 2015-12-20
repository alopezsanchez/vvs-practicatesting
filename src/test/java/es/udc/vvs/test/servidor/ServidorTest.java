package es.udc.vvs.test.servidor;

import static es.udc.vvs.model.util.servidorutil.ModelConstants.MASTER_TOKEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import es.udc.vvs.model.contenido.Contenido;
import es.udc.vvs.model.contenido.anuncioimpl.ImplementacionAnuncio;
import es.udc.vvs.model.contenido.cancionimpl.ImplementacionCancion;
import es.udc.vvs.model.contenido.emisoraimpl.ImplementacionEmisora;
import es.udc.vvs.model.servidor.servidorimpl.ImplementacionServidor;
import es.udc.vvs.model.util.exceptions.TokenInvalidoException;
import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmMonitor;
import etm.core.renderer.SimpleTextRenderer;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.iterable.Iterables;

// TODO: Auto-generated Javadoc
/**
 * The Class ServidorTest.
 */
public class ServidorTest {
	
	private static EtmMonitor monitor;
	
	/** The servidor. */
	private ImplementacionServidor servidor;
	
	/** The token. */
	private String token;
	
	
	@BeforeClass
	public static void setUpMonitor() {
		BasicEtmConfigurator.configure();
	    monitor = EtmManager.getEtmMonitor();
	    monitor.start();
	}
	
	/**
	 * Sets the up.
	 */
	@Before
	public void setUp() {
		servidor = new ImplementacionServidor("servidor");
		token = servidor.alta();
		
	}
	
	 @AfterClass
	 public static void tearDown() {
		 monitor.render(new SimpleTextRenderer());
	 }
	 
	
	/**
	 * Test para obtener nombre de servidor.
	 */
	@Test
	public void obtenerNombreTest() 
	{
		for (String anyString : Iterables.toIterable(PrimitiveGenerators.printableStrings())) {
			servidor = new ImplementacionServidor(anyString);
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
	 * Test baja para varios usuarios.
	 */
	@Test
	public void bajaUsuarios() throws TokenInvalidoException{
		
		String token2 = servidor.alta();
		String token3 = servidor.alta();
		
		servidor.baja(token3);	
		servidor.baja(token);
		servidor.baja(token2);
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
	 * Test agregar Contenido con token mulo.
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
	public void eliminarTest() {
		
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
			
			boolean borrado = true;
			try {
				servidor.eliminar(anyCancion, MASTER_TOKEN);
			} catch (TokenInvalidoException e) {
				borrado = false;
			}
			assertTrue(borrado);
			resultado = servidor.buscar(anyCancion.obtenerTitulo(), token);
			assertEquals(0,resultado.size());
		}
		
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
			servidor.agregar(anuncio1, MASTER_TOKEN);
			servidor.agregar(cancion1, MASTER_TOKEN);
			servidor.agregar(cancion2, MASTER_TOKEN);
			servidor.agregar(cancion3, MASTER_TOKEN);
			servidor.agregar(cancion4, MASTER_TOKEN);
			servidor.agregar(cancion5, MASTER_TOKEN);
			servidor.agregar(anuncio2, MASTER_TOKEN);
			servidor.agregar(cancion6, MASTER_TOKEN);
			servidor.agregar(cancion7, MASTER_TOKEN);
			servidor.agregar(anuncio3, MASTER_TOKEN);
			servidor.agregar(anuncio4, MASTER_TOKEN);
			servidor.agregar(cancion8, MASTER_TOKEN);
			servidor.agregar(emisora, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			agregado = false;
		}
		assertTrue(agregado);
		
		// Primera búsqueda
		List<Contenido> resultado = servidor.buscar("ccccc", token);
		assertEquals(1,resultado.size());
		assertEquals(cancion3.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		assertEquals(cancion3.obtenerDuracion(),resultado.get(0).obtenerDuracion());
		
		// Segunda búsqueda
		resultado = servidor.buscar("PUbli", token);
		assertEquals(4,resultado.size());
		assertEquals(anuncio1.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		assertEquals(anuncio1.obtenerDuracion(),resultado.get(0).obtenerDuracion());
		assertEquals(anuncio2.obtenerTitulo(),resultado.get(1).obtenerTitulo());
		assertEquals(anuncio2.obtenerDuracion(),resultado.get(1).obtenerDuracion());
		assertEquals(anuncio3.obtenerTitulo(),resultado.get(2).obtenerTitulo());
		assertEquals(anuncio3.obtenerDuracion(),resultado.get(2).obtenerDuracion());
		assertEquals(anuncio4.obtenerTitulo(),resultado.get(3).obtenerTitulo());
		assertEquals(anuncio4.obtenerDuracion(),resultado.get(3).obtenerDuracion());
	
		// Tercera búsqueda
		resultado = servidor.buscar("emis", token);
		assertEquals(1,resultado.size());
		assertEquals(emisora.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		assertEquals(emisora.obtenerDuracion(),resultado.get(0).obtenerDuracion());
		
		// Completamos las búsquedas hasta que caduque la sesión (faltan 7)
		for(int i=0;i<8;i++)
			resultado = servidor.buscar("emis", token);
		
		// La siguiente búsqueda con sesión caducada devuelve un anuncio por cada 3 contenidos buscados
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
		
		// Comparamos los atributos de cada item
		assertEquals(anuncio1.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		assertEquals(anuncio1.obtenerDuracion(),resultado.get(0).obtenerDuracion());
		assertEquals(cancion1.obtenerTitulo(),resultado.get(1).obtenerTitulo());
		assertEquals(cancion1.obtenerDuracion(),resultado.get(1).obtenerDuracion());
		assertEquals(cancion4.obtenerTitulo(),resultado.get(2).obtenerTitulo());
		assertEquals(cancion4.obtenerDuracion(),resultado.get(2).obtenerDuracion());
		assertEquals(cancion5.obtenerTitulo(),resultado.get(3).obtenerTitulo());
		assertEquals(cancion5.obtenerDuracion(),resultado.get(3).obtenerDuracion());
		assertEquals(anuncio2.obtenerTitulo(),resultado.get(4).obtenerTitulo());
		assertEquals(anuncio2.obtenerDuracion(),resultado.get(4).obtenerDuracion());
		assertEquals(cancion6.obtenerTitulo(),resultado.get(5).obtenerTitulo());
		assertEquals(cancion6.obtenerDuracion(),resultado.get(5).obtenerDuracion());
		assertEquals(cancion7.obtenerTitulo(),resultado.get(6).obtenerTitulo());
		assertEquals(cancion7.obtenerDuracion(),resultado.get(6).obtenerDuracion());
		assertEquals(cancion8.obtenerTitulo(),resultado.get(7).obtenerTitulo());
		assertEquals(cancion8.obtenerDuracion(),resultado.get(7).obtenerDuracion());
		assertEquals(anuncio3.obtenerTitulo(),resultado.get(8).obtenerTitulo());
		assertEquals(anuncio3.obtenerDuracion(),resultado.get(8).obtenerDuracion());
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
