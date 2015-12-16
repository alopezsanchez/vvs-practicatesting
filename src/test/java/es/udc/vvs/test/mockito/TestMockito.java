package es.udc.vvs.test.mockito;

import static es.udc.vvs.model.util.servidorutil.ModelConstants.MASTER_TOKEN;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.udc.vvs.model.contenido.Contenido;
import es.udc.vvs.model.contenido.anuncioimpl.ImplementacionAnuncio;
import es.udc.vvs.model.contenido.cancionimpl.ImplementacionCancion;
import es.udc.vvs.model.contenido.emisoraimpl.ImplementacionEmisora;
import es.udc.vvs.model.servidor.Servidor;
import es.udc.vvs.model.servidor.servidorimpl.ImplementacionServidor;
import es.udc.vvs.model.servidor.servidorimpl.ImplementacionServidorRespaldo;
import es.udc.vvs.model.util.exceptions.TokenInvalidoException;

// TODO: Auto-generated Javadoc
/**
 * Clase para pruebas dinámicas con Mockito
 */
public class TestMockito {
	
	/** El servidor. */
	private Servidor servidor;
	
	/** El servidor de respaldo. */
	private Servidor servidorRespaldo;
	
	private String token;
	private String tokenR;
	
	
	//DECLARACION DE MOCKS PARA LAS PRUEBAS
	/** Anuncio 1. */
	private Contenido anuncio = mock(ImplementacionAnuncio.class);
	
	/** Anuncio 2. */
	private Contenido anuncio2 = mock(ImplementacionAnuncio.class);
	
	/** Anuncio 3. */
	private Contenido anuncio3 = mock(ImplementacionAnuncio.class);
	
	/** Cancion. */
	private Contenido cancion = mock(ImplementacionCancion.class);
	
	/** Cancion 2. */
	private Contenido cancion2 = mock(ImplementacionCancion.class);
	
	/** Cancion 3. */
	private Contenido cancion3 = mock(ImplementacionCancion.class);
	
	/** Cancion 4. */
	private Contenido cancion4 = mock(ImplementacionCancion.class);
	
