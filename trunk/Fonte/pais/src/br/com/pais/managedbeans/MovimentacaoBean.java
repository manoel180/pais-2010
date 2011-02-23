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

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.CloseEvent;

import br.com.pais.dao.CelulaDao;
import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.MovimentoDao;
import br.com.pais.dao.RelatorioDao;
import br.com.pais.dao.RepasseDao;
import br.com.pais.dao.impl.CelulaDaoImp;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.MovimentoDaoImp;
import br.com.pais.dao.impl.RelatorioDaoImp;
import br.com.pais.dao.impl.RepasseDaoImp;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Movimento;
import br.com.pais.entities.Repasse;
import br.com.pais.entities.RepasseId;
import br.com.pais.relatorio.Protocolo;
import br.com.pais.util.ApplicationSecurityManager;

public class MovimentacaoBean {
	
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();
	private Movimento movimento = new Movimento();
	private Repasse repasse = new Repasse();
	private RepasseId repasseId = new RepasseId();
	private Protocolo protocolo = new Protocolo();

	private Celulas celulaSelecionada;
	private String primeiroDiaCorrente;
	private String ultimoDiaCorrente;
	
	//List
	private List<Celulas> listaCelulas = new ArrayList<Celulas>();
	private List<Discipulos> listaDiscipulos = new ArrayList<Discipulos>();
	
	//Daos
	private MovimentoDao movimentoDao = new MovimentoDaoImp();
	private CelulaDao celulaDao = new CelulaDaoImp();
	private RepasseDao repasseDao = new RepasseDaoImp();
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private RelatorioDao relatorioDao = new RelatorioDaoImp();
	
	public String prepararMovimento(){		
		listaCelulas = new ArrayList<Celulas>();
        listaCelulas.addAll(celulaDao.listarCelulas(discipuloSessao.getDiscipulos().getDisCod()));
		return "/cad/movimentoListar.mir";
	}
	
	public String prepararCadastro(){
		movimento = new Movimento();
		return "/cad/movimentoCadastro";
	}
	
	public void handleClose(CloseEvent event) {  
		movimento = new Movimento();
    }
	
	public void limparRepasseDialog(){
		repasse = new Repasse();
	}
	
	public String salvarMovimento() throws Exception {
		Calendar data = Calendar.getInstance();
		
		FacesContext context = FacesContext.getCurrentInstance();
		movimento.setCelulas(celulaSelecionada);
		movimento.setMovRecebido('N');
		movimento.setBases(null);
		movimento.setMovProtocolo(gerarProtocolo());
		movimento.setMovProtocoloPai(null);
		movimento.setMovHora(data.getTime());
		if (movimentoDao.salvar(movimento) == (true)) {
			//SALVA O REPASSE
			repasseId.setDisCod(discipuloSessao.getDiscipulos().getDisCod());
			repasseId.setMovCod(movimento.getMovCod());
			repasse.setId(repasseId);
			repasse.setResData(movimento.getMovData());
			repasse.setResValor(movimento.getMovValor());
			repasseDao.salvar(repasse);
		}  else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","C�lula n�o cadastrada!"));
		}
		preencheProtocoloGerado();
		
		return null;
	}
	
	public void preencheProtocoloGerado() throws Exception{
		listaDiscipulos = new ArrayList<Discipulos>();
		listaDiscipulos.addAll(discipuloDao.listarDiscipulador(discipuloSessao.getDiscipulos().getDiscipulos().getDisCod()));
        
		PrimeiroUltimoDiaCorrente();
		
		String valor =  NumberFormat.getCurrencyInstance().format(movimento.getMovValor());
		valor = valor.replaceAll("[R$]", "");
		
		SimpleDateFormat formatarDate = new SimpleDateFormat("dd/MM/yyyy");
		String data = formatarDate.format(movimento.getMovData());
		
		protocolo = new Protocolo();
		protocolo.setDiscipulo(discipuloSessao.getDiscipulos().getDisnome());
		protocolo.setFotoDiscipulo(discipuloSessao.getDiscipulos().getDisfoto());
		protocolo.setDiscipulador(listaDiscipulos.get(0).getDisnome());
		protocolo.setFotoDiscipulador(listaDiscipulos.get(0).getDisfoto());
		protocolo.setTipo(movimento.getMovTipo());
		protocolo.setInicio(primeiroDiaCorrente);
		protocolo.setFim(ultimoDiaCorrente);
		protocolo.setValor(valor);
		protocolo.setData(data);
		protocolo.setLocal(repasse.getResLocal());
		protocolo.setProtocolo(movimento.getMovProtocolo());
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
		
		primeiroDiaCorrente = formatarDate.format(datePrimeiroDiaCorrente);
		ultimoDiaCorrente = formatarDate.format(dateUltimoDiaCorrente);
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
	public Repasse getRepasse() {
		return repasse;
	}
	public void setRepasse(Repasse repasse) {
		this.repasse = repasse;
	}
	public RepasseId getRepasseId() {
		return repasseId;
	}
	public void setRepasseId(RepasseId repasseId) {
		this.repasseId = repasseId;
	}
	public Protocolo getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(Protocolo protocolo) {
		this.protocolo = protocolo;
	}
	public List<Discipulos> getListaDiscipulos() {
		return listaDiscipulos;
	}
	public void setListaDiscipulos(List<Discipulos> listaDiscipulos) {
		this.listaDiscipulos = listaDiscipulos;
	}
	public String getPrimeiroDiaCorrente() {
		return primeiroDiaCorrente;
	}
	public void setPrimeiroDiaCorrente(String primeiroDiaCorrente) {
		this.primeiroDiaCorrente = primeiroDiaCorrente;
	}
	public String getUltimoDiaCorrente() {
		return ultimoDiaCorrente;
	}
	public void setUltimoDiaCorrente(String ultimoDiaCorrente) {
		this.ultimoDiaCorrente = ultimoDiaCorrente;
	}
}