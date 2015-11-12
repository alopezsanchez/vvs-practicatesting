package es.udc.vvs.model.servicio;


// TODO: Auto-generated Javadoc
/**
 * Clase Usuario.
 */
public class Usuario {
	
	/** El token que identifica al Usuario. */
	private String token;

	/** Cuando llega a 10 se elimina la sesion del Usuario. */
	private int caduca;
	

	public Usuario(String token) {
		this.token = token;
		this.caduca = 0;
	}


	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}


	/**
	 * Gets the caduca.
	 *
	 * @return the caduca
	 */
	public int getCaduca() {
		return caduca;
	}


	/**
	 * Sets the caduca.
	 *
	 * @param caduca the new caduca
	 */
	public void setCaduca(int caduca) {
		this.caduca = caduca;
	}
	
	

	
}