	/** Emisora. */
	private Contenido emisora = mock(ImplementacionEmisora.class);


	
	/**
	 * Metodo para declaracion de stubs e inicializacion de servidores
	 */
	@Before
	public void init() {
		servidor = new ImplementacionServidor("servidorPrueba");
		
		token = servidor.alta();
		
		// AGREGAMOS CONTENIDOS AL SERVIDOR
		boolean agregado = true;
		try {
			servidor.agregar(anuncio, MASTER_TOKEN);
			servidor.agregar(cancion, MASTER_TOKEN);
			servidor.agregar(emisora, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			agregado = false;
		}
		assertTrue(agregado);
		
		servidorRespaldo = new ImplementacionServidorRespaldo(servidor,"servidorRespaldoPrueba");
		tokenR = servidorRespaldo.alta();
		
		//STUBS PARA LA FUNCION obtenerTitulo()
		when(anuncio.obtenerTitulo()).thenReturn("PUBLICIDAD anuncio");
		when(anuncio2.obtenerTitulo()).thenReturn("PUBLICIDAD anuncio2");
		when(anuncio3.obtenerTitulo()).thenReturn("PUBLICIDAD anuncio3");
		
		when(cancion.obtenerTitulo()).thenReturn("cancion");
		when(cancion2.obtenerTitulo()).thenReturn("cancion2");
		when(cancion3.obtenerTitulo()).thenReturn("cancion3");
		when(cancion4.obtenerTitulo()).thenReturn("cancion4");
		
		when(emisora.obtenerTitulo()).thenReturn("emisora");
		
		//STUBS PARA LA FUNCION obtenerDuracion()
		when(anuncio.obtenerDuracion()).thenReturn(1);
		when(anuncio2.obtenerDuracion()).thenReturn(2);
		when(anuncio3.obtenerDuracion()).thenReturn(3);
				
		when(cancion.obtenerDuracion()).thenReturn(5);
		when(cancion2.obtenerDuracion()).thenReturn(6);
		when(cancion3.obtenerDuracion()).thenReturn(7);
		when(cancion4.obtenerDuracion()).thenReturn(8);
				
		when(emisora.obtenerDuracion()).thenReturn(20);
		
		//STUB PARA LA FUNCION buscar(subcadena)
		List<Contenido> anuncios = new ArrayList<Contenido>();
		anuncios.add(anuncio);
		when(anuncio.buscar("PUBLICIDAD")).thenReturn(anuncios);
		
		List<Contenido> anuncios2 = new ArrayList<Contenido>();
		anuncios2.add(anuncio2);
		when(anuncio2.buscar("PUBLICIDAD")).thenReturn(anuncios2);
		
		List<Contenido> anuncios3 = new ArrayList<Contenido>();
		anuncios3.add(anuncio3);
		when(anuncio3.buscar("PUBLICIDAD")).thenReturn(anuncios3);
		
		List<Contenido> anunciosE = new ArrayList<Contenido>();
		anunciosE.add(anuncio2);
		anunciosE.add(anuncio3);
		when(emisora.buscar("PUBLICIDAD")).thenReturn(anunciosE);
	}
	
	
	/**
	 * Test para probar los mocks.
	 */
	@Test
	public void testMocks()
	{
		assertEquals(anuncio.obtenerTitulo(),"PUBLICIDAD anuncio");
		assertEquals(anuncio2.obtenerTitulo(),"PUBLICIDAD anuncio2");
		assertEquals(anuncio3.obtenerTitulo(),"PUBLICIDAD anuncio3");
		
		assertEquals(cancion.obtenerTitulo(),"cancion");
		assertEquals(cancion2.obtenerTitulo(),"cancion2");
		assertEquals(cancion3.obtenerTitulo(),"cancion3");
		assertEquals(cancion4.obtenerTitulo(),"cancion4");
		
		assertEquals(emisora.obtenerTitulo(),"emisora");
		
		assertEquals(anuncio.obtenerDuracion(),1);
		assertEquals(anuncio2.obtenerDuracion(),2);
		assertEquals(anuncio3.obtenerDuracion(),3);
		
		assertEquals(cancion.obtenerDuracion(),5);
		assertEquals(cancion2.obtenerDuracion(),6);
		assertEquals(cancion3.obtenerDuracion(),7);
		assertEquals(cancion4.obtenerDuracion(),8);
		
		assertEquals(emisora.obtenerDuracion(),20);
		
		assertEquals(anuncio.buscar("PUBLICIDAD").size(),1);
		assertEquals(anuncio.obtenerTitulo(),anuncio.buscar("PUBLICIDAD").get(0).obtenerTitulo());
		assertEquals(anuncio2.buscar("PUBLICIDAD").size(),1);
		assertEquals(anuncio2.obtenerTitulo(),anuncio2.buscar("PUBLICIDAD").get(0).obtenerTitulo());
		assertEquals(anuncio3.buscar("PUBLICIDAD").size(),1);
		assertEquals(anuncio3.obtenerTitulo(),anuncio3.buscar("PUBLICIDAD").get(0).obtenerTitulo());
		assertEquals(emisora.buscar("PUBLICIDAD").size(),2);
		assertEquals(anuncio2.obtenerTitulo(),emisora.buscar("PUBLICIDAD").get(0).obtenerTitulo());
		assertEquals(anuncio3.obtenerTitulo(),emisora.buscar("PUBLICIDAD").get(1).obtenerTitulo());
	}
	
	
	/**
	 * Test para obtener nombre servidores.
	 */
	@Test
	public void testObtenerNombreServidor()
	{
		servidor.obtenerNombre();
		assertEquals("servidorPrueba",servidor.obtenerNombre());
		
		servidorRespaldo.obtenerNombre();
		assertEquals("servidorRespaldoPrueba",servidorRespaldo.obtenerNombre());
	}
	
	/**
	 * Test para dar de alta en los servidores.
	 */
	@Test
	public void testAltaServidor()
	{
		String token = servidor.alta();
		assertNotNull(token);
		
		token = servidorRespaldo.alta();
		assertNotNull(token);
	}
	
	/**
	 * Test baja usuario.
	 */
	@Test
	public void bajaUsuario()
	{
		boolean baja = true;
		String tk = servidor.alta();
		try {
			servidor.baja(tk);
		} catch (TokenInvalidoException e) {
			baja = false;
		}
		assertTrue(baja);
		try {
			servidor.baja(tk);
		} catch (TokenInvalidoException e) {
			baja = false;
		}
		assertFalse(baja);
		
		baja = true;
		tk = servidorRespaldo.alta();
		try {
			servidorRespaldo.baja(tk);
		} catch (TokenInvalidoException e) {
			baja = false;
		}
		assertTrue(baja);
		try {
			servidorRespaldo.baja(tk);
		} catch (TokenInvalidoException e) {
			baja = false;
		}
		assertFalse(baja);
	}
	
	/**
	 * Test baja usuario no existente.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void bajaUsuarioNoExiste() throws TokenInvalidoException 
	{
		String tk ="notexist";
		servidor.baja(tk);
	}
	
	/**
	 * Test baja usuario no existente en servidor de respaldo.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void bajaUsuarioNoExisteRespaldo() throws TokenInvalidoException 
	{
		String tk ="notexist";
		servidorRespaldo.baja(tk);
	}
	
	/**
	 * Test agregar Contenido.
	 */
	@Test
	public void agregarTest() {
		boolean agregado = true;
		try {
			servidor.agregar(anuncio2, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			agregado = false;
		}
		assertTrue(agregado);
		List<Contenido> resultado = servidor.buscar("PUBLICIDAD", token);
		//Verificamos que anuncio2.obtenerTitulo() se ha invocado 1 vez en servidor.buscar()
		verify(anuncio2,times(1)).obtenerTitulo();
		
		assertEquals(2,resultado.size());
		assertEquals(anuncio.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		assertEquals(anuncio2.obtenerTitulo(),resultado.get(1).obtenerTitulo());
		//Verificamos que anuncio.obtenerTitulo() se ha invocado 2 veces mas
		verify(anuncio,times(3)).obtenerTitulo();
		//Verificamos que anuncio.obtenerTitulo() se ha invocado 2 veces mas
		verify(anuncio2,times(3)).obtenerTitulo();
	}
	
	/**
	 * Test agregar Contenido en servidor con respaldo.
	 */
	@Test
	public void agregarRespaldoTest() {
		boolean agregado = true;
		try {
			servidorRespaldo.agregar(anuncio, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			agregado = false;
		}
		assertTrue(agregado);
		List<Contenido> resultado = servidorRespaldo.buscar("PUBLICIDAD", tokenR);
		//Verificamos que anuncio.obtenerTitulo() se ha invocado 1 vez en servidor.buscar()
		verify(anuncio,times(1)).obtenerTitulo();
		
		assertEquals(1,resultado.size());
		assertEquals(anuncio.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		//Verificamos que anuncio.obtenerTitulo() se ha invocado 2 veces mas
		verify(anuncio,times(3)).obtenerTitulo();
	}
	
	/**
	 * Test agregar Contenido con token invalido.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void agregarTokenInvalidoTest() throws TokenInvalidoException 
	{
		servidor.agregar(new ImplementacionAnuncio("PUBLICIDAD dfsdfsd",5), token);
	}
	
	/**
	 * Test agregar Contenido con token invalido en servidor de respaldo.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void agregarTokenInvalidoRespaldoTest() throws TokenInvalidoException 
	{
		servidorRespaldo.agregar(new ImplementacionAnuncio("PUBLICIDAD dfsdfsd",5), token);
	}
	
	/**
	 * Test agregar Contenido con token nulo.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void agregarTokenNuloTest() throws TokenInvalidoException 
	{
		servidor.agregar(new ImplementacionAnuncio("PUBLICIDAD dfsdfsd",5), null);
	}
	
	/**
	 * Test agregar Contenido con token nulo en servidor de respaldo.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void agregarTokenNuloRespaldoTest() throws TokenInvalidoException 
	{
		servidorRespaldo.agregar(new ImplementacionAnuncio("PUBLICIDAD dfsdfsd",5), null);
	}
	
	/**
	 * Test eliminar Contenido.
	 */
	@Test
	public void eliminarTest() {
		List<Contenido> resultado = servidor.buscar("PUBLICIDAD", token);
		assertEquals(1,resultado.size());
		
		boolean borrado = true;
		try {
			servidor.eliminar(anuncio, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			borrado = false;
		}
		assertTrue(borrado);
		
		resultado = servidor.buscar("PUBLICIDAD", token);
		assertEquals(0,resultado.size());
	}
	
	/**
	 * Test eliminar Contenido del servidor de respaldo.
	 */
	@Test
	public void eliminarRespaldoTest() {
		boolean agregado = true;
		try {
			servidorRespaldo.agregar(anuncio2, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			agregado = false;
		}
		assertTrue(agregado);
		List<Contenido> resultado = servidorRespaldo.buscar("PUBLICIDAD", tokenR);
		assertEquals(1,resultado.size());
		
		boolean borrado = true;
		try {
			servidorRespaldo.eliminar(anuncio2, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			borrado = false;
		}
		assertTrue(borrado);
		
		// Nos devuelve un anuncio del servidor de respaldo
		resultado = servidor.buscar("PUBLICIDAD", token);
		assertEquals(1,resultado.size());
		assertEquals(anuncio.obtenerTitulo(),resultado.get(0).obtenerTitulo());
	}
	
	/**
	 * Test eliminar Contenido con token inválido.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void eliminarTokenInvalidoTest() throws TokenInvalidoException {
		
		List<Contenido> resultado = servidor.buscar("PUBLICIDAD", token);
		assertEquals(1,resultado.size());
		
		servidor.eliminar(anuncio, token);
		
	}
	
	/**
	 * Test eliminar Contenido con token inválido en servidor de respaldo.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void eliminarTokenInvalidoRespaldoTest() throws TokenInvalidoException {
		servidorRespaldo.eliminar(anuncio, token);
	}
	
	/**
	 * Test eliminar Contenido con token nulo.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void eliminarTokenNuloTest() throws TokenInvalidoException {
		
		List<Contenido> resultado = servidor.buscar("PUBLICIDAD", token);
		assertEquals(1,resultado.size());
		
		servidor.eliminar(anuncio, null);
	}
	
	/**
	 * Test eliminar Contenido con token nulo en servidor de respaldo.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void eliminarTokenNuloRespaldoTest() throws TokenInvalidoException {
		servidorRespaldo.eliminar(anuncio, null);
	}
	
	/**
	 * Test para buscar contenidos.
	 */
	@Test
	public void buscarTest() {
		
		// PRIMERA BUSQUEDA TOKEN HABILITADO
		List<Contenido> resultado = servidor.buscar("PUBLI", token);
		assertEquals(1,resultado.size());
		
		assertEquals(anuncio.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		assertEquals(anuncio.obtenerDuracion(),resultado.get(0).obtenerDuracion());
		//Verificamos el numero de veces que anuncio ha invocado obtenerTitulo()
		// 1 vez en buscar y 2 en el equals
		verify(anuncio,times(3)).obtenerTitulo();
		//Verificamos el numero de veces que se ha invocado obtenerDuracion()
		// 2 veces en el equals
		verify(anuncio,times(2)).obtenerDuracion();
		
		//Verificamos el numero de veces que anuncio ha invocado obtenerTitulo()
		// 1 vez en buscar
		verify(cancion,times(1)).obtenerTitulo();
		//Verificamos el numero de veces que anuncio ha invocado obtenerTitulo()
		// 1 vez en buscar
		verify(emisora,times(1)).obtenerTitulo();
		
		
		// SEGUNDA BUSQUEDA TOKEN HABILITADO
		resultado = servidor.buscar("canc", token);
		assertEquals(1,resultado.size());
		
		assertEquals(cancion.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		assertEquals(cancion.obtenerDuracion(),resultado.get(0).obtenerDuracion());
		//Verificamos el numero de veces que cancion ha invocado obtenerTitulo()
		// 1 vez en buscar y 2 en el equals y 1 a mayores de la anterior
		verify(cancion,times(4)).obtenerTitulo();
		//Verificamos el numero de veces que cancion ha invocado obtenerDuracion()
		// 2 veces en el equals
		verify(cancion,times(2)).obtenerDuracion();
		
		//Verificamos el numero de veces que anuncio ha invocado obtenerTitulo()
		// 1 vez en buscar y 3 de la anterior
		verify(anuncio,times(4)).obtenerTitulo();
		//Verificamos el numero de veces que anuncio ha invocado obtenerTitulo()
		// 1 vez en buscar y 1 a mayores de la anterior
		verify(emisora,times(2)).obtenerTitulo();
	
		
		// AGREGAMOS MAS CONTENIDOS AL SERVIDOR
		boolean agregado = true;
		try {
			servidor.agregar(cancion2, MASTER_TOKEN);
			servidor.agregar(cancion3, MASTER_TOKEN);
			servidor.agregar(cancion4, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			agregado = false;
		}
		assertTrue(agregado);
		
		for(int i=0;i<8;i++){
			resultado = servidor.buscar("cancion", token);
			assertEquals(4,resultado.size());
		}
			
		// TERCERA BUSQUEDA TOKEN DESHABILITADO
		resultado = servidor.buscar("canc", token);
		assertEquals(6,resultado.size());
		
		assertEquals(anuncio3.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		assertEquals(anuncio3.obtenerDuracion(),resultado.get(0).obtenerDuracion());
		
		assertEquals(cancion.obtenerTitulo(),resultado.get(1).obtenerTitulo());
		assertEquals(cancion.obtenerDuracion(),resultado.get(1).obtenerDuracion());
		
		assertEquals(cancion2.obtenerTitulo(),resultado.get(2).obtenerTitulo());
		assertEquals(cancion2.obtenerDuracion(),resultado.get(2).obtenerDuracion());
		
		assertEquals(cancion3.obtenerTitulo(),resultado.get(3).obtenerTitulo());
		assertEquals(cancion3.obtenerDuracion(),resultado.get(3).obtenerDuracion());
		
		assertEquals(anuncio2.obtenerTitulo(),resultado.get(4).obtenerTitulo());
		assertEquals(anuncio2.obtenerDuracion(),resultado.get(4).obtenerDuracion());
		
		assertEquals(cancion4.obtenerTitulo(),resultado.get(5).obtenerTitulo());
		assertEquals(cancion4.obtenerDuracion(),resultado.get(5).obtenerDuracion());
		
		//Verificamos el numero de veces que se invocaron los metodos de obtenerTitulo()
		// y obtenerDuracion()
		verify(anuncio3,times(2)).obtenerTitulo();
		verify(anuncio3,times(2)).obtenerDuracion();
		
		verify(cancion,times(15)).obtenerTitulo();
		verify(cancion,times(4)).obtenerDuracion();
		
		verify(cancion2,times(11)).obtenerTitulo();
		verify(cancion2,times(2)).obtenerDuracion();
		
		verify(cancion3,times(11)).obtenerTitulo();
		verify(cancion3,times(2)).obtenerDuracion();
		
		verify(anuncio2,times(2)).obtenerTitulo();
		verify(anuncio2,times(2)).obtenerDuracion();
		
		verify(cancion,times(15)).obtenerTitulo();
		verify(cancion,times(4)).obtenerDuracion();
	}
	
	/**
	 * Test para buscar contenidos en servidor de respaldo.
	 */
	@Test
	public void buscarRespaldoTest() {
		
		//Primero probamos que el servidor de respaldo
		// funciona ya con el caso de que no tenga ningun elemento,
		// al tampoco estar registrado en el de respaldo nos incluye un anuncio
		
		// PRIMERA BUSQUEDA TOKEN HABILITADO
		List<Contenido> resultado = servidorRespaldo.buscar("canc", tokenR);
		assertEquals(2,resultado.size());
				
		assertEquals(anuncio3.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		assertEquals(anuncio3.obtenerDuracion(),resultado.get(0).obtenerDuracion());
		
		assertEquals(cancion.obtenerTitulo(),resultado.get(1).obtenerTitulo());
		assertEquals(cancion.obtenerDuracion(),resultado.get(1).obtenerDuracion());
	
		
		// AGREGAMOS CONTENIDOS AL SERVIDOR
		boolean agregado = true;
		try {
			servidorRespaldo.agregar(cancion2, MASTER_TOKEN);
			servidorRespaldo.agregar(cancion3, MASTER_TOKEN);
			servidorRespaldo.agregar(cancion4, MASTER_TOKEN);
			servidorRespaldo.agregar(emisora, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			agregado = false;
		}
		assertTrue(agregado);
		
		
		// SEGUNDA BUSQUEDA TOKEN HABILITADO
		resultado = servidorRespaldo.buscar("canc", tokenR);
		assertEquals(3,resultado.size());
						
		assertEquals(cancion2.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		assertEquals(cancion2.obtenerDuracion(),resultado.get(0).obtenerDuracion());
				
		assertEquals(cancion3.obtenerTitulo(),resultado.get(1).obtenerTitulo());
		assertEquals(cancion3.obtenerDuracion(),resultado.get(1).obtenerDuracion());
		
		assertEquals(cancion4.obtenerTitulo(),resultado.get(2).obtenerTitulo());
		assertEquals(cancion4.obtenerDuracion(),resultado.get(2).obtenerDuracion());
		
		
		for(int i=0;i<8;i++){
			resultado = servidorRespaldo.buscar("cancion", tokenR);
			assertEquals(3,resultado.size());
		}
			
		// TERCERA BUSQUEDA TOKEN DESHABILITADO
		resultado = servidorRespaldo.buscar("canc", token);
		assertEquals(5,resultado.size());
		
		assertEquals(anuncio3.obtenerTitulo(),resultado.get(0).obtenerTitulo());
		assertEquals(anuncio3.obtenerDuracion(),resultado.get(0).obtenerDuracion());
		
		assertEquals(cancion2.obtenerTitulo(),resultado.get(1).obtenerTitulo());
		assertEquals(cancion2.obtenerDuracion(),resultado.get(1).obtenerDuracion());
		
		assertEquals(cancion3.obtenerTitulo(),resultado.get(2).obtenerTitulo());
		assertEquals(cancion3.obtenerDuracion(),resultado.get(2).obtenerDuracion());
		
		assertEquals(cancion4.obtenerTitulo(),resultado.get(3).obtenerTitulo());
		assertEquals(cancion4.obtenerDuracion(),resultado.get(3).obtenerDuracion());
		
		assertEquals(anuncio2.obtenerTitulo(),resultado.get(4).obtenerTitulo());
		assertEquals(anuncio2.obtenerDuracion(),resultado.get(4).obtenerDuracion());
	}
		

}