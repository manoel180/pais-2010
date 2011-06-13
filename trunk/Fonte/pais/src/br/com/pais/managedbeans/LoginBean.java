package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;

import br.com.pais.dao.CelulaDao;
import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.UsuarioDao;
import br.com.pais.dao.impl.CelulaDaoImp;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.UsuarioDaoImp;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Usuarios;
import br.com.pais.exception.GenericException;
import br.com.pais.exception.ValidarCPFException;
import br.com.pais.mensagens.MessageManagerImpl;
import br.com.pais.util.ApplicationSecurityManager;
import br.com.pais.util.Criptografia;
import br.com.pais.util.SendEMail;
import br.com.pais.util.ValidarCPF;
import br.com.pais.util.ValidarEmail;

public class LoginBean {
	protected String login = null;
	protected String cpf = "";
	protected String senha = null;
	protected String cpfDlg = "";
	protected String emailDlg = null;
	private boolean enviarEmailLembrar = true;
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();
	protected Discipulos discipulos = new Discipulos();
	protected DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private ApplicationSecurityManager usuarioSessao = new ApplicationSecurityManager();
	protected Usuarios usuario = new Usuarios();
	protected UsuarioDao  usuarioDao = new UsuarioDaoImp();

	//Controle Acesso Menu Celulas
	private boolean menuCelulas = false;
	//Controle Acesso Menu Bases
	private boolean menuBases = false;
	//Controle Acesso Menu Financeiro
	private boolean menuFazerRepasse = false;
	private boolean menuReceberRepasse = false;
	private boolean menuListarRecebidos = false;
	private boolean menuListarEnviados = false;
	private boolean menuArvoreRepasse = false;
	//Controle Acesso Eventos
	private boolean menuEncontros = false;
	//Controle Acesso Batismo
	private boolean menuBatismo = false;
	
	private String menuUsuario = "";

	//List
	private List<Celulas> listaCelulas = new ArrayList<Celulas>();
	private List<Discipulos> listaDiscipulos = new ArrayList<Discipulos>();
	protected CelulaDao celulaDao = new CelulaDaoImp();
	
