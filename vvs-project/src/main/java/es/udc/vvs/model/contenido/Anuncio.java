package es.udc.vvs.model.contenido;

import java.util.ArrayList;
import java.util.List;

public class Anuncio implements Contenido{
	
	private final static String titulo = "PUBLICIDAD"; /*titulo del anuncio siempre publicidad*/
	private final static int duracion = 5; /*el tiempo que dura el anuncio es siempre 5 segundos*/
	
	private List<Contenido> listaReproduccion = new ArrayList<Contenido>(); /*creamos una lista de 
	reproducción para los anuncio*/
	

	public Anuncio(String titulo, int duracion) {
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

	/* no tiene efecto  */
	public void agregar(Contenido contenido, Contenido predecesor) { }

	/* no tiene efecto  */
	public void eliminar(Contenido contenido) { }
	
	

}
