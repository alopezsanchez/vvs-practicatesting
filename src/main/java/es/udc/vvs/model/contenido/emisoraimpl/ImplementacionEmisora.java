package es.udc.vvs.model.contenido.emisoraimpl;

import java.util.ArrayList;
import java.util.List;

import es.udc.vvs.model.contenido.Contenido;

public class ImplementacionEmisora implements Contenido{
	private String titulo;
	private int duracion;
	private List<Contenido> listaReproduccion = new ArrayList<Contenido>();
	

	public ImplementacionEmisora(String titulo, int duracion) {
		this.titulo = titulo;
		this.duracion = duracion;
		
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
		
		List<Contenido> temp = new ArrayList<Contenido>();
		int tam = listaReproduccion.size();
		Contenido item;
				
		while (tam > 0){
			
			item = listaReproduccion.get(tam-1);

			if (item.obtenerTitulo().contains(subcadena)) {
				temp.add(item);
			}

			tam--;
		}
		
		return temp;
	}

	public void agregar(Contenido contenido, Contenido predecesor) {
		
		if (predecesor == null) {
			
			listaReproduccion.add(contenido);			
			
		}
		
		if (predecesor != null) {
			
			int pos = listaReproduccion.indexOf(predecesor);
			
			listaReproduccion.add(pos+1, contenido);
		}
		
		duracion = duracion + contenido.obtenerDuracion();

		
	}

	public void eliminar(Contenido contenido) {
		
		if (listaReproduccion.remove(contenido))
			
			duracion = duracion - contenido.obtenerDuracion();
		
	}
	
}
