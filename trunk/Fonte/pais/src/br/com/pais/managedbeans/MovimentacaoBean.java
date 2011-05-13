package br.com.pais.managedbeans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.event.DateSelectEvent;

import br.com.pais.dao.BancosDao;
import br.com.pais.dao.BasesDao;
import br.com.pais.dao.CelulaDao;
import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.MovimentoChequeDao;
import br.com.pais.dao.MovimentoDao;
import br.com.pais.dao.RepasseDao;
import br.com.pais.dao.impl.BancosDaoImp;
import br.com.pais.dao.impl.BasesDaoImp;
import br.com.pais.dao.impl.CelulaDaoImp;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.MovimentoChequeDaoImp;
import br.com.pais.dao.impl.MovimentoDaoImp;
import br.com.pais.dao.impl.RepasseDaoImp;
import br.com.pais.entities.Bancos;
import br.com.pais.entities.Bases;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Movimento;
import br.com.pais.entities.Movimentocheque;
import br.com.pais.entities.MovimentochequeId;
import br.com.pais.entities.Repasse;
import br.com.pais.entities.RepasseId;
import br.com.pais.relatorio.RelatorioDao;
import br.com.pais.relatorio.imp.Protocolo;
import br.com.pais.relatorio.imp.ProtocoloCheques;
import br.com.pais.relatorio.imp.RelatorioDaoImp;
import br.com.pais.util.ApplicationSecurityManager;
import br.com.pais.util.SendEMail;

public class MovimentacaoBean {
	
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();
	private Movimento movimento = new Movimento();
	private Repasse repasse = new Repasse();
	private RepasseId repasseId = new RepasseId();
	private Protocolo protocolo = new Protocolo();
	private Bases bases = new Bases();
	private ProtocoloCheques protocoloCheques = new ProtocoloCheques();
	private Movimentocheque movimentoCheque = new Movimentocheque();
	private MovimentochequeId movimentoChequeId = new MovimentochequeId();
	private Movimentocheque movimentoChequeSelecionado;

	private Celulas celulaSelecionada;
	private Date primeiroDiaCorrente;
	private Date ultimoDiaCorrente;

	//List
	private List<Celulas> listaCelulas = new ArrayList<Celulas>();
	private List<Discipulos> listaDiscipulos = new ArrayList<Discipulos>();
	private List<Bancos> listaBancos = new ArrayList<Bancos>();
	private List<Protocolo> listaProtocolo = new ArrayList<Protocolo>();
	private List<ProtocoloCheques> listaProtocoloCheques = new ArrayList<ProtocoloCheques>();
	private List<Bases> listaBases = new ArrayList<Bases>();

	//Daos
	private MovimentoDao movimentoDao = new MovimentoDaoImp();
	private CelulaDao celulaDao = new CelulaDaoImp();
	private RepasseDao repasseDao = new RepasseDaoImp();
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private RelatorioDao relatorioDao = new RelatorioDaoImp();
	private BancosDao bancosDao = new BancosDaoImp();
	private MovimentoChequeDao movimentoChequeDao = new MovimentoChequeDaoImp();
	private BasesDao basesDao = new BasesDaoImp();
	
	private int comboBancos = 0;
	private boolean mostraLogoBanco = false;
	private String logoBanco = null;
	private List<Bancos> listaBancoSelecionado = new ArrayList<Bancos>();
	private boolean preDatado = false;
	private String clienteDesde = null;
	private List<Movimentocheque> listaMovimentoCheque = new ArrayList<Movimentocheque>();
	private List<MovimentochequeId> listaMovimentoChequeId = new ArrayList<MovimentochequeId>();
	private int contadorValidadorCheque = 0;
	private String acaoBotaoCheque = null; 
	private boolean mostraCheque = false;
	private boolean mostraDinheiro = false;
	private boolean mostraCadastrar = false;
	private int contadorValidadorRepasse = 0;
	
	private Double valorTotalMovimento = 0.00;
	private Double valorTotalMovimentoDinheiro = 0.00;
	private Double valorTotalMovimentoCheque = 0.00;
	private boolean dialogRepasseMovimentoDinheiro = false; 
	private boolean dialogRepasseMovimentoDinheiroCheque = false;
	private boolean dialogRepasseMovimentoCheque = false;

	private String btnUpdateRepasseDialog = "outPnlImprimir";
	private String btnOnCompleteRepasseDialog = "repasseDialog.hide(); imprimirDialog.show()";
	
	private Discipulos discipuloLogado = new Discipulos();
	private Discipulos discipuladorLogado = new Discipulos();
	private boolean enviarFinanceiro = false;
	private boolean enviarDiscipulador = false;
	private String repasseLocal;
	private String headerDialogRepasse = "";
	private String valueButtonRepasse = "";
	private String valueButtonAbrirDialogRepasse = "";
	private boolean enviarEmailRepasseRecebido = false;
	
	private boolean mostraDtCelulas = false;
	private boolean mostraDtBases = false;
	private boolean mostraTabViewCelulasBases = false;

