package br.com.pais.managedbeans;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.event.DateSelectEvent;

import br.com.pais.dao.CelulaDao;
import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.GeracaoDao;
import br.com.pais.dao.MovimentoDao;
import br.com.pais.dao.RelatorioDao;
import br.com.pais.dao.RepasseDao;
import br.com.pais.dao.impl.CelulaDaoImp;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.GeracaoDaoImp;
import br.com.pais.dao.impl.MovimentoDaoImp;
import br.com.pais.dao.impl.RelatorioDaoImp;
import br.com.pais.dao.impl.RepasseDaoImp;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Geracoes;
import br.com.pais.entities.Movimento;
import br.com.pais.entities.Repasse;
import br.com.pais.entities.RepasseId;
import br.com.pais.relatorio.Protocolo;
import br.com.pais.util.ApplicationSecurityManager;

public class RepasseBean {
	
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();
	private Movimento movimento = new Movimento();
	private Repasse repasse = new Repasse();
	private RepasseId repasseId = new RepasseId();
	private Celulas celulas = new Celulas();
	private Protocolo protocolo = new Protocolo();
	private Movimento movimentoGerado;
	
	//List
	private List<Discipulos> listaDiscipulos = new ArrayList<Discipulos>();
	private List<Geracoes> listaGeracoes = new ArrayList<Geracoes>();
	private List<Movimento> listaMovimentos = new ArrayList<Movimento>();
	private List<Celulas> listaCelulas = new ArrayList<Celulas>();
	
	//Daos
	private MovimentoDao movimentoDao = new MovimentoDaoImp();
	private RepasseDao repasseDao = new RepasseDaoImp();
	private CelulaDao celulaDao = new CelulaDaoImp();
	private  GeracaoDao geracaoDao = new GeracaoDaoImp();
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private RelatorioDao relatorioDao = new RelatorioDaoImp();
	
	private boolean dtM12 = false;
	private int comboGeracao = 0;
	private Double totalNaoRecebido = 0.00;
	private String totalStringNaoRecebido = "";
	private Double totalRecebido = 0.00;
	private String totalStringRecebido = "";
	private Double totalEnviado = 0.00;
	private String totalStringEnviado = "";
	private Double totalDetalhe = 0.00;
	private String totalStringDetalhe = "";
	private int countLista = 0;
	private Date primeiroDiaCorrente = null;
	private Date ultimoDiaCorrente = null;

