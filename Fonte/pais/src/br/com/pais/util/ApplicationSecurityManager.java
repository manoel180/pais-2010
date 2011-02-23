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


public class ApplicationSecurityManager
{
    public static final String DISCIPULOS = "discipulo";
    
    /*private FacesContext context = FacesContext.getCurrentInstance();
    private HttpSession session = (HttpSession) context.getExternalContext().getSession(false);*/

    public  Discipulos getDiscipulos(){
    	
        // HttpSession session = (HttpSession) context.getExternalContext().getSession(false);    							
        //return (Usuario) session.getAttribute(USER);
    	
    	return (Discipulos) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(DISCIPULOS);
    }

    public void setDiscipulos(Object discipulos)
    {
    	/*FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
    	session.setAttribute(USER, usuario);*/    	
    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(DISCIPULOS,discipulos);  
    }

    
    public void removeUsuario()
    {
//    	FacesContext context = FacesContext.getCurrentInstance();
//        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
    	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(DISCIPULOS);
    	    	//session.removeAttribute(USER);
    }
    
}