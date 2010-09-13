/**
 * 
 */
package br.com.pais.managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.focus.Focus;

import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.entities.Discipulos;
import br.com.pais.exception.GenericException;
import br.com.pais.exception.ValidarCPFException;
import br.com.pais.mensagens.MessageManagerImpl;
import br.com.pais.util.ValidarCPF;





/**
 * @author manoel
 */

public class LoginBean {

	protected String cpf ="";
	protected String senha = null;
	protected Focus focus;
	protected String email = null;
	
	
	protected boolean editar = true; 
	
	protected Discipulos discipulos = new Discipulos();
	protected DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	
	
	public String logar(){
		
		
		try {
			
			if (ValidarCPF.validarCPF(cpf)== true ) {
				discipulos = discipuloDao.encontrarPorCPF(cpf);
					if(discipulos.getDisSenha().equals(senha)){
						return "/principal.mir";
					}
					 else {
							throw new GenericException("usuario.invalido_detail");
						}
			} else {
				throw new GenericException("usuario.invalido_detail");
			}
		} catch (ValidarCPFException e) {
			// TODO Auto-generated catch block
			cpf = "";
			focus.setFor("cpfMask");
			editar=true;		
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro", "usuario.invalido_detail");
			return "/login.mir";
		} catch (GenericException e) {
			cpf = "";
			focus.setFor("cpfMask");
			editar=true;		
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "erro", "CPF ou SENHA inválida");
			facesContext.addMessage(null, message);
		//MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro", "usuario.invalido_detail");
			return "/login.mir";
		}
		
	}
	
	public void validarcpf(ActionEvent ev){
		/*public void verificacpf(){*/
		try {
			if(ValidarCPF.validarCPF(cpf) == true){		
				/*ev.getComponent().getId();*/
				editar=false;					
				focus.setFor("senhaSecret");
				
			}else{
				cpf = "";
				focus.setFor("cpfMask");
				
			}
		} catch (ValidarCPFException e) {
			// TODO Auto-generated catch block
			cpf = "";
			focus.setFor("cpfMask");
		}
	}
		
	public void esqueciSenha(){
		
		
		/*Verifica o cpf se é válido*/
			try {
				if(ValidarCPF.validarCPF(cpf) == true){		
					
					
				}else{
					cpf = "";
					//focus.setFor("cpfMaskDialog");
					
				}
			} catch (ValidarCPFException e) {
				// TODO Auto-generated catch block
				cpf = "";
				//focus.setFor("cpfMaskDialog");
			}
		}
		
	
	
	
	/**
	 * GETTERS and SETTERS
	 */
	

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @param cpf
	 *            the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 

	/**
	 * @return the focus
	 */
	public Focus getFocus() {
		return focus;
	}

	/**
	 * @param focus the focus to set
	 */
	public void setFocus(Focus focus) {
		this.focus = focus;
	}

	/**
	 * @return the editar
	 */
	public boolean isEditar() {
		return editar;
	}

	/**
	 * @param editar the editar to set
	 */
	public void setEditar(boolean editar) {
		this.editar = editar;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}
	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	
}
