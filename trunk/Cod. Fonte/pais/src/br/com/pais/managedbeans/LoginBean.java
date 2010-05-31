/**
 * 
 */
package br.com.pais.managedbeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.component.focus.Focus;

import br.com.pais.exception.GenericException;
import br.com.pais.exception.ValidarCPFException;
import br.com.pais.mensagens.MessageManagerImpl;
import br.com.pais.util.ValidarCPF;





/**
 * @author manoel
 */

@ManagedBean(name = "loginBean")
@SessionScoped()
public class LoginBean {

	protected String cpf ="";
	
	protected String senha;
	
	protected Focus focus;
	protected String email;
	
	protected boolean editar = true; 
	
	

	public String logar(){
		try {
			if (ValidarCPF.validarCPF(cpf)== true && senha.equals("123")) {
				return "/teste.mir";
			} else {
				throw new GenericException("usuario.invalido_detail");
				
				//return "/login.mir";
			}
		} catch (ValidarCPFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GenericException e) {
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",
			"usuario.invalido_detail");
		}
		return "teste.mir";
	}
	public void verificacpf(ActionEvent ev){
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
			
		}
	}
	
	/**
	

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
