package es.udc.vvs.model.servicio;

import java.util.ArrayList;
import java.util.List;

import es.udc.vvs.model.contenido.Contenido;




/**
 * La Clase ImplementacionServicio (sin servidor de respaldo).
 */
public class ImplementacionServicio implements Servicio{
	
	/** Parametro que indica cuando caduca el token. */
	private int caduca;
	
	/** El token que otorga permisos de escritura. */
	private String token;
	
	/** Nombre del servicio. */
	private String nombre;
	
	/** El tipo de contenido. */
	private List<Contenido> contenido;
	
	
	public ImplementacionServicio(String nom) {
		this.caduca = 0;
		this.nombre = nom;
		this.token = null;
		this.contenido = new ArrayList<Contenido>();
	}
	

	/**
	 * Método que devuelve el nombre del Contenido.
	 */
	public String obtenerNombre() {
		return this.nombre;
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
	 * @throws TokenInvalidoException 
	 */
	public void baja(String token) throws TokenInvalidoException {
		if(this.token != null && token.equals(this.token))
		{
			this.token = null;
		}else 
			throw new TokenInvalidoException();
	}

	/**
	 * Método para agregar un Contenido.
	 * @throws TokenInvalidoException 
	 */
	public void agregar(Contenido contenido, String token) throws TokenInvalidoException{
		if(this.token != null && this.token.equals(token)){
			this.contenido.add(contenido);
		}else 
			throw new TokenInvalidoException();
	}

	/**
	 * Método para borrar un Contenido.
	 * @throws TokenInvalidoException 
	 */
	public void eliminar(Contenido contenido, String token) throws TokenInvalidoException {
		if(this.token != null && this.token.equals(token)){
			this.contenido.remove(contenido);
		}else 
			throw new TokenInvalidoException();
	}

	/**
	 * Método para buscar Contenido/s a través del nombre.
	 */
	public List<Contenido> buscar(String subcadena, String token) {
		List<Contenido> result = new ArrayList<Contenido>();
		if(this.token == null){
			List<Contenido> anuncios = new ArrayList<Contenido>();
			for(Contenido cont : this.contenido)
			{
				if(cont.obtenerTitulo().contains("PUBLICIDAD"))
					anuncios.add(cont);
				
				/*if(cont.getClass() == Emisora.class)
					anuncios.addAll(cont.buscar("PUBLICIDAD"));*/
			}
			int j=0;
			int numAnuncios = anuncios.size()-1;
			if(numAnuncios>=0){
				result.add(anuncios.get(numAnuncios));
				numAnuncios--;
			}
			for(int i=0;i<this.contenido.size();i++){
				if(this.contenido.get(i).obtenerTitulo().toLowerCase().contains(subcadena.toLowerCase())){
					result.add(this.contenido.get(i));
					j++;
					if(j>=3){
						if(numAnuncios>=0){
							result.add(anuncios.get(numAnuncios));
							numAnuncios--;
						}else{
							numAnuncios = anuncios.size()-1;
							result.add(anuncios.get(numAnuncios));
							numAnuncios--;
						}
						j=0;
					}
				}
			}
		}else{
			for(int i=0;i<this.contenido.size();i++){
				if(this.contenido.get(i).obtenerTitulo().toLowerCase().contains(subcadena.toLowerCase())){
					result.add(this.contenido.get(i));
				}
			}
			this.caduca++;
			if(this.caduca>9){
				this.caduca=0;
				this.token=null;
			}
		}
		return result;
	}

}
