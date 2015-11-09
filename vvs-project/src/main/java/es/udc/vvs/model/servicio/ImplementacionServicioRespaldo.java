package es.udc.vvs.model.servicio;

import java.util.ArrayList;
import java.util.List;

import es.udc.vvs.model.contenido.Contenido;

// TODO: Auto-generated Javadoc
/**
 * Clase ImplementacionServicioRespaldo que implementa un servicio con respaldo.
 */
public class ImplementacionServicioRespaldo implements Servicio{
	
	/** Parametro que indica cuando caduca el token. */
	private int caduca;
	
	/** El token que otorga permisos de escritura. */
	private String token;
	
	/** Nombre del servicio. */
	private String nombre;
	
	/** El tipo de contenido. */
	private List<Contenido> contenido;
	
	
	/** Servicio de respaldo. */
	private Servicio respaldo;
	
	
	public ImplementacionServicioRespaldo(Servicio respaldo,String nom) {
		this.respaldo = respaldo;
		this.caduca = 0;
		this.nombre = nom;
		this.token = "";
		this.contenido = new ArrayList<Contenido>();
	}
	

	/**
	 * Método que devuelve el nombre del Contenido.
	 *
	 * @return the string
	 */
	public String obtenerNombre() {
		return this.nombre;
	}

	/**
	 * Método que inicializa un token.
	 *
	 * @return the string
	 */
	public String alta() {
		String tk = GenerarToken.generateToken();
		this.token = tk;
		return tk;
	}

	/**
	 * Método que da de baja un token.
	 *
	 * @param token the token
	 * @throws TokenInvalidoException the token invalido exception
	 */
	public void baja(String token) throws TokenInvalidoException {
		if(token.equals(this.token))
		{
			this.token = "";
		}else 
			throw new TokenInvalidoException();
	}

	/**
	 * Método para agregar un Contenido.
	 *
	 * @param contenido the contenido
	 * @param token the token
	 * @throws TokenInvalidoException the token invalido exception
	 */
	public void agregar(Contenido contenido, String token) throws TokenInvalidoException{
		if(this.token.equals(token)){
			this.contenido.add(contenido);
		}else 
			throw new TokenInvalidoException();
	}

	/**
	 * Método para borrar un Contenido.
	 *
	 * @param contenido the contenido
	 * @param token the token
	 * @throws TokenInvalidoException the token invalido exception
	 */
	public void eliminar(Contenido contenido, String token) throws TokenInvalidoException {
		if(this.token.equals(token)){
			this.contenido.remove(contenido);
		}else 
			throw new TokenInvalidoException();
	}

	/**
	 * Método para buscar Contenido/s a través del nombre.
	 *
	 * @param subcadena the subcadena
	 * @param token the token
	 * @return the list
	 */
	public List<Contenido> buscar(String subcadena, String token) {
		List<Contenido> result = new ArrayList<Contenido>();
		if(token.equals("")){
			/*List<Contenido> anuncios = new ArrayList<Contenido>();
			List<Contenido> listaContenidos = new ArrayList<Contenido>(this.contenido);
			for(Contenido cont : listaContenidos)
			{
				if(cont.getClass() == Anuncio.class)
				{
					listaContenidos.remove(cont);
					anuncios.add(cont);
				}
				if(cont.getClass() == Emisora.class)
					anuncios.addAll(cont.buscar("PUBLICIDAD"));
				int j=0;
				int numAnuncios = anuncios.size();
				result.add(anuncios.get(j));
				for(int i=0;i<this.contenido.size();i++){
					if(this.contenido.get(i).obtenerTitulo().toLowerCase().contains(subcadena.toLowerCase())){
						result.add(this.contenido.get(i));
						j++;
						if(j>=3){
							result.add(anuncios.get(j));
							j=0;
						}
					}
				}
			}*/
		}else{
			for(int i=0;i<this.contenido.size();i++){
				if(this.contenido.get(i).obtenerTitulo().toLowerCase().contains(subcadena.toLowerCase())){
					result.add(this.contenido.get(i));
				}
			}
			this.caduca++;
			if(this.caduca>9){
				this.caduca=0;
				this.token="";
			}
		}
		if(result.isEmpty())
			result = this.respaldo.buscar(subcadena, token);
		return result;
	}

}