	public SelectItem[] getGeracaoCombo() {
		listaGeracoes = new ArrayList<Geracoes>();
		listaGeracoes.addAll(geracaoDao.listarGeracoes());
		List<SelectItem> itens = new ArrayList<SelectItem>(listaGeracoes.size());
		
		for(Geracoes g : listaGeracoes) {
			itens.add(new SelectItem(g.getGerCod(), g.getGerDescricao()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}
	
	public String prepararReceberRepasse() throws Exception{ 
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		
		totalNaoRecebido = 0.00;
		
		listaMovimentos = new ArrayList<Movimento>();
		listaMovimentos.addAll(repasseDao.listarMovimentosTodosPeriodo(movimento.getCelulas().getCelCod(),
		movimento.getMovTipo(), "N", inicio, fim));
		
		for(Movimento mov : listaMovimentos){
			totalNaoRecebido = (totalNaoRecebido + mov.getMovValor());
		}
		
		totalStringNaoRecebido =  NumberFormat.getCurrencyInstance().format(totalNaoRecebido);
		totalStringNaoRecebido = totalStringNaoRecebido.replaceAll("[R$]", "");
		
		return "/cad/repasseReceber.mir";
	}
	
	public String prepararRecebidosDetalhe() throws Exception{ 
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		
		totalDetalhe = 0.00;
		
		listaMovimentos = new ArrayList<Movimento>();
		listaMovimentos.addAll(repasseDao.listarMovimentosTodosPeriodo(movimento.getCelulas().getCelCod(),
		movimento.getMovTipo(), "S", inicio, fim));
		
		for(Movimento mov : listaMovimentos){
			totalDetalhe = (totalDetalhe + mov.getMovValor());
		}
		
		totalStringDetalhe =  NumberFormat.getCurrencyInstance().format(totalDetalhe);
		totalStringDetalhe = totalStringDetalhe.replaceAll("[R$]", "");
		
		return "/cad/repasseRecebidosDetalhe.mir";
	}
	
	public String prepararEnviadosDetalhe() throws Exception{ 
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		
		totalDetalhe = 0.00;
		
		listaMovimentos = new ArrayList<Movimento>();
		listaMovimentos.addAll(repasseDao.listarMovimentosTodosPeriodoDetalhe(movimento.getCelulas().getCelCod(),
		movimento.getMovTipo(), inicio, fim));
		
		for(Movimento mov : listaMovimentos){
			totalDetalhe = (totalDetalhe + mov.getMovValor());
		}
		
		totalStringDetalhe =  NumberFormat.getCurrencyInstance().format(totalDetalhe);
		totalStringDetalhe = totalStringDetalhe.replaceAll("[R$]", "");
		
		return "/cad/repasseEnviadosDetalhe.mir";
	}
	
	public String SalvarRepasse() throws Exception{	
		Calendar data = Calendar.getInstance();
		
		//PEGA A CELULA DO LIDER LOGADO
		listaCelulas = new ArrayList<Celulas>();
		listaCelulas.addAll(celulaDao.listarCelulasGeracao(discipuloSessao.getDiscipulos().getDisCod(), 
		comboGeracao));
		
		//SALVAR O NOVO MOVIMENTO DO LIDER LOGADO
		movimento = new Movimento();
		movimento.setBases(null);
		movimento.setCelulas(listaCelulas.get(0));
		movimento.setMovData(pegaDataAtual());
		movimento.setMovHora(data.getTime());
		movimento.setMovProtocolo(gerarProtocolo());
		movimento.setMovProtocoloPai(null);
		movimento.setMovRecebido("N");
		movimento.setMovTipo(listaMovimentos.get(0).getMovTipo());
		movimento.setMovValor(totalNaoRecebido);
		movimentoDao.salvar(movimento);
		
		movimentoGerado = movimento;
		
		//SALVA O REPASSE
		repasseId.setDisCod(discipuloSessao.getDiscipulos().getDisCod());
		repasseId.setMovCod(movimento.getMovCod());
		repasse.setId(repasseId);
		repasse.setResData(movimento.getMovData());
		repasse.setResValor(movimento.getMovValor());
		repasseDao.salvar(repasse);
		
		String protocoloPai = movimento.getMovProtocolo();
		//ATUALIZA OS MOVIMENTOS QUE ESTÃO NO GRID DO DISCIPULO DELE
		for(Movimento mov : listaMovimentos){
			movimento = new Movimento();
			movimento = mov;
			movimento.setMovRecebido("S");
			movimento.setMovProtocoloPai(protocoloPai);
			movimentoDao.atualizar(movimento);
		}
		
		preencheProtocoloGerado();
		
		/*
        PrimeiroUltimoDiaCorrente();
		movimento = new Movimento();
		repasse = new Repasse();
		comboGeracao = 0;
		dtM12 = false;
		
		return "/cad/repasseListarRecebidos.mir";
		*/
		return null;
	}
	
	public void preencheProtocoloGerado() throws Exception{
		listaDiscipulos = new ArrayList<Discipulos>();
		listaDiscipulos.addAll(discipuloDao.listarDiscipulador(discipuloSessao.getDiscipulos().getDiscipulos().getDisCod()));
        
		PrimeiroUltimoDiaCorrente();
		
		String valor =  NumberFormat.getCurrencyInstance().format(movimentoGerado.getMovValor());
		valor = valor.replaceAll("[R$]", "");
		
		SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
		String data = formatarDate.format(movimentoGerado.getMovData());
		
		String primeiroDiaCorrenteTemp = formatarDate.format(primeiroDiaCorrente);
		String ultimoDiaCorrenteTemp = formatarDate.format(ultimoDiaCorrente);
		
		protocolo = new Protocolo();
		protocolo.setDiscipulo(discipuloSessao.getDiscipulos().getDisnome());
		protocolo.setFotoDiscipulo(discipuloSessao.getDiscipulos().getDisfoto());
		protocolo.setDiscipulador(listaDiscipulos.get(0).getDisnome());
		protocolo.setFotoDiscipulador(listaDiscipulos.get(0).getDisfoto());
		protocolo.setTipo(movimentoGerado.getMovTipo());
		protocolo.setInicio(primeiroDiaCorrenteTemp);
		protocolo.setFim(ultimoDiaCorrenteTemp);
		protocolo.setValor(valor);
		protocolo.setData(data);
		protocolo.setLocal(repasse.getResLocal());
		protocolo.setProtocolo(movimentoGerado.getMovProtocolo());
	}
	
	public void imprimirProtocolo(){
		String logo = getDiretorioReal("/img/logoRelatorio.png");
		String fotoDiscipulo = getDiretorioReal(discipuloSessao.getDiscipulos().getDisfoto());
		String fotoDiscipulador = getDiretorioReal(listaDiscipulos.get(0).getDisfoto());
		
		protocolo.setLogo(logo);
		protocolo.setFotoDiscipulo(fotoDiscipulo);
		protocolo.setFotoDiscipulador(fotoDiscipulador);
		
		List<Protocolo> listaProtocolo = new ArrayList<Protocolo>();
		listaProtocolo.add(protocolo);
		relatorioDao.gerarProtocolo(listaProtocolo);
	}
	
	private String getDiretorioReal(String diretorio) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		return session.getServletContext().getRealPath(diretorio);
	}
	
	public void limparRepasseDialog(){
		repasse = new Repasse();
	}
	
	public String prepararListarRepasseRecebidos() throws Exception{
        PrimeiroUltimoDiaCorrente();
		
		movimento = new Movimento();
		repasse = new Repasse();
		
		comboGeracao = 0;
		dtM12 = false;
		
		return "/cad/repasseListarRecebidos.mir";
	}
	
	public String prepararListarRepasseEnviados() throws Exception{
        PrimeiroUltimoDiaCorrente();
		
		movimento = new Movimento();
		repasse = new Repasse();
		
		comboGeracao = 0;
		dtM12 = false;
		
		return "/cad/repasseListarEnviados.mir";
	}
	
	public Date pegaDataAtual() throws Exception {   
		Date dataAtual = new Date(System.currentTimeMillis());    
		SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
		
		String dataString = formatarDate.format(dataAtual);
	
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
		java.sql.Date data = new java.sql.Date(format.parse(dataString).getTime()); 
		
        return data;  
    }
	
	public void listarM12PorGeracaoNaoRecebidos(AjaxBehaviorEvent event) throws Exception {	
		listarM12naoRecebidos();
	}
	
	public void selecionadoCalendarInicioNaoRecebidos(DateSelectEvent event) {  
		primeiroDiaCorrente = event.getDate();
		listarM12naoRecebidos();
    }
	
	public void selecionadoCalendarFimNaoRecebidos(DateSelectEvent event) {  
		ultimoDiaCorrente = event.getDate();
		listarM12naoRecebidos();
    }
	
	public void listarM12naoRecebidos(){
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		
		listaMovimentos = new ArrayList<Movimento>();
		listaMovimentos.addAll(repasseDao.listarMovimentos(discipuloSessao.getDiscipulos().getDisCod(), 
		comboGeracao, "N", movimento.getMovTipo(), inicio, fim));
		
		totalNaoRecebido = 0.00;
		countLista = 0;
		
		if(comboGeracao == 0 || movimento.getMovTipo() == "")dtM12  = false;
		else dtM12 = true;
	}
	
	
	public Double calcularValorTotalMovimentoNaoRecebidos() {
		totalNaoRecebido = 0.00;  
		List<Movimento> listaMovimentosTemp = new ArrayList<Movimento>();
		
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		
		listaMovimentosTemp.addAll(repasseDao.totalMovimento(listaMovimentos.get(countLista).getCelulas().getCelCod(),
		movimento.getMovTipo(), "N", inicio, fim));
		
		for(Movimento mov : listaMovimentosTemp){
			totalNaoRecebido = (totalNaoRecebido + mov.getMovValor());
		}

		countLista++;
        return totalNaoRecebido;
	}          
	
	public void listarM12PorGeracaoRecebidos(AjaxBehaviorEvent event) throws Exception {	
		listarM12Recebidos();
	}
	
	public void selecionadoCalendarInicioRecebidos(DateSelectEvent event) {  
		primeiroDiaCorrente = event.getDate();
		listarM12Recebidos();
    }
	
	public void selecionadoCalendarFimRecebidos(DateSelectEvent event) {  
		ultimoDiaCorrente = event.getDate();
		listarM12Recebidos();
    }
	
	public void listarM12Recebidos(){
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		
		listaMovimentos = new ArrayList<Movimento>();
		listaMovimentos.addAll(repasseDao.listarMovimentos(discipuloSessao.getDiscipulos().getDisCod(), 
		comboGeracao, "S", movimento.getMovTipo(), inicio, fim));
		
		totalRecebido = 0.00;
		countLista = 0;
		
		if(comboGeracao == 0 || movimento.getMovTipo() == "")dtM12  = false;
		else dtM12 = true;
	}
	
	public Double calcularValorTotalMovimentoRecebidos() {
		totalRecebido = 0.00;  
		List<Movimento> listaMovimentosTemp = new ArrayList<Movimento>();
		
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		
		listaMovimentosTemp.addAll(repasseDao.totalMovimento(listaMovimentos.get(countLista).getCelulas().getCelCod(),
		movimento.getMovTipo(), "S", inicio, fim));
		
		for(Movimento mov : listaMovimentosTemp){
			totalRecebido = (totalRecebido + mov.getMovValor());
		}

		countLista++;
        return totalRecebido;
	}
	
	public void listarM12PorGeracaoEnviados(AjaxBehaviorEvent event) throws Exception {	
		listarM12Enviados();
	}
	
	public void selecionadoCalendarInicioEnviados(DateSelectEvent event) {  
		primeiroDiaCorrente = event.getDate();
		listarM12Enviados();
    }
	
	public void selecionadoCalendarFimEnviados(DateSelectEvent event) {  
		ultimoDiaCorrente = event.getDate();
		listarM12Enviados();
    }
	
	public void listarM12Enviados(){
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		
		listaMovimentos = new ArrayList<Movimento>();
		listaMovimentos.addAll(repasseDao.listarMovimentosEnviados(discipuloSessao.getDiscipulos().getDisCod(), 
		comboGeracao, movimento.getMovTipo(), inicio, fim));
		
		totalRecebido = 0.00;
		countLista = 0;
		
		if(comboGeracao == 0 || movimento.getMovTipo() == "")dtM12  = false;
		else dtM12 = true;
	}
	
	public Double calcularValorTotalMovimentoEnviados() {
		totalEnviado = 0.00;  
		List<Movimento> listaMovimentosTemp = new ArrayList<Movimento>();
		
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		
		listaMovimentosTemp.addAll(repasseDao.totalMovimentoEnviados(listaMovimentos.get(countLista).getCelulas().getCelCod(),
		movimento.getMovTipo(), inicio, fim));
		
		for(Movimento mov : listaMovimentosTemp){
			totalEnviado = (totalEnviado + mov.getMovValor());
		}

		countLista++;
        return totalEnviado;
	}
	
	public String prepararRepasse() throws Exception{ 
		PrimeiroUltimoDiaCorrente();
		
		movimento = new Movimento();
		repasse = new Repasse();
		
		comboGeracao = 0;
		dtM12 = false;
		
		return "/cad/repasseCadastro.mir";
	}
	
	public String gerarProtocolo(){
		//GERA A CHAVE UNICA 
		UUID uuid = UUID.randomUUID();  
		String chaveUnica = uuid.toString();
		String chave = chaveUnica.substring(0, 13).toUpperCase();
		
		//PEGA O CPF DO LOGADO
		String cpf = discipuloSessao.getDiscipulos().getDisCpf().toString();
		cpf = cpf.replaceAll("[.]", "");
		cpf = cpf.replaceAll("[-]", "");
		
        return (cpf + "-" +chave);
	}
	
	public void PrimeiroUltimoDiaCorrente() throws Exception {   
		Date pegadataAtual = new Date(System.currentTimeMillis());    
		SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
		
		String dataAtual = formatarDate.format(pegadataAtual);
		
		GregorianCalendar cal = new GregorianCalendar(); 
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH)); 
		int primeiroDia = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		int ultimoDia = cal.get(Calendar.DAY_OF_MONTH);
		
		String primeiroDiaCorrenteTemp = ("0" + primeiroDia + dataAtual.substring(2, 10));
		String ultimoDiaCorrenteTemp = (+ ultimoDia + dataAtual.substring(2, 10));
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		Date datePrimeiroDiaCorrente = (Date)formatter.parse(primeiroDiaCorrenteTemp);
		Date dateUltimoDiaCorrente = (Date)formatter.parse(ultimoDiaCorrenteTemp);
		
		primeiroDiaCorrente = datePrimeiroDiaCorrente;
		ultimoDiaCorrente = dateUltimoDiaCorrente;
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
	public Repasse getRepasse() {
		return repasse;
	}
	public void setRepasse(Repasse repasse) {
		this.repasse = repasse;
	}
	public List<Discipulos> getListaDiscipulos() {
		return listaDiscipulos;
	}
	public void setListaDiscipulos(List<Discipulos> listaDiscipulos) {
		this.listaDiscipulos = listaDiscipulos;
	}
	public boolean isDtM12() {
		return dtM12;
	}
	public void setDtM12(boolean dtM12) {
		this.dtM12 = dtM12;
	}
	public int getComboGeracao() {
		return comboGeracao;
	}
	public void setComboGeracao(int comboGeracao) {
		this.comboGeracao = comboGeracao;
	}
	public List<Movimento> getListaMovimentos() {
		return listaMovimentos;
	}
	public void setListaMovimentos(List<Movimento> listaMovimentos) {
		this.listaMovimentos = listaMovimentos;
	}
	public int getCountLista() {
		return countLista;
	}
	public void setCountLista(int countLista) {
		this.countLista = countLista;
	}
	public Date getPrimeiroDiaCorrente() {
		return primeiroDiaCorrente;
	}
	public void setPrimeiroDiaCorrente(Date primeiroDiaCorrente) {
		this.primeiroDiaCorrente = primeiroDiaCorrente;
	}
	public Date getUltimoDiaCorrente() {
		return ultimoDiaCorrente;
	}
	public void setUltimoDiaCorrente(Date ultimoDiaCorrente) {
		this.ultimoDiaCorrente = ultimoDiaCorrente;
	}
	public Celulas getCelulas() {
		return celulas;
	}
	public void setCelulas(Celulas celulas) {
		this.celulas = celulas;
	}
	public List<Geracoes> getListaGeracoes() {
		return listaGeracoes;
	}
	public void setListaGeracoes(List<Geracoes> listaGeracoes) {
		this.listaGeracoes = listaGeracoes;
	}
	public List<Celulas> getListaCelulas() {
		return listaCelulas;
	}
	public void setListaCelulas(List<Celulas> listaCelulas) {
		this.listaCelulas = listaCelulas;
	}
	public RepasseId getRepasseId() {
		return repasseId;
	}
	public void setRepasseId(RepasseId repasseId) {
		this.repasseId = repasseId;
	}
	public Double getTotalNaoRecebido() {
		return totalNaoRecebido = calcularValorTotalMovimentoNaoRecebidos();
	}
	public void setTotalNaoRecebido(Double totalNaoRecebido) {
		this.totalNaoRecebido = totalNaoRecebido;
	}
	public String getTotalStringNaoRecebido() {
		return totalStringNaoRecebido;
	}
	public void setTotalStringNaoRecebido(String totalStringNaoRecebido) {
		this.totalStringNaoRecebido = totalStringNaoRecebido;
	}
	public Double getTotalRecebido() {
		return totalRecebido = calcularValorTotalMovimentoRecebidos();
	}
	public void setTotalRecebido(Double totalRecebido) {
		this.totalRecebido = totalRecebido;
	}

	public String getTotalStringRecebido() {
		return totalStringRecebido;
	}
	public void setTotalStringRecebido(String totalStringRecebido) {
		this.totalStringRecebido = totalStringRecebido;
	}
	public Double getTotalEnviado() {
		return totalEnviado = calcularValorTotalMovimentoEnviados();
	}
	public void setTotalEnviado(Double totalEnviado) {
		this.totalEnviado = totalEnviado;
	}
	public String getTotalStringEnviado() {
		return totalStringEnviado;
	}
	public void setTotalStringEnviado(String totalStringEnviado) {
		this.totalStringEnviado = totalStringEnviado;
	}
	public Double getTotalDetalhe() {
		return totalDetalhe;
	}
	public void setTotalDetalhe(Double totalDetalhe) {
		this.totalDetalhe = totalDetalhe;
	}
	public String getTotalStringDetalhe() {
		return totalStringDetalhe;
	}
	public void setTotalStringDetalhe(String totalStringDetalhe) {
		this.totalStringDetalhe = totalStringDetalhe;
	}
	public Protocolo getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(Protocolo protocolo) {
		this.protocolo = protocolo;
	}
	public Movimento getMovimentoGerado() {
		return movimentoGerado;
	}
	public void setMovimentoGerado(Movimento movimentoGerado) {
		this.movimentoGerado = movimentoGerado;
	}
}
