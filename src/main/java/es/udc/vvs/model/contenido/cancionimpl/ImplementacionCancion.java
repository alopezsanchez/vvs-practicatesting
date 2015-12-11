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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + duracion;
		result = prime
				* result
				+ ((listaReproduccion == null) ? 0 : listaReproduccion
						.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImplementacionCancion other = (ImplementacionCancion) obj;
		if (duracion != other.duracion)
			return false;
		if (listaReproduccion == null) {
			if (other.listaReproduccion != null)
				return false;
		} else if (!listaReproduccion.equals(other.listaReproduccion))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}
	
	

}
