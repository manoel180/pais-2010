/**
 * 
 */
package br.com.pais.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.pais.dao.EstadoDao;
import br.com.pais.dao.impl.EstadoDaoImp;
import br.com.pais.entities.Estado;
import br.com.pais.exception.ValidarCPFException;
import br.com.pais.util.ValidarCPF;

/**
 * @author manoel
 */

@ManagedBean(name = "loginBean")
@RequestScoped()
public class LoginBean {

	protected Estado estado;
	protected EstadoDao estadoDao;

	protected String cpf;
	protected boolean edita = true;

	public void salvar() {
		estado = new Estado();
		estadoDao = new EstadoDaoImp();
		estado.setUfeNo("am");

		estado.setUfeSg("MM");

		estadoDao.salvar(estado);

	}

	public String logar() {
		try {
			if (ValidarCPF.validarCPF(cpf)== true) {
				return "/teste.mir";
			} else {
				return "/login.mir";
			}
		} catch (ValidarCPFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/teste.mir";
	}
	public void verificacpf(){
		try {
			if(ValidarCPF.validarCPF(cpf) == true){		
				edita=false;
			}else{
				cpf = "";
			}
		} catch (ValidarCPFException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the estado
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

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
	 * @return the edita
	 */
	public boolean isEdita() {
		return edita;
	}

	/**
	 * @param edita the edita to set
	 */
	public void setEdita(boolean edita) {
		this.edita = edita;
	}

}
