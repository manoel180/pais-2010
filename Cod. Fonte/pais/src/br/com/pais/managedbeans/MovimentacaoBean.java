package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.pais.dao.CelulaDao;
import br.com.pais.dao.MovimentoDao;
import br.com.pais.dao.impl.CelulaDaoImp;
import br.com.pais.dao.impl.MovimentoDaoImp;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Mensagem;
import br.com.pais.entities.Movimento;
import br.com.pais.util.ApplicationSecurityManager;

public class MovimentacaoBean {
	
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();
	private Movimento movimento = new Movimento();

	private Celulas celulaSelecionada;
	
	//List
	private List<Celulas> listaCelulas = new ArrayList<Celulas>();
	
	//Daos
	private MovimentoDao movimentoDao = new MovimentoDaoImp();
	private CelulaDao celulaDao = new CelulaDaoImp();
	
	public String prepararMovimento(){		
		listaCelulas = new ArrayList<Celulas>();
        listaCelulas.addAll(celulaDao.listarCelulas(discipuloSessao.getDiscipulos().getDisCod()));
		return "/cad/movimentoListar.mir";
	}
	
	public String prepararCadastro(){
		movimento = new Movimento();
		return "/cad/movimentoCadastro";
	}
	
	public String salvarMovimento() {
		FacesContext context = FacesContext.getCurrentInstance();
		movimento.setCelulas(celulaSelecionada);
		movimento.setMovRecebido('N');
		movimento.setBases(null);
		if (movimentoDao.salvar(movimento) == (true)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERRO!!!","Movimento cadastrado!"));
			listaCelulas = new ArrayList<Celulas>();
	        listaCelulas.addAll(celulaDao.listarCelulas(discipuloSessao.getDiscipulos().getDisCod()));
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","C�lula n�o cadastrada!"));
		}
		return "/cad/movimentoListar.mir";
	}
	
	public ApplicationSecurityManager getDiscipuloSessao() {
		return discipuloSessao;
	}
	public void setDiscipuloSessao(ApplicationSecurityManager discipuloSessao) {
		this.discipuloSessao = discipuloSessao;
	}
	public Movimento getMovimento() {
		return movimento;
	}
	public void setMovimento(Movimento movimento) {
		this.movimento = movimento;
	}
	public Celulas getCelulaSelecionada() {
		return celulaSelecionada;
	}
	public void setCelulaSelecionada(Celulas celulaSelecionada) {
		this.celulaSelecionada = celulaSelecionada;
	}
	public List<Celulas> getListaCelulas() {
		return listaCelulas;
	}
	public void setListaCelulas(List<Celulas> listaCelulas) {
		this.listaCelulas = listaCelulas;
	}
}