package br.com.pais.managedbeans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.GeracaoDao;
import br.com.pais.dao.MensagemDao;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.GeracaoDaoImp;
import br.com.pais.dao.impl.MensagemDaoImp;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Geracoes;
import br.com.pais.entities.Mensagem;
import br.com.pais.util.ApplicationSecurityManager;
import br.com.pais.util.SendEMail;

public class MensagemBean {
	
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();
	private Discipulos discipulos = new Discipulos();
	private Celulas celulas = new Celulas();
	private Geracoes geracoes = new Geracoes();
	private Mensagem mensagem = new Mensagem();
	
	private Mensagem mensagemSelecionada;

	//List
	private List<Geracoes> listaGeracoes = new ArrayList<Geracoes>();
	private List<Discipulos> listaDiscipulos = new ArrayList<Discipulos>();
	private List<Mensagem> listaRecebidas = new ArrayList<Mensagem>();
	private List<Mensagem> listaEnviadas = new ArrayList<Mensagem>();

	//Daos
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private  GeracaoDao geracaoDao = new GeracaoDaoImp();
	private  MensagemDao mensagemDao = new MensagemDaoImp();

	private boolean dtM12 = false;
	private boolean EnviarMensagemSelecionados = true;
	private int qtdMensagem;
	private boolean responder = false;

	//discipulosSelecionados Grid
	private Discipulos[] DiscipuloSelecionados;
	
