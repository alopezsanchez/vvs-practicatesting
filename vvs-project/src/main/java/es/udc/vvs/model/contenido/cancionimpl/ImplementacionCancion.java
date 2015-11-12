/*
 * Implementacion del interfaz Contenido.
 * 
 * @author Alex
 */
package es.udc.vvs.model.contenido.cancionimpl;

import java.util.ArrayList;
import java.util.List;

import es.udc.vvs.model.contenido.Contenido;

public class ImplementacionCancion implements Contenido{
	
	private String titulo;
	private int duracion;
	private List<Contenido> listaReproduccion = new ArrayList<Contenido>();
	

	public ImplementacionCancion(String titulo, int duracion) {
		this.titulo = titulo;
		this.duracion = duracion;
		listaReproduccion.add(this);
		
	}

	public String obtenerTitulo() {
		return titulo;
	}

	public int obtenerDuracion() {
		return duracion;
	}

	public List<Contenido> obtenerListaReproduccion() {
		return listaReproduccion;
	}

	public List<Contenido> buscar(String subcadena) {
		
		if (titulo.contains(subcadena)) {
			return listaReproduccion;
		}
		else {
			return null;
		}
		
	}

	/* agregar y eliminar no tienen efecto */
	public void agregar(Contenido contenido, Contenido predecesor) { }

	public void eliminar(Contenido contenido) { }
	
	

}
