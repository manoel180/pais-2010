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
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.primefaces.event.CloseEvent;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.pais.classe.nopersistence.ArvoreGeracoesNodos;
import br.com.pais.classe.nopersistence.ArvoreListarGeracoes;
import br.com.pais.classe.nopersistence.MovimentoCheques;
import br.com.pais.dao.CelulaDao;
import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.GeracaoDao;
import br.com.pais.dao.MovimentoChequeDao;
import br.com.pais.dao.MovimentoDao;
import br.com.pais.dao.RepasseDao;
import br.com.pais.dao.impl.CelulaDaoImp;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.GeracaoDaoImp;
import br.com.pais.dao.impl.MovimentoChequeDaoImp;
import br.com.pais.dao.impl.MovimentoDaoImp;
import br.com.pais.dao.impl.RepasseDaoImp;
import br.com.pais.entities.Bases;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Geracoes;
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

public class RepasseBean {
	
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();
	private Movimento movimento = new Movimento();
	private Movimento movimentoSelecionado = new Movimento();
	private Movimento movimentoGerado = new Movimento();
	private Movimentocheque movimentoCheque = new Movimentocheque();
	private MovimentochequeId movimentoChequeId = new MovimentochequeId();
	private Repasse repasse = new Repasse();
	private RepasseId repasseId = new RepasseId();
	private Celulas celulas = new Celulas();
	private Protocolo protocolo = new Protocolo();
	private Discipulos discipuloLogado = new Discipulos();
	private Discipulos discipuladorLogado = new Discipulos();

	private Movimentocheque chequeSelecionado;
	private MovimentoCheques movimentoChequeNoPersistence = new MovimentoCheques();
	private ProtocoloCheques protocoloCheques = new ProtocoloCheques();
	private List<ProtocoloCheques> listaProtocoloCheques = new ArrayList<ProtocoloCheques>();

	//List
	private List<Discipulos> listaDiscipulos = new ArrayList<Discipulos>();
	private List<Geracoes> listaGeracoes = new ArrayList<Geracoes>();
	private List<Movimento> listaMovimentos = new ArrayList<Movimento>();
	private List<Movimento> listaMovimentosFilhos = new ArrayList<Movimento>();
	private List<Celulas> listaCelulas = new ArrayList<Celulas>();
	private List<Movimentocheque> movimentoCheques = new ArrayList<Movimentocheque>();
	private List<MovimentoCheques> listaMovimentoCheque = new ArrayList<MovimentoCheques>();
	private List<MovimentochequeId> listaMovimentoChequeId = new ArrayList<MovimentochequeId>();
	private List<Protocolo> listaProtocolo = new ArrayList<Protocolo>();

	//Daos
	private MovimentoDao movimentoDao = new MovimentoDaoImp();
	private RepasseDao repasseDao = new RepasseDaoImp();
	private CelulaDao celulaDao = new CelulaDaoImp();
	private  GeracaoDao geracaoDao = new GeracaoDaoImp();
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private MovimentoChequeDao movimentoChequeDao = new MovimentoChequeDaoImp();
	private RelatorioDao relatorioDao = new RelatorioDaoImp();
	
	private boolean dtM12Celulas = false;
	private boolean dtM12Bases = false;
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
	private int qtdCheques = 0;

	private boolean dtMovimentoDinheiro = false;
	private boolean dtMovimentoCheque = false;
	private boolean dtMovimentoDinheiroCheque = false;
	private boolean preDatado = false;
	
	private Double totalRepasse = 0.00;
	private Double totalRepasseDinheiro = 0.00;
	private Double totalRepasseCheques = 0.00;
	private boolean dialogRepasseDinheiro = false; 
	private boolean dialogRepasseDinheiroCheque = false;
	private boolean dialogRepasseCheque = false;
	private boolean enviarFinanceiro = false;
	private boolean enviarDiscipulador = false;
	private String repasseLocal;
	private String headerDialogRepasse = "";
	private String valueButtonRepasse = "";
	private String valueButtonAbrirDialogRepasse = "";
	
	//ÁRVORE REPASSES
	private int index;
	private TreeNode root;
	private DefaultTreeNode treeNode;
	private List<TreeNode> nodes = new ArrayList<TreeNode>();
	private ArvoreGeracoesNodos arvoreGeracoesNodos = new ArvoreGeracoesNodos();
	private List<ArvoreGeracoesNodos> indexGeracoesNodos = new ArrayList<ArvoreGeracoesNodos>();
	private ArvoreListarGeracoes arvoreLista = new ArvoreListarGeracoes();
	private ArvoreListarGeracoes arvoreSelecionada;
	private ArvoreListarGeracoes repasseSelecionadoArvore;
	private Discipulos discipuloArvoreSelecionada;
	private Movimento movimentoArvoreSelecionada;
	private Geracoes geracaoArvoreSelecionada;
	private boolean dtRepasses = false;

	private int qtdRepassesRecebidos;
	private boolean enviarEmailRepasseRecebido = false;
	private boolean mostrarMensagemArvorePasses = false;
	private boolean mostrarDtArvoreRepasses = false;
	private boolean mostrarPnlGrdRepasseFinanceiro = false;
	private boolean mostrarPnlGrdRepasseDiscipulador = false;
	private boolean mostrarGrdRepasseFinanceiroDinheiro = false;
	private boolean mostrarGrdRepasseFinanceiroCheque = false;
	private boolean mostrarGrdRepasseFinanceiroDinheiroCheque = false;
	private boolean mostrarDtArvoreRepassesDetalhe = false;
	
	private boolean mostrarComboGeracaoEnviados = false;
	private boolean mostrarComboGeracaoNaoRecebidos = false;
	private boolean movimentoCelula = false;

	public String prepararArvoreDiscipulo() throws Exception{
		root = new DefaultTreeNode("root", null);
		nodes = new ArrayList<TreeNode>();
		indexGeracoesNodos = new ArrayList<ArvoreGeracoesNodos>();
		movimento = new Movimento();
		comboGeracao = -1;
		mostrarMensagemArvorePasses = false;
		mostrarDtArvoreRepasses = false;
		PrimeiroUltimoDiaCorrente();
		
		return "/cad/repasseArvoresRepasses.mir";
	}
	
	public void detalheArvoreSelecionada(){	
		List<Object> obj;
		Object[] objeto;
		int codDiscipulo = 0;
		
		if(repasseSelecionadoArvore.getMovimento().getMovVisualizado().equals("N")){
			movimento = new Movimento();
			movimento = repasseSelecionadoArvore.getMovimento();
			movimento.setMovVisualizado("S");
			movimentoDao.atualizar(movimento);
		}
		
		if(repasseSelecionadoArvore.getMovimento().getCelulas() == null){
			obj = repasseDao.trasCodigoDiscipuloRepassePorBase(repasseSelecionadoArvore.getMovimento().getMovCod()); //[2] retornou 
			objeto = obj.toArray(); //transformar sua List num array de Objeto  
			codDiscipulo = (Integer) objeto[0]; //transforma objeto[0] me Double
		}
		else{
			obj = repasseDao.trasCodigoDiscipuloRepassePorCelula(repasseSelecionadoArvore.getMovimento().getMovCod()); //[2] retornou 
			objeto = obj.toArray(); //transformar sua List num array de Objeto  
			codDiscipulo = (Integer) objeto[0]; //transforma objeto[0] me Double
		}
		
		discipuloArvoreSelecionada = discipuloDao.listarDiscipulador(codDiscipulo).get(0);
		movimentoChequeNoPersistence = new MovimentoCheques();
		movimentoChequeNoPersistence.setMovimento(repasseSelecionadoArvore.getMovimento());
		movimentoChequeNoPersistence.setTotalRepasseCheques(0.00);
		movimentoChequeNoPersistence.setTotalRepasseDinheiro(0.00);
		
		if(discipuloArvoreSelecionada.getDiscipulos().getDiscipulos() == null){
			mostrarPnlGrdRepasseDiscipulador = false;
			mostrarPnlGrdRepasseFinanceiro = true;
		}
		else{
			mostrarPnlGrdRepasseDiscipulador = true;
			mostrarPnlGrdRepasseFinanceiro = false;
		}
		
		if(repasseSelecionadoArvore.getMovimento().getMovEspecie().equals("Dinheiro")){
			mostrarGrdRepasseFinanceiroDinheiro = true;
			mostrarGrdRepasseFinanceiroCheque = false;
			mostrarGrdRepasseFinanceiroDinheiroCheque = false;
			mostrarDtArvoreRepassesDetalhe = false;
		}
		
		if(repasseSelecionadoArvore.getMovimento().getMovEspecie().equals("Cheque")){
			mostrarGrdRepasseFinanceiroDinheiro = false;
			mostrarGrdRepasseFinanceiroCheque = true;
			mostrarGrdRepasseFinanceiroDinheiroCheque = false;
			mostrarDtArvoreRepassesDetalhe = true;
			
			movimentoCheques = new ArrayList<Movimentocheque>();
			movimentoCheques.addAll(repasseSelecionadoArvore.getMovimento().getMovimentocheques());
			
			movimentoChequeNoPersistence.setQtdCheques(movimentoCheques.size());
		}
		
		if(repasseSelecionadoArvore.getMovimento().getMovEspecie().equals("Dinheiro e Cheque")){
			mostrarGrdRepasseFinanceiroDinheiro = false;
			mostrarGrdRepasseFinanceiroCheque = false;
			mostrarGrdRepasseFinanceiroDinheiroCheque = true;
			mostrarDtArvoreRepassesDetalhe = true;
			
			movimentoCheques = new ArrayList<Movimentocheque>();
			movimentoCheques.addAll(repasseSelecionadoArvore.getMovimento().getMovimentocheques());
			
            movimentoChequeNoPersistence.setQtdCheques(movimentoCheques.size());
			
			totalRepasse = 0.00;
			totalRepasseCheques = 0.00;
			totalRepasseDinheiro = 0.00;
			totalRepasse = movimentoChequeNoPersistence.getMovimento().getMovValor();
			
			//PEGA TODOS OS CHEQUES E SOMA OS VALORES DELES
			for(Movimentocheque cheq : movimentoCheques){
				totalRepasseCheques = totalRepasseCheques + cheq.getId().getValNum();
			}
			totalRepasseDinheiro = (totalRepasse - totalRepasseCheques);
			
			movimentoChequeNoPersistence.setTotalRepasseCheques(totalRepasseCheques);
			movimentoChequeNoPersistence.setTotalRepasseDinheiro(totalRepasseDinheiro);
		}
	}
	
