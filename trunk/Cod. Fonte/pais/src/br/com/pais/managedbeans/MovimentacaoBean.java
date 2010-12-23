package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

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
	
	public String listarCelulasMovimento(){
		listaCelulas = new ArrayList<Celulas>();
        listaCelulas.addAll(celulaDao.listarCelulas(discipuloSessao.getDiscipulos().getDisCod()));
		return "/cad/movimentoCadastro.mir";
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
