package es.udc.vvs.model.servicio;

public class Usuario {
	
	private String token;
	private int caduca;
	
	
	public Usuario(String token) {
		this.token = token;
		this.caduca = 0;
	}


	public String getToken() {
		return token;
	}


	public int getCaduca() {
		return caduca;
	}


	public void setCaduca(int caduca) {
		this.caduca = caduca;
	}
	
	

	
}
