/*
 * Interfaz que usa el Servidor para su funcionamiento.
 * 
 * @author Alex
 */
package es.udc.vvs.model.contenido;

import java.util.List;

public interface Contenido {
	
	/**
	 * Devuelve el titulo del Contenido.
	 *
	 * @return titulo del Contenido
	 */
	public String obtenerTitulo();
	
	/**
	 * Devuelve la duracion del Contenido.
	 *
	 * @return duracion del contenido
	 */
	public int obtenerDuracion();
	
	/**
	 * Devuelve la lista reproduccion.
	 *
	 * @return lista de reproduccion del Contenido, que contendra un solo elemento,
	 * el propio Contenido
	 */
	public List<Contenido> obtenerListaReproduccion();
	
	/**
	 * Busca Contenido en una lista de reproduccion. Realiza comparaciones usando el título
	 *
	 * @param subcadena del nombre del contenido
	 * @return lista de reproducción de Contenido, si la subcadena concuerda con alguna de la lista
	 */
	public List<Contenido> buscar(String subcadena);
	
	/**
	 * Inserta ordenadamente teniendo en cuenta un predecesor.
	 * En Cancion y Anuncio no tienen efecto.
	 *
	 * @param contenido a insertar
	 * @param predecesor. Elemento anterior al elemento a insertar
	 */
	public void agregar(Contenido contenido, Contenido predecesor);
	
	/**
	 * Eliminar Contenido.
	 * En Cancion y Anuncio no tiene efecto.
	 *
	 * @param contenido the contenido
	 */
	public void eliminar(Contenido contenido);
 
	
}
