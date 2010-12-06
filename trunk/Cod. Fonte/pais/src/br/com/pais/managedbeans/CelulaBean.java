package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.event.AjaxBehaviorEvent;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.model.DualListModel;

import br.com.pais.dao.CelulaDao;
import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.LogradouroDao;
import br.com.pais.dao.impl.CelulaDaoImp;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.LogradouroDaoImp;
import br.com.pais.entities.Bairro;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Estado;
import br.com.pais.entities.Geracoes;
import br.com.pais.entities.Localidade;
import br.com.pais.entities.Logradouro;
import br.com.pais.util.ApplicationSecurityManager;

/**
 * @author manoel
 */

public class CelulaBean {

	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();

	private Celulas celulas = new Celulas();
	private Geracoes geracoes = new Geracoes();
	private Discipulos discipulos = new Discipulos();
	private Estado estado = new Estado();
	private Localidade cidade = new Localidade();
	private Bairro bairro = new Bairro();
	private Logradouro logradouro = new Logradouro();
	
	//celulaSelecionada Grid
	private Celulas celulaSelecionada;
	private Discipulos[] discipulosSelecionados;
	private List<Discipulos> listDisSelecionados = new ArrayList<Discipulos>();

	// Objetos Daos
	private LogradouroDao logradouroDao = new LogradouroDaoImp();
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp(); 
	private CelulaDao celulaDao = new CelulaDaoImp();
	
	private List<Celulas> listaCelulas = new ArrayList<Celulas>();
	private List<Discipulos> listaM12 = new ArrayList<Discipulos>();

	private DualListModel<Discipulos> listaDiscipulos = new DualListModel<Discipulos>();
	private List<Discipulos> sourceDiscipulos = new ArrayList<Discipulos>();
	private List<Discipulos> targetDiscipulos = new ArrayList<Discipulos>();

	public void buscarCEP(AjaxBehaviorEvent event) {
		logradouro = logradouroDao.encontrarPorCEP(getLogradouro().getCep());
		if (logradouro == null) {
			celulas.setCelNuEndereco(null);
			celulas.setCelEndComplemento(null);
		}
	}
	
	public void listarM12PorGeracao(AjaxBehaviorEvent event) {
		sourceDiscipulos = new ArrayList<Discipulos>();
		targetDiscipulos = new ArrayList<Discipulos>();
		sourceDiscipulos.addAll(discipuloDao.listarM12(discipuloSessao.getDiscipulos().getDisCod(),celulas.getCelGeracao()));

		listaDiscipulos = new DualListModel<Discipulos>(sourceDiscipulos, targetDiscipulos);
	}
	
	public String adicionarDiscipulos() {  
		listDisSelecionados = new ArrayList<Discipulos>();
		for(Discipulos dis: discipulosSelecionados){
			//int i = 0;
			listDisSelecionados.add(dis);
				 //System.out.println("\n Nome "+listDisSelecionados.get(i).getDisnome());
		    //i++;
		 }
        return null;  
    }

	public String prepararCelula() {
		//esquerda
		sourceDiscipulos = new ArrayList<Discipulos>();
		//direita
		targetDiscipulos = new ArrayList<Discipulos>();
		sourceDiscipulos.addAll(discipuloDao.listarM12(discipuloSessao.getDiscipulos().getDisCod(),1));

		listaDiscipulos = new DualListModel<Discipulos>(sourceDiscipulos, targetDiscipulos);
		
		logradouro = new Logradouro();
		celulas = new Celulas();
		geracoes = new Geracoes();
				
		return "/cad/celulas.mir";
	}
	
	public String prepararEdicao() {
		logradouro = new Logradouro();
		celulas = new Celulas();
		geracoes = new Geracoes();
		
		logradouro = celulaSelecionada.getLogradouro(); 
		
	    celulas = celulaSelecionada;	
	    celulas.setLogradouro(celulaSelecionada.getLogradouro());
	    
	    listaM12 = new ArrayList<Discipulos>();
	    listaM12.addAll(discipuloDao.listarM12(discipuloSessao.getDiscipulos().getDisCod(), celulas.getCelGeracao()));
	    
	    listDisSelecionados = new ArrayList<Discipulos>();
	    
	    listDisSelecionados = new ArrayList<Discipulos>();
		for(Discipulos dis: celulas.getDiscipuloses()){
			listDisSelecionados.add(dis);
		 }
		
	    //Esquerda
		//sourceDiscipulos = new ArrayList<Discipulos>();
	    //Direita
		//targetDiscipulos = new ArrayList<Discipulos>();
		//targetDiscipulos.addAll(celulas.getDiscipuloses());
		//sourceDiscipulos.addAll(discipuloDao.listarDiscipulos(discipuloSessao.getDiscipulos().getDisCod()));
		
		//listaDiscipulos = new DualListModel<Discipulos>(sourceDiscipulos, targetDiscipulos);
		//listaDiscipulos.setTarget(celulas.getDiscipuloses());
		
		//Varre a listaDiscipulosCadastrados 
		/*
	    for (Discipulos dis : listaDiscipulosCadastrados) {
	    	System.out.println ("\n Tamanho da Lista "+listaDiscipulosCadastrados.size());
	    	System.out.println ("\n Teste "+listaDiscipulosCadastrados.get(0).getDisnome());
	        System.out.println ("\n" +dis.getDisCod());
	    }
	    
	    
		List<String> nomes = new ArrayList<String>();
	    nomes.add("Anderson");
	    nomes.add("rodrigo");
	    nomes.add("karina");
	    nomes.add("giovanni");
	    nomes.add("Anderson");
	    nomes.add("rodrigo"); 
			
	    List<String> temp = new ArrayList<String>(nomes);
		 for(String nome: nomes){
			 temp.remove(nome);
			  if(temp.contains(nome))
				 System.out.println("\n Nome repetido "+nome);
		 }
		 
		/*
	    List<String> nomes = new ArrayList<String>();
	    nomes.add("Anderson");
	    nomes.add("rodrigo");
	    nomes.add("karina");
	    nomes.add("giovanni");
	    nomes.add("Anderson");
	    nomes.add("rodrigo"); 
			
	    List<String> temp = new ArrayList<String>(nomes);
		 for(String nome: nomes){
			 temp.remove(nome);
			  if(temp.contains(nome))
				 System.out.println("\n Nome repetido "+nome);
				 
		 }
		 */
				
		return "/cad/celulasEditar.mir";
	}
	