	public SelectItem[] getBancosCombo() {
		listaBancos = new ArrayList<Bancos>();
		listaBancos.addAll(bancosDao.listarBancosAtivos());
		List<SelectItem> itens = new ArrayList<SelectItem>(listaBancos.size());
		
		for(Bancos b : listaBancos) {
			itens.add(new SelectItem(b.getCodBanco(), b.getDescBanco()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}

	public void comboBancoSelecionado(AjaxBehaviorEvent event) throws Exception {	
		if(comboBancos > 0){
		listaBancoSelecionado = new ArrayList<Bancos>();
		listaBancoSelecionado.addAll(bancosDao.listarBancoSelecionado(comboBancos));
		
		logoBanco = listaBancoSelecionado.get(0).getLogoBanco();
		mostraLogoBanco = true;
		}
		else{
			mostraLogoBanco = false;
		}
	}

	public void editarCheque(){
		movimentoCheque = new Movimentocheque();
		movimentoChequeId = new MovimentochequeId();
		comboBancos = 0;
		logoBanco = null;
		mostraLogoBanco = true;
		clienteDesde = null;
		preDatado = false;
		acaoBotaoCheque = "Editar";
		
		movimentoCheque = movimentoChequeSelecionado;
		movimentoChequeId = movimentoCheque.getId();
		
		comboBancos = movimentoChequeSelecionado.getBancos().getCodBanco();
		logoBanco = movimentoChequeSelecionado.getBancos().getLogoBanco();
		
		clienteDesde = movimentoChequeId.getClienteDesdeMes().concat("/").concat(movimentoChequeId.getClienteDesdeAno());
		
		if(movimentoChequeId.getPreDatado().contains("S"))preDatado = true;
		else preDatado = false;
	}
	
	public void adicionarCheque(){
		if(contadorValidadorCheque <= 0)
		{
		movimentoCheque = new Movimentocheque();
		movimentoChequeId = new MovimentochequeId();
		comboBancos = 0;
		clienteDesde = null;
		logoBanco = null;
		mostraLogoBanco = false;
		preDatado = false;
		acaoBotaoCheque = "Salvar";
		movimentoChequeId.setValNum(0.00);
		}
	}
	
	public void salvarCheque(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(validarCheque() == true){
			if(acaoBotaoCheque.contains("Salvar")){
				salvarChequeLista();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!!","Cheque Salvo Com Sucesso!"));
			}
			
			if(acaoBotaoCheque.contains("Editar")){
				editarChequeLista();
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!!","Cheque Editado Com Sucesso!"));
			}
		}
	}
	
	private void salvarChequeLista(){
		movimentoChequeId.setPagoPara("Ministério Internacional da Restauração");
		movimentoChequeId.setClienteDesdeMes(clienteDesde.substring(0, 2));
		movimentoChequeId.setClienteDesdeAno(clienteDesde.substring(3, 7));
		
		movimentoCheque.setId(movimentoChequeId);
		movimentoCheque.setBancos(listaBancoSelecionado.get(0));
		listaMovimentoCheque.add(movimentoCheque);
	}
	
	private void editarChequeLista(){
		int index = listaMovimentoCheque.indexOf(movimentoCheque);
		
		movimentoChequeId.setPagoPara("Ministério Internacional da Restauração");
		movimentoChequeId.setClienteDesdeMes(clienteDesde.substring(0, 2));
		movimentoChequeId.setClienteDesdeAno(clienteDesde.substring(3, 7));
		
		movimentoCheque.setId(movimentoChequeId);
		movimentoCheque.setBancos(listaBancoSelecionado.get(0));
		listaMovimentoCheque.set(index, movimentoCheque);
	}
	
	public void excluirChequeLista(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(listaMovimentoCheque.remove(movimentoChequeSelecionado)){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!!!","Cheque Excluido Com Sucesso!"));
		}
		else{
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sucesso!!!","Não Foi Possivel Excluir o Cheque!"));
		}
	}
	
	public boolean validarCheque(){
		FacesContext context = FacesContext.getCurrentInstance();
		contadorValidadorCheque = 0;
		
		if(movimentoChequeId.getValNum() <= 0.00){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","Valor do Cheque Zerado!"));
			contadorValidadorCheque = contadorValidadorCheque + 1;
		}
		
		if(movimentoChequeId.getValExtenso().length() <= 0){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","Valor Extenso em Branco!"));
			contadorValidadorCheque = contadorValidadorCheque + 1;
		}
		
		if(contadorValidadorCheque <= 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public SelectItem[] getTipoRepasseCombo() {
		List<SelectItem> itens = new ArrayList<SelectItem>();
		
		//PEGO O DISCIPULADOR DO LOGADO
		discipuladorLogado = new Discipulos();
	    discipuladorLogado = discipuloDao.listarDiscipulador(discipuloSessao.getDiscipulos().getDiscipulos().getDisCod()).get(0);
	    
	    //VERIFICO SE ESSE MEU DISCIPULADOR NÃO TEM DISCIPULADOR, ENTAUM EU SOU M12 DO RENNER
	    if(discipuladorLogado.getDiscipulos() == null){
	    	//So Pode Mandar Mensagem Para Geração
			itens.add(new SelectItem("Oferta de M12", "Oferta de M12"));
			itens.add(new SelectItem("Oferta de Macro-Célula", "Oferta de Macro-Célula"));
			itens.add(new SelectItem("Oferta de Base Célular", "Oferta de Base Célular"));
			itens.add(new SelectItem("Oferta de Base Setorial", "Oferta de Base Setorial"));
			itens.add(new SelectItem("Oferta de Base Regional", "Oferta de Base Regional"));
			itens.add(new SelectItem("Oferta de Base Sede", "Oferta de Base Sede"));
	    }
	    else{
	    	itens.add(new SelectItem("Oferta de Célula", "Oferta de Célula"));
			itens.add(new SelectItem("Oferta de Macro-Célula", "Oferta de Macro-Célula"));
			itens.add(new SelectItem("Oferta de Base Célular", "Oferta de Base Célular"));
			itens.add(new SelectItem("Oferta de Base Setorial", "Oferta de Base Setorial"));
			itens.add(new SelectItem("Oferta de Base Regional", "Oferta de Base Regional"));
			itens.add(new SelectItem("Oferta de Base Sede", "Oferta de Base Sede"));
	    }
		return itens.toArray(new SelectItem[itens.size()]);
	}
	
	public void comboTipoOferta(AjaxBehaviorEvent event){
		/*
		if(movimento.getMovTipo().contains("Selecione")){
			mostraCadastrar = false;
			mostraCheque = false;
			mostraDinheiro = false;
		}
		else{
				if(movimento.getMovEspecie().contains("Dinheiro")){
					mostraCadastrar = true;
					mostraCheque = false;
					mostraDinheiro = true;
				}
				if(movimento.getMovEspecie().contains("Cheque")){
					mostraCadastrar = true;
					mostraCheque = true;
					mostraDinheiro = false;
				}
				if(movimento.getMovEspecie().contains("Dinheiro e Cheque")){
					mostraCadastrar = true;
					mostraCheque = true;
					mostraDinheiro = true;
				}
		}
		*/
		if(movimento.getMovTipo().contains("Selecione")){
			mostraDtBases = false;
			mostraDtCelulas = false;
			mostraTabViewCelulasBases = false;
		}
		else{
			//Pesquisa as células
			if(movimento.getMovTipo().contains("Oferta de M12")
			   ||movimento.getMovTipo().contains("Oferta de Célula") 
			   || movimento.getMovTipo().contains("Oferta de Macro-Célula")){
				listaCelulas = new ArrayList<Celulas>();
		        listaCelulas.addAll(celulaDao.listarCelulas(discipuloSessao.getDiscipulos().getDisCod()));
		        
		        mostraDtBases = false;
				mostraDtCelulas = true;
				mostraTabViewCelulasBases = true;
			}
			//Pesquisa as Bases
			else{
				int tipo = 0;
				if(movimento.getMovTipo().contains("Oferta de Base Célular"))tipo = 1;
				if(movimento.getMovTipo().contains("Oferta de Base Setorial"))tipo = 2;
				if(movimento.getMovTipo().contains("Oferta de Base Regional"))tipo = 3;
				if(movimento.getMovTipo().contains("Oferta de Base Sede"))tipo = 4;
				
				listaBases = new ArrayList<Bases>();
				listaBases.addAll(basesDao.listarBasesPorTipo(discipuloSessao.getDiscipulos().getDisCod(),
				tipo));
				
				mostraDtBases = true;
				mostraDtCelulas = false;
				mostraTabViewCelulasBases = true;
			}
		}
	}
	
	public void comboTipoEspecie(AjaxBehaviorEvent event){
		if(movimento.getMovEspecie().contains("Selecione")){
			mostraCadastrar = false;
			mostraCheque = false;
			mostraDinheiro = false;
		}
		else{
			if(movimento.getMovTipo().contains("Selecione")){
				mostraCadastrar = false;
				mostraCheque = false;
				mostraDinheiro = false;
			}
			else{
				if(movimento.getMovEspecie().contains("Dinheiro")){
					mostraCadastrar = true;
					mostraCheque = false;
					mostraDinheiro = true;
				}
				if(movimento.getMovEspecie().contains("Cheque")){
					mostraCadastrar = true;
					mostraCheque = true;
					mostraDinheiro = false;
				}
				if(movimento.getMovEspecie().contains("Dinheiro e Cheque")){
					mostraCadastrar = true;
					mostraCheque = true;
					mostraDinheiro = true;
				}
			}
		}
	}
	
	public void comboPredatado(AjaxBehaviorEvent event){
		if(movimentoChequeId.getPreDatado().contains("S"))preDatado = true;
		if(movimentoChequeId.getPreDatado().contains("N"))preDatado = false;
	}
	
	public void selecionadoCalendarDataCheque(DateSelectEvent event) {  
		movimentoChequeId.setDataCheque(event.getDate());   
		movimentoChequeId.setDataCidade("Manaus");
		
		SimpleDateFormat formatarData = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = formatarData.format(movimentoChequeId.getDataCheque());
		
		String dia = dataFormatada.substring(0, 2);
		String mes = dataFormatada.substring(3, 5);
		String ano = dataFormatada.substring(8, 10);
		
		movimentoChequeId.setDataDia(dia);
		movimentoChequeId.setDataAno(ano);
		
		if(mes.contains("01"))movimentoChequeId.setDataMes("Janeiro");
		if(mes.contains("02"))movimentoChequeId.setDataMes("Fevereiro");
		if(mes.contains("03"))movimentoChequeId.setDataMes("Março");
		if(mes.contains("04"))movimentoChequeId.setDataMes("Abril");
		if(mes.contains("05"))movimentoChequeId.setDataMes("Maio");
		if(mes.contains("06"))movimentoChequeId.setDataMes("Junho");
		if(mes.contains("07"))movimentoChequeId.setDataMes("Julho");
		if(mes.contains("08"))movimentoChequeId.setDataMes("Agosto");
		if(mes.contains("09"))movimentoChequeId.setDataMes("Setembro");
		if(mes.contains("10"))movimentoChequeId.setDataMes("Outubro");
		if(mes.contains("11"))movimentoChequeId.setDataMes("Novembro");
		if(mes.contains("12"))movimentoChequeId.setDataMes("Dezembro");
    }
	
	public String prepararMovimento(){	
		movimento = new Movimento();
		mostraDtBases = false;
		mostraDtCelulas = false;
		mostraTabViewCelulasBases = false;
		
		return "/cad/movimentoListar.mir";
	}
	
	public String prepararCadastro(){
		//movimento = new Movimento();
		repasse = new Repasse();
		repasseId = new RepasseId();
		protocolo = new Protocolo();
		movimentoCheque = new Movimentocheque();
		movimentoChequeId = new MovimentochequeId();
		mostraCadastrar = false;
		mostraCheque = false;
		mostraDinheiro = false;
		movimento.setMovValor(0.00);
		listaMovimentoCheque = new ArrayList<Movimentocheque>();
		listaMovimentoChequeId = new ArrayList<MovimentochequeId>();
		dialogRepasseMovimentoCheque = false;
		dialogRepasseMovimentoDinheiroCheque = false;
		
		//Se ele for apostolo renner
		if(discipuloSessao.getDiscipulos().getDiscipulos() == null){
			discipuloLogado = null;
		}
		else{
			//PEGO O DISCIPULADOR DO LOGADO
			discipuladorLogado = new Discipulos();
		    discipuladorLogado = discipuloDao.listarDiscipulador(discipuloSessao.getDiscipulos().getDiscipulos().getDisCod()).get(0);
		    
		    //VERIFICO SE ESSE MEU DISCIPULADOR NÃO TEM DISCIPULADOR, ENTAUM EU SOU M12 DO RENNER
		    if(discipuladorLogado.getDiscipulos() == null){
				enviarFinanceiro = true;
				enviarDiscipulador = false;
				
				headerDialogRepasse = "DADOS DO REPASSE Á SER ENVIADO AO FINANCEIRO";
				valueButtonAbrirDialogRepasse = "Enviar ao Financeiro";
				valueButtonRepasse = "Enviar ao Financeiro";
			}
		    else{
		    	enviarFinanceiro = false;
		    	enviarDiscipulador = true;
				
				headerDialogRepasse = "DADOS DO REPASSE";
				valueButtonAbrirDialogRepasse = "Fazer Repasse";
				valueButtonRepasse = "Confirmar";
		    }
		}
		
		return "/cad/movimentoCadastro";
	}
	
	public void repasseDialog(){
		if(validarSalvarRepasse1() == true){
			btnOnCompleteRepasseDialog = "repasseDialog.hide(); imprimirDialog.show();";
			btnUpdateRepasseDialog = "outPnlImprimir";
		}
		else{
			btnOnCompleteRepasseDialog = "repasseDialog.hide();";
			btnUpdateRepasseDialog = "msg";
		}
	}
	
	public String salvarMovimento() throws Exception {
		FacesContext context = FacesContext.getCurrentInstance();
		
		//Se For Tudo Validado
		if(validarSalvarRepasse2() == true){
			
			if(repasse.getResLocal().length() <= 0){
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","Local do Repasse Obrigatório!"));
				return null;
			}
			else{
				if(movimento.getMovEspecie().equals("Dinheiro"))salvarMovimentoDinheiro();
				if(movimento.getMovEspecie().equals("Cheque"))salvarMovimentoCheque();
				if(movimento.getMovEspecie().equals("Dinheiro e Cheque"))salvarMovimentoDinheiroCheque();
				
				return "/cad/movimentoGerado";
			}
		}
		else{
			return null;
		}
	}
	
	public void enviarEmailMensagemRepasseRecebido(String diretorioImg, String nomeEnviou, String nomeRecebe, 
			String emailEnviar){
		if(enviarEmailRepasseRecebido == true){
			new SendEMail().sendSimpleMailEnviarRepasseRecebido(diretorioImg, nomeEnviou, nomeRecebe, emailEnviar);
		}
	}
	
	public void salvarMovimentoDinheiro() throws Exception{
		Date dataAtual = new Date(System.currentTimeMillis());
		Calendar data = Calendar.getInstance();
		String diretorioImg = getDiretorioReal("/img/");
		
		//M12 DO RENNER
	    if(discipuladorLogado.getDiscipulos() == null){
	    	if(movimento.getMovTipo().contains("Oferta de M12") 
			   || movimento.getMovTipo().contains("Oferta de Macro-Célula")){
				movimento.setCelulas(celulaSelecionada);
				movimento.setBases(null);
				movimento.setMovOfertaM12("S");
			}
			//Repasse por Bases
			else{
				movimento.setCelulas(null);
				movimento.setBases(bases);
				movimento.setMovOfertaM12("S");
			}
	    }
	    //DISCIPULOS DOS M12 DO RENNER
	    else{
	    	if(movimento.getMovTipo().contains("Oferta de Célula") 
 			   || movimento.getMovTipo().contains("Oferta de Macro-Célula")){
 				movimento.setCelulas(celulaSelecionada);
 				movimento.setBases(null);
 				movimento.setMovOfertaM12("N");
 			}
 			//Repasse por Bases
 			else{
 				movimento.setCelulas(null);
 				movimento.setBases(bases);
 				movimento.setMovOfertaM12("N");
 			}
	    }
		movimento.setMovRecebido("N");
		movimento.setMovVisualizado("N");
		movimento.setMovProtocolo(gerarProtocolo());
		movimento.setMovProtocoloPai(null);
		movimento.setMovDataCadastro(dataAtual);
		movimento.setMovHoraCadastro(data.getTime());
		if (movimentoDao.salvar(movimento) == (true)) {
			//SALVA O REPASSE
			repasseId.setDisCod(discipuloSessao.getDiscipulos().getDisCod());
			repasseId.setMovCod(movimento.getMovCod());
			repasse.setId(repasseId);
			repasse.setResData(movimento.getMovData());
			repasse.setResValor(movimento.getMovValor());
			repasseDao.salvar(repasse);
		}
		preencheProtocoloGerado();
		
		enviarEmailMensagemRepasseRecebido(diretorioImg, discipuloSessao.getDiscipulos().getDisnome(), 
		discipuloSessao.getDiscipulos().getDiscipulos().getDisnome(), 
		discipuloSessao.getDiscipulos().getDiscipulos().getDisemail());
	}
	
	public void salvarMovimentoCheque() throws Exception{
		Date dataAtual = new Date(System.currentTimeMillis());
		Calendar data = Calendar.getInstance();
		Double valorMovimento = 0.00;
		String diretorioImg = getDiretorioReal("/img/");
		
		for(Movimentocheque mov : listaMovimentoCheque) {
			valorMovimento = valorMovimento + mov.getId().getValNum();
		}
		
		//M12 DO RENNER
	    if(discipuladorLogado.getDiscipulos() == null){
	    	if(movimento.getMovTipo().contains("Oferta de M12") 
			   || movimento.getMovTipo().contains("Oferta de Macro-Célula")){
				movimento.setCelulas(celulaSelecionada);
				movimento.setBases(null);
				movimento.setMovOfertaM12("S");
			}
			//Repasse por Bases
			else{
				movimento.setCelulas(null);
				movimento.setBases(bases);
				movimento.setMovOfertaM12("S");
			}
	    }
	    //DISCIPULOS DOS M12 DO RENNER
	    else{
	    	if(movimento.getMovTipo().contains("Oferta de Célula") 
 			   || movimento.getMovTipo().contains("Oferta de Macro-Célula")){
 				movimento.setCelulas(celulaSelecionada);
 				movimento.setBases(null);
 				movimento.setMovOfertaM12("N");
 			}
 			//Repasse por Bases
 			else{
 				movimento.setCelulas(null);
 				movimento.setBases(bases);
 				movimento.setMovOfertaM12("N");
 			}
	    }
		movimento.setMovRecebido("N");
		movimento.setMovVisualizado("N");
		movimento.setMovProtocolo(gerarProtocolo());
		movimento.setMovProtocoloPai(null);
		movimento.setMovDataCadastro(dataAtual);
		movimento.setMovHoraCadastro(data.getTime());
		movimento.setMovValor(valorMovimento);
		if (movimentoDao.salvar(movimento) == (true)) {
			//SALVA OS CHEQUES
			for(Movimentocheque mov : listaMovimentoCheque) {
				movimentoCheque = new Movimentocheque();
				movimentoChequeId = new MovimentochequeId();
				
				movimentoChequeId = mov.getId();
				movimentoChequeId.setCodBanco(mov.getBancos().getCodBanco());
				movimentoChequeId.setCodMovimento(movimento.getMovCod());
				movimentoCheque.setId(movimentoChequeId);
				movimentoCheque.setBancos(mov.getBancos());
				movimentoCheque.setMovimento(movimento);
				
				movimentoChequeDao.salvar(movimentoCheque);
			}
			
			//SALVA O REPASSE
			repasseId.setDisCod(discipuloSessao.getDiscipulos().getDisCod());
			repasseId.setMovCod(movimento.getMovCod());
			repasse.setId(repasseId);
			repasse.setResData(movimento.getMovData());
			repasse.setResValor(movimento.getMovValor());
			repasseDao.salvar(repasse);
		}
		preencheProtocoloGerado();
		
		enviarEmailMensagemRepasseRecebido(diretorioImg, discipuloSessao.getDiscipulos().getDisnome(), 
				discipuloSessao.getDiscipulos().getDiscipulos().getDisnome(), 
				discipuloSessao.getDiscipulos().getDiscipulos().getDisemail());
	}
	
	public void salvarMovimentoDinheiroCheque() throws Exception{
		Date dataAtual = new Date(System.currentTimeMillis());
		Calendar data = Calendar.getInstance();
		Double valorMovimento = movimento.getMovValor();
		String diretorioImg = getDiretorioReal("/img/");
		
		for(Movimentocheque mov : listaMovimentoCheque) {
			valorMovimento = valorMovimento + mov.getId().getValNum();
		}
		
		//M12 DO RENNER
	    if(discipuladorLogado.getDiscipulos() == null){
	    	if(movimento.getMovTipo().contains("Oferta de M12") 
			   || movimento.getMovTipo().contains("Oferta de Macro-Célula")){
				movimento.setCelulas(celulaSelecionada);
				movimento.setBases(null);
				movimento.setMovOfertaM12("S");
			}
			//Repasse por Bases
			else{
				movimento.setCelulas(null);
				movimento.setBases(bases);
				movimento.setMovOfertaM12("S");
			}
	    }
	    //DISCIPULOS DOS M12 DO RENNER
	    else{
	    	if(movimento.getMovTipo().contains("Oferta de Célula") 
 			   || movimento.getMovTipo().contains("Oferta de Macro-Célula")){
 				movimento.setCelulas(celulaSelecionada);
 				movimento.setBases(null);
 				movimento.setMovOfertaM12("N");
 			}
 			//Repasse por Bases
 			else{
 				movimento.setCelulas(null);
 				movimento.setBases(bases);
 				movimento.setMovOfertaM12("N");
 			}
	    }
		movimento.setMovRecebido("N");
		movimento.setMovVisualizado("N");
		movimento.setMovProtocolo(gerarProtocolo());
		movimento.setMovProtocoloPai(null);
		movimento.setMovDataCadastro(dataAtual);
		movimento.setMovHoraCadastro(data.getTime());
		movimento.setMovValor(valorMovimento);
		if (movimentoDao.salvar(movimento) == (true)) {
			//SALVA OS CHEQUES
			for(Movimentocheque mov : listaMovimentoCheque) {
				movimentoCheque = new Movimentocheque();
				movimentoChequeId = new MovimentochequeId();
				
				movimentoChequeId = mov.getId();
				movimentoChequeId.setCodBanco(mov.getBancos().getCodBanco());
				movimentoChequeId.setCodMovimento(movimento.getMovCod());
				movimentoCheque.setId(movimentoChequeId);
				
				movimentoChequeDao.salvar(movimentoCheque);
			}
			
			//SALVA O REPASSE
			repasseId.setDisCod(discipuloSessao.getDiscipulos().getDisCod());
			repasseId.setMovCod(movimento.getMovCod());
			repasse.setId(repasseId);
			repasse.setResData(movimento.getMovData());
			repasse.setResValor(movimento.getMovValor());
			repasseDao.salvar(repasse);
		}
		preencheProtocoloGerado();
		
		enviarEmailMensagemRepasseRecebido(diretorioImg, discipuloSessao.getDiscipulos().getDisnome(), 
				discipuloSessao.getDiscipulos().getDiscipulos().getDisnome(), 
				discipuloSessao.getDiscipulos().getDiscipulos().getDisemail());
	}
	
	public boolean validarSalvarRepasse1(){
		contadorValidadorRepasse = 0;
		
		//VALIDA SE FOR DINHEIRO
		if(movimento.getMovEspecie().equals("Dinheiro")){
			if(movimento.getMovData() == null){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
			}
			if(movimento.getMovValor() <= 0.00){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
			}
		}
		
		//VALIDA SE FOR CHEQUE
		if(movimento.getMovEspecie().equals("Cheque")){
			if(movimento.getMovData() == null){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
			}
			if(listaMovimentoCheque.size() <= 0){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
			}
		}
		
		//VALIDA SE FOR DINHEIRO E CHEQUE
		if(movimento.getMovEspecie().equals("Dinheiro e Cheque")){
			if(movimento.getMovData() == null){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
			}
			if(movimento.getMovValor() <= 0.00){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
			}
			if(listaMovimentoCheque.size() <= 0){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
			}
		}
		
		if(contadorValidadorRepasse <= 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean validarSalvarRepasse2(){
		FacesContext context = FacesContext.getCurrentInstance();
		contadorValidadorRepasse = 0;
		
		//VALIDA SE FOR DINHEIRO
		if(movimento.getMovEspecie().equals("Dinheiro")){
			if(movimento.getMovData() == null){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","Data do Movimento em Branco!"));
			}
			if(movimento.getMovValor() <= 0.00){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","Valor R$ Zerado!"));
			}
		}
		
		//VALIDA SE FOR CHEQUE
		if(movimento.getMovEspecie().equals("Cheque")){
			if(movimento.getMovData() == null){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","Data do Movimento em Branco!"));
			}
			if(listaMovimentoCheque.size() <= 0){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","Nenhum Cheque Adicionado!"));
			}
		}
		
		//VALIDA SE FOR DINHEIRO E CHEQUE
		if(movimento.getMovEspecie().equals("Dinheiro e Cheque")){
			if(movimento.getMovData() == null){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","Data do Movimento em Branco!"));
			}
			if(movimento.getMovValor() <= 0.00){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","Valor R$ Zerado!"));
			}
			if(listaMovimentoCheque.size() <= 0){
				contadorValidadorRepasse = contadorValidadorRepasse + 1;
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "ERRO!!!","Nenhum Cheque Adicionado!"));
			}
		}
		
		if(contadorValidadorRepasse <= 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public void preencheProtocoloGerado() throws Exception{
		valorTotalMovimento = 0.00;
		valorTotalMovimentoDinheiro = 0.00;
		valorTotalMovimentoCheque = 0.00;
		PrimeiroUltimoDiaCorrente();
		protocolo = new Protocolo();
		
		//ENVIAR PRO FINANCEIRO
		if(enviarFinanceiro == true){
			protocolo.setDiscipulador("FINANCEIRO");
			protocolo.setFotoDiscipulador("/img/dinheiro.png");  
		}
		//ENVIAR AO DISCIPULADOR
		else{
			listaDiscipulos = new ArrayList<Discipulos>();
			listaDiscipulos.addAll(discipuloDao.listarDiscipulador(discipuloSessao.getDiscipulos().getDiscipulos().getDisCod()));
			
			protocolo.setDiscipulador(listaDiscipulos.get(0).getDisnome());
			protocolo.setFotoDiscipulador(listaDiscipulos.get(0).getDisfoto());
		}
		
		listaDiscipulos = new ArrayList<Discipulos>();
		listaDiscipulos.addAll(discipuloDao.listarDiscipulador(discipuloSessao.getDiscipulos().getDiscipulos().getDisCod()));
		
		protocolo.setDiscipulo(discipuloSessao.getDiscipulos().getDisnome());
		protocolo.setFotoDiscipulo(discipuloSessao.getDiscipulos().getDisfoto());
		protocolo.setTipo(movimento.getMovTipo());
		protocolo.setInicio(primeiroDiaCorrente);
		protocolo.setFim(ultimoDiaCorrente);
		protocolo.setValormovimento(movimento.getMovValor());
		protocolo.setData(movimento.getMovData());
		protocolo.setLocal(repasse.getResLocal());
		protocolo.setProtocolo(movimento.getMovProtocolo());
		protocolo.setEspecie(movimento.getMovEspecie());
		protocolo.setListacheques(null);
		protocolo.setValorcheques(null);
		protocolo.setValordinheiro(null);
		
		//Seta os Valores Para O Repasse Gerado
		valorTotalMovimento = movimento.getMovValor();
		
		if(movimento.getMovEspecie().equals("Dinheiro")){
			dialogRepasseMovimentoDinheiro = true;
			dialogRepasseMovimentoDinheiroCheque = false;
			dialogRepasseMovimentoCheque = false;
		}
		
		if(movimento.getMovEspecie().equals("Cheque")){
			dialogRepasseMovimentoDinheiro = true;
			dialogRepasseMovimentoDinheiroCheque = false;
			dialogRepasseMovimentoCheque = true;
		}
		
		if(movimento.getMovEspecie().equals("Dinheiro e Cheque")){
			dialogRepasseMovimentoDinheiro = false;
			dialogRepasseMovimentoDinheiroCheque = true;
			dialogRepasseMovimentoCheque = false;
			for(Movimentocheque mov : listaMovimentoCheque) {
				valorTotalMovimentoCheque = valorTotalMovimentoCheque + mov.getId().getValNum();
			}
			valorTotalMovimentoDinheiro = (valorTotalMovimento - valorTotalMovimentoCheque);
			
			protocolo.setValorcheques(valorTotalMovimentoCheque);
			protocolo.setValordinheiro(valorTotalMovimentoDinheiro);
		}
		
		listaMovimentoChequeId = new ArrayList<MovimentochequeId>();
		for(Movimentocheque mov : listaMovimentoCheque) {
			movimentoChequeId = new MovimentochequeId();
			movimentoChequeId.setValNum(mov.getId().getValNum());
			movimentoChequeId.setDataCheque(mov.getId().getDataCheque());
			
			if(mov.getId().getPreDatado().equals("S"))movimentoChequeId.setPreDatado("Sim");
			else movimentoChequeId.setPreDatado("Não");
			
			listaMovimentoChequeId.add(movimentoChequeId);
		}
	}
	
	public void imprimirProtocolo(){
		if(movimento.getMovEspecie().equals("Dinheiro")){
			preencheProtocoloDinheiro();
			relatorioDao.gerarProtocoloDinheiro(listaProtocolo);
		}
		
		if(movimento.getMovEspecie().equals("Cheque")){
			preencheProtocoloDinheiroCheque();
			relatorioDao.gerarProtocoloCheque(listaProtocolo);
		}
		
		if(movimento.getMovEspecie().equals("Dinheiro e Cheque")){
			preencheProtocoloDinheiroCheque();
			relatorioDao.gerarProtocoloDinheiroCheque(listaProtocolo);
		}
	}
	
	public void preencheProtocoloDinheiro(){
		String logo = getDiretorioReal("/img/logoRelatorio.png");
		//String fotoDiscipulo = getDiretorioReal(discipuloSessao.getDiscipulos().getDisfoto());
		//String fotoDiscipulador = getDiretorioReal(listaDiscipulos.get(0).getDisfoto());
		
		protocolo.setLogo(logo);
		
		listaProtocolo = new ArrayList<Protocolo>();
		listaProtocolo.add(protocolo);
	}
	
	public void preencheProtocoloDinheiroCheque(){
        String logo = getDiretorioReal("/img/logoRelatorio.png");
		
		//PEGA OS CHEQUES NA LISTA DE CHEQUES DO GRID E PREENCHE NO RELATORIO
		listaProtocoloCheques = new ArrayList<ProtocoloCheques>();
		for(MovimentochequeId cheq : listaMovimentoChequeId) {
			protocoloCheques = new ProtocoloCheques();
			protocoloCheques.setDatacheque(cheq.getDataCheque());
			protocoloCheques.setValorcheque(cheq.getValNum());
			
			if(cheq.getPreDatado().equals("Sim") || cheq.getPreDatado().equals("S"))protocoloCheques.setPredatadocheque("Sim");
			else protocoloCheques.setPredatadocheque("Não");
			
			listaProtocoloCheques.add(protocoloCheques);
		}
		protocolo.setLogo(logo);
		protocolo.setListacheques(listaProtocoloCheques);
		
		listaProtocolo = new ArrayList<Protocolo>();
		listaProtocolo.add(protocolo);
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
		//cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH)); 
		//int primeiroDia = cal.get(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		int ultimoDia = cal.get(Calendar.DAY_OF_MONTH);
		
		String primeiroDiaCorrenteTemp = ("01" + dataAtual.substring(2, 10));
		String ultimoDiaCorrenteTemp = (+ ultimoDia + dataAtual.substring(2, 10));
		
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
		Date datePrimeiroDiaCorrente = (Date)formatter.parse(primeiroDiaCorrenteTemp);
		Date dateUltimoDiaCorrente = (Date)formatter.parse(ultimoDiaCorrenteTemp);
		
		primeiroDiaCorrente = datePrimeiroDiaCorrente;
		ultimoDiaCorrente = dateUltimoDiaCorrente;
    }
	
	public String gerarProtocolo(){
		//GERA A CHAVE UNICA 
		UUID uuid = UUID.randomUUID();  
		String chaveUnica = uuid.toString();
		String chave = chaveUnica.substring(0, 8).toUpperCase();
		
		//PEGA O CPF DO LOGADO
		/*
		String cpf = discipuloSessao.getDiscipulos().getDisCpf().toString();
		cpf = cpf.replaceAll("[.]", "");
		cpf = cpf.replaceAll("[-]", "");
		
		return (cpf + "-" +chave);
		*/
		
		//PEGA O CÓDIGO DO LOGADO
		String codigo = discipuloSessao.getDiscipulos().getDisCod().toString();

        return (codigo + "-" +chave);
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
	public Movimentocheque getMovimentoCheque() {
		return movimentoCheque;
	}

	public void setMovimentoCheque(Movimentocheque movimentoCheque) {
		this.movimentoCheque = movimentoCheque;
	}

	public MovimentochequeId getMovimentoChequeId() {
		return movimentoChequeId;
	}

	public void setMovimentoChequeId(MovimentochequeId movimentoChequeId) {
		this.movimentoChequeId = movimentoChequeId;
	}
	
	public List<Bancos> getListaBancos() {
		return listaBancos;
	}

	public void setListaBancos(List<Bancos> listaBancos) {
		this.listaBancos = listaBancos;
	}
	public int getComboBancos() {
		return comboBancos;
	}

	public void setComboBancos(int comboBancos) {
		this.comboBancos = comboBancos;
	}
	public List<Bancos> getListaBancoSelecionado() {
		return listaBancoSelecionado;
	}

	public void setListaBancoSelecionado(List<Bancos> listaBancoSelecionado) {
		this.listaBancoSelecionado = listaBancoSelecionado;
	}
	public String getLogoBanco() {
		return logoBanco;
	}

	public void setLogoBanco(String logoBanco) {
		this.logoBanco = logoBanco;
	}
	
	public boolean isMostraLogoBanco() {
		return mostraLogoBanco;
	}

	public void setMostraLogoBanco(boolean mostraLogoBanco) {
		this.mostraLogoBanco = mostraLogoBanco;
	}
	
	public boolean isPreDatado() {
		return preDatado;
	}

	public void setPreDatado(boolean preDatado) {
		this.preDatado = preDatado;
	}
	
	public String getClienteDesde() {
		return clienteDesde;
	}

	public void setClienteDesde(String clienteDesde) {
		this.clienteDesde = clienteDesde;
	}
	
	public List<Movimentocheque> getListaMovimentoCheque() {
		return listaMovimentoCheque;
	}

	public void setListaMovimentoCheque(List<Movimentocheque> listaMovimentoCheque) {
		this.listaMovimentoCheque = listaMovimentoCheque;
	}

	public List<MovimentochequeId> getListaMovimentoChequeId() {
		return listaMovimentoChequeId;
	}

	public void setListaMovimentoChequeId(
			List<MovimentochequeId> listaMovimentoChequeId) {
		this.listaMovimentoChequeId = listaMovimentoChequeId;
	}
	
	public int getContadorValidadorCheque() {
		return contadorValidadorCheque;
	}

	public void setContadorValidadorCheque(int contadorValidadorCheque) {
		this.contadorValidadorCheque = contadorValidadorCheque;
	}
	
	public Movimentocheque getMovimentoChequeSelecionado() {
		return movimentoChequeSelecionado;
	}

	public void setMovimentoChequeSelecionado(
			Movimentocheque movimentoChequeSelecionado) {
		this.movimentoChequeSelecionado = movimentoChequeSelecionado;
	}
	
	public String getAcaoBotaoCheque() {
		return acaoBotaoCheque;
	}

	public void setAcaoBotaoCheque(String acaoBotaoCheque) {
		this.acaoBotaoCheque = acaoBotaoCheque;
	}
	
	public boolean isMostraCheque() {
		return mostraCheque;
	}

	public void setMostraCheque(boolean mostraCheque) {
		this.mostraCheque = mostraCheque;
	}

	public boolean isMostraDinheiro() {
		return mostraDinheiro;
	}

	public void setMostraDinheiro(boolean mostraDinheiro) {
		this.mostraDinheiro = mostraDinheiro;
	}
	
	public boolean isMostraCadastrar() {
		return mostraCadastrar;
	}

	public void setMostraCadastrar(boolean mostraCadastrar) {
		this.mostraCadastrar = mostraCadastrar;
	}
	
	public int getContadorValidadorRepasse() {
		return contadorValidadorRepasse;
	}

	public void setContadorValidadorRepasse(int contadorValidadorRepasse) {
		this.contadorValidadorRepasse = contadorValidadorRepasse;
	}
	
	public String getBtnUpdateRepasseDialog() {
		return btnUpdateRepasseDialog;
	}

	public void setBtnUpdateRepasseDialog(String btnUpdateRepasseDialog) {
		this.btnUpdateRepasseDialog = btnUpdateRepasseDialog;
	}

	public String getBtnOnCompleteRepasseDialog() {
		return btnOnCompleteRepasseDialog;
	}

	public void setBtnOnCompleteRepasseDialog(String btnOnCompleteRepasseDialog) {
		this.btnOnCompleteRepasseDialog = btnOnCompleteRepasseDialog;
	}
	
	public Double getValorTotalMovimento() {
		return valorTotalMovimento;
	}

	public void setValorTotalMovimento(Double valorTotalMovimento) {
		this.valorTotalMovimento = valorTotalMovimento;
	}

	public Double getValorTotalMovimentoDinheiro() {
		return valorTotalMovimentoDinheiro;
	}

	public void setValorTotalMovimentoDinheiro(Double valorTotalMovimentoDinheiro) {
		this.valorTotalMovimentoDinheiro = valorTotalMovimentoDinheiro;
	}

	public Double getValorTotalMovimentoCheque() {
		return valorTotalMovimentoCheque;
	}

	public void setValorTotalMovimentoCheque(Double valorTotalMovimentoCheque) {
		this.valorTotalMovimentoCheque = valorTotalMovimentoCheque;
	}
	
	public boolean isDialogRepasseMovimentoDinheiroCheque() {
		return dialogRepasseMovimentoDinheiroCheque;
	}

	public void setDialogRepasseMovimentoDinheiroCheque(
			boolean dialogRepasseMovimentoDinheiroCheque) {
		this.dialogRepasseMovimentoDinheiroCheque = dialogRepasseMovimentoDinheiroCheque;
	}
	
	public boolean isDialogRepasseMovimentoCheque() {
		return dialogRepasseMovimentoCheque;
	}

	public void setDialogRepasseMovimentoCheque(boolean dialogRepasseMovimentoCheque) {
		this.dialogRepasseMovimentoCheque = dialogRepasseMovimentoCheque;
	}
	
	public ProtocoloCheques getProtocoloCheques() {
		return protocoloCheques;
	}

	public void setProtocoloCheques(ProtocoloCheques protocoloCheques) {
		this.protocoloCheques = protocoloCheques;
	}
	
	public List<Protocolo> getListaProtocolo() {
		return listaProtocolo;
	}

	public void setListaProtocolo(List<Protocolo> listaProtocolo) {
		this.listaProtocolo = listaProtocolo;
	}
	
	public List<ProtocoloCheques> getListaProtocoloCheques() {
		return listaProtocoloCheques;
	}

	public void setListaProtocoloCheques(
			List<ProtocoloCheques> listaProtocoloCheques) {
		this.listaProtocoloCheques = listaProtocoloCheques;
	}
	
	public boolean isDialogRepasseMovimentoDinheiro() {
		return dialogRepasseMovimentoDinheiro;
	}

	public void setDialogRepasseMovimentoDinheiro(
			boolean dialogRepasseMovimentoDinheiro) {
		this.dialogRepasseMovimentoDinheiro = dialogRepasseMovimentoDinheiro;
	}
	
	public Discipulos getDiscipuloLogado() {
		return discipuloLogado;
	}

	public void setDiscipuloLogado(Discipulos discipuloLogado) {
		this.discipuloLogado = discipuloLogado;
	}

	public Discipulos getDiscipuladorLogado() {
		return discipuladorLogado;
	}

	public void setDiscipuladorLogado(Discipulos discipuladorLogado) {
		this.discipuladorLogado = discipuladorLogado;
	}

	public boolean isEnviarFinanceiro() {
		return enviarFinanceiro;
	}

	public void setEnviarFinanceiro(boolean enviarFinanceiro) {
		this.enviarFinanceiro = enviarFinanceiro;
	}

	public boolean isEnviarDiscipulador() {
		return enviarDiscipulador;
	}

	public void setEnviarDiscipulador(boolean enviarDiscipulador) {
		this.enviarDiscipulador = enviarDiscipulador;
	}

	public String getRepasseLocal() {
		return repasseLocal;
	}

	public void setRepasseLocal(String repasseLocal) {
		this.repasseLocal = repasseLocal;
	}

	public String getHeaderDialogRepasse() {
		return headerDialogRepasse;
	}

	public void setHeaderDialogRepasse(String headerDialogRepasse) {
		this.headerDialogRepasse = headerDialogRepasse;
	}

	public String getValueButtonRepasse() {
		return valueButtonRepasse;
	}

	public void setValueButtonRepasse(String valueButtonRepasse) {
		this.valueButtonRepasse = valueButtonRepasse;
	}

	public String getValueButtonAbrirDialogRepasse() {
		return valueButtonAbrirDialogRepasse;
	}

	public void setValueButtonAbrirDialogRepasse(
			String valueButtonAbrirDialogRepasse) {
		this.valueButtonAbrirDialogRepasse = valueButtonAbrirDialogRepasse;
	}

	public boolean isEnviarEmailRepasseRecebido() {
		return enviarEmailRepasseRecebido;
	}

	public void setEnviarEmailRepasseRecebido(boolean enviarEmailRepasseRecebido) {
		this.enviarEmailRepasseRecebido = enviarEmailRepasseRecebido;
	}
	
	public boolean isMostraDtCelulas() {
		return mostraDtCelulas;
	}

	public void setMostraDtCelulas(boolean mostraDtCelulas) {
		this.mostraDtCelulas = mostraDtCelulas;
	}

	public boolean isMostraDtBases() {
		return mostraDtBases;
	}

	public void setMostraDtBases(boolean mostraDtBases) {
		this.mostraDtBases = mostraDtBases;
	}

	public boolean isMostraTabViewCelulasBases() {
		return mostraTabViewCelulasBases;
	}

	public void setMostraTabViewCelulasBases(boolean mostraTabViewCelulasBases) {
		this.mostraTabViewCelulasBases = mostraTabViewCelulasBases;
	}
	
	public List<Bases> getListaBases() {
		return listaBases;
	}

	public void setListaBases(List<Bases> listaBases) {
		this.listaBases = listaBases;
	}
	
	public Bases getBases() {
		return bases;
	}

	public void setBases(Bases bases) {
		this.bases = bases;
	}
	
}