package br.com.pais.managedbeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import br.com.pais.dao.CelulaDao;
import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.GeracaoDao;
import br.com.pais.dao.LogradouroDao;
import br.com.pais.dao.impl.CelulaDaoImp;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.GeracaoDaoImp;
import br.com.pais.dao.impl.LogradouroDaoImp;
import br.com.pais.entities.Bairro;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Estado;
import br.com.pais.entities.Geracoes;
import br.com.pais.entities.Localidade;
import br.com.pais.entities.Logradouro;
import br.com.pais.util.ApplicationSecurityManager;

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
	private Discipulos[] dtDisSelecionadosAdicionar;
	private Discipulos[] dtDisSelecionadosRemover;
	private List<Discipulos> dtDisAdicionados = new ArrayList<Discipulos>();

	// Objetos Daos
	private LogradouroDao logradouroDao = new LogradouroDaoImp();
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp(); 
	private CelulaDao celulaDao = new CelulaDaoImp();
	private  GeracaoDao geracaoDao = new GeracaoDaoImp();
	
	private List<Celulas> listaCelulas = new ArrayList<Celulas>();
	private List<Geracoes> listaGeracoes = new ArrayList<Geracoes>();
	private List<Discipulos> listaM12 = new ArrayList<Discipulos>();

	public void buscarCEP(AjaxBehaviorEvent event) {
		logradouro = logradouroDao.encontrarPorCEP(getLogradouro().getCep());
		if (logradouro == null) {
			celulas.setCelNuEndereco(null);
			celulas.setCelEndComplemento(null);
		}
	}
	
	public SelectItem[] getGeracaoCombo() {
		listaGeracoes = new ArrayList<Geracoes>();
		listaGeracoes.addAll(geracaoDao.listarGeracoes());
		
		listaCelulas = new ArrayList<Celulas>();
    	listaCelulas.addAll(celulaDao.listarCelulas(discipuloSessao.getDiscipulos().getDisCod()));
    	
    	List<Geracoes> listGerTemp = new ArrayList<Geracoes>(listaGeracoes);
	    for (Geracoes ger : listaGeracoes) {
	    	
	    	for(Celulas cel : listaCelulas){
	    		if(cel.getGeracoes().getGerCod().equals(ger.getGerCod())){
	    			listGerTemp.remove(ger);
	    		}
	    	}
	    }
	    
	    listaGeracoes = new ArrayList<Geracoes>();
		listaGeracoes.addAll(listGerTemp);
		
		List<SelectItem> itens = new ArrayList<SelectItem>(listaGeracoes.size());
		
		for(Geracoes g : listaGeracoes) {
			itens.add(new SelectItem(g.getGerCod(), g.getGerDescricao()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}
	
	public void listarM12PorGeracao(AjaxBehaviorEvent event) {		
		listaM12 = new ArrayList<Discipulos>();
	    listaM12.addAll(discipuloDao.listarM12(discipuloSessao.getDiscipulos().getDisCod(), geracoes.getGerCod()));
	    
	    dtDisAdicionados = new ArrayList<Discipulos>();
	}
	
	public String adicionarDiscipulos() {  
		
		for(Discipulos dis: dtDisSelecionadosAdicionar){
			dtDisAdicionados.add(dis);
		}
		
		List<Discipulos> listTemp = new ArrayList<Discipulos>(listaM12);
	    for (Discipulos dis : dtDisAdicionados) {
	    	
	    	for (Discipulos dis2 : listaM12){
	    		if(dis.getDisCod().equals(dis2.getDisCod())){
	    			listTemp.remove(dis2);
	    		}
	    	}
	    }
	    
	    listaM12 = new ArrayList<Discipulos>();
	    listaM12.addAll(listTemp);
	    
		return null;
    }
	
    public String removerDiscipulos() {  
		
		for(Discipulos dis: dtDisSelecionadosRemover){
			listaM12.add(dis);
		}
		
		
		List<Discipulos> listTemp = new ArrayList<Discipulos>(dtDisAdicionados);
	    for (Discipulos dis : listaM12) {
	    	
	    	for (Discipulos dis2 : dtDisAdicionados){
	    		if(dis.getDisCod().equals(dis2.getDisCod())){
	    			listTemp.remove(dis2);
	    		}
	    	}
	    }
	    
	    dtDisAdicionados = new ArrayList<Discipulos>();
	    dtDisAdicionados.addAll(listTemp);
	    
		return null;
    }

	public String prepararCelula() {
		
		logradouro = new Logradouro();
		celulas = new Celulas();
		geracoes = new Geracoes();
		
	    listaM12 = new ArrayList<Discipulos>();
	    dtDisAdicionados = new ArrayList<Discipulos>();
				
		return "/cad/celulasCadastro.mir";
	}
	
	public String prepararEdicao() {
		logradouro = new Logradouro();
		celulas = new Celulas();
		geracoes = new Geracoes();
		
		logradouro = celulaSelecionada.getLogradouro(); 
		geracoes = celulaSelecionada.getGeracoes();
		
	    celulas = celulaSelecionada;	
	    celulas.setLogradouro(logradouro);
	    
	    listaM12 = new ArrayList<Discipulos>();
	    listaM12.addAll(discipuloDao.listarM12(discipuloSessao.getDiscipulos().getDisCod(), geracoes.getGerCod()));
	    
	    dtDisAdicionados = new ArrayList<Discipulos>();
	    dtDisAdicionados.addAll(celulas.getDiscipuloses());
	    
	    List<Discipulos> listTemp = new ArrayList<Discipulos>(listaM12);
	    for (Discipulos dis : dtDisAdicionados) {
	    	
	    	for (Discipulos dis2 : listaM12){
	    		if(dis.getDisCod().equals(dis2.getDisCod())){
	    			listTemp.remove(dis2);
	    		}
	    	}
	    }
	    
	    listaM12 = new ArrayList<Discipulos>();
	    listaM12.addAll(listTemp);
				
		return "/cad/celulasEditar.mir";
	}
	
    public String prepararListarCelula() {
    	listaCelulas = new ArrayList<Celulas>();
    	listaCelulas.addAll(celulaDao.listarCelulas(discipuloSessao.getDiscipulos().getDisCod()));
		return "/cad/celulasListar.mir";
	}
	
	
	public String salvar() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		celulas.setDiscipulos(discipuloSessao.getDiscipulos());
		celulas.setBases(null);
		celulas.setLogradouro(logradouro);
		celulas.setGeracoes(geracoes);
		celulas.setCelStatus(null);
		celulas.setDiscipuloses(dtDisAdicionados);
		
		if (celulaDao.salvar(celulas) == (true)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERRO!!!","Célula cadastrada!"));
			listaCelulas = new ArrayList<Celulas>();
	    	listaCelulas.addAll(celulaDao.listarCelulas(discipuloSessao.getDiscipulos().getDisCod()));
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","Célula não cadastrada!"));
		}
		
		return "/cad/celulasListar.mir";
	}
	
	public void excluir(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		
		celulaDao.excluir(celulaSelecionada,celulaSelecionada.getCelCod());
		listaCelulas = new ArrayList<Celulas>();
    	listaCelulas.addAll(celulaDao.listarCelulas(discipuloSessao.getDiscipulos().getDisCod()));
    	
    	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERRO!!!","Célula excluida!"));
	}
	
	public String alterar() {
		FacesContext context = FacesContext.getCurrentInstance();
		
		celulas.setDiscipulos(discipuloSessao.getDiscipulos());
		celulas.setLogradouro(logradouro);
		celulas.setDiscipuloses(dtDisAdicionados);
		celulaDao.atualizar(celulas);
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERRO!!!","Célula editada!"));
		listaCelulas = new ArrayList<Celulas>();
    	listaCelulas.addAll(celulaDao.listarCelulas(discipuloSessao.getDiscipulos().getDisCod()));
		
    	return "/cad/celulasListar.mir";
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
	
	public List<Discipulos> getDtDisAdicionados() {
		return dtDisAdicionados;
	}

	public void setDtDisAdicionados(List<Discipulos> dtDisAdicionados) {
		this.dtDisAdicionados = dtDisAdicionados;
	}

	public void setDtDisSelecionadosAdicionar(
			Discipulos[] dtDisSelecionadosAdicionar) {
		this.dtDisSelecionadosAdicionar = dtDisSelecionadosAdicionar;
	}

	public Discipulos[] getDtDisSelecionadosAdicionar() {
		return dtDisSelecionadosAdicionar;
	}

	public void setDtDisSelecionadosRemover(Discipulos[] dtDisSelecionadosRemover) {
		this.dtDisSelecionadosRemover = dtDisSelecionadosRemover;
	}

	public Discipulos[] getDtDisSelecionadosRemover() {
		return dtDisSelecionadosRemover;
	}

	public void setListaGeracoes(List<Geracoes> listaGeracoes) {
		this.listaGeracoes = listaGeracoes;
	}

	public List<Geracoes> getListaGeracoes() {
		return listaGeracoes;
	}
}