    public String prepararListarCelula() {
    	listaCelulas = new ArrayList<Celulas>();
    	listaCelulas.addAll(celulaDao.listarCelulas(discipuloSessao.getDiscipulos().getDisCod()));
		return "/cad/celulasListar.mir";
	}
	
	
	public String salvar(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		celulas.setDiscipulos(discipuloSessao.getDiscipulos());
		celulas.setLogradouro(logradouro);
		celulas.setCelStatus("APROVADO");
		celulas.setDiscipuloses(listaDiscipulos.getTarget());

		if (celulaDao.salvar(celulas) == (true)) {
			//context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERRO!!!","Célula Cadastrada!"));
			return prepararListarCelula();
			
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!!!","Erro ao cadastrar!"));
		}
		return null;
	}
	
	public void alterar(ActionEvent event) {
		//FacesContext context = FacesContext.getCurrentInstance();
		
		celulas.setDiscipulos(discipuloSessao.getDiscipulos());
		celulas.setLogradouro(logradouro);
		celulas.setCelStatus("APROVADO");
		celulas.setDiscipuloses(listDisSelecionados);
		celulaDao.atualizar(celulas);
		
		/*
		if (celulaDao.atualizar(celulas)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!!!","Célula Cadastrada!"));
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!!!","Erro ao cadastrar!"));
		}
		*/
	}
	

	/**
	 * GETTERS and SETTERS
	 */
	
	/**
	 * @return the celulas
	 */
	public Celulas getCelulas() {
		return celulas;
	}

	/**
	 * @param celulas
	 *            the celulas to set
	 */
	public void setCelulas(Celulas celulas) {
		this.celulas = celulas;
	}
	
	public Geracoes getGeracoes() {
		return geracoes;
	}

	public void setGeracoes(Geracoes geracoes) {
		this.geracoes = geracoes;
	}

	/**
	 * @return the discipulos
	 */
	public Discipulos getDiscipulos() {
		return discipulos;
	}

	/**
	 * @param discipulos
	 *            the discipulos to set
	 */
	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
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
	 * @return the cidade
	 */
	public Localidade getCidade() {
		return cidade;
	}

	/**
	 * @param cidade
	 *            the cidade to set
	 */
	public void setCidade(Localidade cidade) {
		this.cidade = cidade;
	}

	/**
	 * @return the bairro
	 */
	public Bairro getBairro() {
		return bairro;
	}

	/**
	 * @param bairro
	 *            the bairro to set
	 */
	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	/**
	 * @return the logradouro
	 */
	public Logradouro getLogradouro() {
		return logradouro;
	}

	/**
	 * @param logradouro
	 *            the logradouro to set
	 */
	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}
	
	/**
	 * @param listaDiscipulos
	 *            the listaDiscipulos to set
	 */
	public void setListaDiscipulos(DualListModel<Discipulos> listaDiscipulos) {
		this.listaDiscipulos = listaDiscipulos;
	}
	
	/**
	 * @return the listaDiscipulos
	 */
	public DualListModel<Discipulos> getListaDiscipulos() {
		return listaDiscipulos;
	}
	
	public List<Celulas> getListaCelulas() {
		return listaCelulas;
	}

	public void setListaCelulas(List<Celulas> listaCelulas) {
		this.listaCelulas = listaCelulas;
	}
	
	public Celulas getCelulaSelecionada() {
		return celulaSelecionada;
	}

	public void setCelulaSelecionada(Celulas celulaSelecionada) {
		this.celulaSelecionada = celulaSelecionada;
	}
	
	public List<Discipulos> getListaM12() {
		return listaM12;
	}

	public void setListaM12(
			List<Discipulos> listaDiscipulosCadastrados) {
		this.listaM12 = listaDiscipulosCadastrados;
	}
	
	public Discipulos[] getDiscipulosSelecionados() {
		return discipulosSelecionados;
	}

	public void setDiscipulosSelecionados(Discipulos[] discipulosSelecionados) {
		this.discipulosSelecionados = discipulosSelecionados;
	}
	
	public List<Discipulos> getListDisSelecionados() {
		return listDisSelecionados;
	}

	public void setListDisSelecionados(List<Discipulos> listDisSelecionados) {
		this.listDisSelecionados = listDisSelecionados;
	}

}
