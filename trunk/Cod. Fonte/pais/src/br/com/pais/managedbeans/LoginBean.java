/**
 * 
 */
package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.focus.Focus;
import org.primefaces.model.StreamedContent;

import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.MensagemDao;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.MensagemDaoImp;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Funcaoeclesiasticas;
import br.com.pais.entities.Mensagem;
import br.com.pais.exception.GenericException;
import br.com.pais.exception.ValidarCPFException;
import br.com.pais.mensagens.MessageManagerImpl;
import br.com.pais.util.ApplicationSecurityManager;
import br.com.pais.util.SendEMail;
import br.com.pais.util.ValidarCPF;

/**
 * @author manoel
 */

public class LoginBean {

	protected String cpf = "";
	protected String senha = null;
	protected Focus focus;
	protected String email = null;
	private StreamedContent image;
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();

	protected boolean editar = true;
	
	private List<Discipulos> ListaEquipe1 = new ArrayList<Discipulos>();
	private List<Discipulos> ListaEquipe2 = new ArrayList<Discipulos>();
	private List<Discipulos> ListaEquipe3 = new ArrayList<Discipulos>();
	private List<Discipulos> ListaEquipe4 = new ArrayList<Discipulos>();
	protected Discipulos discipulos = new Discipulos();
	protected DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private boolean le1Exibir = false;
	private boolean le2Exibir = false;
	private boolean le3Exibir = false;
	private boolean le4Exibir = false;
	private boolean cadCelulas = false;

