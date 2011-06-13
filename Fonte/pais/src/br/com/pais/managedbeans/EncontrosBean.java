package br.com.pais.managedbeans;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.pais.classe.nopersistence.ArvoreDiscipulos;
import br.com.pais.classe.nopersistence.ArvoreGeracoesNodos;
import br.com.pais.classe.nopersistence.DiscipulosEncontroComDeus;
import br.com.pais.classe.nopersistence.EncontrosNoPersistence;
import br.com.pais.classe.nopersistence.EncontrosNoPersistenceAulas;
import br.com.pais.classe.nopersistence.EncontrosNoPersistenceDiscipulos;
import br.com.pais.classe.nopersistence.PalestrasEncontroComDeus;
import br.com.pais.dao.DadosEncontrosDao;
import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.DiscipulosBatismoDao;
import br.com.pais.dao.EncontroFinanceiroDao;
import br.com.pais.dao.EncontrosDao;
import br.com.pais.dao.EncontrosPalestrasDao;
import br.com.pais.dao.EncontrosStatusDao;
import br.com.pais.dao.FrequenciaDao;
import br.com.pais.dao.InscritosDao;
import br.com.pais.dao.LideresDao;
import br.com.pais.dao.MinistraDao;
import br.com.pais.dao.PalestrasDao;
import br.com.pais.dao.ParticipaDao;
import br.com.pais.dao.impl.DadosEncontrosDaoImp;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.DiscipulosBatismoDaoImp;
import br.com.pais.dao.impl.EncontroFinanceiroDaoImp;
import br.com.pais.dao.impl.EncontrosDaoImp;
import br.com.pais.dao.impl.EncontrosPalestrasDaoImp;
import br.com.pais.dao.impl.EncontrosStatusDaoImp;
import br.com.pais.dao.impl.FrequenciaDaoImp;
import br.com.pais.dao.impl.InscritosDaoImp;
import br.com.pais.dao.impl.LideresDaoImp;
import br.com.pais.dao.impl.MinistraDaoImp;
import br.com.pais.dao.impl.PalestrasDaoImp;
import br.com.pais.dao.impl.ParticipaDaoImp;
import br.com.pais.entities.Dadosencontros;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Discipulosbatismo;
import br.com.pais.entities.Encontrofinanceiro;
import br.com.pais.entities.Encontros;
import br.com.pais.entities.Encontrospalestras;
import br.com.pais.entities.Encontrostatus;
import br.com.pais.entities.Encontrostipolideres;
import br.com.pais.entities.Encontrostipomovimento;
import br.com.pais.entities.Frequencia;
import br.com.pais.entities.FrequenciaId;
import br.com.pais.entities.Geracoes;
import br.com.pais.entities.Inscritos;
import br.com.pais.entities.Lideres;
import br.com.pais.entities.Ministra;
import br.com.pais.entities.MinistraId;
import br.com.pais.entities.Palestras;
import br.com.pais.entities.Participa;
import br.com.pais.util.ApplicationSecurityManager;

public class EncontrosBean {
	
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();
	
	private Encontros encontros = new Encontros();
	private Encontrostatus encontroStatus = new Encontrostatus();
	private Dadosencontros dadosEncontros = new Dadosencontros();
	private Dadosencontros dadosEncontrosSelecionado = new Dadosencontros();
	private Lideres lideres = new Lideres();
	private Discipulos ministrador = new Discipulos();

	//ÁRVORE GERAÇÕES
	private int index;
	private TreeNode root;
	private DefaultTreeNode treeNode;
	private List<TreeNode> nodes = new ArrayList<TreeNode>();
	private ArvoreDiscipulos arvoreLista = new ArvoreDiscipulos();
	private List<Geracoes> listaGeracoes = new ArrayList<Geracoes>();
	private List<Discipulos> listaDiscipulosGeracoes = new ArrayList<Discipulos>();
	private ArvoreGeracoesNodos arvoreGeracoesNodos = new ArvoreGeracoesNodos();
	private List<ArvoreGeracoesNodos> indexGeracoesNodos = new ArrayList<ArvoreGeracoesNodos>();
	private ArvoreDiscipulos discipuloArvoreSelecionado;

	//ADICIONAR REMOVER DISCIPULOS
	private List<EncontrosNoPersistenceDiscipulos> dtDisGeracoes = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	private List<EncontrosNoPersistenceDiscipulos> dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	private EncontrosNoPersistenceDiscipulos[] dtDisSelecionadosAdicionar;
	private EncontrosNoPersistenceDiscipulos[] dtDisSelecionadosRemover;

	// Objetos Daos
	private  EncontrosDao encontrosDao = new EncontrosDaoImp();
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp(); 
	private DadosEncontrosDao dadosEncontrosDao = new DadosEncontrosDaoImp();
	private LideresDao lideresDao = new LideresDaoImp();
	private MinistraDao ministraDao = new MinistraDaoImp();
	private InscritosDao inscritosDao = new InscritosDaoImp();
	private ParticipaDao participaDao = new ParticipaDaoImp();
	private PalestrasDao palestrasDao = new PalestrasDaoImp();
	private EncontrosPalestrasDao encontrosPalestrasDao = new EncontrosPalestrasDaoImp();
	private FrequenciaDao frequenciaDao = new FrequenciaDaoImp();
	
	private MinistraId ministraId = new MinistraId();
	private Ministra ministra = new Ministra();
	private Inscritos inscritos = new Inscritos();
	private Participa participa = new Participa();
	private Palestras palestras = new Palestras();
	private Encontrospalestras encontrosPalestras = new Encontrospalestras();
	private FrequenciaId frequenciaId = new FrequenciaId();
	private Frequencia frequencia = new Frequencia();
	
	private List<Palestras> listaPalestras =  new ArrayList<Palestras>();
	private List<Encontrospalestras> listaEncontrosPalestras = new ArrayList<Encontrospalestras>();
	private List<Dadosencontros> listaPreEncontros = new ArrayList<Dadosencontros>();
	private EncontrosNoPersistence encontrosNoPersistence = new EncontrosNoPersistence();
	private EncontrosNoPersistenceAulas encontrosNoPersistenceAula = new EncontrosNoPersistenceAulas();
	private List<EncontrosNoPersistence> listaEncontrosNoPersistence = new ArrayList<EncontrosNoPersistence>();

	//ARVORES PRE-ENCONTRO
	private EncontrosNoPersistenceDiscipulos[] dtDisSelecionadosPresenca;
	private Encontrospalestras encontroPalestra = new Encontrospalestras();
	private int filtroPreEncontro = 0; 
	private EncontrosNoPersistenceDiscipulos encontrosDiscipulo = new EncontrosNoPersistenceDiscipulos();
	private List<EncontrosNoPersistenceDiscipulos> listaDiscipuloPreEncontro = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	private List<EncontrosNoPersistenceDiscipulos> listaDiscipuloPossuiPreEncontro = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	private List<EncontrosNoPersistenceDiscipulos> listaDiscipuloNaoPossuiPreEncontro = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	private List<Frequencia> listaFrequenciaAula = new ArrayList<Frequencia>(); 
	private boolean mostrarDtPreEncontros = false;
	private EncontrosStatusDao encontroStatusDao = new EncontrosStatusDaoImp();
	
	//ENCONTRO COM DEUS
	private BigDecimal valorLocalEncontro;
	private BigDecimal valorTransporte;
	private BigDecimal valorPorPessoa;
	private BigDecimal valorLocalEncontroAntigo;
	private BigDecimal valorTransporteAntigo;
	private BigDecimal valorPorPessoaAntigo;
	private Discipulos liderEspiritual = new Discipulos();
	private Discipulos liderIntercessao = new Discipulos();
	private Discipulos liderCorreio = new Discipulos();
	private Discipulos liderApoioRecepcao = new Discipulos();
	private Lideres liderEspiritualAntigo = new Lideres();
	private Lideres liderIntercessaoAntigo = new Lideres();
	private Lideres liderCorreioAntigo = new Lideres();
	private Lideres liderApoioRecepcaoAntigo = new Lideres();
	private Encontrostipomovimento encontroTipoMovimento = new Encontrostipomovimento();
	private Encontrofinanceiro encontroFinanceiro = new Encontrofinanceiro();
	private Encontrostipolideres encontroTipoLider = new Encontrostipolideres();
	private EncontroFinanceiroDao encontroFinanceiroDao = new EncontroFinanceiroDaoImp();
	
	//ENCONTRO COM DEUS ADICIONAR REMOVER DISCIPULOS
	private List<DiscipulosEncontroComDeus> dtDisEncontroDeus = new ArrayList<DiscipulosEncontroComDeus>();
	private List<DiscipulosEncontroComDeus> dtDisEncontroDeusEscolhidos = new ArrayList<DiscipulosEncontroComDeus>();
	private DiscipulosEncontroComDeus[] dtDisEncontroDeusSelecionadosAdicionar;
	private DiscipulosEncontroComDeus[] dtDisEncontroDeusSelecionadosRemover;
	
	private List<PalestrasEncontroComDeus> listaPalestraEncontroComDeus = new ArrayList<PalestrasEncontroComDeus>();
	private PalestrasEncontroComDeus palestraEncontroComDeus = new PalestrasEncontroComDeus();
	private PalestrasEncontroComDeus palestraEncontroComDeusSelecionada = new PalestrasEncontroComDeus();
	
	private DiscipulosEncontroComDeus discipuloEncontroComDeus = new DiscipulosEncontroComDeus();
	
	private Date horarioPalestra = null;
	private String horarioPalestra2 = "";
	private Discipulos liderPalestra = new Discipulos();
	private int indexPalestraSelecionada = 0;
	
	private boolean mostrarBtnCancelarEncontro = false;
	
	private int filtroEncontroComDeus = 0;
	private List<Dadosencontros> listaEncontroComDeus = new ArrayList<Dadosencontros>();
	private boolean mostrarDtEncontroComDeus = false;
	private int inscricoesPagasEncontroComDeus = 0;
	private BigDecimal valorInscricoesPagasEncontroComDeus = new BigDecimal(0.00); 
	private BigDecimal valorOfertaTirada = new BigDecimal(0.00); 
	private BigDecimal valorOfertaVoluntaria = new BigDecimal(0.00); 
	private BigDecimal valorTotalEntrada = new BigDecimal(0.00); 
	private BigDecimal valorMaterialHigienico = new BigDecimal(0.00);
	private BigDecimal valorRefeicao = new BigDecimal(0.00); 
	private BigDecimal valorCorreio = new BigDecimal(0.00); 
	private BigDecimal valorTotalSaida = new BigDecimal(0.00); 
	private BigDecimal valorTotalBruto = new BigDecimal(0.00); 
	private boolean valorBrutoPositivo = true;
	
	private boolean mostrarBtnVoltarEncontro = false;
	
	private int filtroPosEncontro = 0;
	
	private DiscipulosBatismoDao discipulosBatismoDao = new DiscipulosBatismoDaoImp();
	private Discipulosbatismo discipulosBatismo = new Discipulosbatismo();

	public String prepararCadastrarPreEncontros() {
    	dadosEncontros = new Dadosencontros();
    	ministrador = new Discipulos();
    	filtroPreEncontro = 0;
    	dtDisGeracoes = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>(); 
		listaDiscipuloNaoPossuiPreEncontro = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		listaDiscipuloPossuiPreEncontro = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		
		//Carrego os discipulos com e sem pré-encontro
		carregaArvoreDiscipulosComPreEncontro(discipuloSessao.getDiscipulos());
		carregaArvoreDiscipulosSemPreEncontro(discipuloSessao.getDiscipulos());
		
    	criarArvoreDiscipulosGeracoes();
    	AdicionarPalestra();
		
		return "/cad/preEncontros.mir";
	}
	
	public void carregaArvoreDiscipulosComPreEncontro(Discipulos discipulo){
		//lista todos os discipulos do logado
		List<Discipulos> listaDiscipulos = new ArrayList<Discipulos>();
		listaDiscipulos.addAll(discipulo.getDiscipuloses());
		
		for(Discipulos dis : listaDiscipulos){
			//Tem Pré-Encontro
			if(discipuloDao.discipuloComPreEncontro(dis.getDisCod()).size() > 0){
				encontrosDiscipulo = new EncontrosNoPersistenceDiscipulos();
				encontrosDiscipulo.setDiscipulo(dis);
				encontrosDiscipulo.setPossuiPreEncontro(true);
				listaDiscipuloPossuiPreEncontro.add(encontrosDiscipulo);
			}
			//discipulo possui discipulos
			if(!dis.getDiscipuloses().isEmpty()) {
				carregaArvoreDiscipulosComPreEncontro(dis);
			}
		}
	}
	
	public void carregaArvoreDiscipulosSemPreEncontro(Discipulos discipulo){
		//lista todos os discipulos do logado
		List<Discipulos> listaDiscipulos = new ArrayList<Discipulos>();
		listaDiscipulos.addAll(discipulo.getDiscipuloses());
		
		for(Discipulos dis : listaDiscipulos){
			//Não Tem Pré-Encontro
			if(discipuloDao.discipuloSemPreEncontro(dis.getDisCod()).size() > 0){
				encontrosDiscipulo = new EncontrosNoPersistenceDiscipulos();
				encontrosDiscipulo.setDiscipulo(dis);
				encontrosDiscipulo.setPossuiPreEncontro(false);
				listaDiscipuloNaoPossuiPreEncontro.add(encontrosDiscipulo);
			}
			//discipulo possui discipulos
			if(!dis.getDiscipuloses().isEmpty()) {
				carregaArvoreDiscipulosSemPreEncontro(dis);
			}
		}
	}
    
    public void AdicionarPalestra(){
		listaPalestras =  new ArrayList<Palestras>();
		listaPalestras.addAll(palestrasDao.listarPalestrasEncontros(1));
	}
	
	public void ExcluirPalestra(){
		listaPalestras.remove(palestras);
		palestras = new Palestras();
	}
	
	public void criarArvoreDiscipulosGeracoes(){
		index = 0;
		int indexGeracao = 0;
		indexGeracoesNodos = new ArrayList<ArvoreGeracoesNodos>();
		
		//SETA O LOGADO NO 1ºNODO
		arvoreLista = new ArvoreDiscipulos();
		arvoreLista.setDiscipulo(discipuloSessao.getDiscipulos());
		arvoreLista.setMostrarDiscipulo(true);
		arvoreLista.setGeracao(null);
		arvoreLista.setMostrarGeracao(false);
		arvoreLista.setMostrarGeracao2(false);
		arvoreLista.setMostrarBotoes(false);
		
		root = new DefaultTreeNode("root", null);
		nodes = new ArrayList<TreeNode>();
		nodes.add(new DefaultTreeNode(arvoreLista, root));
		
		listaDiscipulosGeracoes = new ArrayList<Discipulos>();
		listaDiscipulosGeracoes = discipuloSessao.getDiscipulos().getDiscipuloses();
		
		for (Discipulos dis : listaDiscipulosGeracoes) {
        	
        	indexGeracao = montarArvoreGeracaoMovimentoPai(dis, dis.getGeracoes());
        	
        	//SETA O DISCIPULO NO NODO
        	arvoreLista = new ArvoreDiscipulos();
        	arvoreLista.setDiscipulo(dis);
			arvoreLista.setGeracao(dis.getGeracoes());
			arvoreLista.setMostrarDiscipulo(true);
			arvoreLista.setMostrarGeracao(false);
			arvoreLista.setMostrarGeracao2(true);
			arvoreLista.setMostrarBotoes(true);
        	
        	treeNode = new DefaultTreeNode(arvoreLista, nodes.get(indexGeracao));
        	nodes.add(treeNode);
	    	index = nodes.indexOf(treeNode);
	    	
	    	carregarDiscipulosFilhos(dis, nodes.get(index));
		}	
	}
	