	public void listarArvoreRepasses(AjaxBehaviorEvent event) throws Exception {	
		criarArvoreRepasseDiscipulo();
	}
	
	public void calendarInicioArvoreRepasses(DateSelectEvent event) {  
		primeiroDiaCorrente = event.getDate();
		criarArvoreRepasseDiscipulo();
    }
	
	public void calendarFimArvoreRepasses(DateSelectEvent event) {  
		ultimoDiaCorrente = event.getDate();
		criarArvoreRepasseDiscipulo();
    }
	
	 public void handleCloseDialogDetalheRepasse(CloseEvent event) {
		 criarArvoreRepasseDiscipulo();
	 }
	
	public void criarArvoreRepasseDiscipulo(){
		
		if(comboGeracao < 0 
		||	movimento.getMovTipo() == null || movimento.getMovTipo() == ""
		|| movimento.getMovEspecie() == null || movimento.getMovEspecie() == ""){
			dtRepasses = false;
		}
		else{
			dtRepasses = true;
		}
		
		if(dtRepasses == true){
			listaMovimentos = new ArrayList<Movimento>();
			listaMovimentos.addAll(movimentoDao.listarArvoreRepasses(arvoreSelecionada.getDiscipulo().getDisCod(),
			comboGeracao, movimento.getMovTipo(), movimento.getMovEspecie(), primeiroDiaCorrente, ultimoDiaCorrente));
			
			root = new DefaultTreeNode("root", null);
			nodes = new ArrayList<TreeNode>();	
			indexGeracoesNodos = new ArrayList<ArvoreGeracoesNodos>();
			index = 0;
			int indexGeracao = 0;
			
			if(listaMovimentos.size() <= 0){
				//SETA A MENSAGEM DE NENHUM MOVIMENTO ENCONTRADO
				mostrarMensagemArvorePasses = true;
				mostrarDtArvoreRepasses = false;
			}
			else{
				mostrarMensagemArvorePasses = false;
				mostrarDtArvoreRepasses = true;
			        	
		        for(Movimento mov : listaMovimentos){
		        	indexGeracao = montarArvoreGeracaoMovimentoPai(mov.getCelulas().getGeracoes());
		        	
		        	//SETA O MOVIMENTO PAI NO NODO
		        	arvoreLista = new ArvoreListarGeracoes();
		    		arvoreLista.setDiscipulo(mov.getCelulas().getDiscipulos());
		    		arvoreLista.setGeracao(mov.getCelulas().getGeracoes());
		    		arvoreLista.setMovimento(mov);
		    		arvoreLista.setMostrarDetalhe(true);
		    		arvoreLista.setMostrarFoto(true);
		    		arvoreLista.setMostrarGeracao(false);
		    		arvoreLista.setMostrarGeracao2(true);
		    		
		        	treeNode = new DefaultTreeNode(arvoreLista, nodes.get(indexGeracao));
		        	nodes.add(treeNode);
			    	index = nodes.indexOf(treeNode);
			    	
			    	carregarMovimentosFilhos(mov, nodes.get(index));
				}
			}
		}
	}
	
	public int montarArvoreGeracaoMovimentoPai(Geracoes geracao){
		boolean existeGeracaoCriada = false;
		//CRIA A PRIMEIRA GERAÇÃO
		if(indexGeracoesNodos.size() <= 0){
			//SETA A GERAÇÃO NO NODO
			arvoreLista = new ArvoreListarGeracoes();
			arvoreLista.setDiscipulo(null);
			arvoreLista.setGeracao(geracao);
			arvoreLista.setMovimento(null);
			arvoreLista.setMostrarDetalhe(false);
			arvoreLista.setMostrarFoto(false);
			arvoreLista.setMostrarGeracao(true);
			arvoreLista.setMostrarGeracao2(false);
			
	    	treeNode = new DefaultTreeNode(arvoreLista, root);
	    	nodes.add(treeNode);
	    	index = nodes.indexOf(treeNode);
	    	
	    	//CRIA O NODO NA ARVORE GERAÇÕES NODOS
			arvoreGeracoesNodos = new ArvoreGeracoesNodos();
			arvoreGeracoesNodos.setCodigoGeracao(geracao.getGerCod());
			arvoreGeracoesNodos.setCodigoIndexNodo(index);
			indexGeracoesNodos.add(arvoreGeracoesNodos);
			
			return index;
		}
		else{
			for(ArvoreGeracoesNodos nodo: indexGeracoesNodos){
				//SE EXISTIR ESSE NODO DESSA GERAÇÃO JÁ CRIADO
				if(nodo.getCodigoGeracao() == geracao.getGerCod()){
					existeGeracaoCriada = true;
					return nodo.getCodigoIndexNodo();
				}
				else{
					existeGeracaoCriada = false;
				}
			}
			//VERIFICA SE FOI EXISTE GERAÇÃO NA ARVORE
			if(existeGeracaoCriada == false){
				//SETA A GERAÇÃO NO NODO
				arvoreLista = new ArvoreListarGeracoes();
				arvoreLista.setDiscipulo(null);
				arvoreLista.setGeracao(geracao);
				arvoreLista.setMovimento(null);
				arvoreLista.setMostrarDetalhe(false);
				arvoreLista.setMostrarFoto(false);
				arvoreLista.setMostrarGeracao(true);
				arvoreLista.setMostrarGeracao2(false);
				
		    	treeNode = new DefaultTreeNode(arvoreLista, root);
		    	nodes.add(treeNode);
		    	index = nodes.indexOf(treeNode);
		    	
		    	//CRIA O NODO NA ARVORE GERAÇÕES NODOS
				arvoreGeracoesNodos = new ArvoreGeracoesNodos();
				arvoreGeracoesNodos.setCodigoGeracao(geracao.getGerCod());
				arvoreGeracoesNodos.setCodigoIndexNodo(index);
				indexGeracoesNodos.add(arvoreGeracoesNodos);
				
				return index;
			}
			return 0;
		}
	}
	
	public void carregarMovimentosFilhos(Movimento movimento, TreeNode pai) {
		//VERIFICA SE ESSE ESSE MOVIMENTO TEM FILHOS
    	listaMovimentosFilhos = new ArrayList<Movimento>();
    	listaMovimentosFilhos.addAll(retornaFilhosMovimento(movimento));

    	for (Movimento mov : listaMovimentosFilhos) {
			//SETA O MOVIMENTO NO NODO
        	arvoreLista = new ArvoreListarGeracoes();
        	
        	//Pesquisa Movimento Detalhados Celulas
    		if(mov.getMovTipo().equals("Oferta de Célula")
    		|| mov.getMovTipo().equals("Oferta de Macro-Célula")){
    			arvoreLista.setDiscipulo(mov.getCelulas().getDiscipulos());
        		arvoreLista.setGeracao(mov.getCelulas().getGeracoes());
        		arvoreLista.setMovimento(mov);
        		arvoreLista.setMostrarDetalhe(true);
        		arvoreLista.setMostrarFoto(true);
        		arvoreLista.setMostrarFoto2(false);
        		arvoreLista.setMostrarGeracao(false);
        		arvoreLista.setMostrarGeracao2(true);
    		}
    		else{
    			arvoreLista.setDiscipulo(mov.getBases().getLiderBase());
        		arvoreLista.setGeracao(mov.getBases().getLiderBase().getGeracoes());
        		arvoreLista.setMovimento(mov);
        		arvoreLista.setMostrarDetalhe(true);
        		arvoreLista.setMostrarFoto(false);
        		arvoreLista.setMostrarFoto2(true);
        		arvoreLista.setMostrarGeracao(false);
        		arvoreLista.setMostrarGeracao2(true);
    		}
    		
			treeNode = new DefaultTreeNode(arvoreLista,pai);
			nodes.add(treeNode);
			index = nodes.indexOf(treeNode);
			if(!retornaFilhosMovimento(mov).isEmpty()) {
				carregarMovimentosFilhos(mov, (nodes.get(index)));
			}
		}
	}
	
