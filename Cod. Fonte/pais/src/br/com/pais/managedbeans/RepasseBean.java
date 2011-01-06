package br.com.pais.managedbeans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.DateSelectEvent;

import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.GeracaoDao;
import br.com.pais.dao.MovimentoDao;
import br.com.pais.dao.RepasseDao;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.GeracaoDaoImp;
import br.com.pais.dao.impl.MovimentoDaoImp;
import br.com.pais.dao.impl.RepasseDaoImp;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Geracoes;
import br.com.pais.entities.Mensagem;
import br.com.pais.entities.Movimento;
import br.com.pais.entities.Repasse;
import br.com.pais.util.ApplicationSecurityManager;

public class RepasseBean {
	
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();
	private Movimento movimento = new Movimento();
	private Repasse repasse = new Repasse();
	
	//List
	private List<Discipulos> listaDiscipulos = new ArrayList<Discipulos>();
	private List<Geracoes> listaGeracoes = new ArrayList<Geracoes>();
	private List<Movimento> listaMovimentos = new ArrayList<Movimento>();
	
	//Daos
	private MovimentoDao movimentoDao = new MovimentoDaoImp();
	private RepasseDao repasseDao = new RepasseDaoImp();
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private  GeracaoDao geracaoDao = new GeracaoDaoImp();
	
	private boolean dtM12 = false;
	private int comboGeracao = 0;
	private Double total = 0.00;
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
		
		List<Celulas> listaCelulas = new ArrayList<Celulas>();
		listaCelulas.addAll(discipuloSessao.getDiscipulos().getCelulases());
		
		listaMovimentos = new ArrayList<Movimento>();
		listaMovimentos.addAll(repasseDao.listarMovimentosTodosPeriodo(movimento.getCelulas().getCelCod(),
		movimento.getMovTipo(), inicio, fim));
		
		return "/cad/repasseReceber.mir";
	}
	
	public void listarM12PorGeracao(AjaxBehaviorEvent event) throws Exception {	
		listarM12();
	}
	
	public void selecionadoCalendarInicio(DateSelectEvent event) {  
		primeiroDiaCorrente = event.getDate();
		listarM12();
    }
	
	public void selecionadoCalendarFim(DateSelectEvent event) {  
		ultimoDiaCorrente = event.getDate();
		listarM12();
    }
	
	public void listarM12(){
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		
		listaMovimentos = new ArrayList<Movimento>();
		listaMovimentos.addAll(repasseDao.listarMovimentos(discipuloSessao.getDiscipulos().getDisCod(), 
		comboGeracao, movimento.getMovTipo(), inicio, fim));
		
		total = 0.00;
		countLista = 0;
		
		if(comboGeracao == 0 || movimento.getMovTipo() == "")dtM12  = false;
		else dtM12 = true;
	}
	
	
	public Double calcularValorTotalMovimento() {
		total = 0.00;  
		List<Movimento> listaMovimentosTemp = new ArrayList<Movimento>();
		
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		
		listaMovimentosTemp.addAll(repasseDao.totalMovimento(listaMovimentos.get(countLista).getCelulas().getCelCod(),
		movimento.getMovTipo(), inicio, fim));
		
		for(Movimento mov : listaMovimentosTemp){
			total = (total + mov.getMovValor());
		}

		countLista++;
        return total;
	}                                                                                                                                                                                                                                                                                                                                                             
	
	public String prepararRepasse() throws Exception{ 
		PrimeiroUltimoDiaCorrente();
		
		movimento = new Movimento();
		repasse = new Repasse();
		
		comboGeracao = 0;
		dtM12 = false;
		
		return "/cad/repasseCadastro.mir";
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
	public Double getTotal() {
		return total = calcularValorTotalMovimento();
	}
	public void setTotal(Double total) {
		this.total = total;
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
}
