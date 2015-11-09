package es.udc.vvs.model.servicio;

import java.util.List;

import es.udc.vvs.model.contenido.Contenido;

public interface Servicio {
	
	public String obtenerNombre();
	
	public String alta();
	
	public void baja(String token) throws TokenInvalidoException;
	
	public void agregar(Contenido contenido, String token) throws TokenInvalidoException;
	
	public void eliminar(Contenido contenido, String token) throws TokenInvalidoException;
	
	public List<Contenido> buscar(String subcadena, String token);

}