	public List<Movimento> retornaFilhosMovimento(Movimento movimento){
		listaMovimentosFilhos = new ArrayList<Movimento>();
    	listaMovimentosFilhos.addAll(movimentoDao.listarRepassesFilhos(movimento.getMovProtocolo()));
    	return listaMovimentosFilhos;
	}
	
	public String prepararArvoreRepasses(){
		criarArvoreDiscipulosGeracoes();
		return "/cad/repasseArvores.mir";
	}
	
	public void criarArvoreDiscipulosGeracoes(){
		//SETA O LOGADO NO 1ºNODO
		arvoreLista = new ArvoreListarGeracoes();
		arvoreLista.setDiscipulo(discipuloSessao.getDiscipulos());
		arvoreLista.setGeracao(null);
		arvoreLista.setMostrarDetalhe(false);
		arvoreLista.setMostrarFoto(true);
		arvoreLista.setMostrarGeracao(false);
		arvoreLista.setMostrarGeracao2(false);
		
		root = new DefaultTreeNode("root", null);
		nodes = new ArrayList<TreeNode>();
		nodes.add(new DefaultTreeNode(arvoreLista, root));
		
		listaGeracoes = new ArrayList<Geracoes>();
		listaGeracoes.addAll(geracaoDao.listarGeracoes());
		for(Geracoes ger: listaGeracoes){
			index = 0;
			listaDiscipulos = new ArrayList<Discipulos>();
			listaDiscipulos.addAll(discipuloDao.listarM12(discipuloSessao.getDiscipulos().getDisCod(), ger.getGerCod()));
			
			if(listaDiscipulos.size() > 0){
				//SETA A GERAÇÃO NO NODO
				arvoreLista = new ArvoreListarGeracoes();
	    		arvoreLista.setDiscipulo(null);
	    		arvoreLista.setGeracao(ger);
	    		arvoreLista.setMostrarDetalhe(false);
	    		arvoreLista.setMostrarFoto(false);
	    		arvoreLista.setMostrarGeracao(true);
	    		arvoreLista.setMostrarGeracao2(false);
	        	treeNode = new DefaultTreeNode(arvoreLista, nodes.get(0));
	        	nodes.add(treeNode);
	        	index = nodes.indexOf(treeNode);  
	        	
		        for (Discipulos dis : listaDiscipulos) {
		        	//SETA O DISCIPULO NO NODO
		        	arvoreLista = new ArvoreListarGeracoes();
		    		arvoreLista.setDiscipulo(dis);
		    		arvoreLista.setGeracao(ger);
		    		arvoreLista.setMostrarDetalhe(true);
		    		arvoreLista.setMostrarFoto(true);
		    		arvoreLista.setMostrarGeracao(false);
		    		arvoreLista.setMostrarGeracao2(true);
		    		
		        	treeNode = new DefaultTreeNode(arvoreLista, nodes.get(index));
				}
			}
		}
	}
	
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
		repasseLocal = "";
	
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
				valueButtonAbrirDialogRepasse = "Confirmar Recebimento";
				valueButtonRepasse = "Confirmar";
		    }
		}
		
		//SETA O MOVIMENTO SELECIONADO NO FILTRO
		movimentoSelecionado = new Movimento();
		movimentoSelecionado = movimento;
		
		//Pesquisa Movimento Detalhados Celulas
		if(movimentoSelecionado.getMovTipo().equals("Oferta de Célula")
		|| movimentoSelecionado.getMovTipo().equals("Oferta de Macro-Célula")){
			
			listaMovimentos = new ArrayList<Movimento>();
			listaMovimentos.addAll(repasseDao.listarMovimentosTodosReceberCelulas(movimentoSelecionado.getCelulas().getCelCod(),
			movimentoSelecionado.getMovTipo(), movimentoSelecionado.getMovEspecie(), "N", inicio, fim));
			
			movimentoCelula = true;
		}
		//Pesquisa Movimento Detalhados Bases
		else{
			listaMovimentos = new ArrayList<Movimento>();
			listaMovimentos.addAll(repasseDao.listarMovimentosTodosReceberBases(movimentoSelecionado.getBases().getBasCod(),
			movimentoSelecionado.getMovTipo(), movimentoSelecionado.getMovEspecie(), "N", inicio, fim));
			
			movimentoCelula = false;
		}
		
		totalNaoRecebido = 0.00;
		for(Movimento mov : listaMovimentos){
			totalNaoRecebido = (totalNaoRecebido + mov.getMovValor());
		}
		
		totalStringNaoRecebido =  NumberFormat.getCurrencyInstance().format(totalNaoRecebido);
		totalStringNaoRecebido = totalStringNaoRecebido.replaceAll("[R$]", "");
		
		if(movimento.getMovEspecie().equals("Dinheiro")){
			dtMovimentoDinheiro = true;
			dtMovimentoCheque = false;
			dtMovimentoDinheiroCheque = false;
			
			listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			for(Movimento mov : listaMovimentos){
				movimentoChequeNoPersistence = new MovimentoCheques();
				movimentoChequeNoPersistence.setMovimento(mov);
				movimentoChequeNoPersistence.setTotalRepasseDinheiro(null);
				movimentoChequeNoPersistence.setTotalRepasseCheques(null);
				movimentoChequeNoPersistence.setQtdCheques(0);
				listaMovimentoCheque.add(movimentoChequeNoPersistence);
			}
		}
		
		if(movimento.getMovEspecie().equals("Cheque")){
			dtMovimentoDinheiro = false;
			dtMovimentoCheque = true;
			dtMovimentoDinheiroCheque = false;
			
			listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			for(Movimento mov : listaMovimentos){
				movimentoChequeNoPersistence = new MovimentoCheques();
				movimentoChequeNoPersistence.setMovimento(mov);
				movimentoChequeNoPersistence.setTotalRepasseDinheiro(null);
				movimentoChequeNoPersistence.setTotalRepasseCheques(mov.getMovValor());
				movimentoChequeNoPersistence.setQtdCheques(mov.getMovimentocheques().size());
				listaMovimentoCheque.add(movimentoChequeNoPersistence);
			}
		}
		
		if(movimento.getMovEspecie().equals("Dinheiro e Cheque")){
			dtMovimentoDinheiro = false;
			dtMovimentoCheque = false;
			dtMovimentoDinheiroCheque = true;
			
			listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			for(Movimento mov : listaMovimentos){
				CalculaValorTotalEmDinheiroEmCheque(mov);
				movimentoChequeNoPersistence = new MovimentoCheques();
				movimentoChequeNoPersistence.setMovimento(mov);
				movimentoChequeNoPersistence.setTotalRepasseDinheiro(totalRepasseDinheiro);
				movimentoChequeNoPersistence.setTotalRepasseCheques(totalRepasseCheques);
				movimentoChequeNoPersistence.setQtdCheques(mov.getMovimentocheques().size());
				listaMovimentoCheque.add(movimentoChequeNoPersistence);
			}
		}
		
		return "/cad/repasseReceber.mir";
	}
	
	public void CalculaValorTotalEmDinheiroEmCheque(Movimento movimento){
		totalRepasseCheques = 0.00;
		for(Movimentocheque cheq : movimento.getMovimentocheques()){
			totalRepasseCheques = (totalRepasseCheques + cheq.getId().getValNum());
		}
		totalRepasseDinheiro = (movimento.getMovValor() - totalRepasseCheques);
	}
	
	
	public void detalheCheque(){
		if(chequeSelecionado.getId().getPreDatado().equals("S"))preDatado = true;
		else preDatado = false;
	}
	
	public String prepararRecebidosDetalhe() throws Exception{ 
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		
		//Seta o Movimento Selecionado No Filtro
		movimentoSelecionado = new Movimento();
		movimentoSelecionado = movimento;
		
		//Pesquisa Movimento Detalhados Celulas
		if(movimentoSelecionado.getMovTipo().equals("Oferta de Célula")
		|| movimentoSelecionado.getMovTipo().equals("Oferta de Macro-Célula")){
			
			listaMovimentos = new ArrayList<Movimento>();
			listaMovimentos.addAll(repasseDao.listarMovimentosTodosRecebidosCelulas(movimento.getCelulas().getCelCod(),
			movimento.getMovTipo(), movimento.getMovEspecie(), "S", inicio, fim));
			
			movimentoCelula = true;
		}
		//Pesquisa Movimento Detalhados Bases
		else{
			listaMovimentos = new ArrayList<Movimento>();
			listaMovimentos.addAll(repasseDao.listarMovimentosTodosRecebidosBases(movimento.getBases().getBasCod(),
			movimento.getMovTipo(), movimento.getMovEspecie(), "S", inicio, fim));
			
			movimentoCelula = false;
		}
		
		totalNaoRecebido = 0.00;
		for(Movimento mov : listaMovimentos){
			totalNaoRecebido = (totalNaoRecebido + mov.getMovValor());
		}
		totalStringNaoRecebido =  NumberFormat.getCurrencyInstance().format(totalNaoRecebido);
		totalStringNaoRecebido = totalStringNaoRecebido.replaceAll("[R$]", "");
		
		if(movimento.getMovEspecie().equals("Dinheiro")){
			dtMovimentoDinheiro = true;
			dtMovimentoCheque = false;
			dtMovimentoDinheiroCheque = false;
			
			listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			for(Movimento mov : listaMovimentos){
				movimentoChequeNoPersistence = new MovimentoCheques();
				movimentoChequeNoPersistence.setMovimento(mov);
				movimentoChequeNoPersistence.setTotalRepasseDinheiro(null);
				movimentoChequeNoPersistence.setTotalRepasseCheques(null);
				movimentoChequeNoPersistence.setQtdCheques(0);
				listaMovimentoCheque.add(movimentoChequeNoPersistence);
			}
		}
		
		if(movimento.getMovEspecie().equals("Cheque")){
			dtMovimentoDinheiro = false;
			dtMovimentoCheque = true;
			dtMovimentoDinheiroCheque = false;
			
			listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			for(Movimento mov : listaMovimentos){
				movimentoChequeNoPersistence = new MovimentoCheques();
				movimentoChequeNoPersistence.setMovimento(mov);
				movimentoChequeNoPersistence.setTotalRepasseDinheiro(null);
				movimentoChequeNoPersistence.setTotalRepasseCheques(mov.getMovValor());
				movimentoChequeNoPersistence.setQtdCheques(mov.getMovimentocheques().size());
				listaMovimentoCheque.add(movimentoChequeNoPersistence);
			}
		}
		
		if(movimento.getMovEspecie().equals("Dinheiro e Cheque")){
			dtMovimentoDinheiro = false;
			dtMovimentoCheque = false;
			dtMovimentoDinheiroCheque = true;
			
			listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			for(Movimento mov : listaMovimentos){
				CalculaValorTotalEmDinheiroEmCheque(mov);
				movimentoChequeNoPersistence = new MovimentoCheques();
				movimentoChequeNoPersistence.setMovimento(mov);
				movimentoChequeNoPersistence.setTotalRepasseDinheiro(totalRepasseDinheiro);
				movimentoChequeNoPersistence.setTotalRepasseCheques(totalRepasseCheques);
				movimentoChequeNoPersistence.setQtdCheques(mov.getMovimentocheques().size());
				listaMovimentoCheque.add(movimentoChequeNoPersistence);
			}
		}
		
		return "/cad/repasseRecebidosDetalhe.mir";
	}
	
	public String prepararEnviadosDetalhe() throws Exception{ 
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		
		//PEGO O DISCIPULADOR DO LOGADO
		discipuladorLogado = new Discipulos();
	    discipuladorLogado = discipuloDao.listarDiscipulador(discipuloSessao.getDiscipulos().getDiscipulos().getDisCod()).get(0);
	    	
		//Seta o Movimento Selecionado No Filtro
		movimentoSelecionado = new Movimento();
		movimentoSelecionado = movimento;
		
		//M12 DO RENNER
	    if(discipuladorLogado.getDiscipulos() == null){
	    	listaMovimentos = new ArrayList<Movimento>();
			listaMovimentos.addAll(repasseDao.listarMovimentosTodosEnviadosCelulas(movimento.getCelulas().getCelCod(),
			movimento.getMovTipo(), movimento.getMovEspecie(), inicio, fim));
			
			movimentoCelula = true;
	    }
	    //DISCIPILOS DO M12 DO RENNER
	    else{
	    	//Pesquisa Movimento Detalhados Celulas
			if(movimentoSelecionado.getMovTipo().equals("Oferta de Célula")
			|| movimentoSelecionado.getMovTipo().equals("Oferta de Macro-Célula")){
				
				listaMovimentos = new ArrayList<Movimento>();
				listaMovimentos.addAll(repasseDao.listarMovimentosTodosEnviadosCelulas(movimento.getCelulas().getCelCod(),
				movimento.getMovTipo(), movimento.getMovEspecie(), inicio, fim));
				
				movimentoCelula = true;
			}
			//Pesquisa Movimento Detalhados Bases
			else{
				listaMovimentos = new ArrayList<Movimento>();
				listaMovimentos.addAll(repasseDao.listarMovimentosTodosEnviadosBases(movimento.getBases().getBasCod(),
				movimento.getMovTipo(), movimento.getMovEspecie(), inicio, fim));
				
				movimentoCelula = false;
			}
	    }
		
		totalNaoRecebido = 0.00;
		for(Movimento mov : listaMovimentos){
			totalNaoRecebido = (totalNaoRecebido + mov.getMovValor());
		}
		totalStringNaoRecebido =  NumberFormat.getCurrencyInstance().format(totalNaoRecebido);
		totalStringNaoRecebido = totalStringNaoRecebido.replaceAll("[R$]", "");
		
		if(movimento.getMovEspecie().equals("Dinheiro")){
			dtMovimentoDinheiro = true;
			dtMovimentoCheque = false;
			dtMovimentoDinheiroCheque = false;
			
			listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			for(Movimento mov : listaMovimentos){
				movimentoChequeNoPersistence = new MovimentoCheques();
				movimentoChequeNoPersistence.setMovimento(mov);
				movimentoChequeNoPersistence.setTotalRepasseDinheiro(null);
				movimentoChequeNoPersistence.setTotalRepasseCheques(null);
				movimentoChequeNoPersistence.setQtdCheques(0);
				listaMovimentoCheque.add(movimentoChequeNoPersistence);
			}
		}
		
		if(movimento.getMovEspecie().equals("Cheque")){
			dtMovimentoDinheiro = false;
			dtMovimentoCheque = true;
			dtMovimentoDinheiroCheque = false;
			
			listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			for(Movimento mov : listaMovimentos){
				movimentoChequeNoPersistence = new MovimentoCheques();
				movimentoChequeNoPersistence.setMovimento(mov);
				movimentoChequeNoPersistence.setTotalRepasseDinheiro(null);
				movimentoChequeNoPersistence.setTotalRepasseCheques(mov.getMovValor());
				movimentoChequeNoPersistence.setQtdCheques(mov.getMovimentocheques().size());
				listaMovimentoCheque.add(movimentoChequeNoPersistence);
			}
		}
		
		if(movimento.getMovEspecie().equals("Dinheiro e Cheque")){
			dtMovimentoDinheiro = false;
			dtMovimentoCheque = false;
			dtMovimentoDinheiroCheque = true;
			
			listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			for(Movimento mov : listaMovimentos){
				CalculaValorTotalEmDinheiroEmCheque(mov);
				movimentoChequeNoPersistence = new MovimentoCheques();
				movimentoChequeNoPersistence.setMovimento(mov);
				movimentoChequeNoPersistence.setTotalRepasseDinheiro(totalRepasseDinheiro);
				movimentoChequeNoPersistence.setTotalRepasseCheques(totalRepasseCheques);
				movimentoChequeNoPersistence.setQtdCheques(mov.getMovimentocheques().size());
				listaMovimentoCheque.add(movimentoChequeNoPersistence);
			}
		}
		
		return "/cad/repasseEnviadosDetalhe.mir";
	}
	
	public void enviarEmailMensagemRepasseRecebido(String diretorioImg, String nomeEnviou, String nomeRecebe, 
			String emailEnviar){
		if(enviarEmailRepasseRecebido == true){
			new SendEMail().sendSimpleMailEnviarRepasseRecebido(diretorioImg, nomeEnviou, nomeRecebe, emailEnviar);
		}
	}
	
	public String SalvarRepasse() throws Exception{	
		FacesContext context = FacesContext.getCurrentInstance();		
		Date dataAtual = new Date(System.currentTimeMillis());
		Calendar data = Calendar.getInstance();
		String diretorioImg = getDiretorioReal("/img/");
		
		if(repasseLocal.length()<= 0){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Local do Repasse Obrigatório!"));
			return null;
		}
		else{
			//PEGA A CELULA DO LIDER LOGADO
			listaCelulas = new ArrayList<Celulas>();
			listaCelulas.addAll(celulaDao.listarCelulasGeracao(discipuloSessao.getDiscipulos().getDisCod(), 
			comboGeracao));
			
			//SALVAR O NOVO MOVIMENTO DO LIDER LOGADO
			movimento = new Movimento();
			movimento.setBases(null);
			movimento.setCelulas(listaCelulas.get(0));
			movimento.setMovData(pegaDataAtual());
			movimento.setMovDataCadastro(dataAtual);
			movimento.setMovHoraCadastro(data.getTime());
			movimento.setMovDataBaixa(null);
			movimento.setMovHoraBaixa(null);
			movimento.setMovProtocolo(gerarProtocolo());
			movimento.setMovProtocoloPai(null);
			
			if(enviarFinanceiro == true)movimento.setMovOfertaM12("S");
			else movimento.setMovOfertaM12("N");
			
			movimento.setMovRecebido("N");
			movimento.setMovVisualizado("N");
			movimento.setMovTipo(movimentoSelecionado.getMovTipo());
			movimento.setMovEspecie(movimentoSelecionado.getMovEspecie());
			movimento.setMovValor(totalNaoRecebido);
			movimentoDao.salvar(movimento);
			
			movimentoGerado = new Movimento();
			movimentoGerado = movimento;
			
			//SALVA O REPASSE DO NOVO MOVIMENTO DO LIDER LOGADO
			repasseId = new RepasseId();
			repasseId.setDisCod(discipuloSessao.getDiscipulos().getDisCod());
			repasseId.setMovCod(movimentoGerado.getMovCod());
			repasse.setId(repasseId);
			repasse.setResData(movimentoGerado.getMovData());
			repasse.setResValor(movimentoGerado.getMovValor());
			repasse.setResLocal(repasseLocal);
			repasseDao.salvar(repasse);
			
			String protocoloPai = movimentoGerado.getMovProtocolo();
			//ATUALIZA OS MOVIMENTOS QUE ESTÃO NO GRID DO DISCIPULO DELE
			for(MovimentoCheques mov : listaMovimentoCheque){
				movimento = new Movimento();
				movimento = mov.getMovimento();
				movimento.setMovDataBaixa(movimentoGerado.getMovData());
				movimento.setMovHoraBaixa(movimentoGerado.getMovHoraCadastro());
				movimento.setMovRecebido("S");
				movimento.setMovProtocoloPai(protocoloPai);
				movimentoDao.atualizar(movimento);
			}
			
			listaMovimentoChequeId = new ArrayList<MovimentochequeId>();
			//SALVA OS CHEQUES
			for(MovimentoCheques mov : listaMovimentoCheque){
				movimento = new Movimento();
				movimento = mov.getMovimento();
				
				movimentoCheques = new ArrayList<Movimentocheque>();
				movimentoCheques.addAll(movimento.getMovimentocheques());
				
				for(Movimentocheque cheq : movimentoCheques){
					movimentoCheque = new Movimentocheque();
					movimentoChequeId = new MovimentochequeId();
					
					movimentoChequeId = cheq.getId();
					movimentoChequeId.setCodBanco(cheq.getBancos().getCodBanco());
					movimentoChequeId.setCodMovimento(movimentoGerado.getMovCod());
					movimentoCheque.setId(movimentoChequeId);
					movimentoChequeDao.salvar(movimentoCheque);
					
					prencheChequeDialogRepasseGerado(movimentoChequeId);
				}
			}
			
			preencheProtocoloGerado();
			
			enviarEmailMensagemRepasseRecebido(diretorioImg, discipuloSessao.getDiscipulos().getDisnome(), 
					discipuloSessao.getDiscipulos().getDiscipulos().getDisnome(), 
					discipuloSessao.getDiscipulos().getDiscipulos().getDisemail());
			
			return "/cad/repasseGerado.mir";
		}
	}
	
	public void prencheChequeDialogRepasseGerado(MovimentochequeId id){
		movimentoChequeId = new MovimentochequeId();
		movimentoChequeId = id;
		
		if(movimentoChequeId.getPreDatado().equals("S"))movimentoChequeId.setPreDatado("Sim");
		else movimentoChequeId.setPreDatado("Não");
		
		listaMovimentoChequeId.add(movimentoChequeId);
	}
	
	public void preencheProtocoloGerado() throws Exception{
		totalRepasse = 0.00;
		totalRepasseDinheiro = 0.00;
		totalRepasseCheques = 0.00;
		protocolo = new Protocolo();
		PrimeiroUltimoDiaCorrente();
		
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
		
		protocolo.setDiscipulo(discipuloSessao.getDiscipulos().getDisnome());
		protocolo.setFotoDiscipulo(discipuloSessao.getDiscipulos().getDisfoto());
		protocolo.setTipo(movimentoGerado.getMovTipo());
		protocolo.setInicio(primeiroDiaCorrente);
		protocolo.setFim(ultimoDiaCorrente);
		protocolo.setValormovimento(movimentoGerado.getMovValor());
		protocolo.setData(movimentoGerado.getMovData());
		protocolo.setLocal(repasseLocal);
		protocolo.setProtocolo(movimentoGerado.getMovProtocolo());
		protocolo.setEspecie(movimentoGerado.getMovEspecie());
		protocolo.setListacheques(null);
		protocolo.setValorcheques(null);
		protocolo.setValordinheiro(null);
		
		//Seta os Valores Para O Repasse Gerado
		totalRepasse = movimentoGerado.getMovValor();
		
		if(movimentoGerado.getMovEspecie().equals("Dinheiro")){
			dialogRepasseDinheiro = true;
			dialogRepasseDinheiroCheque = false;
			dialogRepasseCheque = false;
		}
		
		if(movimentoGerado.getMovEspecie().equals("Cheque")){
			dialogRepasseDinheiro = true;
			dialogRepasseDinheiroCheque = false;
			dialogRepasseCheque = true;
		}
		
		if(movimentoGerado.getMovEspecie().equals("Dinheiro e Cheque")){
			dialogRepasseDinheiro = false;
			dialogRepasseDinheiroCheque = true;
			dialogRepasseCheque = false;
			
			//PEGA TODOS OS CHEQUES E SOMA OS VALORES DELES
			for(MovimentochequeId cheq : listaMovimentoChequeId){
				totalRepasseCheques = totalRepasseCheques + cheq.getValNum();
			}
			totalRepasseDinheiro = (totalRepasse - totalRepasseCheques);
			
			protocolo.setValorcheques(totalRepasseCheques);
			protocolo.setValordinheiro(totalRepasseDinheiro);
		}
	}
	
	public void imprimirProtocolo(){
		if(movimentoGerado.getMovEspecie().equals("Dinheiro")){
			preencheProtocoloDinheiro();
			relatorioDao.gerarProtocoloDinheiro(listaProtocolo);
		}
		
		if(movimentoGerado.getMovEspecie().equals("Cheque")){
			preencheProtocoloDinheiroCheque();
			relatorioDao.gerarProtocoloCheque(listaProtocolo);
		}
		
		if(movimentoGerado.getMovEspecie().equals("Dinheiro e Cheque")){
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
	
	public String prepararListarRepasseRecebidos() throws Exception{
        PrimeiroUltimoDiaCorrente();
		
		movimento = new Movimento();
		repasse = new Repasse();
		
		comboGeracao = 0;
		dtM12Celulas = false;
		dtM12Bases = false;
		
		return "/cad/repasseListarRecebidos.mir";
	}
	
	public String prepararListarRepasseEnviados() throws Exception{
        PrimeiroUltimoDiaCorrente();
		
		movimento = new Movimento();
		repasse = new Repasse();
		
		comboGeracao = 0;
		dtM12Celulas = false;
		dtM12Bases = false;
		
		//PEGO O DISCIPULADOR DO LOGADO
		discipuladorLogado = new Discipulos();
	    discipuladorLogado = discipuloDao.listarDiscipulador(discipuloSessao.getDiscipulos().getDiscipulos().getDisCod()).get(0);
	    
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
		
		//Pesquisa Movimento Por Celulas
		if(movimento.getMovTipo().equals("Oferta de Célula")
		   || movimento.getMovTipo().equals("Oferta de Macro-Célula")){
		   
		   if(comboGeracao == 0 || movimento.getMovTipo() == "" || movimento.getMovTipo() == null  
			|| movimento.getMovEspecie() == "" || movimento.getMovEspecie() == null){
			   dtM12Celulas  = false;
			   dtM12Bases  = false;
		   }
		   else{
				dtM12Celulas = true;
				dtM12Bases  = false;
		   }
		   
		   if(dtM12Celulas == true){
			   listaMovimentos = new ArrayList<Movimento>();
			   listaMovimentos.addAll(repasseDao.listarMovimentosReceberCelulas(discipuloSessao.getDiscipulos().getDisCod(), 
			   comboGeracao, "N", movimento.getMovTipo(), movimento.getMovEspecie(), inicio, fim));
			   
			   listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			   for(Movimento mov: listaMovimentos){
				   calcularValorTotalMovimentoNaoRecebidos(true, mov.getCelulas(), null);
				   
				   movimentoChequeNoPersistence = new MovimentoCheques();
				   movimentoChequeNoPersistence.setMovimento(mov);
				   movimentoChequeNoPersistence.setTotalRepasseDinheiro(totalNaoRecebido);
				   movimentoChequeNoPersistence.setQtdCheques(0);
				   movimentoChequeNoPersistence.setTotalRepasseCheques(null);
				   listaMovimentoCheque.add(movimentoChequeNoPersistence);
			   }
		   }
		}
		//Pesquisa Movimento Por Bases
		else{
			if(comboGeracao == 0 || movimento.getMovTipo() == "" || movimento.getMovTipo() == null  
			|| movimento.getMovEspecie() == "" || movimento.getMovEspecie() == null){
			   dtM12Celulas  = false;
			   dtM12Bases  = false;
		    }
		    else{
				dtM12Celulas = false;
				dtM12Bases  = true;
		    }
			
			if(dtM12Bases == true){
				listaMovimentos = new ArrayList<Movimento>();
				listaMovimentos.addAll(repasseDao.listarMovimentosReceberBases(discipuloSessao.getDiscipulos().getDisCod(), 
				comboGeracao, "N", movimento.getMovTipo(), movimento.getMovEspecie(), inicio, fim));
				
				listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			    for(Movimento mov: listaMovimentos){
				   calcularValorTotalMovimentoNaoRecebidos(false, null, mov.getBases());
				   
				   movimentoChequeNoPersistence = new MovimentoCheques();
				   movimentoChequeNoPersistence.setMovimento(mov);
				   movimentoChequeNoPersistence.setTotalRepasseDinheiro(totalNaoRecebido);
				   movimentoChequeNoPersistence.setQtdCheques(0);
				   movimentoChequeNoPersistence.setTotalRepasseCheques(null);
				   listaMovimentoCheque.add(movimentoChequeNoPersistence);
			   }
			}
		}
	}
	
	
	public void calcularValorTotalMovimentoNaoRecebidos(boolean celula, Celulas cel, Bases bas){
		SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		totalNaoRecebido = 0.00;
		
		if(celula == true){
			List<Object> obj = repasseDao.totalMovimentosReceberCelulas(cel.getCelCod(),
			movimento.getMovTipo(), movimento.getMovEspecie(), "N", inicio, fim); //[1500.00] retornou        
			Object[] objeto = obj.toArray(); //transformar sua List num array de Objeto  
			totalNaoRecebido = (Double) objeto[0]; //transforma objeto[0] em Double
		}
		else{
			List<Object> obj = repasseDao.totalMovimentosReceberBases(bas.getBasCod(),
			movimento.getMovTipo(), movimento.getMovEspecie(), "N", inicio, fim); //[1500.00] retornou        
			Object[] objeto = obj.toArray(); //transformar sua List num array de Objeto  
			totalNaoRecebido = (Double) objeto[0]; //transforma objeto[0] em Double
		}
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
		
		//Pesquisa Movimento Por Celulas
		if(movimento.getMovTipo().equals("Oferta de Célula")
		   || movimento.getMovTipo().equals("Oferta de Macro-Célula")){
		   
		   if(comboGeracao == 0 || movimento.getMovTipo() == "" || movimento.getMovTipo() == null  
			|| movimento.getMovEspecie() == "" || movimento.getMovEspecie() == null){
			   dtM12Celulas  = false;
			   dtM12Bases  = false;
		   }
		   else{
				dtM12Celulas = true;
				dtM12Bases  = false;
		   }
		   
		   if(dtM12Celulas == true){
			   listaMovimentos = new ArrayList<Movimento>();
			   listaMovimentos.addAll(repasseDao.listarMovimentosRecebidosCelulas(discipuloSessao.getDiscipulos().getDisCod(), 
			   comboGeracao, "S", movimento.getMovTipo(), movimento.getMovEspecie(), inicio, fim));
			   
			   listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			   for(Movimento mov: listaMovimentos){
				   calcularValorTotalMovimentoRecebidos(true, mov.getCelulas(), null);
				   
				   movimentoChequeNoPersistence = new MovimentoCheques();
				   movimentoChequeNoPersistence.setMovimento(mov);
				   movimentoChequeNoPersistence.setTotalRepasseDinheiro(totalRecebido);
				   movimentoChequeNoPersistence.setQtdCheques(0);
				   movimentoChequeNoPersistence.setTotalRepasseCheques(null);
				   listaMovimentoCheque.add(movimentoChequeNoPersistence);
			   }
		   }
		}
		//Pesquisa Movimento Por Bases
		else{
			if(comboGeracao == 0 || movimento.getMovTipo() == "" || movimento.getMovTipo() == null  
			|| movimento.getMovEspecie() == "" || movimento.getMovEspecie() == null){
			   dtM12Celulas  = false;
			   dtM12Bases  = false;
		    }
		    else{
				dtM12Celulas = false;
				dtM12Bases  = true;
		    }
			
			if(dtM12Bases == true){
				listaMovimentos = new ArrayList<Movimento>();
				listaMovimentos.addAll(repasseDao.listarMovimentosRecebidosBases(discipuloSessao.getDiscipulos().getDisCod(), 
				comboGeracao, "S", movimento.getMovTipo(), movimento.getMovEspecie(), inicio, fim));
				
				listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			    for(Movimento mov: listaMovimentos){
				   calcularValorTotalMovimentoRecebidos(false, null, mov.getBases());
				   
				   movimentoChequeNoPersistence = new MovimentoCheques();
				   movimentoChequeNoPersistence.setMovimento(mov);
				   movimentoChequeNoPersistence.setTotalRepasseDinheiro(totalRecebido);
				   movimentoChequeNoPersistence.setQtdCheques(0);
				   movimentoChequeNoPersistence.setTotalRepasseCheques(null);
				   listaMovimentoCheque.add(movimentoChequeNoPersistence);
			   }
			}
		}
	}
	
	public void calcularValorTotalMovimentoRecebidos(boolean celula, Celulas cel, Bases bas) {
        SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		totalRecebido = 0.00; 
		
		if(celula == true){
			List<Object> obj = repasseDao.totalMovimentosRecebidosCelulas(cel.getCelCod(),
			movimento.getMovTipo(), movimento.getMovEspecie(), "S", inicio, fim); //[1500.00] retornou        
			Object[] objeto = obj.toArray(); //transformar sua List num array de Objeto  
			totalRecebido = (Double) objeto[0]; //transforma objeto[0] em Double
		}
		else{
			List<Object> obj = repasseDao.totalMovimentosRecebidosBases(bas.getBasCod(),
			movimento.getMovTipo(), movimento.getMovEspecie(), "S", inicio, fim); //[1500.00] retornou        
			Object[] objeto = obj.toArray(); //transformar sua List num array de Objeto  
			totalRecebido = (Double) objeto[0]; //transforma objeto[0] em Double
		}
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
		
		//M12 DO RENNER
	    if(discipuladorLogado.getDiscipulos() == null){
	    	mostrarComboGeracaoEnviados = true;
	    	
    	    if(comboGeracao == 0 || movimento.getMovTipo() == "" || movimento.getMovTipo() == null  
			|| movimento.getMovEspecie() == "" || movimento.getMovEspecie() == null){
			   dtM12Celulas  = false;
			   dtM12Bases  = false;
		   }
		   else{
				dtM12Celulas = true;
				dtM12Bases  = false;
		   }
    			   
		   if(dtM12Celulas == true){
			   listaMovimentos = new ArrayList<Movimento>();
			   listaMovimentos.addAll(repasseDao.listarMovimentosEnviadosCelulas(discipuloSessao.getDiscipulos().getDisCod(), 
			   comboGeracao, movimento.getMovTipo(), movimento.getMovEspecie(), inicio, fim));
			   
			   listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			   for(Movimento mov: listaMovimentos){
				   calcularValorTotalMovimentoEnviados(true, mov.getCelulas(), null);
				   
				   movimentoChequeNoPersistence = new MovimentoCheques();
				   movimentoChequeNoPersistence.setMovimento(mov);
				   movimentoChequeNoPersistence.setTotalRepasseDinheiro(totalEnviado);
				   movimentoChequeNoPersistence.setQtdCheques(0);
				   movimentoChequeNoPersistence.setTotalRepasseCheques(null);
				   listaMovimentoCheque.add(movimentoChequeNoPersistence);
			   }
		   }
	    }
	    //DISCIPULOS DOS M12 DO RENNER
	    else{
		//Pesquisa Movimento Por Celulas
		if(movimento.getMovTipo().equals("Oferta de Célula")
		   || movimento.getMovTipo().equals("Oferta de Macro-Célula")){
			mostrarComboGeracaoEnviados = true;
			
		   if(comboGeracao == 0 || movimento.getMovTipo() == "" || movimento.getMovTipo() == null  
			|| movimento.getMovEspecie() == "" || movimento.getMovEspecie() == null){
			   dtM12Celulas  = false;
			   dtM12Bases  = false;
		   }
		   else{
				dtM12Celulas = true;
				dtM12Bases  = false;
		   }
		   
		   if(dtM12Celulas == true){
			   listaMovimentos = new ArrayList<Movimento>();
			   listaMovimentos.addAll(repasseDao.listarMovimentosEnviadosCelulas(discipuloSessao.getDiscipulos().getDisCod(), 
			   comboGeracao, movimento.getMovTipo(), movimento.getMovEspecie(), inicio, fim));
			   
			   listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			   for(Movimento mov: listaMovimentos){
				   calcularValorTotalMovimentoEnviados(true, mov.getCelulas(), null);
				   
				   movimentoChequeNoPersistence = new MovimentoCheques();
				   movimentoChequeNoPersistence.setMovimento(mov);
				   movimentoChequeNoPersistence.setTotalRepasseDinheiro(totalEnviado);
				   movimentoChequeNoPersistence.setQtdCheques(0);
				   movimentoChequeNoPersistence.setTotalRepasseCheques(null);
				   listaMovimentoCheque.add(movimentoChequeNoPersistence);
			   }
		   }
		}
		//Pesquisa Movimento Por Bases
		else{
			mostrarComboGeracaoEnviados = false;
			
			if(movimento.getMovTipo() == "" || movimento.getMovTipo() == null  
			|| movimento.getMovEspecie() == "" || movimento.getMovEspecie() == null){
			   dtM12Celulas  = false;
			   dtM12Bases  = false;
		    }
		    else{
				dtM12Celulas = false;
				dtM12Bases  = true;
		    }
			
			if(dtM12Bases == true){
				listaMovimentos = new ArrayList<Movimento>();
				listaMovimentos.addAll(repasseDao.listarMovimentosEnviadosBases(discipuloSessao.getDiscipulos().getDisCod(), 
				movimento.getMovTipo(), movimento.getMovEspecie(), inicio, fim));
				
				listaMovimentoCheque = new ArrayList<MovimentoCheques>();
			    for(Movimento mov: listaMovimentos){
			       calcularValorTotalMovimentoEnviados(false, null, mov.getBases());
				   
				   movimentoChequeNoPersistence = new MovimentoCheques();
				   movimentoChequeNoPersistence.setMovimento(mov);
				   movimentoChequeNoPersistence.setTotalRepasseDinheiro(totalEnviado);
				   movimentoChequeNoPersistence.setQtdCheques(0);
				   movimentoChequeNoPersistence.setTotalRepasseCheques(null);
				   listaMovimentoCheque.add(movimentoChequeNoPersistence);
			   }
			}
		  }
	    }
	}
	
	public void calcularValorTotalMovimentoEnviados(boolean celula, Celulas cel, Bases bas) {
        SimpleDateFormat formatarDate = new SimpleDateFormat("yyyy-MM-dd");
		String inicio = formatarDate.format(primeiroDiaCorrente);
		String fim = formatarDate.format(ultimoDiaCorrente);
		totalEnviado = 0.00; 
		
		if(celula == true){
			List<Object> obj = repasseDao.totalMovimentosEnviadosCelulas(cel.getCelCod(),
			movimento.getMovTipo(), movimento.getMovEspecie(), inicio, fim); //[1500.00] retornou        
			Object[] objeto = obj.toArray(); //transformar sua List num array de Objeto  
			totalEnviado = (Double) objeto[0]; //transforma objeto[0] em Double
		}
		else{
			List<Object> obj = repasseDao.totalMovimentosEnviadosBases(bas.getBasCod(),
			movimento.getMovTipo(), movimento.getMovEspecie(), inicio, fim); //[1500.00] retornou        
			Object[] objeto = obj.toArray(); //transformar sua List num array de Objeto  
			totalEnviado = (Double) objeto[0]; //transforma objeto[0] em Double
		}
	}
	
	public SelectItem[] getTipoRepasseCombo() {
		List<SelectItem> itens = new ArrayList<SelectItem>();
	    
	    //VERIFICO SE ESSE MEU DISCIPULADOR NÃO TEM DISCIPULADOR, ENTAUM EU SOU M12 DO RENNER
	    if(discipuladorLogado.getDiscipulos() == null){
	    	//So Pode Mandar Mensagem Para Geração
			itens.add(new SelectItem("Oferta de M12", "Oferta de M12"));
			itens.add(new SelectItem("Oferta de Célula", "Oferta de Célula"));
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
	
	public String prepararRepasse() throws Exception{ 
		PrimeiroUltimoDiaCorrente();
		
		movimento = new Movimento();
		repasse = new Repasse();
		
		comboGeracao = 0;
		dtM12Celulas = false;
		dtM12Bases = false;
		mostrarComboGeracaoNaoRecebidos = false;
		
		return "/cad/repasseCadastro.mir";
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
		return totalNaoRecebido;
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
		return totalRecebido;
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
		return totalEnviado;
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
	public Movimento getMovimentoSelecionado() {
		return movimentoSelecionado;
	}

	public void setMovimentoSelecionado(Movimento movimentoSelecionado) {
		this.movimentoSelecionado = movimentoSelecionado;
	}
	
	public List<Movimentocheque> getMovimentoCheques() {
		return movimentoCheques;
	}

	public void setMovimentoCheques(List<Movimentocheque> movimentoCheques) {
		this.movimentoCheques = movimentoCheques;
	}
	
	public int getQtdCheques() {
		qtdCheques = movimento.getMovimentocheques().size();
		return qtdCheques;
	}

	public void setQtdCheques(int qtdCheques) {
		this.qtdCheques = qtdCheques;
	}
	
	public boolean isDtMovimentoDinheiro() {
		return dtMovimentoDinheiro;
	}

	public void setDtMovimentoDinheiro(boolean dtMovimentoDinheiro) {
		this.dtMovimentoDinheiro = dtMovimentoDinheiro;
	}

	public boolean isDtMovimentoCheque() {
		return dtMovimentoCheque;
	}

	public void setDtMovimentoCheque(boolean dtMovimentoCheque) {
		this.dtMovimentoCheque = dtMovimentoCheque;
	}

	public boolean isDtMovimentoDinheiroCheque() {
		return dtMovimentoDinheiroCheque;
	}

	public void setDtMovimentoDinheiroCheque(boolean dtMovimentoDinheiroCheque) {
		this.dtMovimentoDinheiroCheque = dtMovimentoDinheiroCheque;
	}
	
	public Movimentocheque getChequeSelecionado() {
		return chequeSelecionado;
	}

	public void setChequeSelecionado(Movimentocheque chequeSelecionado) {
		this.chequeSelecionado = chequeSelecionado;
	}
	
	public boolean isPreDatado() {
		return preDatado;
	}

	public void setPreDatado(boolean preDatado) {
		this.preDatado = preDatado;
	}
	
	public Double getTotalRepasseDinheiro() {
		return totalRepasseDinheiro;
	}

	public void setTotalRepasseDinheiro(Double totalRepasseDinheiro) {
		this.totalRepasseDinheiro = totalRepasseDinheiro;
	}

	public Double getTotalRepasseCheques() {
		return totalRepasseCheques;
	}

	public void setTotalRepasseCheques(Double totalRepasseCheques) {
		this.totalRepasseCheques = totalRepasseCheques;
	}
	
	public List<MovimentoCheques> getListaMovimentoCheque() {
		return listaMovimentoCheque;
	}

	public void setListaMovimentoCheque(List<MovimentoCheques> listaMovimentoCheque) {
		this.listaMovimentoCheque = listaMovimentoCheque;
	}
	public MovimentoCheques getMovimentoChequeNoPersistence() {
		return movimentoChequeNoPersistence;
	}

	public void setMovimentoChequeNoPersistence(
			MovimentoCheques movimentoChequeNoPersistence) {
		this.movimentoChequeNoPersistence = movimentoChequeNoPersistence;
	}
	
	public Movimento getMovimentoGerado() {
		return movimentoGerado;
	}

	public void setMovimentoGerado(Movimento movimentoGerado) {
		this.movimentoGerado = movimentoGerado;
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
	
	public Double getTotalRepasse() {
		return totalRepasse;
	}

	public void setTotalRepasse(Double totalRepasse) {
		this.totalRepasse = totalRepasse;
	}
	
	public boolean isDialogRepasseDinheiro() {
		return dialogRepasseDinheiro;
	}

	public void setDialogRepasseDinheiro(boolean dialogRepasseDinheiro) {
		this.dialogRepasseDinheiro = dialogRepasseDinheiro;
	}

	public boolean isDialogRepasseDinheiroCheque() {
		return dialogRepasseDinheiroCheque;
	}

	public void setDialogRepasseDinheiroCheque(boolean dialogRepasseDinheiroCheque) {
		this.dialogRepasseDinheiroCheque = dialogRepasseDinheiroCheque;
	}

	public boolean isDialogRepasseCheque() {
		return dialogRepasseCheque;
	}

	public void setDialogRepasseCheque(boolean dialogRepasseCheque) {
		this.dialogRepasseCheque = dialogRepasseCheque;
	}
	
	public List<MovimentochequeId> getListaMovimentoChequeId() {
		return listaMovimentoChequeId;
	}

	public void setListaMovimentoChequeId(
			List<MovimentochequeId> listaMovimentoChequeId) {
		this.listaMovimentoChequeId = listaMovimentoChequeId;
	}
	
	public List<Protocolo> getListaProtocolo() {
		return listaProtocolo;
	}

	public void setListaProtocolo(List<Protocolo> listaProtocolo) {
		this.listaProtocolo = listaProtocolo;
	}
	
	public ProtocoloCheques getProtocoloCheques() {
		return protocoloCheques;
	}

	public void setProtocoloCheques(ProtocoloCheques protocoloCheques) {
		this.protocoloCheques = protocoloCheques;
	}

	public List<ProtocoloCheques> getListaProtocoloCheques() {
		return listaProtocoloCheques;
	}

	public void setListaProtocoloCheques(
			List<ProtocoloCheques> listaProtocoloCheques) {
		this.listaProtocoloCheques = listaProtocoloCheques;
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

	public String getHeaderDialogRepasse() {
		return headerDialogRepasse;
	}

	public void setHeaderDialogRepasse(String headerDialogRepasse) {
		this.headerDialogRepasse = headerDialogRepasse;
	}

	public String getRepasseLocal() {
		return repasseLocal;
	}

	public void setRepasseLocal(String repasseLocal) {
		this.repasseLocal = repasseLocal;
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
	
	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}
	
	public DefaultTreeNode getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(DefaultTreeNode treeNode) {
		this.treeNode = treeNode;
	}
	
	public List<TreeNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeNode> nodes) {
		this.nodes = nodes;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public ArvoreListarGeracoes getArvoreLista() {
		return arvoreLista;
	}

	public void setArvoreLista(ArvoreListarGeracoes arvoreLista) {
		this.arvoreLista = arvoreLista;
	}
	
	public ArvoreListarGeracoes getArvoreSelecionada() {
		return arvoreSelecionada;
	}

	public void setArvoreSelecionada(ArvoreListarGeracoes arvoreSelecionada) {
		this.arvoreSelecionada = arvoreSelecionada;
	}
	
	public boolean isDtRepasses() {
		return dtRepasses;
	}

	public void setDtRepasses(boolean dtRepasses) {
		this.dtRepasses = dtRepasses;
	}
	
	public List<Movimento> getListaMovimentosFilhos() {
		return listaMovimentosFilhos;
	}

	public void setListaMovimentosFilhos(List<Movimento> listaMovimentosFilhos) {
		this.listaMovimentosFilhos = listaMovimentosFilhos;
	}
	
	public int getQtdRepassesRecebidos() {
		int repassesCelulas = repasseDao.listarMovimentosLembrarCelulas(discipuloSessao.getDiscipulos().getDisCod()).size();
		int repassesBases = repasseDao.listarMovimentosLembrarBases(discipuloSessao.getDiscipulos().getDisCod()).size();
		qtdRepassesRecebidos = (repassesCelulas + repassesBases);
		return qtdRepassesRecebidos;
	}

	public void setQtdRepassesRecebidos(int qtdRepassesRecebidos) {
		this.qtdRepassesRecebidos = qtdRepassesRecebidos;
	}
	
	public boolean isEnviarEmailRepasseRecebido() {
		return enviarEmailRepasseRecebido;
	}

	public void setEnviarEmailRepasseRecebido(boolean enviarEmailRepasseRecebido) {
		this.enviarEmailRepasseRecebido = enviarEmailRepasseRecebido;
	}
	
	public boolean isMostrarMensagemArvorePasses() {
		return mostrarMensagemArvorePasses;
	}

	public void setMostrarMensagemArvorePasses(boolean mostrarMensagemArvorePasses) {
		this.mostrarMensagemArvorePasses = mostrarMensagemArvorePasses;
	}
	
	public boolean isMostrarDtArvoreRepasses() {
		return mostrarDtArvoreRepasses;
	}

	public void setMostrarDtArvoreRepasses(boolean mostrarDtArvoreRepasses) {
		this.mostrarDtArvoreRepasses = mostrarDtArvoreRepasses;
	}

	public List<ArvoreGeracoesNodos> getIndexGeracoesNodos() {
		return indexGeracoesNodos;
	}

	public void setIndexGeracoesNodos(List<ArvoreGeracoesNodos> indexGeracoesNodos) {
		this.indexGeracoesNodos = indexGeracoesNodos;
	}

	public ArvoreGeracoesNodos getArvoreGeracoesNodos() {
		return arvoreGeracoesNodos;
	}

	public void setArvoreGeracoesNodos(ArvoreGeracoesNodos arvoreGeracoesNodos) {
		this.arvoreGeracoesNodos = arvoreGeracoesNodos;
	}
	
	public ArvoreListarGeracoes getRepasseSelecionadoArvore() {
		return repasseSelecionadoArvore;
	}

	public void setRepasseSelecionadoArvore(
			ArvoreListarGeracoes repasseSelecionadoArvore) {
		this.repasseSelecionadoArvore = repasseSelecionadoArvore;
	}
	
	public Discipulos getDiscipuloArvoreSelecionada() {
		return discipuloArvoreSelecionada;
	}

	public void setDiscipuloArvoreSelecionada(Discipulos discipuloArvoreSelecionada) {
		this.discipuloArvoreSelecionada = discipuloArvoreSelecionada;
	}

	public Movimento getMovimentoArvoreSelecionada() {
		return movimentoArvoreSelecionada;
	}

	public void setMovimentoArvoreSelecionada(Movimento movimentoArvoreSelecionada) {
		this.movimentoArvoreSelecionada = movimentoArvoreSelecionada;
	}

	public Geracoes getGeracaoArvoreSelecionada() {
		return geracaoArvoreSelecionada;
	}

	public void setGeracaoArvoreSelecionada(Geracoes geracaoArvoreSelecionada) {
		this.geracaoArvoreSelecionada = geracaoArvoreSelecionada;
	}
	
	public boolean isMostrarPnlGrdRepasseFinanceiro() {
		return mostrarPnlGrdRepasseFinanceiro;
	}

	public void setMostrarPnlGrdRepasseFinanceiro(
			boolean mostrarPnlGrdRepasseFinanceiro) {
		this.mostrarPnlGrdRepasseFinanceiro = mostrarPnlGrdRepasseFinanceiro;
	}

	public boolean isMostrarPnlGrdRepasseDiscipulador() {
		return mostrarPnlGrdRepasseDiscipulador;
	}

	public void setMostrarPnlGrdRepasseDiscipulador(
			boolean mostrarPnlGrdRepasseDiscipulador) {
		this.mostrarPnlGrdRepasseDiscipulador = mostrarPnlGrdRepasseDiscipulador;
	}
	
	public boolean isMostrarGrdRepasseFinanceiroDinheiro() {
		return mostrarGrdRepasseFinanceiroDinheiro;
	}

	public void setMostrarGrdRepasseFinanceiroDinheiro(
			boolean mostrarGrdRepasseFinanceiroDinheiro) {
		this.mostrarGrdRepasseFinanceiroDinheiro = mostrarGrdRepasseFinanceiroDinheiro;
	}

	public boolean isMostrarGrdRepasseFinanceiroCheque() {
		return mostrarGrdRepasseFinanceiroCheque;
	}

	public void setMostrarGrdRepasseFinanceiroCheque(
			boolean mostrarGrdRepasseFinanceiroCheque) {
		this.mostrarGrdRepasseFinanceiroCheque = mostrarGrdRepasseFinanceiroCheque;
	}

	public boolean isMostrarGrdRepasseFinanceiroDinheiroCheque() {
		return mostrarGrdRepasseFinanceiroDinheiroCheque;
	}

	public void setMostrarGrdRepasseFinanceiroDinheiroCheque(
			boolean mostrarGrdRepasseFinanceiroDinheiroCheque) {
		this.mostrarGrdRepasseFinanceiroDinheiroCheque = mostrarGrdRepasseFinanceiroDinheiroCheque;
	}

	public boolean isMostrarDtArvoreRepassesDetalhe() {
		return mostrarDtArvoreRepassesDetalhe;
	}

	public void setMostrarDtArvoreRepassesDetalhe(
			boolean mostrarDtArvoreRepassesDetalhe) {
		this.mostrarDtArvoreRepassesDetalhe = mostrarDtArvoreRepassesDetalhe;
	}
	
	public boolean isMostrarComboGeracaoEnviados() {
		return mostrarComboGeracaoEnviados;
	}

	public void setMostrarComboGeracaoEnviados(boolean mostrarComboGeracaoEnviados) {
		this.mostrarComboGeracaoEnviados = mostrarComboGeracaoEnviados;
	}

	public boolean isDtM12Celulas() {
		return dtM12Celulas;
	}

	public void setDtM12Celulas(boolean dtM12Celulas) {
		this.dtM12Celulas = dtM12Celulas;
	}

	public boolean isDtM12Bases() {
		return dtM12Bases;
	}

	public void setDtM12Bases(boolean dtM12Bases) {
		this.dtM12Bases = dtM12Bases;
	}
	
	public boolean isMostrarComboGeracaoNaoRecebidos() {
		return mostrarComboGeracaoNaoRecebidos;
	}

	public void setMostrarComboGeracaoNaoRecebidos(
			boolean mostrarComboGeracaoNaoRecebidos) {
		this.mostrarComboGeracaoNaoRecebidos = mostrarComboGeracaoNaoRecebidos;
	}
	
	public boolean isMovimentoCelula() {
		return movimentoCelula;
	}

	public void setMovimentoCelula(boolean movimentoCelula) {
		this.movimentoCelula = movimentoCelula;
	}
}
