/**
 * 
 */
package br.com.pais.util;

/**
 * @author MANOEL DA SILVA NETO
 *
 */

import javax.faces.context.FacesContext;

import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Usuarios;


public class ApplicationSecurityManager
{
    public static final String DISCIPULOS = "discipulo";
    public static final String USUARIOS = "usuario";
    
    public Discipulos getDiscipulos() {
		return (Discipulos) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(DISCIPULOS);
	}

	public void setDiscipulos(Object discipulos) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(DISCIPULOS, discipulos);
	}

	public void removeDiscipulo() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove(DISCIPULOS);
	}
	
	public Usuarios getUsuarios() {

		return (Usuarios) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get(USUARIOS);
	}

	public void setUsuarios(Object usuarios) {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(USUARIOS, usuarios);
	}

	public void removeUsuario() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.remove(USUARIOS);
	}
    
}