package es.udc.vvs.model.servicio;

import java.util.ArrayList;
import java.util.List;

import es.udc.vvs.model.contenido.Contenido;
import es.udc.vvs.model.contenido.GenerarToken;




/**
 * La Clase ImplementacionServicio (sin servidor de respaldo).
 */
public class ImplementacionServicio implements Servicio{
	
	/** Parametro que indica cuando caduca el token. */
	private int caduca;
	
	/** El token que otorga permisos de escritura. */
	private String token;
	
	/** El tipo de contenido. */
	private Contenido contenido;
	
	
	public ImplementacionServicio(Contenido contenido) {
		this.caduca = 0;
		this.token = "";
		this.contenido = contenido;
	}
	

	/**
	 * Método que devuelve el nombre del Contenido.
	 */
	public String obtenerNombre() {
		return contenido.obtenerTitulo();
	}

	/**
	 * Método que inicializa un token.
	 */
	public String alta() {
		String tk = GenerarToken.generateToken();
		this.token = tk;
		return tk;
	}

	/**
	 * Método que da de baja un token.
	 */
	public void baja(String token) {
		if(token.equals(this.token))
		{
			this.token = "";
		}
	}

	/**
	 * Método para agregar un Contenido.
	 */
	public void agregar(Contenido contenido, String token) {
		List<Contenido> cont = contenido.obtenerListaReproduccion();
		Contenido predecesor = null;
		if(this.token.equals(token)){
			int i = 0;
			while(predecesor.obtenerTitulo().compareTo(contenido.obtenerTitulo()) < 0){
				predecesor = cont.get(i);
				i++;
			}
			this.contenido.agregar(contenido, predecesor);
		}
	}

	/**
	 * Método para borrar un Contenido.
	 */
	public void eliminar(Contenido contenido, String token) {
		if(this.token.equals(token)){
			this.contenido.eliminar(contenido);
		}
	}

	/**
	 * Método para buscar Contenido/s a través del nombre.
	 */
	public List<Contenido> buscar(String subcadena, String token) {
		List<Contenido> result = new ArrayList<Contenido>();
		if(token.equals("")){
			//this.setContenido(new Anuncio());
			//Anuncio anuncio = this.contenido.obtenerListaReproduccion().get(0);
			List<Contenido> resultCont = this.contenido.buscar(subcadena);
			//result.add(anuncio);
			int j=0;
			for(int i=0;0<resultCont.size();i++){
				j++;
				if(j>=3){
					j=0;
					//result.add(Anuncio);
				}
				result.add(resultCont.get(i));
			}
		}else
			result = this.contenido.buscar(subcadena);
		
		this.caduca++;
		if(this.caduca>=10){
			this.caduca=0;
			this.token="";
		}
		return result;
	}


	/**
	 * Gets the contenido.
	 *
	 * @return the contenido
	 */
	public Contenido getContenido() {
		return contenido;
	}

	/**
	 * Sets the contenido.
	 *
	 * @param contenido the new contenido
	 */
	public void setContenido(Contenido contenido) {
		this.contenido = contenido;
	}

	
}