	public String logar() {

		try {

			if (ValidarCPF.validarCPF(cpf) == true) {
				discipulos = discipuloDao.encontrarPorCPF(cpf);
				
				if (discipulos != null  && discipulos.getDisSenha().equals(senha)) {
					discipuloSessao.setDiscipulos(discipulos);
					ListaEquipe1 = new ArrayList<Discipulos>();
					ListaEquipe2 = new ArrayList<Discipulos>();
					ListaEquipe3 = new ArrayList<Discipulos>();
					ListaEquipe4 = new ArrayList<Discipulos>();
					ListaEquipe1.addAll(discipuloDao.listarM12(discipuloSessao.getDiscipulos().getDisCod(), 1));
					ListaEquipe2.addAll(discipuloDao.listarM12(discipuloSessao.getDiscipulos().getDisCod(), 2));
					ListaEquipe3.addAll(discipuloDao.listarM12(discipuloSessao.getDiscipulos().getDisCod(), 3));
					ListaEquipe4.addAll(discipuloDao.listarM12(discipuloSessao.getDiscipulos().getDisCod(), 4));
					le1Exibir = !ListaEquipe1.isEmpty();
					le2Exibir = !ListaEquipe2.isEmpty();
					le3Exibir = !ListaEquipe3.isEmpty();
					le4Exibir = !ListaEquipe4.isEmpty();
					cadCelulas = discipuloDao.listarDiscipulos(discipuloSessao.getDiscipulos().getDisCod()).size()>=3;
				       
					return "/principal.mir";
				} else {
					throw new GenericException("usuario.invalido_detail");
				}
			} else {
				throw new GenericException("usuario.invalido_detail");
			}
		} catch (ValidarCPFException e) {
			// TODO Auto-generated catch block
			cpf = "";
			//focus.setFor("cpfMask");
			editar = true;
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",
					"usuario.invalido_detail");
			return "/login.mir";
		} catch (GenericException e) {
			cpf = "";
			//focus.setFor("cpfMask");
			editar = true;
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "erro",
					"CPF ou SENHA inválida");
			facesContext.addMessage(null, message);
			// MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR,
			// "erro", "usuario.invalido_detail");
			return "/login.mir";
		}
	}

	public String sairAplicacao() {
		cpf = null;
		senha =null;
		email = null;
		discipuloSessao.removeUsuario();
		discipulos = null;
		ListaEquipe1 = null;
		ListaEquipe2 = null;
		ListaEquipe3 = null;
		ListaEquipe4 = null;
		
		return "/login.mir";
	}

	public void validarcpf(ActionEvent ev) {
		/* public void verificacpf(){ */
		try {
			if (ValidarCPF.validarCPF(cpf) == true) {
				/* ev.getComponent().getId(); */
				editar = false;
				focus.setFor("senhaSecret");
			} else {
				cpf = "";
				focus.setFor("cpfMask");

			}
		} catch (ValidarCPFException e) {
			// TODO Auto-generated catch block
			cpf = "";
			focus.setFor("cpfMask");
		}
	}

	public void esqueciSenha() {
		List<Funcaoeclesiasticas> listaFuncaoEclesiasticas = new ArrayList<Funcaoeclesiasticas>();
		/* Verifica o cpf se é válido */
		try {
			if (ValidarCPF.validarCPF(cpf) == true) {
				discipulos = discipuloDao.encontrarPorCPF(cpf);
				if(discipulos.getDisemail().equals(email)){
					
					new SendEMail().sendMailLembreteSenha(discipulos.getFuncaoeclesiasticas().getFunDescricao(),
							discipulos.getDisnome(), discipulos.getDisemail(),
							discipulos.getDisSenha(), discipulos.getDisCpf());
				}
				else{
					cpf = null;
					email = null;
					MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "erro",
					"erro.emailNaoCadastrado_detail");
				}
	
			} else {
				cpf = null;
				email = null;
				MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "erro",
				"erro.emailNaoCadastrado_detail");
				// focus.setFor("cpfMaskDialog");
			}
		} catch (ValidarCPFException e) {
			// TODO Auto-generated catch block
			cpf = null;
			email = null;
			// focus.setFor("cpfMaskDialog");
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
	 * /**
	 * 
	 * @return the focus
	 */
	public Focus getFocus() {
		return focus;
	}

	/**
	 * @param focus
	 *            the focus to set
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
	 * @param editar
	 *            the editar to set
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
	 * @param email
	 *            the email to set
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
	 * @param senha
	 *            the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * @return the discipuloSessao
	 */
	public ApplicationSecurityManager getDiscipuloSessao() {
		return discipuloSessao;
	}

	/**
	 * @param discipuloSessao
	 *            the discipuloSessao to set
	 */
	public void setDiscipuloSessao(ApplicationSecurityManager discipuloSessao) {
		this.discipuloSessao = discipuloSessao;
	}

	

	/**
	 * @return the listaEquipe1
	 */
	public List<Discipulos> getListaEquipe1() {
		return ListaEquipe1;
	}

	/**
	 * @param listaEquipe1 the listaEquipe1 to set
	 */
	public void setListaEquipe1(List<Discipulos> listaEquipe1) {
		ListaEquipe1 = listaEquipe1;
	}

	/**
	 * @return the listaEquipe2
	 */
	public List<Discipulos> getListaEquipe2() {
		return ListaEquipe2;
	}

	/**
	 * @param listaEquipe2 the listaEquipe2 to set
	 */
	public void setListaEquipe2(List<Discipulos> listaEquipe2) {
		ListaEquipe2 = listaEquipe2;
	}

	/**
	 * @return the listaEquipe3
	 */
	public List<Discipulos> getListaEquipe3() {
		return ListaEquipe3;
	}

	/**
	 * @param listaEquipe3 the listaEquipe3 to set
	 */
	public void setListaEquipe3(List<Discipulos> listaEquipe3) {
		ListaEquipe3 = listaEquipe3;
	}

	/**
	 * @return the listaEquipe4
	 */
	public List<Discipulos> getListaEquipe4() {
		return ListaEquipe4;
	}

	/**
	 * @param listaEquipe4 the listaEquipe4 to set
	 */
	public void setListaEquipe4(List<Discipulos> listaEquipe4) {
		ListaEquipe4 = listaEquipe4;
	}

	/**
	 * @return the le1Exibir
	 */
	public boolean isLe1Exibir() {
		return le1Exibir;
	}

	/**
	 * @param le1Exibir the le1Exibir to set
	 */
	public void setLe1Exibir(boolean le1Exibir) {
		this.le1Exibir = le1Exibir;
	}

	/**
	 * @return the le2Exibir
	 */
	public boolean isLe2Exibir() {
		return le2Exibir;
	}

	/**
	 * @param le2Exibir the le2Exibir to set
	 */
	public void setLe2Exibir(boolean le2Exibir) {
		this.le2Exibir = le2Exibir;
	}

	/**
	 * @return the le3Exibir
	 */
	public boolean isLe3Exibir() {
		return le3Exibir;
	}

	/**
	 * @param le3Exibir the le3Exibir to set
	 */
	public void setLe3Exibir(boolean le3Exibir) {
		this.le3Exibir = le3Exibir;
	}

	/**
	 * @return the le4Exibir
	 */
	public boolean isLe4Exibir() {
		return le4Exibir;
	}

	/**
	 * @param le4Exibir the le4Exibir to set
	 */
	public void setLe4Exibir(boolean le4Exibir) {
		this.le4Exibir = le4Exibir;
	}

	/**
	 * @return the cadCelulas
	 */
	public boolean isCadCelulas() {
		return cadCelulas;
	}

	/**
	 * @param cadCelulas the cadCelulas to set
	 */
	public void setCadCelulas(boolean cadCelulas) {
		this.cadCelulas = cadCelulas;
	}

}
