package es.udc.vvs.test.mockito;

import static es.udc.vvs.model.util.servidorutil.ModelConstants.MASTER_TOKEN;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import es.udc.vvs.model.contenido.Contenido;
import es.udc.vvs.model.contenido.cancionimpl.ImplementacionCancion;
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
	private Servidor servidor = mock(ImplementacionServidor.class);
	
	/** El servidor de respaldo. */
	private Servidor servidorRespaldo = mock(ImplementacionServidorRespaldo.class);

	/** Un contenido. */
	private Contenido contenido;
	
	/**
	 * Metodo de inicializacion de los stubs
	 */
	@Before
	public void init() {

		//stubs para obtención del nombre de servicio
		when(servidor.obtenerNombre()).thenReturn("ServidorPrueba");
		when(servidorRespaldo.obtenerNombre()).thenReturn("ServidorPruebaRespaldo");
		
		//stubs para alta en el servicio
		when(servidor.alta()).thenReturn("anyString");
		when(servidorRespaldo.alta()).thenReturn("anyString");
		
		try {
			//stubs para baja en el servicio
			doThrow(new TokenInvalidoException()).when(servidor).baja(null);
			doThrow(new TokenInvalidoException()).when(servidorRespaldo).baja(null);
			
			//stubs para agregar Contenido en el servicio
			doThrow(new TokenInvalidoException()).when(servidor).agregar(contenido, MASTER_TOKEN);
			doThrow(new TokenInvalidoException()).when(servidorRespaldo).agregar(contenido, MASTER_TOKEN);
			
			//stubs para eliminar Contenido en el servicio
			doThrow(new TokenInvalidoException()).when(servidor).eliminar(contenido, MASTER_TOKEN);
			doThrow(new TokenInvalidoException()).when(servidorRespaldo).eliminar(contenido, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			e.printStackTrace();
		}
		
		//stubs para buscar Contenidos en el servicio
		when(servidor.buscar("Cancion", "")).thenReturn(new ArrayList<Contenido>());
		when(servidorRespaldo.buscar("Cancion", "")).thenReturn(new ArrayList<Contenido>());
		
	}
	
	/**
	 * Test para obtener nombre servidores.
	 */
	@Test
	public void testObtenerNombreServidor()
	{
		//ejecutamos la lógica a probar
		String nombre = servidor.obtenerNombre();
		
		//verificamos que se haya invocado el método
		verify(servidor, times(1)).obtenerNombre();
		
		assertEquals("ServidorPrueba",nombre);
		
		
		//ejecutamos la lógica a probar
		nombre = servidorRespaldo.obtenerNombre();
		
		//verificamos que se haya invocado el método
		verify(servidorRespaldo, times(1)).obtenerNombre();
		
		assertEquals("ServidorPruebaRespaldo",nombre);
	}
	
	/**
	 * Test para dar de alta en los servidores.
	 */
	@Test
	public void testAltaServidor()
	{
		servidor.alta();
		verify(servidor, times(1)).alta();
		
		servidor.alta();
		verify(servidor, times(2)).alta();
		
		servidor.alta();
		verify(servidor, times(3)).alta();
		
		
		servidorRespaldo.alta();
		servidorRespaldo.alta();
		verify(servidorRespaldo, times(2)).alta();
	}
	
	/**
	 * Test para dar de baja en los servidores en el caso.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test
	public void testBajaServidor()
	{
		String token = servidor.alta();
		token = servidorRespaldo.alta();
		
		try {
			servidor.baja(token);
			servidorRespaldo.baja(token);
			verify(servidor, times(1)).baja(token);
			verify(servidorRespaldo, times(1)).baja(token);
		} catch (TokenInvalidoException e) {
			e.printStackTrace();
		}
		
		verify(servidor, times(1)).alta();
		verify(servidorRespaldo, times(1)).alta();
	}
	
	/**
	 * Test para dar de baja en los servidores en el caso
	 * de que el token sea inválido.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test(expected = TokenInvalidoException.class)
	public void testBajaTokenNuloServidor() throws TokenInvalidoException
	{
		servidor.baja(null);
		verify(servidor, times(1)).baja(null);

		servidor.baja(null);
		verify(servidor, times(2)).baja(null);
		
		servidorRespaldo.baja(null);
		verify(servidor, times(1)).baja(null);
	}
	
	/**
	 * Test para agregar un Contenido a los servidores.
	 */
	@Test
	public void testAgregarServidor()
	{
		contenido = new ImplementacionCancion("CancionPrueba",5);
		
		try {
			servidor.agregar(contenido, MASTER_TOKEN);
			servidor.agregar(contenido, MASTER_TOKEN);
			servidorRespaldo.agregar(contenido, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			e.printStackTrace();
		}
		try {
			verify(servidor, times(2)).agregar(contenido, MASTER_TOKEN);
			verify(servidorRespaldo, times(1)).agregar(contenido, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test eliminar un Contenido en los servidores.
	 *
	 * @throws TokenInvalidoException the token invalido exception
	 */
	@Test
	public void testEliminarServidor() throws TokenInvalidoException
	{
		contenido = new ImplementacionCancion("CancionPrueba",5);
		
		try {
			servidor.agregar(contenido, MASTER_TOKEN);
			servidorRespaldo.agregar(contenido, MASTER_TOKEN);
			servidor.eliminar(contenido, MASTER_TOKEN);
			servidorRespaldo.eliminar(contenido, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			e.printStackTrace();
		}
		
		verify(servidor, times(1)).agregar(contenido, MASTER_TOKEN);
		verify(servidorRespaldo, times(1)).agregar(contenido, MASTER_TOKEN);
		
		verify(servidor, times(1)).eliminar(contenido, MASTER_TOKEN);
		verify(servidorRespaldo, times(1)).eliminar(contenido, MASTER_TOKEN);	
	}
	
	/**
	 * Test para buscar Contenidos en los servidores.
	 */
	@Test
	public void testBuscarServidor()
	{
		String tk = servidor.alta();
		String tkR = servidorRespaldo.alta();
		contenido = new ImplementacionCancion("CancionPrueba",5);
		
		try {
			servidor.agregar(contenido, MASTER_TOKEN);
			servidorRespaldo.agregar(contenido, MASTER_TOKEN);
		} catch (TokenInvalidoException e) {
			e.printStackTrace();
		}
		
		servidor.buscar("Cancion", tk);
		verify(servidor, times(1)).buscar("Cancion", tk);
		
		servidor.buscar("Cancion", tk);
		servidor.buscar("Cancion", tk);
		verify(servidor, times(3)).buscar("Cancion", tk);
		
		//************ Servidor Respaldo ****************************
		servidorRespaldo.buscar("Cancion", tkR);
		servidorRespaldo.buscar("Cancion", tkR);
		verify(servidorRespaldo, times(2)).buscar("Cancion", tkR);
		
		servidorRespaldo.buscar("Cancion", tkR);
		verify(servidorRespaldo, times(3)).buscar("Cancion", tkR);
		
		servidorRespaldo.buscar("Cancion", tkR);
		verify(servidorRespaldo, times(4)).buscar("Cancion", tkR);
		
		servidorRespaldo.buscar("Cancion", tkR);
		verify(servidorRespaldo, times(5)).buscar("Cancion", tkR);
	}

}