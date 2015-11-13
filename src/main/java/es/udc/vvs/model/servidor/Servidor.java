package es.udc.vvs.model.servidor;

import java.util.List;

import es.udc.vvs.model.contenido.Contenido;
import es.udc.vvs.model.util.exceptions.TokenInvalidoException;

public interface Servidor {
	
	public String obtenerNombre();
	
	public String alta();
	
	public void baja(String token) throws TokenInvalidoException;
	
	public void agregar(Contenido contenido, String token) throws TokenInvalidoException;
	
	public void eliminar(Contenido contenido, String token) throws TokenInvalidoException;
	
	public List<Contenido> buscar(String subcadena, String token);

}