	public SelectItem[] getGeracaoCombo() {
		listaGeracoes = new ArrayList<Geracoes>();
		listaGeracoes.addAll(geracaoDao.listarGeracoes());
		List<SelectItem> itens = new ArrayList<SelectItem>(listaGeracoes.size());
		
		for(Geracoes g : listaGeracoes) {
			itens.add(new SelectItem(g.getGerCod(), g.getGerDescricao()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}
	
	public String prepararMensagem() {
		celulas = new Celulas();
		geracoes = new Geracoes();
		discipulos = new Discipulos();
		mensagem = new Mensagem();
		
		dtM12 = false;
		listaDiscipulos = new ArrayList<Discipulos>();
		
		return "/cad/mensagensCadastro.mir";
	}
	
	public String prepararListarMensagem() {	
	   return "/principal.mir";
	}
	
	public String checkResponder(){
		if(responder == true)responder = true;
		else responder = false;
		return null;
	}
	
	public String atualizarStatusMensagem(){
		mensagemSelecionada.setMensLida("S");
		mensagemDao.atualizar(mensagemSelecionada);
		
		responder = false;
		mensagem = new Mensagem();
		return null;
	}
	
	public String caixaEntrada(){
		listaRecebidas = new ArrayList<Mensagem>();
        listaRecebidas.addAll(mensagemDao.listarCaixaEntrada(discipuloSessao.getDiscipulos()));
		return "/cad/mensagensRecebidas.mir";
	}
	
	public String caixaSaida(){
		listaEnviadas = new ArrayList<Mensagem>();
        listaEnviadas.addAll(mensagemDao.listarCaixaSaida(discipuloSessao.getDiscipulos()));
		return "/cad/mensagensEnviadas.mir";
	}

	public void listarM12PorGeracao(AjaxBehaviorEvent event) {		
		listaDiscipulos = new ArrayList<Discipulos>();
		listaDiscipulos.addAll(discipuloDao.listarM12(discipuloSessao.getDiscipulos().getDisCod(), celulas.getCelGeracao()));
		
		if(celulas.getCelGeracao()==0)dtM12  = false;
		else dtM12 = true;
	}
	
	public Date pegaDataAtual() throws Exception {   
		Date dataAtual = new Date(System.currentTimeMillis());    
		SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
		
		String dataString = formatarDate.format(dataAtual);
	
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
		java.sql.Date data = new java.sql.Date(format.parse(dataString).getTime()); 
		
        return data;  
    }
	
	public void enviarSelecionados(ActionEvent event) throws Exception{
		mensagem = new Mensagem();
		EnviarMensagemSelecionados = true;
	}
	
	public void enviarTodos(ActionEvent event) throws Exception{
		mensagem = new Mensagem();
		EnviarMensagemSelecionados = false;
	}
	
	public void enviarMensagem(ActionEvent event) throws Exception{
		String mensTexto = mensagem.getMensTexto();
		
		if(EnviarMensagemSelecionados == true)EnviarSelecionados(mensTexto);
		else EnviarTodos(mensTexto);
	}
	
	public void EnviarSelecionados(String mensTexto) throws Exception{
		FacesContext context = FacesContext.getCurrentInstance();
		if(DiscipuloSelecionados.length == 0)
		{
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!!!","Mensagem não enviada selecione algum discipulo!"));
		}
		else
		{
			for(Discipulos dis : DiscipuloSelecionados)
			{
				mensagem = new Mensagem();	
				mensagem.setMensTexto(mensTexto);
				mensagem.setMensData(pegaDataAtual());
				mensagem.setDiscipulosByMensDisCod(discipuloSessao.getDiscipulos());
				mensagem.setDiscipulosByMensDisCodRecebe(dis);
				mensagem.setMensLida("N");
				mensagemDao.salvar(mensagem);
				
				new SendEMail().sendSimpleMailEnviarMensagem(discipuloSessao.getDiscipulos().getDisnome(), dis.getDisnome(), dis.getDisemail());
			}
			listaDiscipulos = new ArrayList<Discipulos>();
			listaDiscipulos.addAll(discipuloDao.listarM12(discipuloSessao.getDiscipulos().getDisCod(), celulas.getCelGeracao()));
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!!!","Mensagem Enviada com Sucesso!"));
		}
	}
	
	public void EnviarTodos(String mensTexto) throws Exception{
		FacesContext context = FacesContext.getCurrentInstance();
		for(Discipulos dis : listaDiscipulos)
		{
			mensagem = new Mensagem();	
			mensagem.setMensTexto(mensTexto);
			mensagem.setMensData(pegaDataAtual());
			mensagem.setDiscipulosByMensDisCod(discipuloSessao.getDiscipulos());
			mensagem.setDiscipulosByMensDisCodRecebe(dis);
			mensagem.setMensLida("N");
			mensagemDao.salvar(mensagem);
			
			new SendEMail().sendSimpleMailEnviarMensagem(discipuloSessao.getDiscipulos().getDisnome(), dis.getDisnome(), dis.getDisemail());
		}
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!!!","Mensagem Enviada com Sucesso!"));
	}
	
	public void responderMensagem() throws Exception{
		    FacesContext context = FacesContext.getCurrentInstance();
			mensagem.setMensData(pegaDataAtual());
			mensagem.setDiscipulosByMensDisCod(mensagemSelecionada.getDiscipulosByMensDisCodRecebe());
			mensagem.setDiscipulosByMensDisCodRecebe(mensagemSelecionada.getDiscipulosByMensDisCod());
			mensagem.setMensLida("N");
			mensagemDao.salvar(mensagem);
			
			//new SendEMail().sendSimpleMailEnviarMensagem(discipuloSessao.getDiscipulos().getDisnome(), dis.getDisnome(), dis.getDisemail());
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!!!","Resposta Enviada com Sucesso!"));
	}
	
	public void setDiscipuloSessao(ApplicationSecurityManager discipuloSessao) {
		this.discipuloSessao = discipuloSessao;
	}

	public ApplicationSecurityManager getDiscipuloSessao() {
		return discipuloSessao;
	}
	
	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	public Discipulos getDiscipulos() {
		return discipulos;
	}
	
	public Celulas getCelulas() {
		return celulas;
	}

	public void setCelulas(Celulas celulas) {
		this.celulas = celulas;
	}

	public void setGeracoes(Geracoes geracoes) {
		this.geracoes = geracoes;
	}

	public Geracoes getGeracoes() {
		return geracoes;
	}
	
	public Mensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}
	
	public List<Discipulos> getListaDiscipulos() {
		return listaDiscipulos;
	}

	public void setListaDiscipulos(List<Discipulos> listaDiscipulos) {
		this.listaDiscipulos = listaDiscipulos;
	}
	
	public List<Geracoes> getListaGeracoes() {
		return listaGeracoes;
	}

	public void setListaGeracoes(List<Geracoes> listaGeracoes) {
		this.listaGeracoes = listaGeracoes;
	}

	public void setDiscipuloSelecionados(Discipulos[] discipuloSelecionados) {
		DiscipuloSelecionados = discipuloSelecionados;
	}

	public Discipulos[] getDiscipuloSelecionados() {
		return DiscipuloSelecionados;
	}
	
	public boolean isDtM12() {
		return dtM12;
	}

	public void setDtM12(boolean dtM12) {
		this.dtM12 = dtM12;
	}
	
	public boolean isEnviarMensagemSelecionados() {
		return EnviarMensagemSelecionados;
	}

	public void setEnviarMensagemSelecionados(boolean enviarMensagemSelecionados) {
		EnviarMensagemSelecionados = enviarMensagemSelecionados;
	}
	
	public List<Mensagem> getListaRecebidas() {
		return listaRecebidas;
	}

	public void setListaRecebidas(List<Mensagem> listaRecebidas) {
		this.listaRecebidas = listaRecebidas;
	}

	public List<Mensagem> getListaEnviadas() {
		return listaEnviadas;
	}

	public void setListaEnviadas(List<Mensagem> listaEnviadas) {
		this.listaEnviadas = listaEnviadas;
	}

	public Mensagem getMensagemSelecionada() {
		return mensagemSelecionada;
	}

	public void setMensagemSelecionada(Mensagem mensagemSelecionada) {
		this.mensagemSelecionada = mensagemSelecionada;
	}
	
	public boolean isResponder() {
		return responder;
	}

	public void setResponder(boolean responder) {
		this.responder = responder;
	}
	
	public int getQtdMensagem() {
		listaRecebidas = new ArrayList<Mensagem>();
        listaRecebidas.addAll(mensagemDao.listarMensagensRecebidas(discipuloSessao.getDiscipulos()));
        qtdMensagem = listaRecebidas.size();
		return qtdMensagem;
	}

	public void setQtdMensagem(int qtdMensagem) {
		this.qtdMensagem = qtdMensagem;
	}
	
}
