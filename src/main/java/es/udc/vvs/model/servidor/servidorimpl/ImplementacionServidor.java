package es.udc.vvs.model.servidor.servidorimpl;

import java.util.ArrayList;
import java.util.List;

import es.udc.vvs.model.contenido.Contenido;
import es.udc.vvs.model.contenido.emisoraimpl.ImplementacionEmisora;
import es.udc.vvs.model.servidor.Servidor;
import es.udc.vvs.model.servidor.Usuario;
import es.udc.vvs.model.util.exceptions.TokenInvalidoException;
import es.udc.vvs.model.util.servidorutil.GenerarToken;
import static es.udc.vvs.model.util.servidorutil.ModelConstants.*;


/**
 * La Clase ImplementacionServicio (sin servidor de respaldo).
 */
public class ImplementacionServidor implements Servidor{
	
	/** Nombre del servicio. */
	private String nombre;
	
	/** Lista de usuarios registrados. */
	private List<Usuario> usuarios;
	
	/** El tipo de contenido. */
	private List<Contenido> contenido;
	
	
	public ImplementacionServidor(String nom) {
		this.nombre = nom;
		this.contenido = new ArrayList<Contenido>();
		this.usuarios = new ArrayList<Usuario>();
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
		this.usuarios.add(new Usuario(tk));
		return tk;
	}

	/**
	 * Método que da de baja un token.
	 * @throws TokenInvalidoException 
	 */
	public void baja(String token) throws TokenInvalidoException {
		if(this.existeUsuario(token))
		{
			this.borrarUsuario(token);
		}else 
			throw new TokenInvalidoException();
	}

	/**
	 * Método para agregar un Contenido.
	 * @throws TokenInvalidoException 
	 */
	public void agregar(Contenido contenido, String token) throws TokenInvalidoException{
		if((token!=null) && (token.equals(MASTER_TOKEN))){
			this.contenido.add(contenido);
		}else 
			throw new TokenInvalidoException();
	}

	/**
	 * Método para borrar un Contenido.
	 * @throws TokenInvalidoException 
	 */
	public void eliminar(Contenido contenido, String token) throws TokenInvalidoException {
		if((token!=null) && (token.equals(MASTER_TOKEN))){
			this.contenido.remove(contenido);
		}else 
			throw new TokenInvalidoException();
	}

	/**
	 * Método para buscar Contenido/s a través del nombre.
	 */
	public List<Contenido> buscar(String subcadena, String token) {
		List<Contenido> result = new ArrayList<Contenido>();
		if(subcadena != null){
			if(!this.existeUsuario(token)){
				List<Contenido> anuncios = new ArrayList<Contenido>();
				for(Contenido cont : this.contenido)
				{
					if(cont.obtenerTitulo().contains("PUBLICIDAD"))
						anuncios.add(cont);
					
					if(cont.getClass() == ImplementacionEmisora.class)
						anuncios.addAll(cont.buscar("PUBLICIDAD"));
				}
				int j=0;
				int numAnuncios = anuncios.size()-1;
				if(anuncios.size()>0){
					if(numAnuncios>=0){
						result.add(anuncios.get(numAnuncios));
						numAnuncios--;
					}
				}
				for(int i=0;i<this.contenido.size();i++){
					if(this.contenido.get(i).obtenerTitulo().toLowerCase().contains(subcadena.toLowerCase())){
						result.add(this.contenido.get(i));
						j++;
						if(anuncios.size()>0){
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
				}
			}else{
				for(int i=0;i<this.contenido.size();i++){
					if(this.contenido.get(i).obtenerTitulo().toLowerCase().contains(subcadena.toLowerCase())){
						result.add(this.contenido.get(i));
					}
				}
				Usuario u = this.buscarUsuario(token);
				u.setCaduca(u.getCaduca()+1);
				if(u.getCaduca()>9){
					this.borrarUsuario(token);
				}
			}
		}
		return result;
	}
		
	public boolean existeUsuario(String tk){
		boolean existe = false;
		if(tk!=null)
			for(Usuario u: this.usuarios){
				if(u.getToken().equals(tk))
					existe = true;
			}
		return existe;
	}
		
	private void borrarUsuario(String tk){
		Usuario uB = null;
		if(tk!=null)
			for(Usuario u: this.usuarios){
				if(u.getToken().equals(tk))
					uB = u;
			}
		this.usuarios.remove(uB);
	}
	
	private Usuario buscarUsuario(String tk){
		Usuario uB = null;
		if(tk!=null)
			for(Usuario u: this.usuarios){
				if(u.getToken().equals(tk))
					uB = u;
			}
		return uB;
	}
		

}
