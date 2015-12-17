package es.udc.vvs.test.emisora;

import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.udc.vvs.model.contenido.Contenido;
import es.udc.vvs.model.contenido.cancionimpl.ImplementacionCancion;
import es.udc.vvs.model.contenido.emisoraimpl.ImplementacionEmisora;
import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;
import net.java.quickcheck.generator.iterable.Iterables;

/**
 * 
 *
 * @author Grupo 8
 */
public class EmisoraTest {

	private ImplementacionEmisora emisora1; // NOPMD by adrian on 17/12/15 13:26
	private ImplementacionEmisora emisora2; // NOPMD by adrian on 17/12/15 13:26
	private ImplementacionEmisora emisora3; // NOPMD by adrian on 17/12/15 13:26

	private ImplementacionCancion cancion1; // NOPMD by adrian on 17/12/15 13:26
	private ImplementacionCancion cancion2; // NOPMD by adrian on 17/12/15 13:26
	private ImplementacionCancion cancion3; // NOPMD by adrian on 17/12/15 13:26

	/**
	 * SetUp de las pruebas de Emisora
	 */
	@Before
	public void setUp() {

		emisora1 = new ImplementacionEmisora("emisora1", 0);
		emisora2 = new ImplementacionEmisora("emisora2", 0);
		emisora3 = new ImplementacionEmisora("emisora3", 0);

		cancion1 = new ImplementacionCancion("cancion1", 4);
		cancion2 = new ImplementacionCancion("cancion2", 5);
		cancion3 = new ImplementacionCancion("cancion3", 6);

	}

	/**
	 * Test del metodo obtenerTitulo()
	 */
	@Test
	public void obtenerTituloTest() {

		for (String anyString : Iterables.toIterable(PrimitiveGenerators
				.printableStrings())) {
			emisora1 = new ImplementacionEmisora(anyString, 0);
			
			assertEquals(emisora1.obtenerTitulo(), anyString);
			//assertTrue(emisora1.obtenerTitulo().equals(anyString));
		}
	}

	/**
	 * Test del metodo agregar() para agregar un contenido a una emisora
	 */
	@Test
	public void agregarTest() {

		for (ImplementacionCancion anyCancion : Iterables
				.toIterable(new CancionGenerator())) {
			emisora1.agregar(anyCancion, null);

			assertEquals(anyCancion, emisora1
					.buscar(anyCancion.obtenerTitulo()).get(0));
		}
	}

	/**
	 * Test del metodo eliminar() para eliminar un contenido de una emisora
	 */
	@Test
	public void eliminarTestEquals() {

		List<Contenido> listaReproduccion = new ArrayList<Contenido>();

		for (ImplementacionCancion anyCancion : Iterables
				.toIterable(new CancionGenerator())) {
			emisora1.agregar(anyCancion, null);
			listaReproduccion.add(anyCancion);
			assertEquals(anyCancion, emisora1
					.buscar(anyCancion.obtenerTitulo()).get(0));
		}
	}
	
	/**
	 * Test del metodo eliminar() para eliminar un contenido de una emisora
	 */
	@Test
	public void eliminarTestNotEquals() {

		List<Contenido> listaReproduccion = new ArrayList<Contenido>();

		for (ImplementacionCancion anyCancion : Iterables
				.toIterable(new CancionGenerator())) {
			emisora1.agregar(anyCancion, null);
			ImplementacionCancion otherCancion = new CancionGenerator().next();
			listaReproduccion.add(anyCancion);

			emisora1.eliminar(anyCancion);
			assertNotEquals(listaReproduccion,
					emisora1.buscar(anyCancion.obtenerTitulo()));
		}
	}

	/**
	 * Test del metodo obtenerDuracion() para obtener la duración de una
	 * emisora.
	 */
	@Test
	public void obtenerDuracionTest() {

		for (int anyDuracion : Iterables.toIterable(PrimitiveGenerators
				.integers())) {
			emisora1 = new ImplementacionEmisora("emisora1", anyDuracion);

			assertEquals(emisora1.obtenerDuracion(), anyDuracion);
		}
	}

	/**
	 * Test del metodo obtenerListaReproduccion() para obtener una l. de
	 * reproduccion.
	 */
	@Test
	public void obtenerListaReproduccionTest() {

		CancionGenerator cGen = new CancionGenerator();
		List<Contenido> listaReproduccion = new ArrayList<Contenido>();

		for (ImplementacionCancion anyCancion : Iterables.toIterable(cGen)) {
			emisora3.agregar(anyCancion, null);
			int i = emisora3.obtenerListaReproduccion().size();
			listaReproduccion.add(anyCancion);

			// Al insertar al final, se comprueba que la ultima cancion de la
			// lista de reproduccion es igual a la añadida.
			// ya que el equals de las listas no funciona.
			assertEquals(anyCancion,
					emisora3.obtenerListaReproduccion().get(i - 1));

		}

	}

	/**
	 * Test del metodo buscar() que de un resultado positivo
	 */
	@Test
	public void buscarTest() {
		Generator<ImplementacionCancion> cGen = new CancionGenerator();
		List<Contenido> lista = new ArrayList<Contenido>();

		for (ImplementacionCancion anyCancion : Iterables.toIterable(cGen)) {
			emisora3 = new ImplementacionEmisora("emisora3", 0);
			emisora3.agregar(anyCancion, null);
			lista.add(anyCancion);

			// COMO HACER QUE LA LISTA SEA IGUAL?
			assertEquals(anyCancion, emisora3
					.buscar(anyCancion.obtenerTitulo()).get(0));

		}
	}

	class CancionGenerator implements Generator<ImplementacionCancion> {
		Generator<Integer> iGen = PrimitiveGenerators.integers(-50, 50);
		Generator<String> sGen = PrimitiveGenerators.printableStrings();

		public ImplementacionCancion next() {
			String anyTitulo = sGen.next();
			int anyDuracion = iGen.next();

			return new ImplementacionCancion(anyTitulo, anyDuracion);
		}

	}

}