	public int montarArvoreGeracaoMovimentoPai(Discipulos discipulo, Geracoes geracao){
		boolean existeGeracaoCriada = false;
		//CRIA A PRIMEIRA GERAÇÃO
		if(indexGeracoesNodos.size() <= 0){
			//SETA A GERAÇÃO NO NODO
			arvoreLista = new ArvoreDiscipulos();
			arvoreLista.setDiscipulo(null);
			arvoreLista.setGeracao(geracao);
			arvoreLista.setMostrarDiscipulo(false);
			arvoreLista.setMostrarGeracao(true);
			arvoreLista.setMostrarGeracao2(false);
			arvoreLista.setMostrarBotoes(false);
			
	    	treeNode = new DefaultTreeNode(arvoreLista, root);
	    	nodes.add(treeNode);
	    	index = nodes.indexOf(treeNode);
	    	
	    	//CRIA O NODO NA ARVORE GERAÇÕES NODOS
			arvoreGeracoesNodos = new ArvoreGeracoesNodos();
			arvoreGeracoesNodos.setCodigoGeracao(geracao.getGerCod());
			arvoreGeracoesNodos.setCodigoIndexNodo(index);
			arvoreGeracoesNodos.setCodigoDiscipulador(discipulo.getDisCod());
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
				arvoreLista = new ArvoreDiscipulos();
				arvoreLista.setDiscipulo(null);
				arvoreLista.setGeracao(geracao);
				arvoreLista.setMostrarDiscipulo(false);
				arvoreLista.setMostrarGeracao(true);
				arvoreLista.setMostrarGeracao2(false);
				arvoreLista.setMostrarBotoes(false);
				
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
	
	public void carregarDiscipulosFilhos(Discipulos discipulo, TreeNode pai) {
		listaDiscipulosGeracoes = new ArrayList<Discipulos>();
		listaDiscipulosGeracoes = discipulo.getDiscipuloses();
		
		for (Discipulos dis : listaDiscipulosGeracoes) {
        	
        	//SETA O DISCIPULO NO NODO
        	arvoreLista = new ArvoreDiscipulos();
        	arvoreLista.setDiscipulo(dis);
			arvoreLista.setGeracao(dis.getGeracoes());
			arvoreLista.setMostrarDiscipulo(true);
			arvoreLista.setMostrarGeracao(false);
			arvoreLista.setMostrarGeracao2(true);
			arvoreLista.setMostrarBotoes(true);
        	
        	treeNode = new DefaultTreeNode(arvoreLista, pai);
        	nodes.add(treeNode);
	    	index = nodes.indexOf(treeNode);
	    	
	    	if(! dis.getDiscipuloses().isEmpty()) {
	    		carregarDiscipulosFilhos(dis, (nodes.get(index)));
			}
		}
	}
	
	public void filtrarDiscipulosPreEncontro(AjaxBehaviorEvent event) throws Exception {
		//Com Pré-Encontros
		if(filtroPreEncontro == 1){
			dtDisGeracoes = new ArrayList<EncontrosNoPersistenceDiscipulos>(); 
			for(EncontrosNoPersistenceDiscipulos dis :  listaDiscipuloPossuiPreEncontro){
				dtDisGeracoes.add(dis);
			}
		}
		//Sem Pré-Encontros
		if(filtroPreEncontro == 2){
			dtDisGeracoes = new ArrayList<EncontrosNoPersistenceDiscipulos>();
			for(EncontrosNoPersistenceDiscipulos dis :  listaDiscipuloNaoPossuiPreEncontro){
				dtDisGeracoes.add(dis);
			}
		}
	}
	
	public void adicionarDiscipulosPreEncontro() {  
    	List<EncontrosNoPersistenceDiscipulos> listaTempSelecionados = new ArrayList<EncontrosNoPersistenceDiscipulos>();
    	//Guarda os Selecionados Na Lista Temporaria
		for(EncontrosNoPersistenceDiscipulos dis: dtDisSelecionadosAdicionar){
			listaTempSelecionados.add(dis);
		}
		
		//Adiciona na lista dos escolhidos a lista de baixo na tela
		for(EncontrosNoPersistenceDiscipulos dis: listaTempSelecionados){
			dtDisEscolhidos.add(dis);
		}
		
		//Remove o discipulo na lista da geração filtrada a lista de cima na tela
		List<EncontrosNoPersistenceDiscipulos> listaTempRemover = new ArrayList<EncontrosNoPersistenceDiscipulos>(dtDisGeracoes);
	    for(EncontrosNoPersistenceDiscipulos dis : listaTempSelecionados){
	    	for(EncontrosNoPersistenceDiscipulos dis2 : dtDisGeracoes){
	    		if(dis.getDiscipulo().getDisCod().equals(dis2.getDiscipulo().getDisCod())){
	    			listaTempRemover.remove(dis2);
	    		}
	    	}
	    }
	    dtDisGeracoes = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	    dtDisGeracoes.addAll(listaTempRemover);
	    
	    //Possui Pré-Encontro
	    if(filtroPreEncontro == 1){
	    	listaDiscipuloPossuiPreEncontro = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	    	listaDiscipuloPossuiPreEncontro.addAll(dtDisGeracoes);
	    }
	    //Sem Pré-Encontro
	    if(filtroPreEncontro == 2){
	    	listaDiscipuloNaoPossuiPreEncontro = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	    	listaDiscipuloNaoPossuiPreEncontro.addAll(dtDisGeracoes);
	    }
    }
    
    public void removerDiscipulosPreEncontro() {  
    	List<EncontrosNoPersistenceDiscipulos> listaTempSelecionados = new ArrayList<EncontrosNoPersistenceDiscipulos>();
    	//Guarda os Selecionados Na Lista Temporaria
		for(EncontrosNoPersistenceDiscipulos dis: dtDisSelecionadosRemover){
			listaTempSelecionados.add(dis);
		}
		
		//Adiciona o discipulo na lista da geração filtrada a lista de cima na tela
		for(EncontrosNoPersistenceDiscipulos dis: listaTempSelecionados){
			//Possui Pré-Encontro
			if(dis.isPossuiPreEncontro() == true){
				//Filtro Possui Pré-Encontro
			    if(filtroPreEncontro == 1){
			    	dtDisGeracoes.add(dis);
			    	listaDiscipuloPossuiPreEncontro.add(dis);
			    }
			    //Filtro Não Possui Pré-Encontro
			    if(filtroPreEncontro == 2){
			    	listaDiscipuloPossuiPreEncontro.add(dis);
			    }
			}		
			//Não Possui Pré-Encontro
			else{
				//Filtro Possui Pré-Encontro
			    if(filtroPreEncontro == 1){
			    	listaDiscipuloNaoPossuiPreEncontro.add(dis);
			    }
				//Filtro Não Possui Pré-Encontro
			    if(filtroPreEncontro == 2){
			    	dtDisGeracoes.add(dis);
			    	listaDiscipuloNaoPossuiPreEncontro.add(dis);
			    }
			}
		}
	    
	    //Remove o discipulo na lista dos escolhidos a lista de baixo na tela
		List<EncontrosNoPersistenceDiscipulos> listaTempRemover = new ArrayList<EncontrosNoPersistenceDiscipulos>(dtDisEscolhidos);
	    for(EncontrosNoPersistenceDiscipulos dis : listaTempSelecionados) {
	    	for(EncontrosNoPersistenceDiscipulos dis2 : dtDisEscolhidos){
	    		if(dis.getDiscipulo().getDisCod().equals(dis2.getDiscipulo().getDisCod())){
	    			listaTempRemover.remove(dis2);
	    		}
	    	}
	    }
	    dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	    dtDisEscolhidos.addAll(listaTempRemover);
    }
	
	public String prepararPresencaPreEncontro(){
		FacesContext context = FacesContext.getCurrentInstance(); 
		
		List<Inscritos> listaIncritos = new ArrayList<Inscritos>();
		List<Discipulos> listaTempDiscipulos = new ArrayList<Discipulos>();
		
		dadosEncontros = new Dadosencontros();
		dadosEncontros = encontrosNoPersistenceAula.getPalestras().getDadosencontros();
		ministrador = new Discipulos();
		ministrador = encontrosNoPersistenceAula.getPalestras().getDadosencontros().getMinistras().get(0).getDiscipulos();
		encontroPalestra = new Encontrospalestras();
		encontroPalestra = encontrosNoPersistenceAula.getPalestras();
		
		//Aula Não Liberada
		if(encontrosNoPersistenceAula.isLiberada() == false){
			//Primeira Aula
			if(encontrosNoPersistenceAula.getAula() <= 1){
				//Pega os incritos
				listaIncritos.addAll(dadosEncontros.getInscritoses());
				for(Inscritos ins : listaIncritos){
					listaTempDiscipulos.add(ins.getDiscipulos());
				}
				
				dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
				for(Discipulos dis : listaTempDiscipulos){
					encontrosDiscipulo = new EncontrosNoPersistenceDiscipulos();
					encontrosDiscipulo.setDiscipulo(dis);
					encontrosDiscipulo.setPossuiPreEncontro(true);
					dtDisEscolhidos.add(encontrosDiscipulo);
				}
				return "/cad/preEncontrosPresenca.mir";
			}
			//Segunda Aula em Diante
			else{
				//Aula Anterior Liberada
				if(retornaAulaAnterior(encontrosNoPersistenceAula.getAula()).isLiberada() == true){
					
					//Pega os incritos
					listaIncritos.addAll(dadosEncontros.getInscritoses());
					for(Inscritos ins : listaIncritos){
						listaTempDiscipulos.add(ins.getDiscipulos());
					}
					
					dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
					for(Discipulos dis : listaTempDiscipulos){
						encontrosDiscipulo = new EncontrosNoPersistenceDiscipulos();
						encontrosDiscipulo.setDiscipulo(dis);
						encontrosDiscipulo.setPossuiPreEncontro(true);
						dtDisEscolhidos.add(encontrosDiscipulo);
					}
					return "/cad/preEncontrosPresenca.mir";
				}
				//Aula Anterior Não Liberada
				else{
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Aula Ainda Não Liberada!"));
					return null;
				}
			}
		}
		//Aula Já Liberada Mostra os Detalhe Dessa Aula
		else{
			listaFrequenciaAula = new ArrayList<Frequencia>(); 
			listaFrequenciaAula.addAll(encontroPalestra.getFrequencias());
			//presentes
			List<Discipulos> listaTempDiscipuloPresente = new ArrayList<Discipulos>();
			for(Frequencia freq: listaFrequenciaAula){
				listaTempDiscipuloPresente.add(freq.getDiscipulos());
			}
			
			//inscritos
			listaIncritos.addAll(dadosEncontros.getInscritoses());
			for(Inscritos ins : listaIncritos){
				listaTempDiscipulos.add(ins.getDiscipulos());
			}
			
			listaDiscipuloPreEncontro = new ArrayList<EncontrosNoPersistenceDiscipulos>();
			for(Discipulos dis : listaTempDiscipulos){
				encontrosDiscipulo = new EncontrosNoPersistenceDiscipulos();
    			encontrosDiscipulo.setDiscipulo(dis);
    			encontrosDiscipulo.setPossuiPreEncontro(true);
    			encontrosDiscipulo.setPresenteAulaPreEncontro(false);
    			listaDiscipuloPreEncontro.add(encontrosDiscipulo);
			}
			
		    for(Discipulos dis : listaTempDiscipuloPresente){
		    	for(EncontrosNoPersistenceDiscipulos dis2 : listaDiscipuloPreEncontro){
		    		if(dis.getDisCod().equals(dis2.getDiscipulo().getDisCod())){
		    			int index = listaDiscipuloPreEncontro.indexOf(dis2);
		    			
		    			encontrosDiscipulo = new EncontrosNoPersistenceDiscipulos();
		    			encontrosDiscipulo.setDiscipulo(dis2.getDiscipulo());
		    			encontrosDiscipulo.setPossuiPreEncontro(true);
		    			encontrosDiscipulo.setPresenteAulaPreEncontro(true);
		    			listaDiscipuloPreEncontro.set(index, encontrosDiscipulo);
		    		}
		    	}
		    }
			
			return "/editar/preEncontrosPresenca.mir";
		}
	}
	
	public EncontrosNoPersistenceAulas retornaAulaAnterior(int aula){
		if(aula == 2){
			return encontrosNoPersistence.getAula1();
		}
		if(aula == 3){
			return encontrosNoPersistence.getAula2();
		}
		if(aula == 4){
			return encontrosNoPersistence.getAula3();
		}
		return null;
	}
	
	public boolean validacaoPresencaPreEncontro(){
		FacesContext context = FacesContext.getCurrentInstance();
		int contadorValidador = 0;
		
		//Data Ministração
		if(encontroPalestra.getDataRealizacao() == null){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Selecione a Data da Ministração!"));
		}
		
		if(contadorValidador <= 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public String prepararEfetuarPresencaPreEncontros(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		//Se for a ultima aula Finaliza Esse Encontro
		if(encontrosNoPersistenceAula.getAula() == 4){
			encontroStatus = new Encontrostatus();
			encontroStatus.setEncstacod(2);//1 = Em Andamento 2 = Finalizado
			dadosEncontros.setEncontrostatusByDadencstatus(encontroStatus);
			dadosEncontrosDao.atualizar(dadosEncontros);
		}
		//Atualiza a data da palestra
		encontrosPalestrasDao.atualizar(encontroPalestra);
		
		//Se For Tudo Validado
		if(validacaoPresencaPreEncontro() == true){
			//dar a presenca
			for(EncontrosNoPersistenceDiscipulos dis : dtDisSelecionadosPresenca){
				frequenciaId = new FrequenciaId();
				frequenciaId.setDiscipulos(dis.getDiscipulo().getDisCod());
				frequenciaId.setEncontrospalestras(encontroPalestra.getEncpalestrasCod());
				frequencia = new Frequencia();
				frequencia.setId(frequenciaId);
				frequenciaDao.salvar(frequencia);
			}
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Presença Efetivada com sucesso!"));
			
			filtroPreEncontro = 0;
			listaEncontrosNoPersistence = new ArrayList<EncontrosNoPersistence>();
			listaPreEncontros = new ArrayList<Dadosencontros>();
			mostrarDtPreEncontros = false;
			
			return "/list/preEncontros.mir";
		}
		else{
			return null;
		}
	}
	
	// ComboBox EncontrosStatus
	public SelectItem[] getComboEncontrosStatus() {
		List<Encontrostatus> status = encontroStatusDao.listaEncontrosStatus();
		List<SelectItem> itens = new ArrayList<SelectItem>(status.size());

		for (Encontrostatus e : status) {
			itens.add(new SelectItem(e.getEncstacod(), e.getEncstadescricao()));
		}
		return itens.toArray(new SelectItem[itens.size()]);
	}
	
	public String prepararListarPreEncontros() {
		filtroPreEncontro = 0;
		listaEncontrosNoPersistence = new ArrayList<EncontrosNoPersistence>();
		listaPreEncontros = new ArrayList<Dadosencontros>();
		mostrarDtPreEncontros = false;
		
		return "/list/preEncontros.mir";
	}
	
	public void filtrarPreEncontro(AjaxBehaviorEvent event) throws Exception {
		//Selecione
		if(filtroPreEncontro <= 0){
			mostrarDtPreEncontros = false;
		}
		//Em Andamento Finalizado
		else{
			ListarPreEncontros(filtroPreEncontro);
			mostrarDtPreEncontros = true;
		}
	}
	
	public void ListarPreEncontros(int status){
		//Lista os pre encontros em andamento
		listaPreEncontros = new ArrayList<Dadosencontros>();
		listaPreEncontros.addAll(dadosEncontrosDao.listarPreEncontros(discipuloSessao.getDiscipulos().getDisCod(), status));
		
		listaEncontrosNoPersistence = new ArrayList<EncontrosNoPersistence>();	
		for(Dadosencontros pre : listaPreEncontros){
			encontrosNoPersistence = new EncontrosNoPersistence();
			encontrosNoPersistence.setDadosEncontros(pre);
			encontrosNoPersistence.setMinistrador(pre.getMinistras().get(0).getDiscipulos());
			
			//Pega as palestras
			listaEncontrosPalestras = new ArrayList<Encontrospalestras>();
			listaEncontrosPalestras.addAll(pre.getEncontrospalestrases());
			int numPal = 1;
			for(Encontrospalestras pal: listaEncontrosPalestras){
				encontrosNoPersistenceAula = new EncontrosNoPersistenceAulas();
				//Aulas naum liberadas
				if(pal.getDataRealizacao() == null){
					if(numPal == 1){
						encontrosNoPersistenceAula.setLiberada(false);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(1);
						encontrosNoPersistence.setAula1(encontrosNoPersistenceAula);
					}
					if(numPal == 2){
						encontrosNoPersistenceAula.setLiberada(false);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(2);
						encontrosNoPersistence.setAula2(encontrosNoPersistenceAula);
					}
					if(numPal == 3){
						encontrosNoPersistenceAula.setLiberada(false);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(3);
						encontrosNoPersistence.setAula3(encontrosNoPersistenceAula);
					}
					if(numPal == 4){
						encontrosNoPersistenceAula.setLiberada(false);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(4);
						encontrosNoPersistence.setAula4(encontrosNoPersistenceAula);
					}
				}
				//Aulas Liberadas
				else{
					if(numPal == 1){
						encontrosNoPersistenceAula.setLiberada(true);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(1);
						encontrosNoPersistence.setAula1(encontrosNoPersistenceAula);
					}
					if(numPal == 2){
						encontrosNoPersistenceAula.setLiberada(true);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(2);
						encontrosNoPersistence.setAula2(encontrosNoPersistenceAula);
					}
					if(numPal == 3){
						encontrosNoPersistenceAula.setLiberada(true);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(3);
						encontrosNoPersistence.setAula3(encontrosNoPersistenceAula);
					}
					if(numPal == 4){
						encontrosNoPersistenceAula.setLiberada(true);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(4);
						encontrosNoPersistence.setAula4(encontrosNoPersistenceAula);
					}
				}
				numPal++;
			}
			listaEncontrosNoPersistence.add(encontrosNoPersistence);
		}
	}
	
	// ComboBox Encontros
	public SelectItem[] getEncontrosCheck() {
		List<Encontros> le = encontrosDao.todos();
		List<SelectItem> itens = new ArrayList<SelectItem>(le.size());

		for (Encontros e : le) {
			itens.add(new SelectItem(e.getEncCod(), e.getEncDescricao()));
		}// for end
		return itens.toArray(new SelectItem[itens.size()]);
	}
	
	public boolean validacaoPreEncontro(){
		FacesContext context = FacesContext.getCurrentInstance();
		int contadorValidador = 0;
		
		//Ministrador
		if(ministrador.getDisCod() == null){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Selecione o Ministrador!"));
		}
		//horario
		if(dadosEncontros.getDadenchorario() == null){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Informe o Horário!"));
		}
		//Data de Inicio
		if(dadosEncontros.getDadencdatainicio() == null){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Informe a data de inicio!"));
		}
		//local
		if(dadosEncontros.getDadenclocal() == null || dadosEncontros.getDadenclocal() == ""){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Informe o Local!"));
		}
		//Discipulos Escolhidos
		if(dtDisEscolhidos.size() <= 0){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Nenhum Discipulo Adicionado no Pré-Encontro!"));
		}
		
		if(contadorValidador <= 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public String SalvarPreEncontro(){

		FacesContext context = FacesContext.getCurrentInstance();
		
		//Se For Tudo Validado
		if(validacaoPreEncontro() == true){
			
		//Salva o Pré-Encontro 
		encontros = new Encontros();
		encontros.setEncCod(1);//1 = Pré Encontros
		encontroStatus = new Encontrostatus();
		encontroStatus.setEncstacod(1);//1 = Em Andamento 2 = Finalizado
		dadosEncontros.setEncontros(encontros);
		dadosEncontros.setDadosencontros(null);//encontroPai
		dadosEncontros.setEncontrostatusByDadencstatus(encontroStatus);//status
		dadosEncontros.setEncontrostatusBySolicitaescolalideres(encontroStatus);
		dadosEncontros.setDiscipulos(discipuloSessao.getDiscipulos());//responsavel
		dadosEncontros.setDadencqtdinscritos(dtDisEscolhidos.size());
		dadosEncontrosDao.salvar(dadosEncontros);
		
		//Ministrador
		ministraId = new MinistraId();
		ministraId.setDiscipulosDisCod(ministrador.getDisCod());
		ministraId.setDadosencontrosDadenccod(dadosEncontros.getDadenccod());
		ministra.setId(ministraId);
		ministraDao.salvar(ministra);
		
		//inscritos
		for(EncontrosNoPersistenceDiscipulos dis : dtDisEscolhidos){
			inscritos = new Inscritos();
			inscritos.setDiscipulos(dis.getDiscipulo());
			inscritos.setDadosencontros(dadosEncontros);
			inscritos.setPago(null);
			inscritosDao.salvar(inscritos);
		}
		
		//Participa
		for(EncontrosNoPersistenceDiscipulos dis : dtDisEscolhidos){
			participa = new Participa();
			participa.setDadosencontros(dadosEncontros);
			participa.setDiscipulos(dis.getDiscipulo());
			participaDao.salvar(participa);
		}
		
		//Encontros Palestras
		for(Palestras pal : listaPalestras){
			encontrosPalestras = new Encontrospalestras();
			encontrosPalestras.setPalestras(pal);//palestras_cod
			encontrosPalestras.setDiscipulos(ministrador);
			encontrosPalestras.setDadosencontros(dadosEncontros);
			encontrosPalestras.setDataRealizacao(null);
			encontrosPalestras.setHorarioRealizacao(null);
			encontrosPalestrasDao.salvar(encontrosPalestras);
		}
		
		filtroPreEncontro = 0;
		listaEncontrosNoPersistence = new ArrayList<EncontrosNoPersistence>();
		listaPreEncontros = new ArrayList<Dadosencontros>();
		mostrarDtPreEncontros = false;

		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Pré-Encontro Cadastrado com Sucesso!"));
		
		return "/list/preEncontros.mir";
		}
		else{
			return null;
		}
	}
	
	public String prepararListarEncontroComDeusCadastrar() {
		listaEncontrosNoPersistence = new ArrayList<EncontrosNoPersistence>();
		listaPreEncontros = new ArrayList<Dadosencontros>();
		
		ListarPreEncontrosFinalizados();
		
		return "/cad/encontroComDeusListar.mir";
	}
	
	public void ListarPreEncontrosFinalizados(){
		//Lista os pre encontros finalizados
		listaPreEncontros = new ArrayList<Dadosencontros>();
		listaPreEncontros.addAll(dadosEncontrosDao.listarPreEncontros(discipuloSessao.getDiscipulos().getDisCod(), 2));
		
		//verificar se esse encontro tem encontro filho
		List<Dadosencontros> listaTempRemover = new ArrayList<Dadosencontros>(listaPreEncontros);
		for(Dadosencontros enc : listaPreEncontros){
			int retorno = dadosEncontrosDao.listarEncontrosFilhos(enc.getDadenccod()).size();
    		if(retorno > 0){
    			listaTempRemover.remove(enc);
    		}
    	}
		listaPreEncontros = new ArrayList<Dadosencontros>();
		listaPreEncontros.addAll(listaTempRemover);
		
		listaEncontrosNoPersistence = new ArrayList<EncontrosNoPersistence>();	
		for(Dadosencontros pre : listaPreEncontros){
			encontrosNoPersistence = new EncontrosNoPersistence();
			encontrosNoPersistence.setDadosEncontros(pre);
			encontrosNoPersistence.setMinistrador(pre.getMinistras().get(0).getDiscipulos());
			
			//Pega as palestras
			listaEncontrosPalestras = new ArrayList<Encontrospalestras>();
			listaEncontrosPalestras.addAll(pre.getEncontrospalestrases());
			int numPal = 1;
			for(Encontrospalestras pal: listaEncontrosPalestras){
				encontrosNoPersistenceAula = new EncontrosNoPersistenceAulas();
				//Aulas naum liberadas
				if(pal.getDataRealizacao() == null){
					if(numPal == 1){
						encontrosNoPersistenceAula.setLiberada(false);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(1);
						encontrosNoPersistence.setAula1(encontrosNoPersistenceAula);
					}
					if(numPal == 2){
						encontrosNoPersistenceAula.setLiberada(false);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(2);
						encontrosNoPersistence.setAula2(encontrosNoPersistenceAula);
					}
					if(numPal == 3){
						encontrosNoPersistenceAula.setLiberada(false);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(3);
						encontrosNoPersistence.setAula3(encontrosNoPersistenceAula);
					}
					if(numPal == 4){
						encontrosNoPersistenceAula.setLiberada(false);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(4);
						encontrosNoPersistence.setAula4(encontrosNoPersistenceAula);
					}
				}
				//Aulas Liberadas
				else{
					if(numPal == 1){
						encontrosNoPersistenceAula.setLiberada(true);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(1);
						encontrosNoPersistence.setAula1(encontrosNoPersistenceAula);
					}
					if(numPal == 2){
						encontrosNoPersistenceAula.setLiberada(true);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(2);
						encontrosNoPersistence.setAula2(encontrosNoPersistenceAula);
					}
					if(numPal == 3){
						encontrosNoPersistenceAula.setLiberada(true);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(3);
						encontrosNoPersistence.setAula3(encontrosNoPersistenceAula);
					}
					if(numPal == 4){
						encontrosNoPersistenceAula.setLiberada(true);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(4);
						encontrosNoPersistence.setAula4(encontrosNoPersistenceAula);
					}
				}
				numPal++;
			}
			listaEncontrosNoPersistence.add(encontrosNoPersistence);
		}
	}
	
	public String prepararCadastrarEncontroComDeus() {
    	dadosEncontros = new Dadosencontros();
    	liderEspiritual = new Discipulos();
    	liderApoioRecepcao = new Discipulos();
    	liderCorreio = new Discipulos();
    	liderIntercessao = new Discipulos();
    	dtDisEncontroDeus = new ArrayList<DiscipulosEncontroComDeus>();
    	dtDisEncontroDeusEscolhidos = new ArrayList<DiscipulosEncontroComDeus>();
    	
    	valorLocalEncontro = new BigDecimal(0.00);
    	valorPorPessoa = new BigDecimal(0.00);
    	valorTransporte = new BigDecimal(0.00);
    	
    	criarArvoreDiscipulosGeracoes();
    	listarPalestrasEncontroComDeus();
    	
    	listarEncontristas();
		
		return "/cad/encontroComDeus.mir";
	}
	
	public void listarPalestrasEncontroComDeus(){
		listaPalestras =  new ArrayList<Palestras>();
		listaPalestras.addAll(palestrasDao.listarPalestrasEncontros(2));
		
		listaPalestraEncontroComDeus = new ArrayList<PalestrasEncontroComDeus>();
		for(Palestras pal : listaPalestras){
			encontroPalestra = new Encontrospalestras();
			encontroPalestra.setPalestras(pal);
			encontroPalestra.setDiscipulos(null);
			encontroPalestra.setDadosencontros(null);
			encontroPalestra.setDataRealizacao(null);
			encontroPalestra.setHorarioRealizacao(null);
			
			palestraEncontroComDeus = new PalestrasEncontroComDeus();
			palestraEncontroComDeus.setAtualizada(false);
			palestraEncontroComDeus.setPalestra(encontroPalestra);
			
			listaPalestraEncontroComDeus.add(palestraEncontroComDeus);
		}
	}
	
	public void listarEncontristas(){
		//Pega os incritos
		List<Inscritos> listaIncritos = new ArrayList<Inscritos>();
		listaIncritos.addAll(encontrosNoPersistence.getDadosEncontros().getInscritoses());
		dtDisEncontroDeus = new ArrayList<DiscipulosEncontroComDeus>();
		for(Inscritos ins : listaIncritos){
			discipuloEncontroComDeus = new DiscipulosEncontroComDeus();
			discipuloEncontroComDeus.setDiscipulo(ins.getDiscipulos());
			discipuloEncontroComDeus.setAulas(retornaAulasPresentesPreEncontro(encontrosNoPersistence.getDadosEncontros().getDadenccod(),
			ins.getDiscipulos().getDisCod()));
			discipuloEncontroComDeus.setPago(false);
			dtDisEncontroDeus.add(discipuloEncontroComDeus);
		}
	}
	
	public int retornaAulasPresentesPreEncontro(int encontro, int discipulo){
		listaFrequenciaAula = new ArrayList<Frequencia>(); 
		listaFrequenciaAula.addAll(frequenciaDao.listaFrequenciaDiscipuloEncontro(encontro, discipulo));
		
		return listaFrequenciaAula.size();
	}
	
	public void adicionarDiscipulosEncontroComDeus() {  
    	List<DiscipulosEncontroComDeus> listaTempSelecionados = new ArrayList<DiscipulosEncontroComDeus>();
    	//Guarda os Selecionados Na Lista Temporaria
		for(DiscipulosEncontroComDeus dis: dtDisEncontroDeusSelecionadosAdicionar){
			listaTempSelecionados.add(dis);
		}
		
		//Adiciona na lista dos escolhidos a lista de baixo na tela
		for(DiscipulosEncontroComDeus dis: listaTempSelecionados){
			dtDisEncontroDeusEscolhidos.add(dis);
		}
		
		//Remove o discipulo na lista da geração filtrada a lista de cima na tela
		List<DiscipulosEncontroComDeus> listaTempRemover = new ArrayList<DiscipulosEncontroComDeus>(dtDisEncontroDeus);
	    for(DiscipulosEncontroComDeus dis : listaTempSelecionados){
	    	for(DiscipulosEncontroComDeus dis2 : dtDisEncontroDeus){
	    		if(dis.getDiscipulo().getDisCod().equals(dis2.getDiscipulo().getDisCod())){
	    			listaTempRemover.remove(dis2);
	    		}
	    	}
	    }
	    dtDisEncontroDeus = new ArrayList<DiscipulosEncontroComDeus>();
	    dtDisEncontroDeus.addAll(listaTempRemover);
    }
	
	public void removerDiscipulosEncontroComDeus() {  
		List<DiscipulosEncontroComDeus> listaTempSelecionados = new ArrayList<DiscipulosEncontroComDeus>();
    	//Guarda os Selecionados Na Lista Temporaria
		for(DiscipulosEncontroComDeus dis: dtDisEncontroDeusSelecionadosRemover){
			listaTempSelecionados.add(dis);
		}
		
		//Adiciona na lista dos encontristas a lista de cima na tela
		for(DiscipulosEncontroComDeus dis: listaTempSelecionados){
			dtDisEncontroDeus.add(dis);
		}
		
		//Remove o discipulo na lista dos escolhidos a lista de baixo na tela
		List<DiscipulosEncontroComDeus> listaTempRemover = new ArrayList<DiscipulosEncontroComDeus>(dtDisEncontroDeusEscolhidos);
	    for(DiscipulosEncontroComDeus dis : listaTempSelecionados){
	    	for(DiscipulosEncontroComDeus dis2 : dtDisEncontroDeusEscolhidos){
	    		if(dis.getDiscipulo().getDisCod().equals(dis2.getDiscipulo().getDisCod())){
	    			listaTempRemover.remove(dis2);
	    		}
	    	}
	    }
	    dtDisEncontroDeusEscolhidos = new ArrayList<DiscipulosEncontroComDeus>();
	    dtDisEncontroDeusEscolhidos.addAll(listaTempRemover);
    }
	
	public void pagarEncontroComDeusInscritos(){
		for(DiscipulosEncontroComDeus dis: dtDisEncontroDeusSelecionadosRemover){
			int index = dtDisEncontroDeusEscolhidos.indexOf(dis);
			discipuloEncontroComDeus = new DiscipulosEncontroComDeus();
			discipuloEncontroComDeus.setDiscipulo(dis.getDiscipulo());
			discipuloEncontroComDeus.setAulas(dis.getAulas());
			discipuloEncontroComDeus.setPago(true);
			dtDisEncontroDeusEscolhidos.set(index, discipuloEncontroComDeus);
		}
	}
	
	public void defazerPagamentoEncontroComDeusInscritos(){
		for(DiscipulosEncontroComDeus dis: dtDisEncontroDeusSelecionadosRemover){
			int index = dtDisEncontroDeusEscolhidos.indexOf(dis);
			discipuloEncontroComDeus = new DiscipulosEncontroComDeus();
			discipuloEncontroComDeus.setDiscipulo(dis.getDiscipulo());
			discipuloEncontroComDeus.setAulas(dis.getAulas());
			discipuloEncontroComDeus.setPago(false);
			dtDisEncontroDeusEscolhidos.set(index, discipuloEncontroComDeus);
		}
	}
	
	public void dlgDetalhePalestra(){
		if(palestraEncontroComDeusSelecionada.getPalestra().getDiscipulos() != null){
			liderPalestra = palestraEncontroComDeusSelecionada.getPalestra().getDiscipulos();
		}
		else{
			liderPalestra = new Discipulos();
		}
		horarioPalestra = palestraEncontroComDeusSelecionada.getPalestra().getHorarioRealizacao();
		if(horarioPalestra == null){
			horarioPalestra2 = "";
		}
		else{
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
			horarioPalestra2 = dateFormat.format(horarioPalestra);
		}
	}
	
	public void dlgDetalhePalestraSalvar(){
		indexPalestraSelecionada = listaPalestraEncontroComDeus.indexOf(palestraEncontroComDeusSelecionada);
		
		encontroPalestra = new Encontrospalestras();
		encontroPalestra.setPalestras(palestraEncontroComDeusSelecionada.getPalestra().getPalestras());
		
		if(liderPalestra.getDisCod() == null){
			encontroPalestra.setDiscipulos(null);
		}
		else{
			encontroPalestra.setDiscipulos(liderPalestra);
		}
		encontroPalestra.setDadosencontros(null);
		encontroPalestra.setDataRealizacao(null);
		encontroPalestra.setHorarioRealizacao(horarioPalestra);
		
		palestraEncontroComDeus = new PalestrasEncontroComDeus();
		palestraEncontroComDeus.setPalestra(encontroPalestra);
		if(liderPalestra.getDisCod() == null || horarioPalestra == null){
			palestraEncontroComDeus.setAtualizada(false);
		}
		else{
			palestraEncontroComDeus.setAtualizada(true);
		}
		listaPalestraEncontroComDeus.set(indexPalestraSelecionada, palestraEncontroComDeus);
	}
	
	public void validarHoraPalestra() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("HH:mm"); 
		
		if(horarioPalestra2.equals("__:__") || horarioPalestra2.equals("")){
			horarioPalestra = null;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERRO", "Horário em branco"));
		}
		else{
			String hora = (horarioPalestra2.substring(0, 2));
			String min = (horarioPalestra2.substring(3, 5));
			
			int horaInt = Integer.parseInt(hora);
			int minInt = Integer.parseInt(min);
			
			if(horaInt > 23 || minInt > 59){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERRO", "Hora inválida, formato correto de 00:00 até 23:59"));
			}
			else{
				horarioPalestra = (Date)formatter.parse(horarioPalestra2);
			}
		}
	}
	
	public boolean validacaoEncontroComDeus(){
		FacesContext context = FacesContext.getCurrentInstance();
		int contadorValidador = 0;
		
		if(dadosEncontros.getDadenclocal() == null || dadosEncontros.getDadenclocal() == ""){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Digite o Local do Encontro com Deus!"));
		}
		
		if(dadosEncontros.getDadencdatainicio() == null){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Selecione a Data de Inicio!"));
		}
		
		if(dadosEncontros.getDadencdatafim() == null){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Selecione a Data de Termino!"));
		}
		
		if(valorLocalEncontro.doubleValue() == 0.00){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Valor do Local do Encontro Zerado!"));
		}
		
		if(valorPorPessoa.doubleValue() == 0.00){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Valor do encontro por Pessoa Zerado!"));
		}
		
		if(valorTransporte.doubleValue() == 0.00){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Valor do transporte Zerado!"));
		}
		
		if(liderEspiritual.getDisnome() == null || liderEspiritual.getDisnome() == ""){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Lider Espiritual em Branco!"));
		}
		
		if(liderIntercessao.getDisnome() == null || liderIntercessao.getDisnome() == ""){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Lider da Intercessão em Branco!"));
		}
		
		if(liderCorreio.getDisnome() == null || liderCorreio.getDisnome() == ""){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Lider dos Correios em Branco!"));
		}
		
		if(liderApoioRecepcao.getDisnome() == null || liderApoioRecepcao.getDisnome() == ""){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Lider do Apoio e Recepção em Branco!"));
		}
		
		if(dtDisEncontroDeusEscolhidos.size() <= 0){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Nenhum Díscipulo Adicionado no Encontro Com Deus!"));
		}
		
		int palestrasAtualizadas = 0;
		for(PalestrasEncontroComDeus pal : listaPalestraEncontroComDeus){
			if(pal.isAtualizada() == false){
				palestrasAtualizadas++;
			}
		}
		
		if(palestrasAtualizadas > 0){
			contadorValidador = contadorValidador + 1;
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Existem Palestras ainda Não Confirmadas!"));
		}
		
		if(contadorValidador <= 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	public String salvarEncontroComDeus() {
        FacesContext context = FacesContext.getCurrentInstance();
		
		//Se For Tudo Validado
		if(validacaoEncontroComDeus() == true){
			//Salva o Encontro com Deus 
			encontros = new Encontros();
			encontros.setEncCod(2);//2 = Encontro Deus
			encontroStatus = new Encontrostatus();
			encontroStatus.setEncstacod(1);//1 = Em Andamento 2 = Finalizado
			dadosEncontros.setEncontros(encontros);
			dadosEncontros.setDadosencontros(encontrosNoPersistence.getDadosEncontros());//encontroPai
			dadosEncontros.setEncontrostatusByDadencstatus(encontroStatus);//status
			dadosEncontros.setEncontrostatusBySolicitaescolalideres(encontroStatus);//status
			dadosEncontros.setDiscipulos(discipuloSessao.getDiscipulos());//responsavel
			dadosEncontros.setDadencqtdinscritos(dtDisEncontroDeusEscolhidos.size());
			dadosEncontrosDao.salvar(dadosEncontros);
			
			//Valor Local
			encontroTipoMovimento = new Encontrostipomovimento();
			encontroTipoMovimento.setEnctipmovCod(6); //local
			encontroFinanceiro = new Encontrofinanceiro();
			encontroFinanceiro.setDadosencontros(dadosEncontros);
			encontroFinanceiro.setValorMovimento(valorLocalEncontro);
			encontroFinanceiro.setEncontrostipomovimento(encontroTipoMovimento);
			encontroFinanceiroDao.salvar(encontroFinanceiro);
			
			//Valor transporte
			encontroTipoMovimento = new Encontrostipomovimento();
			encontroTipoMovimento.setEnctipmovCod(9); //transporte
			encontroFinanceiro = new Encontrofinanceiro();
			encontroFinanceiro.setDadosencontros(dadosEncontros);
			encontroFinanceiro.setValorMovimento(valorTransporte);
			encontroFinanceiro.setEncontrostipomovimento(encontroTipoMovimento);
			encontroFinanceiroDao.salvar(encontroFinanceiro);
			
			//Valor por pessoa
			encontroTipoMovimento = new Encontrostipomovimento();
			encontroTipoMovimento.setEnctipmovCod(1); //por pessoa
			encontroFinanceiro = new Encontrofinanceiro();
			encontroFinanceiro.setDadosencontros(dadosEncontros);
			encontroFinanceiro.setValorMovimento(valorPorPessoa);
			encontroFinanceiro.setEncontrostipomovimento(encontroTipoMovimento);
			encontroFinanceiroDao.salvar(encontroFinanceiro);
			
			//Lider Espiritual
			encontroTipoLider = new Encontrostipolideres();
			encontroTipoLider.setEnctiplidercod(1);//espiritual
			lideres = new Lideres();
			lideres.setDadosencontros(dadosEncontros);
			lideres.setDiscipulos(liderEspiritual);
			lideres.setEncontrostipolideres(encontroTipoLider);
			lideresDao.salvar(lideres);
			
			//Lider Intercessão
			encontroTipoLider = new Encontrostipolideres();
			encontroTipoLider.setEnctiplidercod(2);//intercessão
			lideres = new Lideres();
			lideres.setDadosencontros(dadosEncontros);
			lideres.setDiscipulos(liderIntercessao);
			lideres.setEncontrostipolideres(encontroTipoLider);
			lideresDao.salvar(lideres);
			
			//Lider Correio
			encontroTipoLider = new Encontrostipolideres();
			encontroTipoLider.setEnctiplidercod(3);//correio
			lideres = new Lideres();
			lideres.setDadosencontros(dadosEncontros);
			lideres.setDiscipulos(liderCorreio);
			lideres.setEncontrostipolideres(encontroTipoLider);
			lideresDao.salvar(lideres);
			
			//Lider Apoio
			encontroTipoLider = new Encontrostipolideres();
			encontroTipoLider.setEnctiplidercod(4);//apoio
			lideres = new Lideres();
			lideres.setDadosencontros(dadosEncontros);
			lideres.setDiscipulos(liderApoioRecepcao);
			lideres.setEncontrostipolideres(encontroTipoLider);
			lideresDao.salvar(lideres);
			
			//Inscritos
			for(DiscipulosEncontroComDeus dis: dtDisEncontroDeusEscolhidos){
				
				Character pago = new Character('N');
				if(dis.isPago() == true){
					pago = 'S';
					encontroComDeusPagamentoInscricao(dadosEncontros);
				}
				else{
					pago = 'N';
				}
				
				inscritos = new Inscritos();
				inscritos.setDadosencontros(dadosEncontros);
				inscritos.setDiscipulos(dis.getDiscipulo());
				inscritos.setPago(pago);
				inscritosDao.salvar(inscritos);
			}
			
			//participa
			for(DiscipulosEncontroComDeus dis: dtDisEncontroDeusEscolhidos){
				participa = new Participa();
				participa.setDadosencontros(dadosEncontros);
				participa.setDiscipulos(dis.getDiscipulo());
				participaDao.salvar(participa);
			}
			
			//Palestras
			for(PalestrasEncontroComDeus pal: listaPalestraEncontroComDeus){
				encontrosPalestras = new Encontrospalestras();
				encontrosPalestras.setPalestras(pal.getPalestra().getPalestras());//palestras_cod
				encontrosPalestras.setDiscipulos(pal.getPalestra().getDiscipulos());
				encontrosPalestras.setDadosencontros(dadosEncontros);
				encontrosPalestras.setDataRealizacao(pal.getPalestra().getDataRealizacao());
				encontrosPalestras.setHorarioRealizacao(pal.getPalestra().getHorarioRealizacao());
				encontrosPalestrasDao.salvar(encontrosPalestras);
			}
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Encontro com Deus Cadastrado com Sucesso!"));
			
			mostrarDtEncontroComDeus = false;
			listaEncontroComDeus = new ArrayList<Dadosencontros>();
			filtroEncontroComDeus = 0;
			
			return "/list/encontroComDeus.mir";
		}
		else{
			return null;
		}
	}
	
	public void encontroComDeusPagamentoInscricao(Dadosencontros encontro){
		//PAGAMENTO INSCRIÇÃO
		encontroTipoMovimento = new Encontrostipomovimento();
		encontroTipoMovimento.setEnctipmovCod(12); //PAGAMENTO INSCRIÇÃO
		encontroFinanceiro = new Encontrofinanceiro();
		encontroFinanceiro.setDadosencontros(encontro);
		encontroFinanceiro.setValorMovimento(valorPorPessoa);
		encontroFinanceiro.setEncontrostipomovimento(encontroTipoMovimento);
		encontroFinanceiroDao.salvar(encontroFinanceiro);
	}
	
	public String prepararListarEncontrosComDeus(){
		mostrarDtEncontroComDeus = false;
		listaEncontroComDeus = new ArrayList<Dadosencontros>();
		filtroEncontroComDeus = 0;
		
		return "/list/encontroComDeus.mir";
	}
	
	public void filtrarEncontroComDeus(AjaxBehaviorEvent event) throws Exception {
		//Selecione
		if(filtroEncontroComDeus <= 0){
			mostrarDtEncontroComDeus = false;
		}
		//Em Andamento ou Finalizado
		else{
			ListarEncontrosComDeus(filtroEncontroComDeus);
			mostrarDtEncontroComDeus = true;
		}
	}
	
	public void ListarEncontrosComDeus(int status){
		//Lista Encontro Com Deus
		listaEncontroComDeus = new ArrayList<Dadosencontros>();
		listaEncontroComDeus.addAll(dadosEncontrosDao.listarEncontrosComDeus(discipuloSessao.getDiscipulos().getDisCod(), status));
	}
	
	public String prepararEditarEncontroComDeus() {
		//Financeiro
		List<Encontrofinanceiro> listaTempEncontroFinanceiro = new ArrayList<Encontrofinanceiro>();
		listaTempEncontroFinanceiro.addAll(dadosEncontros.getEncontrofinanceiros());
		
		for(Encontrofinanceiro fin : listaTempEncontroFinanceiro){
			//local
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 6){
				valorLocalEncontro = fin.getValorMovimento();
				valorLocalEncontroAntigo = valorLocalEncontro;
			}
			//transporte
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 9){
				valorTransporte = fin.getValorMovimento();
				valorTransporteAntigo = valorTransporte;
			}
			//pessoa
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 1){
				valorPorPessoa = fin.getValorMovimento();
				valorPorPessoaAntigo = valorPorPessoa;
			}
		}
		
		//lideres
		List<Lideres> listaTempLideres = new ArrayList<Lideres>();
		listaTempLideres.addAll(dadosEncontros.getLidereses());
		for(Lideres lid : listaTempLideres){
			//LIDER ESPIRITUAL 
			if(lid.getEncontrostipolideres().getEnctiplidercod() == 1){
				liderEspiritual = lid.getDiscipulos();
				liderEspiritualAntigo = lid;
			}
			//INTERCESSAO
			if(lid.getEncontrostipolideres().getEnctiplidercod() == 2){
				liderIntercessao = lid.getDiscipulos();
				liderIntercessaoAntigo = lid;
			}
			//CORREIOS
			if(lid.getEncontrostipolideres().getEnctiplidercod() == 3){
				liderCorreio = lid.getDiscipulos();
				liderCorreioAntigo = lid;
			}
			//APOIO
			if(lid.getEncontrostipolideres().getEnctiplidercod() == 4){
				liderApoioRecepcao = lid.getDiscipulos();
				liderApoioRecepcaoAntigo = lid;
			}
		}
	
    	dtDisEncontroDeus = new ArrayList<DiscipulosEncontroComDeus>();
    	dtDisEncontroDeusEscolhidos = new ArrayList<DiscipulosEncontroComDeus>();
    	
    	criarArvoreDiscipulosGeracoes();
    	listarPalestrasEncontroComDeusEditar();
    	
    	listarEncontristasEditar();
		
		return "/editar/encontroComDeus.mir";
	}
	
	public void listarPalestrasEncontroComDeusEditar(){
		List<Encontrospalestras> listaTempPalestras = new ArrayList<Encontrospalestras>();
		listaTempPalestras.addAll(dadosEncontros.getEncontrospalestrases());
		
		listaPalestraEncontroComDeus = new ArrayList<PalestrasEncontroComDeus>();
		for(Encontrospalestras pal : listaTempPalestras){
			encontroPalestra = new Encontrospalestras();
			encontroPalestra.setPalestras(pal.getPalestras());
			encontroPalestra.setDiscipulos(pal.getDiscipulos());
			encontroPalestra.setDadosencontros(pal.getDadosencontros());
			encontroPalestra.setDataRealizacao(pal.getDataRealizacao());
			encontroPalestra.setHorarioRealizacao(pal.getHorarioRealizacao());
			palestraEncontroComDeus = new PalestrasEncontroComDeus();
			palestraEncontroComDeus.setAtualizada(true);
			palestraEncontroComDeus.setPalestra(encontroPalestra);
			
			listaPalestraEncontroComDeus.add(palestraEncontroComDeus);
		}
	}
	
	public void listarEncontristasEditar(){
		//Pré-Encontro desse Encontro
		Dadosencontros preEncontro = dadosEncontros.getDadosencontros();
		//Pega os incritos do pré-encontro
		List<Inscritos> listaIncritosPre = new ArrayList<Inscritos>();
		listaIncritosPre.addAll(preEncontro.getInscritoses());
		
		//Pega os incritos do encontro com deus
		List<Inscritos> listaIncritosEncontroComDeus = new ArrayList<Inscritos>();
		listaIncritosEncontroComDeus.addAll(dadosEncontros.getInscritoses());
		
		//verificar os dentro e fora do encontro com deus
		List<Inscritos> listaTempRemover = new ArrayList<Inscritos>(listaIncritosPre);
		for(Inscritos ins : listaIncritosPre){
			for(Inscritos ins2 : listaIncritosEncontroComDeus){
	    		if(ins.getDiscipulos().getDisCod().equals(ins2.getDiscipulos().getDisCod())){
	    			listaTempRemover.remove(ins);
	    		}
	    	}
	    }
		listaIncritosPre = new ArrayList<Inscritos>();
		listaIncritosPre.addAll(listaTempRemover);
		
		//adiciona na lista dos fora do encontro com deus
		dtDisEncontroDeus = new ArrayList<DiscipulosEncontroComDeus>();
		for(Inscritos ins : listaIncritosPre){
			discipuloEncontroComDeus = new DiscipulosEncontroComDeus();
			discipuloEncontroComDeus.setDiscipulo(ins.getDiscipulos());
			discipuloEncontroComDeus.setAulas(retornaAulasPresentesPreEncontro(preEncontro.getDadenccod(),
			ins.getDiscipulos().getDisCod()));
			discipuloEncontroComDeus.setPago(false);
			dtDisEncontroDeus.add(discipuloEncontroComDeus);
		}
		
		//adiciona na lista dos dentro do encontro com deus
		inscricoesPagasEncontroComDeus = 0;
		dtDisEncontroDeusEscolhidos = new ArrayList<DiscipulosEncontroComDeus>();
		for(Inscritos ins : listaIncritosEncontroComDeus){
			discipuloEncontroComDeus = new DiscipulosEncontroComDeus();
			discipuloEncontroComDeus.setDiscipulo(ins.getDiscipulos());
			discipuloEncontroComDeus.setAulas(retornaAulasPresentesPreEncontro(preEncontro.getDadenccod(),
			ins.getDiscipulos().getDisCod()));
			
			if(ins.getPago() == 'S'){
				inscricoesPagasEncontroComDeus++; 
				discipuloEncontroComDeus.setPago(true);
			}
			else{
				discipuloEncontroComDeus.setPago(false);
			}
			
			dtDisEncontroDeusEscolhidos.add(discipuloEncontroComDeus);
		}
	}
	
	public String editarEncontroComDeus() {
        FacesContext context = FacesContext.getCurrentInstance();
		
		//Se For Tudo Validado
		if(validacaoEncontroComDeus() == true){
			//Edita o Encontro com Deus 
			dadosEncontros.setDadencqtdinscritos(dtDisEncontroDeusEscolhidos.size());
			dadosEncontrosDao.atualizar(dadosEncontros);
			
			//Valor Local
			if(valorLocalEncontroAntigo != valorLocalEncontro){
				encontroFinanceiro = new Encontrofinanceiro();
				encontroFinanceiro = encontroFinanceiroDao.listarFinanceiroEncontro(dadosEncontros.getDadenccod(), 6).get(0);
				encontroFinanceiro.setValorMovimento(valorLocalEncontro);
				encontroFinanceiroDao.atualizar(encontroFinanceiro);
			}
			
			//Valor transporte
			if(valorTransporteAntigo != valorTransporte){
				encontroFinanceiro = new Encontrofinanceiro();
				encontroFinanceiro = encontroFinanceiroDao.listarFinanceiroEncontro(dadosEncontros.getDadenccod(), 9).get(0);
				encontroFinanceiro.setValorMovimento(valorTransporte);
				encontroFinanceiroDao.atualizar(encontroFinanceiro);
			}
			
			//Valor Por Pessoa
			/*
			if(valorPorPessoaAntigo != valorPorPessoa){
				//Financeiro
				encontroFinanceiro = new Encontrofinanceiro();
				encontroFinanceiro = encontroFinanceiroDao.listarFinanceiroEncontro(dadosEncontros.getDadenccod(), 1).get(0);
				encontroFinanceiro.setValorMovimento(valorPorPessoa);
				encontroFinanceiroDao.atualizar(encontroFinanceiro);
				
				//atualiza as inscrições
				List<Encontrofinanceiro> listaTempEncontroFinanceiro = new ArrayList<Encontrofinanceiro>();
				listaTempEncontroFinanceiro.addAll(encontroFinanceiroDao.listarFinanceiroEncontro(dadosEncontros.getDadenccod(), 12));
				
				for(Encontrofinanceiro fin : listaTempEncontroFinanceiro){
					fin.setValorMovimento(valorPorPessoa);
					encontroFinanceiroDao.atualizar(fin);
				}
			}
			*/
			
			//Lider Espiritual
			if(liderEspiritualAntigo.getDiscipulos().getDisCod() != liderEspiritual.getDisCod()){
				liderEspiritualAntigo.setDiscipulos(liderEspiritual);
				lideresDao.atualizar(liderEspiritualAntigo);
			}
			
			//Lider Intercessão
			if(liderIntercessaoAntigo.getDiscipulos().getDisCod() != liderIntercessao.getDisCod()){
				liderIntercessaoAntigo.setDiscipulos(liderIntercessao);
				lideresDao.atualizar(liderIntercessaoAntigo);
			}
			
			//Lider Correio
			if(liderCorreioAntigo.getDiscipulos().getDisCod() != liderCorreio.getDisCod()){
				liderCorreioAntigo.setDiscipulos(liderCorreio);
				lideresDao.atualizar(liderCorreioAntigo);
			}
			
			//Lider Apoio Recepção
			if(liderApoioRecepcaoAntigo.getDiscipulos().getDisCod() != liderApoioRecepcao.getDisCod()){
				liderApoioRecepcaoAntigo.setDiscipulos(liderApoioRecepcao);
				lideresDao.atualizar(liderApoioRecepcaoAntigo);
			}
			
			//exclui todos os movimento financeiro e inscricoes e participa para guardar tudo novamente
			//participa
			List<Participa> listaTempParticipa = new ArrayList<Participa>();
			listaTempParticipa.addAll(dadosEncontros.getParticipas());
			for(Participa par: listaTempParticipa){
				participaDao.excluir(par, par.getParticipaCod());
			}
			//Financeiro
			List<Encontrofinanceiro> listaTempEncontroFinanceiro = new ArrayList<Encontrofinanceiro>();
			listaTempEncontroFinanceiro.addAll(encontroFinanceiroDao.listarFinanceiroEncontro(dadosEncontros.getDadenccod(), 12));
			for(Encontrofinanceiro fin : listaTempEncontroFinanceiro){
				encontroFinanceiroDao.excluir(fin, fin.getEncFinanCod());
			}
			//inscricoes
			List<Inscritos> listaIncritosEncontroComDeus = new ArrayList<Inscritos>();
			listaIncritosEncontroComDeus.addAll(dadosEncontros.getInscritoses());
			for(Inscritos ins : listaIncritosEncontroComDeus){
				inscritosDao.excluir(ins, ins.getInscritoCod());
			}
			
			//SALVA TUDO NOVAMENTE
			//Inscritos
			for(DiscipulosEncontroComDeus dis: dtDisEncontroDeusEscolhidos){
				
				Character pago = new Character('N');
				if(dis.isPago() == true){
					pago = 'S';
					encontroComDeusPagamentoInscricao(dadosEncontros);
				}
				else{
					pago = 'N';
				}
				
				inscritos = new Inscritos();
				inscritos.setDadosencontros(dadosEncontros);
				inscritos.setDiscipulos(dis.getDiscipulo());
				inscritos.setPago(pago);
				inscritosDao.salvar(inscritos);
			}
			
			//participa
			for(DiscipulosEncontroComDeus dis: dtDisEncontroDeusEscolhidos){
				participa = new Participa();
				participa.setDadosencontros(dadosEncontros);
				participa.setDiscipulos(dis.getDiscipulo());
				participaDao.salvar(participa);
			}
			
			//exclui todas as palestras
			List<Encontrospalestras> listaTempPalestras = new ArrayList<Encontrospalestras>();
			listaTempPalestras.addAll(dadosEncontros.getEncontrospalestrases());
			for(Encontrospalestras pal : listaTempPalestras){
				encontrosPalestrasDao.excluir(pal, pal.getEncpalestrasCod());
			}
			
			//Grava novamente
			for(PalestrasEncontroComDeus pal: listaPalestraEncontroComDeus){
				encontrosPalestras = new Encontrospalestras();
				encontrosPalestras.setPalestras(pal.getPalestra().getPalestras());//palestras_cod
				encontrosPalestras.setDiscipulos(pal.getPalestra().getDiscipulos());
				encontrosPalestras.setDadosencontros(dadosEncontros);
				encontrosPalestras.setDataRealizacao(null);
				encontrosPalestras.setHorarioRealizacao(pal.getPalestra().getHorarioRealizacao());
				encontrosPalestrasDao.salvar(encontrosPalestras);
			}
			
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Encontro com Deus Editado com Sucesso!"));
			
			mostrarDtEncontroComDeus = false;
			listaEncontroComDeus = new ArrayList<Dadosencontros>();
			filtroEncontroComDeus = 0;
			
			return "/list/encontroComDeus.mir";
		}
		else{
			return null;
		}
	}
	
	public String prepararFinalizarEncontroComDeus() {
		//Financeiro
		List<Encontrofinanceiro> listaTempEncontroFinanceiro = new ArrayList<Encontrofinanceiro>();
		listaTempEncontroFinanceiro.addAll(dadosEncontros.getEncontrofinanceiros());
		
		for(Encontrofinanceiro fin : listaTempEncontroFinanceiro){
			//local
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 6){
				valorLocalEncontro = fin.getValorMovimento();
				valorLocalEncontroAntigo = valorLocalEncontro;
			}
			//transporte
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 9){
				valorTransporte = fin.getValorMovimento();
				valorTransporteAntigo = valorTransporte;
			}
			//pessoa
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 1){
				valorPorPessoa = fin.getValorMovimento();
				valorPorPessoaAntigo = valorPorPessoa;
			}
		}
		
		//lideres
		List<Lideres> listaTempLideres = new ArrayList<Lideres>();
		listaTempLideres.addAll(dadosEncontros.getLidereses());
		for(Lideres lid : listaTempLideres){
			//LIDER ESPIRITUAL 
			if(lid.getEncontrostipolideres().getEnctiplidercod() == 1){
				liderEspiritual = lid.getDiscipulos();
				liderEspiritualAntigo = lid;
			}
			//INTERCESSAO
			if(lid.getEncontrostipolideres().getEnctiplidercod() == 2){
				liderIntercessao = lid.getDiscipulos();
				liderIntercessaoAntigo = lid;
			}
			//CORREIOS
			if(lid.getEncontrostipolideres().getEnctiplidercod() == 3){
				liderCorreio = lid.getDiscipulos();
				liderCorreioAntigo = lid;
			}
			//APOIO
			if(lid.getEncontrostipolideres().getEnctiplidercod() == 4){
				liderApoioRecepcao = lid.getDiscipulos();
				liderApoioRecepcaoAntigo = lid;
			}
		}
	
    	dtDisEncontroDeus = new ArrayList<DiscipulosEncontroComDeus>();
    	dtDisEncontroDeusEscolhidos = new ArrayList<DiscipulosEncontroComDeus>();
    	
    	valorOfertaTirada = new BigDecimal(0.00);
    	valorOfertaVoluntaria = new BigDecimal(0.00);
    	valorMaterialHigienico = new BigDecimal(0.00);
    	valorRefeicao = new BigDecimal(0.00);
    	valorCorreio = new BigDecimal(0.00);
    	
    	listarPalestrasEncontroComDeusEditar();
    	listarEncontristasEditar();
    	calcularTotalInscricaoPagaEncontroComDeus();
    	calcularValorTotalEntrada();
    	calcularValorTotalSaida();
    	calcularValorTotalLiquido();
    	
		return "/editar/encontroComDeusFinalizar.mir";
	}
	
	public void calcularTotalInscricaoPagaEncontroComDeus(){
		valorInscricoesPagasEncontroComDeus = new BigDecimal(valorPorPessoa.doubleValue() * inscricoesPagasEncontroComDeus);
	}
	
	public void calcularValorTotalEntrada(){
		valorTotalEntrada = new BigDecimal(0.00);
    	valorTotalEntrada = new BigDecimal((valorInscricoesPagasEncontroComDeus.doubleValue() + valorOfertaTirada.doubleValue() + valorOfertaVoluntaria.doubleValue()));
    	calcularValorTotalLiquido();
	}
	
	public void calcularValorTotalSaida(){
		valorTotalSaida = new BigDecimal(0.00);
    	valorTotalSaida = new BigDecimal((valorLocalEncontro.doubleValue() + valorTransporte.doubleValue() 
    	+ valorMaterialHigienico.doubleValue() + valorRefeicao.doubleValue() + valorCorreio.doubleValue()));
    	calcularValorTotalLiquido();
	}
	
	public void calcularValorTotalLiquido(){
		valorTotalBruto = new BigDecimal(valorTotalEntrada.doubleValue() - valorTotalSaida.doubleValue());
		if(valorTotalBruto.doubleValue() >= 0){
			valorBrutoPositivo = true;
		}
		else{
			valorBrutoPositivo = false;
		}
	}
	
	public String finalizarEncontroComDeus() {
        FacesContext context = FacesContext.getCurrentInstance();
        
        //Oferta Tirada
        if(valorOfertaTirada.doubleValue() > 0.00){
        	encontroTipoMovimento = new Encontrostipomovimento();
    		encontroTipoMovimento.setEnctipmovCod(2); //tirada
    		encontroFinanceiro = new Encontrofinanceiro();
    		encontroFinanceiro.setDadosencontros(dadosEncontros);
    		encontroFinanceiro.setValorMovimento(valorOfertaTirada);
    		encontroFinanceiro.setEncontrostipomovimento(encontroTipoMovimento);
    		encontroFinanceiroDao.salvar(encontroFinanceiro);
        }
        
        //Oferta voluntaria
        if(valorOfertaVoluntaria.doubleValue() > 0.00){
        	encontroTipoMovimento = new Encontrostipomovimento();
    		encontroTipoMovimento.setEnctipmovCod(3); //oferta voluntaria
    		encontroFinanceiro = new Encontrofinanceiro();
    		encontroFinanceiro.setDadosencontros(dadosEncontros);
    		encontroFinanceiro.setValorMovimento(valorOfertaVoluntaria);
    		encontroFinanceiro.setEncontrostipomovimento(encontroTipoMovimento);
    		encontroFinanceiroDao.salvar(encontroFinanceiro);
        }
        
        //Total Entrada
        encontroTipoMovimento = new Encontrostipomovimento();
		encontroTipoMovimento.setEnctipmovCod(5); //total entrada
		encontroFinanceiro = new Encontrofinanceiro();
		encontroFinanceiro.setDadosencontros(dadosEncontros);
		encontroFinanceiro.setValorMovimento(valorTotalEntrada);
		encontroFinanceiro.setEncontrostipomovimento(encontroTipoMovimento);
		encontroFinanceiroDao.salvar(encontroFinanceiro);
		
		//material higienico
        if(valorMaterialHigienico.doubleValue() > 0.00){
        	encontroTipoMovimento = new Encontrostipomovimento();
    		encontroTipoMovimento.setEnctipmovCod(7); //material higienico
    		encontroFinanceiro = new Encontrofinanceiro();
    		encontroFinanceiro.setDadosencontros(dadosEncontros);
    		encontroFinanceiro.setValorMovimento(valorMaterialHigienico);
    		encontroFinanceiro.setEncontrostipomovimento(encontroTipoMovimento);
    		encontroFinanceiroDao.salvar(encontroFinanceiro);
        }
        
        //refeiçao
        if(valorRefeicao.doubleValue() > 0.00){
        	encontroTipoMovimento = new Encontrostipomovimento();
    		encontroTipoMovimento.setEnctipmovCod(8); //refeicao
    		encontroFinanceiro = new Encontrofinanceiro();
    		encontroFinanceiro.setDadosencontros(dadosEncontros);
    		encontroFinanceiro.setValorMovimento(valorRefeicao);
    		encontroFinanceiro.setEncontrostipomovimento(encontroTipoMovimento);
    		encontroFinanceiroDao.salvar(encontroFinanceiro);
        }
        
        //correio
        if(valorCorreio.doubleValue() > 0.00){
        	encontroTipoMovimento = new Encontrostipomovimento();
    		encontroTipoMovimento.setEnctipmovCod(10); //correio
    		encontroFinanceiro = new Encontrofinanceiro();
    		encontroFinanceiro.setDadosencontros(dadosEncontros);
    		encontroFinanceiro.setValorMovimento(valorCorreio);
    		encontroFinanceiro.setEncontrostipomovimento(encontroTipoMovimento);
    		encontroFinanceiroDao.salvar(encontroFinanceiro);
        }
        
        //Total Saída
        encontroTipoMovimento = new Encontrostipomovimento();
		encontroTipoMovimento.setEnctipmovCod(11); //total saida
		encontroFinanceiro = new Encontrofinanceiro();
		encontroFinanceiro.setDadosencontros(dadosEncontros);
		encontroFinanceiro.setValorMovimento(valorTotalSaida);
		encontroFinanceiro.setEncontrostipomovimento(encontroTipoMovimento);
		encontroFinanceiroDao.salvar(encontroFinanceiro);
        
		//saldo liquido
        encontroTipoMovimento = new Encontrostipomovimento();
		encontroTipoMovimento.setEnctipmovCod(13); //saldo liquido
		encontroFinanceiro = new Encontrofinanceiro();
		encontroFinanceiro.setDadosencontros(dadosEncontros);
		encontroFinanceiro.setValorMovimento(valorTotalBruto);
		encontroFinanceiro.setEncontrostipomovimento(encontroTipoMovimento);
		encontroFinanceiroDao.salvar(encontroFinanceiro);
		
		//finaliza o encontro
		encontroStatus = new Encontrostatus();
		encontroStatus.setEncstacod(2);//1 = Em Andamento 2 = Finalizado
		dadosEncontros.setEncontrostatusByDadencstatus(encontroStatus);//status
		dadosEncontrosDao.atualizar(dadosEncontros);
		
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Encontro com Deus Finalizado com Sucesso!"));
		
		mostrarDtEncontroComDeus = false;
		listaEncontroComDeus = new ArrayList<Dadosencontros>();
		filtroEncontroComDeus = 0;
		
		return "/list/encontroComDeus.mir";
	}
	
	public String prepararDetalheEncontroComDeus() {
		//Financeiro
		List<Encontrofinanceiro> listaTempEncontroFinanceiro = new ArrayList<Encontrofinanceiro>();
		listaTempEncontroFinanceiro.addAll(dadosEncontros.getEncontrofinanceiros());
		
		for(Encontrofinanceiro fin : listaTempEncontroFinanceiro){
			//pessoa
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 1){
				valorPorPessoa = fin.getValorMovimento();
			}
			//Oferta tirada
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 2){
				valorOfertaTirada = fin.getValorMovimento();
			}
			//Oferta voluntaria
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 3){
				valorOfertaVoluntaria = fin.getValorMovimento();
			}
			//total entrada
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 5){
				valorTotalEntrada = fin.getValorMovimento();
			}
			
			//local
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 6){
				valorLocalEncontro = fin.getValorMovimento();
			}
			//transporte
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 9){
				valorTransporte = fin.getValorMovimento();
			}
			//material higienico
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 7){
				valorMaterialHigienico = fin.getValorMovimento();
			}
			//refeicao
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 8){
				valorRefeicao = fin.getValorMovimento();
			}
			//correio
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 10){
				valorCorreio = fin.getValorMovimento();
			}
			//total saida
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 11){
				valorTotalSaida = fin.getValorMovimento();
			}
			
			//total liquido
			if(fin.getEncontrostipomovimento().getEnctipmovCod() == 13){
				valorTotalBruto = fin.getValorMovimento();
			}
		}
		
		//lideres
		List<Lideres> listaTempLideres = new ArrayList<Lideres>();
		listaTempLideres.addAll(dadosEncontros.getLidereses());
		for(Lideres lid : listaTempLideres){
			//LIDER ESPIRITUAL 
			if(lid.getEncontrostipolideres().getEnctiplidercod() == 1){
				liderEspiritual = lid.getDiscipulos();
			}
			//INTERCESSAO
			if(lid.getEncontrostipolideres().getEnctiplidercod() == 2){
				liderIntercessao = lid.getDiscipulos();
			}
			//CORREIOS
			if(lid.getEncontrostipolideres().getEnctiplidercod() == 3){
				liderCorreio = lid.getDiscipulos();
			}
			//APOIO
			if(lid.getEncontrostipolideres().getEnctiplidercod() == 4){
				liderApoioRecepcao = lid.getDiscipulos();
			}
		}
	
    	dtDisEncontroDeus = new ArrayList<DiscipulosEncontroComDeus>();
    	dtDisEncontroDeusEscolhidos = new ArrayList<DiscipulosEncontroComDeus>();
    	
    	listarPalestrasEncontroComDeusEditar();
    	listarEncontristasEditar();
    	calcularTotalInscricaoPagaEncontroComDeus();
    	//calcularValorTotalEntrada();
    	//calcularValorTotalSaida();
    	//calcularValorTotalLiquido();
    	
		return "/editar/encontroComDeusDetalhe.mir";
	}
	
	public String prepararListarEncontrosComDeusFinalizados(){
		listarEncontrosComDeusFinalizados();
		
		return "/cad/posEncontroListar.mir";
	}
	
	public void listarEncontrosComDeusFinalizados(){
		//Lista Encontro Com Deus
		listaEncontroComDeus = new ArrayList<Dadosencontros>();
		listaEncontroComDeus.addAll(dadosEncontrosDao.listarEncontrosComDeus(discipuloSessao.getDiscipulos().getDisCod(), 2));
		
		//verificar se esse encontro tem encontro filho
		List<Dadosencontros> listaTempRemover = new ArrayList<Dadosencontros>(listaEncontroComDeus);
		for(Dadosencontros enc : listaEncontroComDeus){
			int retorno = dadosEncontrosDao.listarEncontrosFilhos(enc.getDadenccod()).size();
    		if(retorno > 0){
    			listaTempRemover.remove(enc);
    		}
    	}
		listaEncontroComDeus = new ArrayList<Dadosencontros>();
		listaEncontroComDeus.addAll(listaTempRemover);
	}
	
	public String prepararCadastrarPosEncontro() {
		dadosEncontros = new Dadosencontros();
		ministrador = new Discipulos();
    	dtDisGeracoes = new ArrayList<EncontrosNoPersistenceDiscipulos>();
		dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>(); 
		
		//Carrego os discipulos do encontro com deus
		carregaDiscipulosEncontroComDeusFinalizado();
    	criarArvoreDiscipulosGeracoes();
    	AdicionarPalestraPosEncontro();
    	
		return "/cad/posEncontro.mir";
	}
	
	public void carregaDiscipulosEncontroComDeusFinalizado(){
		//Pega os incritos do encontro com deus finalizado
		List<Inscritos> listaIncritosEncontroComDeus = new ArrayList<Inscritos>();
		listaIncritosEncontroComDeus.addAll(dadosEncontrosSelecionado.getInscritoses());
		
		for(Inscritos ins : listaIncritosEncontroComDeus){
			encontrosDiscipulo = new EncontrosNoPersistenceDiscipulos();
			encontrosDiscipulo.setPossuiPreEncontro(true);
			encontrosDiscipulo.setDiscipulo(ins.getDiscipulos());
			dtDisGeracoes.add(encontrosDiscipulo);
    	}
	}
	
	public void AdicionarPalestraPosEncontro(){
		listaPalestras =  new ArrayList<Palestras>();
		listaPalestras.addAll(palestrasDao.listarPalestrasEncontros(3));
	}
	
	public void adicionarDiscipuloEncontroComDeus() {  
    	List<EncontrosNoPersistenceDiscipulos> listaTempSelecionados = new ArrayList<EncontrosNoPersistenceDiscipulos>();
    	//Guarda os Selecionados Na Lista Temporaria
		for(EncontrosNoPersistenceDiscipulos dis: dtDisSelecionadosAdicionar){
			listaTempSelecionados.add(dis);
		}
		
		//Adiciona na lista dos escolhidos a lista de baixo na tela
		for(EncontrosNoPersistenceDiscipulos dis: listaTempSelecionados){
			dtDisEscolhidos.add(dis);
		}
		
		//Remove o discipulo na lista da geração filtrada a lista de cima na tela
		List<EncontrosNoPersistenceDiscipulos> listaTempRemover = new ArrayList<EncontrosNoPersistenceDiscipulos>(dtDisGeracoes);
	    for(EncontrosNoPersistenceDiscipulos dis : listaTempSelecionados){
	    	for(EncontrosNoPersistenceDiscipulos dis2 : dtDisGeracoes){
	    		if(dis.getDiscipulo().getDisCod().equals(dis2.getDiscipulo().getDisCod())){
	    			listaTempRemover.remove(dis2);
	    		}
	    	}
	    }
	    dtDisGeracoes = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	    dtDisGeracoes.addAll(listaTempRemover);
	}
	
	public void removerDiscipuloEncontroComDeus() {  
    	List<EncontrosNoPersistenceDiscipulos> listaTempSelecionados = new ArrayList<EncontrosNoPersistenceDiscipulos>();
    	//Guarda os Selecionados Na Lista Temporaria
		for(EncontrosNoPersistenceDiscipulos dis: dtDisSelecionadosRemover){
			listaTempSelecionados.add(dis);
		}
		
		//Adiciona o discipulo na lista da geração filtrada a lista de cima na tela
		for(EncontrosNoPersistenceDiscipulos dis: listaTempSelecionados){
			dtDisGeracoes.add(dis);
		}
	    
	    //Remove o discipulo na lista dos escolhidos a lista de baixo na tela
		List<EncontrosNoPersistenceDiscipulos> listaTempRemover = new ArrayList<EncontrosNoPersistenceDiscipulos>(dtDisEscolhidos);
	    for(EncontrosNoPersistenceDiscipulos dis : listaTempSelecionados) {
	    	for(EncontrosNoPersistenceDiscipulos dis2 : dtDisEscolhidos){
	    		if(dis.getDiscipulo().getDisCod().equals(dis2.getDiscipulo().getDisCod())){
	    			listaTempRemover.remove(dis2);
	    		}
	    	}
	    }
	    dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
	    dtDisEscolhidos.addAll(listaTempRemover);
    }
	
	public String SalvarPosEncontro(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		//Se For Tudo Validado
		if(validacaoPreEncontro() == true){
			
		//Salva o Pré-Encontro 
		encontros = new Encontros();
		encontros.setEncCod(3);//3 = Pós-Encontros
		encontroStatus = new Encontrostatus();
		encontroStatus.setEncstacod(1);//1 = Em Andamento 2 = Finalizado
		dadosEncontros.setEncontros(encontros);
		dadosEncontros.setDadosencontros(dadosEncontrosSelecionado);//encontroPai
		dadosEncontros.setEncontrostatusByDadencstatus(encontroStatus);//status
		dadosEncontros.setEncontrostatusBySolicitaescolalideres(encontroStatus);//status
		dadosEncontros.setDiscipulos(discipuloSessao.getDiscipulos());//responsavel
		dadosEncontros.setDadencqtdinscritos(dtDisEscolhidos.size());
		dadosEncontrosDao.salvar(dadosEncontros);
		
		//Ministrador
		ministraId = new MinistraId();
		ministraId.setDiscipulosDisCod(ministrador.getDisCod());
		ministraId.setDadosencontrosDadenccod(dadosEncontros.getDadenccod());
		ministra.setId(ministraId);
		ministraDao.salvar(ministra);
		
		//inscritos
		for(EncontrosNoPersistenceDiscipulos dis : dtDisEscolhidos){
			inscritos = new Inscritos();
			inscritos.setDiscipulos(dis.getDiscipulo());
			inscritos.setDadosencontros(dadosEncontros);
			inscritos.setPago(null);
			inscritosDao.salvar(inscritos);
		}
		
		//Participa
		for(EncontrosNoPersistenceDiscipulos dis : dtDisEscolhidos){
			participa = new Participa();
			participa.setDadosencontros(dadosEncontros);
			participa.setDiscipulos(dis.getDiscipulo());
			participaDao.salvar(participa);
		}
		
		//Encontros Palestras
		for(Palestras pal : listaPalestras){
			encontrosPalestras = new Encontrospalestras();
			encontrosPalestras.setPalestras(pal);//palestras_cod
			encontrosPalestras.setDiscipulos(ministrador);
			encontrosPalestras.setDadosencontros(dadosEncontros);
			encontrosPalestras.setDataRealizacao(null);
			encontrosPalestras.setHorarioRealizacao(null);
			encontrosPalestrasDao.salvar(encontrosPalestras);
		}
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Pré-Encontro Cadastrado com Sucesso!"));
		
        filtroPosEncontro = 0;
		listaEncontrosNoPersistence = new ArrayList<EncontrosNoPersistence>();
		listaPreEncontros = new ArrayList<Dadosencontros>();
		mostrarDtPreEncontros = false;
		
		return "/list/posEncontro.mir";
		}
		else{
			return null;
		}
	}
	
	public String prepararListarPosEncontros() {
		filtroPosEncontro = 0;
		listaEncontrosNoPersistence = new ArrayList<EncontrosNoPersistence>();
		listaPreEncontros = new ArrayList<Dadosencontros>();
		mostrarDtPreEncontros = false;
		
		return "/list/posEncontro.mir";
	}
	
	public void filtrarPosEncontro(AjaxBehaviorEvent event) throws Exception {
		//Selecione
		if(filtroPosEncontro <= 0){
			mostrarDtPreEncontros = false;
		}
		//Em Andamento Finalizado
		else{
			ListarPosEncontros(filtroPosEncontro);
			mostrarDtPreEncontros = true;
		}
	}
	
	public void ListarPosEncontros(int status){
		//Lista os pre encontros em andamento
		listaPreEncontros = new ArrayList<Dadosencontros>();
		listaPreEncontros.addAll(dadosEncontrosDao.listarPosEncontros(discipuloSessao.getDiscipulos().getDisCod(), status));
		
		listaEncontrosNoPersistence = new ArrayList<EncontrosNoPersistence>();	
		for(Dadosencontros pre : listaPreEncontros){
			encontrosNoPersistence = new EncontrosNoPersistence();
			encontrosNoPersistence.setDadosEncontros(pre);
			encontrosNoPersistence.setMinistrador(pre.getMinistras().get(0).getDiscipulos());
			
			//Pega as palestras
			listaEncontrosPalestras = new ArrayList<Encontrospalestras>();
			listaEncontrosPalestras.addAll(pre.getEncontrospalestrases());
			int numPal = 1;
			for(Encontrospalestras pal: listaEncontrosPalestras){
				encontrosNoPersistenceAula = new EncontrosNoPersistenceAulas();
				//Aulas naum liberadas
				if(pal.getDataRealizacao() == null){
					if(numPal == 1){
						encontrosNoPersistenceAula.setLiberada(false);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(1);
						encontrosNoPersistence.setAula1(encontrosNoPersistenceAula);
					}
					if(numPal == 2){
						encontrosNoPersistenceAula.setLiberada(false);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(2);
						encontrosNoPersistence.setAula2(encontrosNoPersistenceAula);
					}
					if(numPal == 3){
						encontrosNoPersistenceAula.setLiberada(false);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(3);
						encontrosNoPersistence.setAula3(encontrosNoPersistenceAula);
					}
					if(numPal == 4){
						encontrosNoPersistenceAula.setLiberada(false);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(4);
						encontrosNoPersistence.setAula4(encontrosNoPersistenceAula);
					}
				}
				//Aulas Liberadas
				else{
					if(numPal == 1){
						encontrosNoPersistenceAula.setLiberada(true);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(1);
						encontrosNoPersistence.setAula1(encontrosNoPersistenceAula);
					}
					if(numPal == 2){
						encontrosNoPersistenceAula.setLiberada(true);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(2);
						encontrosNoPersistence.setAula2(encontrosNoPersistenceAula);
					}
					if(numPal == 3){
						encontrosNoPersistenceAula.setLiberada(true);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(3);
						encontrosNoPersistence.setAula3(encontrosNoPersistenceAula);
					}
					if(numPal == 4){
						encontrosNoPersistenceAula.setLiberada(true);
						encontrosNoPersistenceAula.setPalestras(pal);
						encontrosNoPersistenceAula.setAula(4);
						encontrosNoPersistence.setAula4(encontrosNoPersistenceAula);
					}
				}
				numPal++;
			}
			listaEncontrosNoPersistence.add(encontrosNoPersistence);
		}
	}
	
	public String prepararPresencaPosEncontro(){
		FacesContext context = FacesContext.getCurrentInstance(); 
		
		List<Inscritos> listaIncritos = new ArrayList<Inscritos>();
		List<Discipulos> listaTempDiscipulos = new ArrayList<Discipulos>();
		
		dadosEncontros = new Dadosencontros();
		dadosEncontros = encontrosNoPersistenceAula.getPalestras().getDadosencontros();
		ministrador = new Discipulos();
		ministrador = encontrosNoPersistenceAula.getPalestras().getDadosencontros().getMinistras().get(0).getDiscipulos();
		encontroPalestra = new Encontrospalestras();
		encontroPalestra = encontrosNoPersistenceAula.getPalestras();
		
		//Aula Não Liberada
		if(encontrosNoPersistenceAula.isLiberada() == false){
			//Primeira Aula
			if(encontrosNoPersistenceAula.getAula() <= 1){
				//Pega os incritos
				listaIncritos.addAll(dadosEncontros.getInscritoses());
				for(Inscritos ins : listaIncritos){
					listaTempDiscipulos.add(ins.getDiscipulos());
				}
				
				dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
				for(Discipulos dis : listaTempDiscipulos){
					encontrosDiscipulo = new EncontrosNoPersistenceDiscipulos();
					encontrosDiscipulo.setDiscipulo(dis);
					encontrosDiscipulo.setPossuiPreEncontro(true);
					dtDisEscolhidos.add(encontrosDiscipulo);
				}
				return "/cad/posEncontroPresenca.mir";
			}
			//Segunda Aula em Diante
			else{
				//Aula Anterior Liberada
				if(retornaAulaAnterior(encontrosNoPersistenceAula.getAula()).isLiberada() == true){
					
					//Pega os incritos
					listaIncritos.addAll(dadosEncontros.getInscritoses());
					for(Inscritos ins : listaIncritos){
						listaTempDiscipulos.add(ins.getDiscipulos());
					}
					
					dtDisEscolhidos = new ArrayList<EncontrosNoPersistenceDiscipulos>();
					for(Discipulos dis : listaTempDiscipulos){
						encontrosDiscipulo = new EncontrosNoPersistenceDiscipulos();
						encontrosDiscipulo.setDiscipulo(dis);
						encontrosDiscipulo.setPossuiPreEncontro(true);
						dtDisEscolhidos.add(encontrosDiscipulo);
					}
					return "/cad/posEncontroPresenca.mir";
				}
				//Aula Anterior Não Liberada
				else{
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO!","Aula Ainda Não Liberada!"));
					return null;
				}
			}
		}
		//Aula Já Liberada Mostra os Detalhe Dessa Aula
		else{
			listaFrequenciaAula = new ArrayList<Frequencia>(); 
			listaFrequenciaAula.addAll(encontroPalestra.getFrequencias());
			//presentes
			List<Discipulos> listaTempDiscipuloPresente = new ArrayList<Discipulos>();
			for(Frequencia freq: listaFrequenciaAula){
				listaTempDiscipuloPresente.add(freq.getDiscipulos());
			}
			
			//inscritos
			listaIncritos.addAll(dadosEncontros.getInscritoses());
			for(Inscritos ins : listaIncritos){
				listaTempDiscipulos.add(ins.getDiscipulos());
			}
			
			listaDiscipuloPreEncontro = new ArrayList<EncontrosNoPersistenceDiscipulos>();
			for(Discipulos dis : listaTempDiscipulos){
				encontrosDiscipulo = new EncontrosNoPersistenceDiscipulos();
    			encontrosDiscipulo.setDiscipulo(dis);
    			encontrosDiscipulo.setPossuiPreEncontro(true);
    			encontrosDiscipulo.setPresenteAulaPreEncontro(false);
    			listaDiscipuloPreEncontro.add(encontrosDiscipulo);
			}
			
		    for(Discipulos dis : listaTempDiscipuloPresente){
		    	for(EncontrosNoPersistenceDiscipulos dis2 : listaDiscipuloPreEncontro){
		    		if(dis.getDisCod().equals(dis2.getDiscipulo().getDisCod())){
		    			int index = listaDiscipuloPreEncontro.indexOf(dis2);
		    			
		    			encontrosDiscipulo = new EncontrosNoPersistenceDiscipulos();
		    			encontrosDiscipulo.setDiscipulo(dis2.getDiscipulo());
		    			encontrosDiscipulo.setPossuiPreEncontro(true);
		    			encontrosDiscipulo.setPresenteAulaPreEncontro(true);
		    			listaDiscipuloPreEncontro.set(index, encontrosDiscipulo);
		    		}
		    	}
		    }
			
			return "/editar/posEncontroPresenca.mir";
		}
	}
	
	public String prepararEfetuarPresencaPosEncontro(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		//Se for a ultima aula Finaliza Esse Encontro
		if(encontrosNoPersistenceAula.getAula() == 4){
			encontroStatus = new Encontrostatus();
			encontroStatus.setEncstacod(2);//1 = Em Andamento 2 = Finalizado
			dadosEncontros.setEncontrostatusByDadencstatus(encontroStatus);
			dadosEncontrosDao.atualizar(dadosEncontros);
		}
		//Atualiza a data da palestra
		encontrosPalestrasDao.atualizar(encontroPalestra);
		
		//Se For Tudo Validado
		if(validacaoPresencaPreEncontro() == true){
			//dar a presenca
			for(EncontrosNoPersistenceDiscipulos dis : dtDisSelecionadosPresenca){
				frequenciaId = new FrequenciaId();
				frequenciaId.setDiscipulos(dis.getDiscipulo().getDisCod());
				frequenciaId.setEncontrospalestras(encontroPalestra.getEncpalestrasCod());
				frequencia = new Frequencia();
				frequencia.setId(frequenciaId);
				frequenciaDao.salvar(frequencia);
			}
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCESSO!","Presença Efetivada com sucesso!"));
			
			filtroPosEncontro = 0;
			listaEncontrosNoPersistence = new ArrayList<EncontrosNoPersistence>();
			listaPreEncontros = new ArrayList<Dadosencontros>();
			mostrarDtPreEncontros = false;
			
			return "/list/posEncontro.mir";
		}
		else{
			return null;
		}
	}
	
	public Encontros getEncontros() {
		return encontros;
	}
	
	public void setEncontros(Encontros encontros) {
		this.encontros = encontros;
	}

	public Dadosencontros getDadosEncontros() {
		return dadosEncontros;
	}

	public void setDadosEncontros(Dadosencontros dadosEncontros) {
		this.dadosEncontros = dadosEncontros;
	}

	public Lideres getLideres() {
		return lideres;
	}

	public void setLideres(Lideres lideres) {
		this.lideres = lideres;
	}

	public ApplicationSecurityManager getDiscipuloSessao() {
		return discipuloSessao;
	}

	public void setDiscipuloSessao(ApplicationSecurityManager discipuloSessao) {
		this.discipuloSessao = discipuloSessao;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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

	public ArvoreDiscipulos getArvoreLista() {
		return arvoreLista;
	}

	public void setArvoreLista(ArvoreDiscipulos arvoreLista) {
		this.arvoreLista = arvoreLista;
	}

	public List<Geracoes> getListaGeracoes() {
		return listaGeracoes;
	}

	public void setListaGeracoes(List<Geracoes> listaGeracoes) {
		this.listaGeracoes = listaGeracoes;
	}

	public List<Discipulos> getListaDiscipulosGeracoes() {
		return listaDiscipulosGeracoes;
	}

	public void setListaDiscipulosGeracoes(List<Discipulos> listaDiscipulosGeracoes) {
		this.listaDiscipulosGeracoes = listaDiscipulosGeracoes;
	}

	public ArvoreGeracoesNodos getArvoreGeracoesNodos() {
		return arvoreGeracoesNodos;
	}

	public void setArvoreGeracoesNodos(ArvoreGeracoesNodos arvoreGeracoesNodos) {
		this.arvoreGeracoesNodos = arvoreGeracoesNodos;
	}

	public List<ArvoreGeracoesNodos> getIndexGeracoesNodos() {
		return indexGeracoesNodos;
	}

	public void setIndexGeracoesNodos(List<ArvoreGeracoesNodos> indexGeracoesNodos) {
		this.indexGeracoesNodos = indexGeracoesNodos;
	}
	
	public Discipulos getMinistrador() {
		return ministrador;
	}

	public void setMinistrador(Discipulos ministrador) {
		this.ministrador = ministrador;
	}

	public List<EncontrosNoPersistenceDiscipulos> getDtDisGeracoes() {
		return dtDisGeracoes;
	}

	public void setDtDisGeracoes(
			List<EncontrosNoPersistenceDiscipulos> dtDisGeracoes) {
		this.dtDisGeracoes = dtDisGeracoes;
	}

	public List<EncontrosNoPersistenceDiscipulos> getDtDisEscolhidos() {
		return dtDisEscolhidos;
	}

	public void setDtDisEscolhidos(
			List<EncontrosNoPersistenceDiscipulos> dtDisEscolhidos) {
		this.dtDisEscolhidos = dtDisEscolhidos;
	}

	public EncontrosNoPersistenceDiscipulos[] getDtDisSelecionadosAdicionar() {
		return dtDisSelecionadosAdicionar;
	}

	public void setDtDisSelecionadosAdicionar(
			EncontrosNoPersistenceDiscipulos[] dtDisSelecionadosAdicionar) {
		this.dtDisSelecionadosAdicionar = dtDisSelecionadosAdicionar;
	}

	public EncontrosNoPersistenceDiscipulos[] getDtDisSelecionadosRemover() {
		return dtDisSelecionadosRemover;
	}

	public void setDtDisSelecionadosRemover(
			EncontrosNoPersistenceDiscipulos[] dtDisSelecionadosRemover) {
		this.dtDisSelecionadosRemover = dtDisSelecionadosRemover;
	}

	public ArvoreDiscipulos getDiscipuloArvoreSelecionado() {
		return discipuloArvoreSelecionado;
	}

	public void setDiscipuloArvoreSelecionado(
			ArvoreDiscipulos discipuloArvoreSelecionado) {
		this.discipuloArvoreSelecionado = discipuloArvoreSelecionado;
	}
	
	public Palestras getPalestras() {
		return palestras;
	}

	public void setPalestras(Palestras palestras) {
		this.palestras = palestras;
	}

	public List<Palestras> getListaPalestras() {
		return listaPalestras;
	}

	public void setListaPalestras(List<Palestras> listaPalestras) {
		this.listaPalestras = listaPalestras;
	}

	public MinistraId getMinistraId() {
		return ministraId;
	}

	public void setMinistraId(MinistraId ministraId) {
		this.ministraId = ministraId;
	}

	public Ministra getMinistra() {
		return ministra;
	}

	public void setMinistra(Ministra ministra) {
		this.ministra = ministra;
	}

	public Inscritos getInscritos() {
		return inscritos;
	}

	public void setInscritos(Inscritos inscritos) {
		this.inscritos = inscritos;
	}

	public Participa getParticipa() {
		return participa;
	}

	public void setParticipa(Participa participa) {
		this.participa = participa;
	}

	public Encontrospalestras getEncontrosPalestras() {
		return encontrosPalestras;
	}

	public void setEncontrosPalestras(Encontrospalestras encontrosPalestras) {
		this.encontrosPalestras = encontrosPalestras;
	}
	
	public List<Dadosencontros> getListaPreEncontros() {
		return listaPreEncontros;
	}

	public void setListaPreEncontros(List<Dadosencontros> listaPreEncontros) {
		this.listaPreEncontros = listaPreEncontros;
	}

	public List<EncontrosNoPersistence> getListaEncontrosNoPersistence() {
		return listaEncontrosNoPersistence;
	}

	public void setListaEncontrosNoPersistence(
			List<EncontrosNoPersistence> listaEncontrosNoPersistence) {
		this.listaEncontrosNoPersistence = listaEncontrosNoPersistence;
	}

	public EncontrosNoPersistence getEncontrosNoPersistence() {
		return encontrosNoPersistence;
	}

	public void setEncontrosNoPersistence(
			EncontrosNoPersistence encontrosNoPersistence) {
		this.encontrosNoPersistence = encontrosNoPersistence;
	}
	
	public List<Encontrospalestras> getListaEncontrosPalestras() {
		return listaEncontrosPalestras;
	}

	public void setListaEncontrosPalestras(
			List<Encontrospalestras> listaEncontrosPalestras) {
		this.listaEncontrosPalestras = listaEncontrosPalestras;
	}
	
	public EncontrosNoPersistenceDiscipulos[] getDtDisSelecionadosPresenca() {
		return dtDisSelecionadosPresenca;
	}

	public void setDtDisSelecionadosPresenca(EncontrosNoPersistenceDiscipulos[] dtDisSelecionadosPresenca) {
		this.dtDisSelecionadosPresenca = dtDisSelecionadosPresenca;
	}
	
	public Encontrospalestras getEncontroPalestra() {
		return encontroPalestra;
	}

	public void setEncontroPalestra(Encontrospalestras encontroPalestra) {
		this.encontroPalestra = encontroPalestra;
	}
	
	public FrequenciaId getFrequenciaId() {
		return frequenciaId;
	}

	public void setFrequenciaId(FrequenciaId frequenciaId) {
		this.frequenciaId = frequenciaId;
	}

	public EncontrosNoPersistenceAulas getEncontrosNoPersistenceAula() {
		return encontrosNoPersistenceAula;
	}

	public void setEncontrosNoPersistenceAula(
			EncontrosNoPersistenceAulas encontrosNoPersistenceAula) {
		this.encontrosNoPersistenceAula = encontrosNoPersistenceAula;
	}

	public int getFiltroPreEncontro() {
		return filtroPreEncontro;
	}

	public void setFiltroPreEncontro(int filtroPreEncontro) {
		this.filtroPreEncontro = filtroPreEncontro;
	}
	
	public EncontrosNoPersistenceDiscipulos getEncontrosDiscipulo() {
		return encontrosDiscipulo;
	}

	public void setEncontrosDiscipulo(
			EncontrosNoPersistenceDiscipulos encontrosDiscipulo) {
		this.encontrosDiscipulo = encontrosDiscipulo;
	}

	public List<EncontrosNoPersistenceDiscipulos> getListaDiscipuloPossuiPreEncontro() {
		return listaDiscipuloPossuiPreEncontro;
	}

	public void setListaDiscipuloPossuiPreEncontro(
			List<EncontrosNoPersistenceDiscipulos> listaDiscipuloPossuiPreEncontro) {
		this.listaDiscipuloPossuiPreEncontro = listaDiscipuloPossuiPreEncontro;
	}

	public List<EncontrosNoPersistenceDiscipulos> getListaDiscipuloNaoPossuiPreEncontro() {
		return listaDiscipuloNaoPossuiPreEncontro;
	}

	public void setListaDiscipuloNaoPossuiPreEncontro(
			List<EncontrosNoPersistenceDiscipulos> listaDiscipuloNaoPossuiPreEncontro) {
		this.listaDiscipuloNaoPossuiPreEncontro = listaDiscipuloNaoPossuiPreEncontro;
	}
	
	public List<Frequencia> getListaFrequenciaAula() {
		return listaFrequenciaAula;
	}

	public void setListaFrequenciaAula(List<Frequencia> listaFrequenciaAula) {
		this.listaFrequenciaAula = listaFrequenciaAula;
	}

	public List<EncontrosNoPersistenceDiscipulos> getListaDiscipuloPreEncontro() {
		return listaDiscipuloPreEncontro;
	}

	public void setListaDiscipuloPreEncontro(
			List<EncontrosNoPersistenceDiscipulos> listaDiscipuloPreEncontro) {
		this.listaDiscipuloPreEncontro = listaDiscipuloPreEncontro;
	}
	
	public boolean isMostrarDtPreEncontros() {
		return mostrarDtPreEncontros;
	}

	public void setMostrarDtPreEncontros(boolean mostrarDtPreEncontros) {
		this.mostrarDtPreEncontros = mostrarDtPreEncontros;
	}

	public Encontrostatus getEncontroStatus() {
		return encontroStatus;
	}

	public void setEncontroStatus(Encontrostatus encontroStatus) {
		this.encontroStatus = encontroStatus;
	}

	public BigDecimal getValorLocalEncontro() {
		return valorLocalEncontro;
	}

	public void setValorLocalEncontro(BigDecimal valorLocalEncontro) {
		this.valorLocalEncontro = valorLocalEncontro;
	}

	public BigDecimal getvalorTransporte() {
		return valorTransporte;
	}

	public void setvalorTransporte(BigDecimal valorTransporte) {
		this.valorTransporte = valorTransporte;
	}

	public BigDecimal getValorPorPessoa() {
		return valorPorPessoa;
	}

	public void setValorPorPessoa(BigDecimal valorPorPessoa) {
		this.valorPorPessoa = valorPorPessoa;
	}

	public Discipulos getLiderEspiritual() {
		return liderEspiritual;
	}

	public void setLiderEspiritual(Discipulos liderEspiritual) {
		this.liderEspiritual = liderEspiritual;
	}

	public Discipulos getLiderIntercessao() {
		return liderIntercessao;
	}

	public void setLiderIntercessao(Discipulos liderIntercessao) {
		this.liderIntercessao = liderIntercessao;
	}

	public Discipulos getLiderCorreio() {
		return liderCorreio;
	}

	public void setLiderCorreio(Discipulos liderCorreio) {
		this.liderCorreio = liderCorreio;
	}

	public Discipulos getLiderApoioRecepcao() {
		return liderApoioRecepcao;
	}

	public void setLiderApoioRecepcao(Discipulos liderApoioRecepcao) {
		this.liderApoioRecepcao = liderApoioRecepcao;
	}

	public List<DiscipulosEncontroComDeus> getDtDisEncontroDeus() {
		return dtDisEncontroDeus;
	}

	public void setDtDisEncontroDeus(List<DiscipulosEncontroComDeus> dtDisEncontroDeus) {
		this.dtDisEncontroDeus = dtDisEncontroDeus;
	}

	public List<DiscipulosEncontroComDeus> getDtDisEncontroDeusEscolhidos() {
		return dtDisEncontroDeusEscolhidos;
	}

	public void setDtDisEncontroDeusEscolhidos(
			List<DiscipulosEncontroComDeus> dtDisEncontroDeusEscolhidos) {
		this.dtDisEncontroDeusEscolhidos = dtDisEncontroDeusEscolhidos;
	}

	public DiscipulosEncontroComDeus[] getDtDisEncontroDeusSelecionadosAdicionar() {
		return dtDisEncontroDeusSelecionadosAdicionar;
	}

	public void setDtDisEncontroDeusSelecionadosAdicionar(
			DiscipulosEncontroComDeus[] dtDisEncontroDeusSelecionadosAdicionar) {
		this.dtDisEncontroDeusSelecionadosAdicionar = dtDisEncontroDeusSelecionadosAdicionar;
	}

	public DiscipulosEncontroComDeus[] getDtDisEncontroDeusSelecionadosRemover() {
		return dtDisEncontroDeusSelecionadosRemover;
	}

	public void setDtDisEncontroDeusSelecionadosRemover(
			DiscipulosEncontroComDeus[] dtDisEncontroDeusSelecionadosRemover) {
		this.dtDisEncontroDeusSelecionadosRemover = dtDisEncontroDeusSelecionadosRemover;
	}

	public List<PalestrasEncontroComDeus> getListaPalestraEncontroComDeus() {
		return listaPalestraEncontroComDeus;
	}

	public void setListaPalestraEncontroComDeus(
			List<PalestrasEncontroComDeus> listaPalestraEncontroComDeus) {
		this.listaPalestraEncontroComDeus = listaPalestraEncontroComDeus;
	}

	public PalestrasEncontroComDeus getPalestraEncontroComDeus() {
		return palestraEncontroComDeus;
	}

	public void setPalestraEncontroComDeus(
			PalestrasEncontroComDeus palestraEncontroComDeus) {
		this.palestraEncontroComDeus = palestraEncontroComDeus;
	}

	public Discipulos getLiderPalestra() {
		return liderPalestra;
	}

	public void setLiderPalestra(Discipulos liderPalestra) {
		this.liderPalestra = liderPalestra;
	}

	public PalestrasEncontroComDeus getPalestraEncontroComDeusSelecionada() {
		return palestraEncontroComDeusSelecionada;
	}

	public void setPalestraEncontroComDeusSelecionada(
			PalestrasEncontroComDeus palestraEncontroComDeusSelecionada) {
		this.palestraEncontroComDeusSelecionada = palestraEncontroComDeusSelecionada;
	}

	public Date getHorarioPalestra() {
		return horarioPalestra;
	}

	public void setHorarioPalestra(Date horarioPalestra) {
		this.horarioPalestra = horarioPalestra;
	}

	public int getIndexPalestraSelecionada() {
		return indexPalestraSelecionada;
	}

	public void setIndexPalestraSelecionada(int indexPalestraSelecionada) {
		this.indexPalestraSelecionada = indexPalestraSelecionada;
	}

	public DiscipulosEncontroComDeus getDiscipuloEncontroComDeus() {
		return discipuloEncontroComDeus;
	}

	public void setDiscipuloEncontroComDeus(
			DiscipulosEncontroComDeus discipuloEncontroComDeus) {
		this.discipuloEncontroComDeus = discipuloEncontroComDeus;
	}

	public boolean isMostrarBtnCancelarEncontro() {
		return mostrarBtnCancelarEncontro;
	}

	public void setMostrarBtnCancelarEncontro(boolean mostrarBtnCancelarEncontro) {
		this.mostrarBtnCancelarEncontro = mostrarBtnCancelarEncontro;
	}

	public Encontrostipomovimento getEncontroTipoMovimento() {
		return encontroTipoMovimento;
	}

	public void setEncontroTipoMovimento(
			Encontrostipomovimento encontroTipoMovimento) {
		this.encontroTipoMovimento = encontroTipoMovimento;
	}

	public Encontrofinanceiro getEncontroFinanceiro() {
		return encontroFinanceiro;
	}

	public void setEncontroFinanceiro(Encontrofinanceiro encontroFinanceiro) {
		this.encontroFinanceiro = encontroFinanceiro;
	}

	public Encontrostipolideres getEncontroTipoLider() {
		return encontroTipoLider;
	}

	public void setEncontroTipoLider(Encontrostipolideres encontroTipoLider) {
		this.encontroTipoLider = encontroTipoLider;
	}

	public int getFiltroEncontroComDeus() {
		return filtroEncontroComDeus;
	}

	public void setFiltroEncontroComDeus(int filtroEncontroComDeus) {
		this.filtroEncontroComDeus = filtroEncontroComDeus;
	}

	public List<Dadosencontros> getListaEncontroComDeus() {
		return listaEncontroComDeus;
	}

	public void setListaEncontroComDeus(List<Dadosencontros> listaEncontroComDeus) {
		this.listaEncontroComDeus = listaEncontroComDeus;
	}

	public boolean isMostrarDtEncontroComDeus() {
		return mostrarDtEncontroComDeus;
	}

	public void setMostrarDtEncontroComDeus(boolean mostrarDtEncontroComDeus) {
		this.mostrarDtEncontroComDeus = mostrarDtEncontroComDeus;
	}

	public BigDecimal getValorLocalEncontroAntigo() {
		return valorLocalEncontroAntigo;
	}

	public void setValorLocalEncontroAntigo(BigDecimal valorLocalEncontroAntigo) {
		this.valorLocalEncontroAntigo = valorLocalEncontroAntigo;
	}

	public BigDecimal getvalorTransporteAntigo() {
		return valorTransporteAntigo;
	}

	public void setvalorTransporteAntigo(BigDecimal valorTransporteAntigo) {
		this.valorTransporteAntigo = valorTransporteAntigo;
	}

	public BigDecimal getValorPorPessoaAntigo() {
		return valorPorPessoaAntigo;
	}

	public void setValorPorPessoaAntigo(BigDecimal valorPorPessoaAntigo) {
		this.valorPorPessoaAntigo = valorPorPessoaAntigo;
	}

	public Lideres getLiderEspiritualAntigo() {
		return liderEspiritualAntigo;
	}

	public void setLiderEspiritualAntigo(Lideres liderEspiritualAntigo) {
		this.liderEspiritualAntigo = liderEspiritualAntigo;
	}

	public Lideres getLiderIntercessaoAntigo() {
		return liderIntercessaoAntigo;
	}

	public void setLiderIntercessaoAntigo(Lideres liderIntercessaoAntigo) {
		this.liderIntercessaoAntigo = liderIntercessaoAntigo;
	}

	public Lideres getLiderCorreioAntigo() {
		return liderCorreioAntigo;
	}

	public void setLiderCorreioAntigo(Lideres liderCorreioAntigo) {
		this.liderCorreioAntigo = liderCorreioAntigo;
	}

	public Lideres getLiderApoioRecepcaoAntigo() {
		return liderApoioRecepcaoAntigo;
	}

	public void setLiderApoioRecepcaoAntigo(Lideres liderApoioRecepcaoAntigo) {
		this.liderApoioRecepcaoAntigo = liderApoioRecepcaoAntigo;
	}

	public String getHorarioPalestra2() {
		return horarioPalestra2;
	}

	public void setHorarioPalestra2(String horarioPalestra2) {
		this.horarioPalestra2 = horarioPalestra2;
	}

	public int getInscricoesPagasEncontroComDeus() {
		return inscricoesPagasEncontroComDeus;
	}

	public void setInscricoesPagasEncontroComDeus(int inscricoesPagasEncontroComDeus) {
		this.inscricoesPagasEncontroComDeus = inscricoesPagasEncontroComDeus;
	}

	public BigDecimal getValorInscricoesPagasEncontroComDeus() {
		return valorInscricoesPagasEncontroComDeus;
	}

	public void setValorInscricoesPagasEncontroComDeus(
			BigDecimal valorInscricoesPagasEncontroComDeus) {
		this.valorInscricoesPagasEncontroComDeus = valorInscricoesPagasEncontroComDeus;
	}

	public BigDecimal getValorOfertaTirada() {
		return valorOfertaTirada;
	}

	public void setValorOfertaTirada(BigDecimal valorOfertaTirada) {
		this.valorOfertaTirada = valorOfertaTirada;
	}

	public BigDecimal getValorOfertaVoluntaria() {
		return valorOfertaVoluntaria;
	}

	public void setValorOfertaVoluntaria(BigDecimal valorOfertaVoluntaria) {
		this.valorOfertaVoluntaria = valorOfertaVoluntaria;
	}

	public BigDecimal getValorTotalEntrada() {
		return valorTotalEntrada;
	}

	public void setValorTotalEntrada(BigDecimal valorTotalEntrada) {
		this.valorTotalEntrada = valorTotalEntrada;
	}
	
	public BigDecimal getValorTransporte() {
		return valorTransporte;
	}

	public void setValorTransporte(BigDecimal valorTransporte) {
		this.valorTransporte = valorTransporte;
	}

	public BigDecimal getValorTransporteAntigo() {
		return valorTransporteAntigo;
	}

	public void setValorTransporteAntigo(BigDecimal valorTransporteAntigo) {
		this.valorTransporteAntigo = valorTransporteAntigo;
	}

	public BigDecimal getValorMaterialHigienico() {
		return valorMaterialHigienico;
	}

	public void setValorMaterialHigienico(BigDecimal valorMaterialHigienico) {
		this.valorMaterialHigienico = valorMaterialHigienico;
	}

	public BigDecimal getValorRefeicao() {
		return valorRefeicao;
	}

	public void setValorRefeicao(BigDecimal valorRefeicao) {
		this.valorRefeicao = valorRefeicao;
	}

	public BigDecimal getValorCorreio() {
		return valorCorreio;
	}

	public void setValorCorreio(BigDecimal valorCorreio) {
		this.valorCorreio = valorCorreio;
	}

	public BigDecimal getValorTotalSaida() {
		return valorTotalSaida;
	}

	public void setValorTotalSaida(BigDecimal valorTotalSaida) {
		this.valorTotalSaida = valorTotalSaida;
	}

	public BigDecimal getValorTotalBruto() {
		return valorTotalBruto;
	}

	public void setValorTotalBruto(BigDecimal valorTotalBruto) {
		this.valorTotalBruto = valorTotalBruto;
	}

	public boolean isValorBrutoPositivo() {
		return valorBrutoPositivo;
	}

	public void setValorBrutoPositivo(boolean valorBrutoPositivo) {
		this.valorBrutoPositivo = valorBrutoPositivo;
	}

	public boolean isMostrarBtnVoltarEncontro() {
		return mostrarBtnVoltarEncontro;
	}

	public void setMostrarBtnVoltarEncontro(boolean mostrarBtnVoltarEncontro) {
		this.mostrarBtnVoltarEncontro = mostrarBtnVoltarEncontro;
	}

	public Dadosencontros getDadosEncontrosSelecionado() {
		return dadosEncontrosSelecionado;
	}

	public void setDadosEncontrosSelecionado(
			Dadosencontros dadosEncontrosSelecionado) {
		this.dadosEncontrosSelecionado = dadosEncontrosSelecionado;
	}

	public int getFiltroPosEncontro() {
		return filtroPosEncontro;
	}

	public void setFiltroPosEncontro(int filtroPosEncontro) {
		this.filtroPosEncontro = filtroPosEncontro;
	}

	public Discipulosbatismo getDiscipulosBatismo() {
		return discipulosBatismo;
	}

	public void setDiscipulosBatismo(Discipulosbatismo discipulosBatismo) {
		this.discipulosBatismo = discipulosBatismo;
	}
}