	public boolean validacaoLogin(){
		int contadorValidador = 0;
		if (cpf.isEmpty()) {
			contadorValidador++;
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",
					"erro.loginCpfEmBranco");
		}
		if (senha.isEmpty()) {
			contadorValidador++;
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",
					"erro.loginSenhaEmBranco");
		}
		
		if (contadorValidador <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public String logar() throws GenericException {
		String retorno = null;
		if(validacaoLogin()){
			try {
				if(ValidarCPF.validarCPF(cpf) == true) {
					discipulos = discipuloDao.encontrarPorCPF(cpf);
					if (discipulos != null  && discipulos.getDisSenha().equals(new Criptografia().criptografar(senha))) {
						discipuloSessao.setDiscipulos(discipulos);
						//Controle Acesso nos Menus
						menuCelulas();
						menuBases();
						menuFinanceiro();
						menuEventos();
						menuBatismo();
						
						retorno = "/principal.mir";
					}
					else{
						try {
							throw new GenericException("usuario.invalido_detail");
						} catch (GenericException e) {
							MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR,"erro", e.getMessage());
						}
						finally{
							retorno = null;
						}
					}
				}
			} catch (ValidarCPFException e) {
				retorno = null;
				MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR,"erro", e.getMessage());
			}
		}
		return retorno;
	}
	
	
	public boolean validacaoPainel(){
		int contadorValidador = 0;
		if (login.isEmpty()) {
			contadorValidador++;
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",
					"erro.loginUsuarioEmBranco");
		}
		if (senha.isEmpty()) {
			contadorValidador++;
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",
					"erro.loginSenhaEmBranco");
		}
		
		if (contadorValidador <= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public String logarPainel(){
		String retorno = null;
		if(validacaoPainel()){
			usuario = usuarioDao.encontrarPorlogin(login);
			if(usuario != null  && usuario.getSenha().equals(new Criptografia().criptografar(senha))) {
				usuarioSessao.setUsuarios(usuario);
				//Controle Acesso nos Menus
				controleMenusPainel(usuario);
				retorno = "/principal_painel.mir";
			}
			else{
				try {
					throw new GenericException("usuario.invalido_detail");
				} catch (GenericException e) {
					MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR,"erro", e.getMessage());
				}
				finally{
					retorno = null;
				}
			}
		}
		return retorno;
	}
	
	public void controleMenusPainel(Usuarios usuario){
		if(usuario.getTipousuarios().getTipUsuCod() == 1){
			menuUsuario = "menu_painel.xhtml";
		}else if(usuario.getTipousuarios().getTipUsuCod() == 2){
			menuUsuario = "menu_secretaria.xhtml";
		}
	}

	public String sairAplicacao() {
		cpf = null;
		senha =null;
		cpfDlg = null;
		emailDlg = null;
		discipuloSessao.removeUsuario();
		discipulos = null;
		
		return "/login.mir";
	}
	
	public String sairPainel() {
		login = null;
		senha = null;
		cpfDlg = null;
		emailDlg = null;
		usuarioSessao.removeUsuario();
		usuario = new Usuarios();
		
		return "/painel.mir";
	}
	
	public boolean validacaoDlgLembrarSenha(){
		int contadorValidador = 0;
		if (cpfDlg.isEmpty()) {
			contadorValidador++;
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",
					"erro.loginCpfEmBranco");
		}
		if (emailDlg.isEmpty()) {
			contadorValidador++;
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",
					"erro.loginEmailEmBranco");
		}else{
			if(!ValidarEmail.validarEmail(emailDlg)){
				contadorValidador++;
				MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",
						"erro.emailInvalido_detail");
			}
		}
		
		if (contadorValidador <= 0) {
			return true;
		} else {
			return false;
		}
	}

	public void esqueciSenha() {
		if(validacaoDlgLembrarSenha()){
			try {
				if (ValidarCPF.validarCPF(cpfDlg) == true) {
					discipulos = discipuloDao.encontrarPorCPF(cpfDlg);
					if(discipulos.getDisemail().equals(emailDlg)){
						enviarEmailLembrarSenha();
					}
					else{
						cpfDlg = null;
						emailDlg = null;
						MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",
						"erro.emailNaoCadastrado_detail");
					}
				}
			} catch (ValidarCPFException e) {
				MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR,"erro", e.getMessage());
			}
		}
	}
	
	public void enviarEmailLembrarSenha(){
		if(enviarEmailLembrar){
			new SendEMail().sendMailLembreteSenha(discipulos.getFuncaoeclesiasticas().getFunDescricao(),
					discipulos.getDisnome(), discipulos.getDisemail(),
					discipulos.getDisSenha(), discipulos.getDisCpf());
		}
	}
	
	public void menuCelulas(){
		if(discipuloSessao.getDiscipulos().getFuncaoeclesiasticas().getFunCod() >= 3){
			menuCelulas = true;
		}
	}
	
	public void menuBases(){
		if(discipuloSessao.getDiscipulos().getFuncaoeclesiasticas().getFunCod() >= 3){
			menuBases = true;
		}
	}
	
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
		}else{
			menuEncontros = false;
		}
	}
	
	public void menuBatismo(){
		if(discipuloSessao.getDiscipulos().getFuncaoeclesiasticas().getFunCod() >= 7){
			menuBatismo = true;
		}else{
			menuBatismo = false;
		}
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCpfDlg() {
		return cpfDlg;
	}

	public void setCpfDlg(String cpfDlg) {
		this.cpfDlg = cpfDlg;
	}

	public String getEmailDlg() {
		return emailDlg;
	}

	public void setEmailDlg(String emailDlg) {
		this.emailDlg = emailDlg;
	}

	public ApplicationSecurityManager getDiscipuloSessao() {
		return discipuloSessao;
	}

	public void setDiscipuloSessao(ApplicationSecurityManager discipuloSessao) {
		this.discipuloSessao = discipuloSessao;
	}

	public boolean isMenuCelulas() {
		return menuCelulas;
	}

	public void setMenuCelulas(boolean menuCelulas) {
		this.menuCelulas = menuCelulas;
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

	public boolean isMenuBases() {
		return menuBases;
	}

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

	public boolean isEnviarEmailLembrar() {
		return enviarEmailLembrar;
	}

	public void setEnviarEmailLembrar(boolean enviarEmailLembrar) {
		this.enviarEmailLembrar = enviarEmailLembrar;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public ApplicationSecurityManager getUsuarioSessao() {
		return usuarioSessao;
	}

	public void setUsuarioSessao(ApplicationSecurityManager usuarioSessao) {
		this.usuarioSessao = usuarioSessao;
	}

	public String getMenuUsuario() {
		return menuUsuario;
	}

	public void setMenuUsuario(String menuUsuario) {
		this.menuUsuario = menuUsuario;
	}
}
