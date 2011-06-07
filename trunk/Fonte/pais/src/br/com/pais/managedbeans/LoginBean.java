/**
 * 
 */
package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.focus.Focus;

import br.com.pais.dao.CelulaDao;
import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.impl.CelulaDaoImp;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Discipulos;
import br.com.pais.exception.GenericException;
import br.com.pais.exception.ValidarCPFException;
import br.com.pais.mensagens.MessageManagerImpl;
import br.com.pais.util.ApplicationSecurityManager;
import br.com.pais.util.Criptografia;
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
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();

	protected boolean editar = true;
	private boolean cadCelulas = false;
	private boolean menuBases = false;
	//Controle Acesso Menu Financeiro
	private boolean menuFazerRepasse = false;
	private boolean menuReceberRepasse = false;
	private boolean menuListarRecebidos = false;
	private boolean menuListarEnviados = false;
	private boolean menuArvoreRepasse = false;
	//Eventos
	private boolean menuEncontros = false;
	//Batismo
	private boolean menuBatismo = false;

	//List
	private List<Celulas> listaCelulas = new ArrayList<Celulas>();
	private List<Discipulos> listaDiscipulos = new ArrayList<Discipulos>();
	
	protected Discipulos discipulos = new Discipulos();
	protected DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	protected CelulaDao celulaDao = new CelulaDaoImp();
	

	public String logar() {

		try {
			if (ValidarCPF.validarCPF(cpf) == true) {
				discipulos = discipuloDao.encontrarPorCPF(cpf);
				
				if (discipulos != null  && discipulos.getDisSenha().equals(new Criptografia().criptografar(senha))) {
					discipuloSessao.setDiscipulos(discipulos);
					cadCelulas = discipuloDao.listarDiscipulos(discipuloSessao.getDiscipulos().getDisCod()).size()>=3;
				    
					//Controle Acesso no Menu Financeiro
					menuFinanceiro();
					menuEventos();
					menuBatismo();
					menuBases = discipuloSessao.getDiscipulos().getFuncaoeclesiasticas().getFunCod() >= 3;
					return "/principal.mir";
				} else {
					throw new GenericException("usuario.invalido_detail");
				}
			} else {
				throw new GenericException("usuario.invalido_detail");
			}
		} catch (ValidarCPFException e) {
			cpf = "";
			editar = true;
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "Erro","usuario.invalido_detail");
			return "/login.mir";
		} catch (GenericException e) {
			cpf = "";
			editar = true;
			FacesContext facesContext = FacesContext.getCurrentInstance();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro","CPF ou SENHA inválida!");
			facesContext.addMessage(null, message);
			return "/login.mir";
		}
	}

	public String sairAplicacao() {
		cpf = null;
		senha =null;
		email = null;
		discipuloSessao.removeUsuario();
		discipulos = null;
		
		return "/login.mir";
	}

	public void validarcpf(ActionEvent ev) {
		try {
			if (ValidarCPF.validarCPF(cpf) == true) {
				editar = false;
				focus.setFor("senhaSecret");
			} else {
				cpf = "";
				focus.setFor("cpfMask");

			}
		} catch (ValidarCPFException e) {
			cpf = "";
			focus.setFor("cpfMask");
		}
	}

	public void esqueciSenha() {
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
	
	//CONTROLE ACESSO MENU FINANCEIRO
	public void menuFinanceiro(){
		boolean possuiCelula = false;
		boolean funEclesiasticaMaiorDiscipulo = false;
		
		//Se ele for apostolo renner
		if(discipuloSessao.getDiscipulos().getDiscipulos() == null){
			menuArvoreRepasse = true;
			menuFazerRepasse = false;
			menuReceberRepasse = false;
			menuListarRecebidos = false;
			menuListarEnviados = false;
		}
		else{
			listaDiscipulos = new ArrayList<Discipulos>();
			listaDiscipulos.addAll(discipuloDao.listarDiscipulosLiderPraCima(discipuloSessao.getDiscipulos().getDisCod()));
			
			if(listaDiscipulos.size() == 0) funEclesiasticaMaiorDiscipulo = false;
			else funEclesiasticaMaiorDiscipulo = true;
			
			listaCelulas = new ArrayList<Celulas>();
			listaCelulas.addAll(celulaDao.listarCelulas(discipuloSessao.getDiscipulos().getDisCod()));
			
			if(listaCelulas.size() == 0) possuiCelula = false;
			else possuiCelula = true;
			
			//Receber Repasse
			if(possuiCelula == true && funEclesiasticaMaiorDiscipulo == true) menuReceberRepasse = true;
			else menuReceberRepasse = false;
			
			menuArvoreRepasse = false;
			menuFazerRepasse = possuiCelula;
			menuListarEnviados = possuiCelula;
			menuListarRecebidos = funEclesiasticaMaiorDiscipulo;
		}
	}
	
	public void menuEventos(){
		if(discipuloSessao.getDiscipulos().getFuncaoeclesiasticas().getFunCod() >= 7){
			menuEncontros = true;
		}
		else{
			menuEncontros = false;
		}
	}
	
	public void menuBatismo(){
		if(discipuloSessao.getDiscipulos().getFuncaoeclesiasticas().getFunCod() >= 7){
			menuBatismo = true;
			menuBatismo = true;
		}
		else{
			menuBatismo = false;
			menuBatismo = false;
		}
	}

	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Focus getFocus() {
		return focus;
	}
	public void setFocus(Focus focus) {
		this.focus = focus;
	}
	public boolean isEditar() {
		return editar;
	}
	public void setEditar(boolean editar) {
		this.editar = editar;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public ApplicationSecurityManager getDiscipuloSessao() {
		return discipuloSessao;
	}
	public void setDiscipuloSessao(ApplicationSecurityManager discipuloSessao) {
		this.discipuloSessao = discipuloSessao;
	}
	public boolean isCadCelulas() {
		return cadCelulas;
	}
	public void setCadCelulas(boolean cadCelulas) {
		this.cadCelulas = cadCelulas;
	}
	public boolean isMenuFazerRepasse() {
		return menuFazerRepasse;
	}
	public void setMenuFazerRepasse(boolean menuFazerRepasse) {
		this.menuFazerRepasse = menuFazerRepasse;
	}
	public boolean isMenuReceberRepasse() {
		return menuReceberRepasse;
	}
	public void setMenuReceberRepasse(boolean menuReceberRepasse) {
		this.menuReceberRepasse = menuReceberRepasse;
	}
	public List<Celulas> getListaCelulas() {
		return listaCelulas;
	}
	public void setListaCelulas(List<Celulas> listaCelulas) {
		this.listaCelulas = listaCelulas;
	}
	public List<Discipulos> getListaDiscipulos() {
		return listaDiscipulos;
	}
	public void setListaDiscipulos(List<Discipulos> listaDiscipulos) {
		this.listaDiscipulos = listaDiscipulos;
	}
	public boolean isMenuListarRecebidos() {
		return menuListarRecebidos;
	}
	public void setMenuListarRecebidos(boolean menuListarRecebidos) {
		this.menuListarRecebidos = menuListarRecebidos;
	}
	public boolean isMenuListarEnviados() {
		return menuListarEnviados;
	}
	public void setMenuListarEnviados(boolean menuListarEnviados) {
		this.menuListarEnviados = menuListarEnviados;
	}
	
	public boolean isMenuArvoreRepasse() {
		return menuArvoreRepasse;
	}

	public void setMenuArvoreRepasse(boolean menuArvoreRepasse) {
		this.menuArvoreRepasse = menuArvoreRepasse;
	}

	/**
	 * @return the menuBases
	 */
	public boolean isMenuBases() {
		return menuBases;
	}

	/**
	 * @param menuBases the menuBases to set
	 */
	public void setMenuBases(boolean menuBases) {
		this.menuBases = menuBases;
	}

	public boolean isMenuEncontros() {
		return menuEncontros;
	}

	public void setMenuEncontros(boolean menuEncontros) {
		this.menuEncontros = menuEncontros;
	}

	public boolean isMenuBatismo() {
		return menuBatismo;
	}

	public void setMenuBatismo(boolean menuBatismo) {
		this.menuBatismo = menuBatismo;
	}
}
