package br.com.pais.managedbeans;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

import br.com.pais.classe.nopersistence.ArvoreDiscipulos;
import br.com.pais.classe.nopersistence.ArvoreGeracoesNodos;
import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.FormacaoeclesiasticasDao;
import br.com.pais.dao.FuncaoeclesiasticasDao;
import br.com.pais.dao.LogradouroDao;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.EncontrosDaoImp;
import br.com.pais.dao.impl.FormacaoeclesiasticasDaoImp;
import br.com.pais.dao.impl.FuncaoeclesiasticasDaoImp;
import br.com.pais.dao.impl.LogradouroDaoImp;
import br.com.pais.entities.Bairro;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Encontros;
import br.com.pais.entities.Estado;
import br.com.pais.entities.Estadocivil;
import br.com.pais.entities.Formacaoacademica;
import br.com.pais.entities.Formacaoeclesiasticas;
import br.com.pais.entities.Funcaoeclesiasticas;
import br.com.pais.entities.Geracoes;
import br.com.pais.entities.Localidade;
import br.com.pais.entities.Logradouro;
import br.com.pais.exception.ValidarCPFException;
import br.com.pais.exception.ValidarTEException;
import br.com.pais.util.ApplicationSecurityManager;
import br.com.pais.util.Criptografia;
import br.com.pais.util.SendEMail;
import br.com.pais.util.ValidarCPF;
import br.com.pais.util.ValidarTituloEleitor;

/**
 * @author manoel
 */
@ManagedBean
@SessionScoped
public class DiscipuloBean {

	private boolean editar = true;
	private boolean isConjugeCad = false;
	private Discipulos conjugePesq;
	private boolean conjugeCadEdit = false;
	private boolean fotoEdit = false;
	private String senha;

	private Discipulos discipulos = new Discipulos();
	private Encontros encontros = new Encontros();
	private Geracoes geracoes = new Geracoes();
	private Estadocivil estadocivil = new Estadocivil();
	private Formacaoacademica formacaoacademica = new Formacaoacademica();
	private Formacaoeclesiasticas formacaoeclesiastica = new Formacaoeclesiasticas();
	private Funcaoeclesiasticas funcaoeclesiasticas = new Funcaoeclesiasticas();
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();

	// Objetos Daos
	private FuncaoeclesiasticasDao funcaoeclesiasticasDao = new FuncaoeclesiasticasDaoImp();
	byte[] conteudo;
	String path;
	

	private List<Formacaoeclesiasticas> ListaFormacoesEclesiasticas = new ArrayList<Formacaoeclesiasticas>();
	private List<Funcaoeclesiasticas> listaFuncaoEclesiasticas = new ArrayList<Funcaoeclesiasticas>();
	private DualListModel<Formacaoeclesiasticas> ListaFormacaoEclesiasticas = new DualListModel<Formacaoeclesiasticas>();
	private DualListModel<Encontros> ListaEncontros = new DualListModel<Encontros>();
	private List<Encontros> source = new ArrayList<Encontros>();
	private List<Encontros> target = new ArrayList<Encontros>();
	List<Discipulos> listDiscipulosConjuges = new ArrayList<Discipulos>();

	private List<Formacaoeclesiasticas> sourceFormacaoEclesiasticas = new ArrayList<Formacaoeclesiasticas>();
	private List<Formacaoeclesiasticas> targetFormacaoEclesiasticas = new ArrayList<Formacaoeclesiasticas>();

	private Formacaoeclesiasticas[] SelecaoFormacoesEclesiasticas;
	private Encontros[] SelecaoEncontros;

	private FormacaoeclesiasticasDao formacaoeclesiasticasDao = new FormacaoeclesiasticasDaoImp();
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp();

	private String nomeArquivoSelecionado;
	private StreamedContent imagem;

	private Estado estado = new Estado();
	private Localidade cidade = new Localidade();
	private Bairro bairro = new Bairro();
	private Logradouro logradouro = new Logradouro();

	// Objetos Daos
	private LogradouroDao logradouroDao = new LogradouroDaoImp();

	private boolean editarSenha;

	private boolean editarM12 = false;
	private List<Discipulos> listaDiscipulos = new ArrayList<Discipulos>();
	
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
	
	// Buscar pelo cep
	public void buscarCEP(AjaxBehaviorEvent event) {
		logradouro = logradouroDao.encontrarPorCEP(getLogradouro().getCep());
		if (logradouro == null) {

			discipulos.setDisEndNumero(null);
			discipulos.setDisEndComplemento(null);
		}

	}

	public List<Discipulos> completeDiscipulos(String query) {		
		listDiscipulosConjuges = discipuloDao.listarDiscipulosCasados(query, discipuloSessao.getDiscipulos().getDisSexo());
		return listDiscipulosConjuges;
	}
	
	public void isCasado(AjaxBehaviorEvent event) {
		if (estadocivil.getEstCod() == 2) {
			editar = false;
			conjugeCadEdit = false;
		
		} else {
			editar = true;
			conjugeCadEdit = true;
			discipulos.setDisconjuge(null);
			
		}

	}
	public void isCasadoCad(AjaxBehaviorEvent event) {
		if (isConjugeCad != true) {
			discipulos.setDisconjuge(null);
		} 
	}

	public void handleSelect(SelectEvent event) {
		conjugePesq = discipuloDao.pesquisarPorId(conjugePesq.getDisCod());
	}
	
	
	public void isM12(AjaxBehaviorEvent event) {
		if (discipulos.getDism12() == 's') {
			editarM12  = true;
			geracoes = new Geracoes();
		} else {
			editarM12 = false;
			geracoes=(null);
		}

	}

	public void isDiscipulo(AjaxBehaviorEvent event) {
		if (funcaoeclesiasticas.getFunCod() == 1
				|| funcaoeclesiasticas.getFunCod() == 2) {
			editarSenha = true;
			discipulos.setDisSenha(null);
		} else {

			editarSenha = false;

		}

	}

	public void validarcpf(AjaxBehaviorEvent ev) {
		try {
			if (ValidarCPF.validarCPF(getDiscipulos().getDisCpf()) == false) {

				discipulos.setDisCpf(null);
			}
		} catch (ValidarCPFException e) {
			// TODO Auto-generated catch block
			discipulos.setDisCpf(null);
		}
	}

	public void validartituloeleitor(AjaxBehaviorEvent ev) {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			int qtdCompleta = 0;
			String zeros = "";
			if (getDiscipulos().getDisTitEleitor().length() > 0) {
				qtdCompleta = 12 - getDiscipulos().getDisTitEleitor().length();
				for (int i = 0; i < qtdCompleta; i++) {
					zeros += "0";
				}
				discipulos.setDisTitEleitor(zeros
						+ getDiscipulos().getDisTitEleitor());
				if (ValidarTituloEleitor.validarTE(getDiscipulos()
						.getDisTitEleitor()) == false) {
					discipulos.setDisTitEleitor(null);
				}
			}
		} catch (ValidarTEException e) {
			// TODO Auto-generated catch block

			context.addMessage(null, new FacesMessage("Erro",
					"CEP não encontrado. "));
			discipulos.setDisTitEleitor(null);
		}
	}

	public String handleFileUpload(FileUploadEvent event) {
		
		
		try {
			if (discipulos.getDisCod() != null){
				fotoEdit = true;
			}else
			{
				fotoEdit= false;
			}
			setNomeArquivoSelecionado(event.getFile().getFileName());
			imagem = new DefaultStreamedContent(event.getFile()
					.getInputstream());

			// Para pegar direto o caminho da imagem
			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) faces
					.getExternalContext().getRequest();
			String path = request.getSession().getServletContext()
					.getRealPath("/fotos");
		
			conteudo = event.getFile().getContents();

			String caminho = path + "\\" + discipulos.getDisCpf() + ".jpg";// event.getFile().getFileName();
		
		} catch (IOException ex) {
			Logger.getLogger(FileUploadController.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return nomeArquivoSelecionado;
	}

	public String prepararListarDiscipulos() {
	
		//listaDiscipulos  = new ArrayList<Discipulos>();
		//listaDiscipulos.addAll(discipuloDao.listarDiscipulos(discipuloSessao.getDiscipulos().getDisCod()));
		
		criarArvoreDiscipulosGeracoes();
		
		return "/list/discipulos.mir";
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
	
	public void carregarLideres(Discipulos discipulos, TreeNode pai) {
		treeNode = new DefaultTreeNode();
		listaDiscipulos =  discipulos.getDiscipuloses();
		
		for(Discipulos d : listaDiscipulos){
            treeNode = new DefaultTreeNode(d,pai);
			nodes.add(treeNode);
			index = nodes.indexOf(treeNode);
			if(!discipulos.getDiscipuloses().isEmpty()){
				carregarLideres(d, (nodes.get(index)));
			}
		}
	}
	
	
	public String prepararDiscipulo() {
		estadocivil = new Estadocivil();
		estadocivil.setEstCod(2);
		
		conjugePesq = new Discipulos();
		isConjugeCad = false;
		listaFuncaoEclesiasticas = funcaoeclesiasticasDao.listarFuncaoPorSexo(
				discipuloSessao.getDiscipulos().getDisSexo(), discipuloSessao
						.getDiscipulos().getFuncaoeclesiasticas().getFunCod());
		editar = false;
		editarSenha = true;
		editarM12 = false;
		source = new ArrayList<Encontros>();
		target = new ArrayList<Encontros>();
		sourceFormacaoEclesiasticas = new ArrayList<Formacaoeclesiasticas>();
		targetFormacaoEclesiasticas = new ArrayList<Formacaoeclesiasticas>();
		nomeArquivoSelecionado = null;
		sourceFormacaoEclesiasticas.addAll(new FormacaoeclesiasticasDaoImp().todos());
		source.addAll(new EncontrosDaoImp().todos());

		funcaoeclesiasticas = new Funcaoeclesiasticas();
		geracoes = new Geracoes();

		ListaFormacaoEclesiasticas = new DualListModel<Formacaoeclesiasticas>(sourceFormacaoEclesiasticas, targetFormacaoEclesiasticas);
		ListaEncontros = new DualListModel<Encontros>(source, target);
		logradouro = new Logradouro();
		formacaoacademica = new Formacaoacademica();

		discipulos = new Discipulos();
		discipulos.setDism12('n');

		InputStream stream = this.getClass().getResourceAsStream(
				"/br/com/pais/util/sem_foto.jpg");
		imagem = new DefaultStreamedContent(stream, "image/jpeg", "sem_foto");
		// ListaEncontros = new EncontrosDaoImp().todos();
		// ListaFormacoesEclesiasticas = formacaoeclesiasticasDao.todos();

		return "/cad/discipulos.mir";

	}
	public String prepararEdicao() {
		nomeArquivoSelecionado = "";
		fotoEdit = false;
		source = new ArrayList<Encontros>();
		target = new ArrayList<Encontros>();
		sourceFormacaoEclesiasticas = new ArrayList<Formacaoeclesiasticas>();
		targetFormacaoEclesiasticas = new ArrayList<Formacaoeclesiasticas>();
		isConjugeCad = false;
		conjugeCadEdit = true;
		conjugePesq = null;
		editar = true;
		editarM12 = discipulos.getDism12() == 's';
		
		source.addAll(new EncontrosDaoImp().todos());
		target = discipulos.getEncontroses();
		
		sourceFormacaoEclesiasticas.addAll(new FormacaoeclesiasticasDaoImp().todos());
		targetFormacaoEclesiasticas = discipulos.getFormacaoeclesiasticases();
		
		for (Formacaoeclesiasticas fe : targetFormacaoEclesiasticas){
			for (Formacaoeclesiasticas fe2: sourceFormacaoEclesiasticas)
				if(fe2.getForEcCod() == fe.getForEcCod()){
					sourceFormacaoEclesiasticas.remove(fe2);
					break;
				}
		}
		 
		for (Encontros e : target){
			for (Encontros e2: source)
				if(e2.getEncCod()== e.getEncCod()){
					source.remove(e2);
					break;
				}
		}
		 		
		
		ListaFormacaoEclesiasticas = new DualListModel<Formacaoeclesiasticas>(sourceFormacaoEclesiasticas, targetFormacaoEclesiasticas);
		ListaEncontros = new DualListModel<Encontros>(source, target);
		
		listaFuncaoEclesiasticas = funcaoeclesiasticasDao.listarFuncaoPorSexo(
				discipuloSessao.getDiscipulos().getDisSexo(), discipuloSessao
						.getDiscipulos().getFuncaoeclesiasticas().getFunCod());
		
		
		logradouro = new Logradouro();
		estadocivil = discipulos.getEstadocivil();
		logradouro = discipulos.getLogradouro();
		formacaoacademica = discipulos.getFormacaoacademica();
		funcaoeclesiasticas = discipulos.getFuncaoeclesiasticas();
		geracoes = discipulos.getGeracoes();
		
		
		if(discipulos.getEstadocivil().getEstCod()==2){
			if(discipulos.getDiscipulosByDisConjugecad()==(null)){
				isConjugeCad = false;
				conjugeCadEdit = false;
				conjugePesq = null;
				editar = false;
			}else{
				editar = false;
				isConjugeCad = true;
				conjugeCadEdit = false;
				conjugePesq = discipulos.getDiscipulosByDisConjugecad();
				
			}
		}	
		 		
		return "/editar/discipulos.mir";
	}


	public String prepararEdicaoMeusDados() {
		nomeArquivoSelecionado = "";
		fotoEdit = false;
		source = new ArrayList<Encontros>();
		target = new ArrayList<Encontros>();
		sourceFormacaoEclesiasticas = new ArrayList<Formacaoeclesiasticas>();
		targetFormacaoEclesiasticas = new ArrayList<Formacaoeclesiasticas>();
		isConjugeCad = false;
		conjugeCadEdit = true;
		conjugePesq = null;
		editar = true;
		discipulos = discipuloSessao.getDiscipulos();
		editarM12 = discipulos.getDism12() == 's';
		
		source.addAll(new EncontrosDaoImp().todos());
		target = discipulos.getEncontroses();
		
		sourceFormacaoEclesiasticas.addAll(new FormacaoeclesiasticasDaoImp().todos());
		targetFormacaoEclesiasticas = discipulos.getFormacaoeclesiasticases();
		
		for (Formacaoeclesiasticas fe : targetFormacaoEclesiasticas){
			for (Formacaoeclesiasticas fe2: sourceFormacaoEclesiasticas)
				if(fe2.getForEcCod() == fe.getForEcCod()){
					sourceFormacaoEclesiasticas.remove(fe2);
					break;
				}
		}
		 
		for (Encontros e : target){
			for (Encontros e2: source)
				if(e2.getEncCod()== e.getEncCod()){
					source.remove(e2);
					break;
				}
		}
		 		
		
		ListaFormacaoEclesiasticas = new DualListModel<Formacaoeclesiasticas>(sourceFormacaoEclesiasticas, targetFormacaoEclesiasticas);
		ListaEncontros = new DualListModel<Encontros>(source, target);
		
		listaFuncaoEclesiasticas = funcaoeclesiasticasDao.listarFuncaoPorSexo(
				discipuloSessao.getDiscipulos().getDisSexo(), discipuloSessao
						.getDiscipulos().getFuncaoeclesiasticas().getFunCod());
		
		
		logradouro = new Logradouro();
		estadocivil = discipulos.getEstadocivil();
		logradouro = discipulos.getLogradouro();
		formacaoacademica = discipulos.getFormacaoacademica();
		funcaoeclesiasticas = discipulos.getFuncaoeclesiasticas();
		geracoes = discipulos.getGeracoes();
		
		
		if(discipulos.getEstadocivil().getEstCod()==2){
			if(discipulos.getDiscipulosByDisConjugecad()==(null)){
				isConjugeCad = false;
				conjugeCadEdit = false;
				conjugePesq = null;
				editar = false;
			}else{
				editar = false;
				isConjugeCad = true;
				conjugeCadEdit = false;
				conjugePesq = discipulos.getDiscipulosByDisConjugecad();
				
			}
		}	
		 		
		return "/editar/editarmeusdados.mir";
	}

	public void editar(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (fotoEdit == true) {
			//discipulos.setDisfoto("/img/sem_foto.jpg"); // Falta Verificar
			discipulos.setDisfoto("/fotos/" + salvarFoto());
		} /*else {
			discipulos.setDisfoto("/fotos/" + salvarFoto());
		}*/
		
		

		if (logradouro == null) {
			logradouro.setCep(null);
			context.addMessage(null, new FacesMessage("ERRO", " CEP inválido"));
		} else {
			discipulos.setLogradouro(logradouro);
		}

		validarcpf(null);
		if (discipulos.getDisTitEleitor() != null) {
			validartituloeleitor(null);
		}
		
		discipulos.setDisSenha(new Criptografia().criptografar(discipulos.getDisSenha()));
		discipulos.setDisSexo(discipuloSessao.getDiscipulos().getDisSexo());
		discipulos.setEncontroses(ListaEncontros.getTarget());
		discipulos.setFormacaoeclesiasticases(ListaFormacaoEclesiasticas.getTarget());
		discipulos.setDiscipulos(discipuloSessao.getDiscipulos());

		
		discipulos.setEstadocivil(estadocivil);
		if(estadocivil.getEstCod()==2 && isConjugeCad==true){
			discipulos.setDiscipulosByDisConjugecad(conjugePesq);
		}
		
		discipulos.setFormacaoacademica(formacaoacademica);
		discipulos.setFuncaoeclesiasticas(funcaoeclesiasticas);
		discipulos.setGeracoes(geracoes);

		if (discipuloDao.atualizar(discipulos) == (true)) {
			if(estadocivil.getEstCod()==2 && isConjugeCad==true){
				conjugePesq.setDiscipulosByDisConjugecad(discipulos);
				conjugePesq.setEstadocivil(estadocivil);
				discipuloDao.atualizar(conjugePesq);
			}
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "SUCESSO!!!",
					"Dados atualizados com sucesso!"));
			
			/*if (discipulos.getDisemail() != null
					|| discipulos.getDisemail() != ""
					&& (funcaoeclesiasticas.getFunCod() != 1 || funcaoeclesiasticas
							.getFunCod() != 2)) {

				for (Funcaoeclesiasticas fe : listaFuncaoEclesiasticas) {
					if (fe.getFunCod() == funcaoeclesiasticas.getFunCod()) {

						funcaoeclesiasticas = fe;
					}
				}// for end

				new SendEMail().sendSimpleMailEnviarSenha(
						funcaoeclesiasticas.getFunDescricao(),
						discipulos.getDisnome(), discipulos.getDisemail(),
						discipulos.getDisSenha(), discipulos.getDisCpf());
			}*/ 
		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "ERRO!!!",
					"Erro ao cadastrar!"));
		}
	}
	
	
	public void salvar(ActionEvent event) {
		
		FacesContext context = FacesContext.getCurrentInstance();
		if (conteudo == null) {
			discipulos.setDisfoto("/img/sem_foto.jpg"); 
		} else {
			discipulos.setDisfoto("/fotos/" + salvarFoto());
		}

		if (logradouro == null) {
			logradouro.setCep(null);
			context.addMessage(null, new FacesMessage("ERRO", " CEP inválido"));
		} else {
			discipulos.setLogradouro(logradouro);
		}

		validarcpf(null);
		if (discipulos.getDisTitEleitor() != null) {
			validartituloeleitor(null);
		}
		
		discipulos.setDisSexo(discipuloSessao.getDiscipulos().getDisSexo());
		discipulos.setEncontroses(ListaEncontros.getTarget());
		discipulos.setFormacaoeclesiasticases(ListaFormacaoEclesiasticas.getTarget());
		discipulos.setDiscipulos(discipuloSessao.getDiscipulos());

		
		discipulos.setEstadocivil(estadocivil);
		if(estadocivil.getEstCod()==2 && isConjugeCad==true){
			discipulos.setDiscipulosByDisConjugecad(conjugePesq);
		}
		if(discipulos.getDisSenha()!= null) {
			senha = discipulos.getDisSenha();
			discipulos.setDisSenha(new Criptografia().criptografar(discipulos.getDisSenha()));
		}
		discipulos.setFormacaoacademica(formacaoacademica);
		discipulos.setFuncaoeclesiasticas(funcaoeclesiasticas);
		discipulos.setGeracoes(geracoes);

	
		
		if (discipuloDao.salvar(discipulos) == (true)) {
			if(estadocivil.getEstCod()==2 && isConjugeCad==true){
				conjugePesq.setDiscipulosByDisConjugecad(discipulos);
				conjugePesq.setEstadocivil(estadocivil);
				discipuloDao.atualizar(conjugePesq);
			}
			
			if (discipulos.getDisemail() != null
					|| discipulos.getDisemail() != ""
					&& (funcaoeclesiasticas.getFunCod() != 1 || funcaoeclesiasticas
							.getFunCod() != 2)) {

				for (Funcaoeclesiasticas fe : listaFuncaoEclesiasticas) {
					if (fe.getFunCod() == funcaoeclesiasticas.getFunCod()) {

						funcaoeclesiasticas = fe;
					}
				}// for end

				new SendEMail().sendSimpleMailEnviarSenha(
						funcaoeclesiasticas.getFunDescricao(),
						discipulos.getDisnome(), discipulos.getDisemail(),
						discipulos.getDisSenha(), senha);
			}
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "SUCESSO!!!",
					discipulos.getDisnome() + " cadastrado com sucesso!"));
			prepararDiscipulo();
		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "ERRO!!!",
					"Erro ao cadastrar!"));
		}
	}

	public String salvarFoto() {
		String Foto = null;
		try {
		
			// Para pegar direto o caminho da imagem
			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) faces
					.getExternalContext().getRequest();
			String path = request.getSession().getServletContext()
					.getRealPath("/fotos/");

			Foto = discipulos.getDisCpf() + ".jpg";
			String caminho = path + "/" + Foto;

			FileOutputStream fos = new FileOutputStream(caminho);

			fos.write(conteudo);

			fos.close();
			return Foto;

		} catch (IOException ex) {
			Logger.getLogger(FileUploadController.class.getName()).log(
					Level.SEVERE, null, ex);
		}

		return Foto;

	}

	/**
	 * GETTERS and SETTERS
	 */

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
	 * @return the editar
	 */
	public boolean isEditar() {
		return editar;
	}

	/**
	 * @param editar
	 *            the editar to set
	 */
	public void setEditar(boolean editar) {
		this.editar = editar;
	}

	/**
	 * @return the estadocivil
	 */
	public Estadocivil getEstadocivil() {
		return estadocivil;
	}

	/**
	 * @param estadocivil
	 *            the estadocivil to set
	 */
	public void setEstadocivil(Estadocivil estadocivil) {
		this.estadocivil = estadocivil;
	}

	/**
	 * @return the formacaoeclesiastica
	 */
	public Formacaoeclesiasticas getFormacaoeclesiastica() {
		return formacaoeclesiastica;
	}

	/**
	 * @param formacaoeclesiastica
	 *            the formacaoeclesiastica to set
	 */
	public void setFormacaoeclesiastica(Formacaoeclesiasticas formacaoeclesiastica) {
		this.formacaoeclesiastica = formacaoeclesiastica;
	}

	/**
	 * @return the listaFormacoesEclesiasticas
	 */
	public List<Formacaoeclesiasticas> getListaFormacoesEclesiasticas() {
		return ListaFormacoesEclesiasticas;
	}

	/**
	 * @param listaFormacoesEclesiasticas
	 *            the listaFormacoesEclesiasticas to set
	 */
	public void setListaFormacoesEclesiasticas(
			List<Formacaoeclesiasticas> listaFormacoesEclesiasticas) {
		ListaFormacoesEclesiasticas = listaFormacoesEclesiasticas;
	}

	/**
	 * @return the selecaoFormacoesEclesiasticas
	 */
	public Formacaoeclesiasticas[] getSelecaoFormacoesEclesiasticas() {
		return SelecaoFormacoesEclesiasticas;
	}

	/**
	 * @param selecaoFormacoesEclesiasticas
	 *            the selecaoFormacoesEclesiasticas to set
	 */
	public void setSelecaoFormacoesEclesiasticas(
			Formacaoeclesiasticas[] selecaoFormacoesEclesiasticas) {
		SelecaoFormacoesEclesiasticas = selecaoFormacoesEclesiasticas;
	}

	/**
	 * @return the nomeArquivoSelecionado
	 */
	public String getNomeArquivoSelecionado() {
		return nomeArquivoSelecionado;
	}

	/**
	 * @param nomeArquivoSelecionado
	 *            the nomeArquivoSelecionado to set
	 */
	public void setNomeArquivoSelecionado(String nomeArquivoSelecionado) {
		this.nomeArquivoSelecionado = nomeArquivoSelecionado;
	}

	/**
	 * @return the imagem
	 */
	public StreamedContent getImagem() {
		if(imagem == null || imagem.equals(null)) {
			InputStream stream = this.getClass().getResourceAsStream(
			"/br/com/pais/util/sem_foto.jpg");
			return imagem = new DefaultStreamedContent(stream, "image/jpeg", "sem_foto");
		}else {
			return imagem;
		}
	}

	/**
	 * @param imagem
	 *            the imagem to set
	 */
	public void setImagem(StreamedContent imagem) {
		this.imagem = imagem;
	}

	/**
	 * @return the selecaoEncontros
	 */
	public Encontros[] getSelecaoEncontros() {
		return SelecaoEncontros;
	}

	/**
	 * @param selecaoEncontros
	 *            the selecaoEncontros to set
	 */
	public void setSelecaoEncontros(Encontros[] selecaoEncontros) {
		SelecaoEncontros = selecaoEncontros;
	}

	/**
	 * @param listaEncontros
	 *            the listaEncontros to set
	 */
	public void setListaEncontros(DualListModel<Encontros> listaEncontros) {
		ListaEncontros = listaEncontros;
	}

	/**
	 * @return the listaEncontros
	 */
	public DualListModel<Encontros> getListaEncontros() {
		return ListaEncontros;
	}

	/**
	 * @return the encontros
	 */
	public Encontros getEncontros() {
		return encontros;
	}

	/**
	 * @param encontros
	 *            the encontros to set
	 */
	public void setEncontros(Encontros encontros) {
		this.encontros = encontros;
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
	 * @return the geracoes
	 */
	public Geracoes getGeracoes() {
		return geracoes;
	}

	/**
	 * @param geracoes
	 *            the geracoes to set
	 */
	public void setGeracoes(Geracoes geracoes) {
		this.geracoes = geracoes;
	}

	/**
	 * @return the listaFormacaoEclesiasticas
	 */
	public DualListModel<Formacaoeclesiasticas> getListaFormacaoEclesiasticas() {
		return ListaFormacaoEclesiasticas;
	}

	/**
	 * @param listaFormacaoEclesiasticas
	 *            the listaFormacaoEclesiasticas to set
	 */
	public void setListaFormacaoEclesiasticas(DualListModel<Formacaoeclesiasticas> listaFormacaoEclesiasticas) {
		ListaFormacaoEclesiasticas = listaFormacaoEclesiasticas;
	}

	/**
	 * @return the formacaoacademica
	 */
	public Formacaoacademica getFormacaoacademica() {
		return formacaoacademica;
	}

	/**
	 * @param formacaoacademica
	 *            the formacaoacademica to set
	 */
	public void setFormacaoacademica(Formacaoacademica formacaoacademica) {
		this.formacaoacademica = formacaoacademica;
	}

	/**
	 * @return the funcaoeclesiasticas
	 */
	public Funcaoeclesiasticas getFuncaoeclesiasticas() {
		return funcaoeclesiasticas;
	}

	/**
	 * @param funcaoeclesiasticas
	 *            the funcaoeclesiasticas to set
	 */
	public void setFuncaoeclesiasticas(Funcaoeclesiasticas funcaoeclesiasticas) {
		this.funcaoeclesiasticas = funcaoeclesiasticas;
	}

	/**
	 * @return the editarSenha
	 */
	public boolean isEditarSenha() {
		return editarSenha;
	}

	/**
	 * @param editarSenha
	 *            the editarSenha to set
	 */
	public void setEditarSenha(boolean editarSenha) {
		this.editarSenha = editarSenha;
	}

	/**
	 * @return the listaFuncaoEclesiasticas
	 */
	public List<Funcaoeclesiasticas> getListaFuncaoEclesiasticas() {
		return listaFuncaoEclesiasticas;
	}

	/**
	 * @param listaFuncaoEclesiasticas
	 *            the listaFuncaoEclesiasticas to set
	 */
	public void setListaFuncaoEclesiasticas(List<Funcaoeclesiasticas> listaFuncaoEclesiasticas) {
		this.listaFuncaoEclesiasticas = listaFuncaoEclesiasticas;
	}

	/**
	 * @return the editarM12
	 */
	public boolean isEditarM12() {
		return editarM12;
	}

	/**
	 * @param editarM12 the editarM12 to set
	 */
	public void setEditarM12(boolean editarM12) {
		this.editarM12 = editarM12;
	}

	/**
	 * @return the isConjugeCad
	 */
	public boolean isConjugeCad() {
		return isConjugeCad;
	}

	/**
	 * @param isConjugeCad the isConjugeCad to set
	 */
	public void setConjugeCad(boolean isConjugeCad) {
		this.isConjugeCad = isConjugeCad;
	}

	/**
	 * @return the conjugePesq
	 */
	public Discipulos getConjugePesq() {
		return conjugePesq;
	}

	/**
	 * @param conjugePesq the conjugePesq to set
	 */
	public void setConjugePesq(Discipulos conjugePesq) {
		this.conjugePesq = conjugePesq;
	}

	/**
	 * @return the listDiscipulosConjuges
	 */
	public List<Discipulos> getListDiscipulosConjuges() {
		return listDiscipulosConjuges;
	}

	/**
	 * @param listDiscipulosConjuges the listDiscipulosConjuges to set
	 */
	public void setListDiscipulosConjuges(List<Discipulos> listDiscipulosConjuges) {
		this.listDiscipulosConjuges = listDiscipulosConjuges;
	}

	/**
	 * @return the conjugeCadEdit
	 */
	public boolean isConjugeCadEdit() {
		return conjugeCadEdit;
	}

	/**
	 * @param conjugeCadEdit the conjugeCadEdit to set
	 */
	public void setConjugeCadEdit(boolean conjugeCadEdit) {
		this.conjugeCadEdit = conjugeCadEdit;
	}

	/**
	 * @return the listaDiscipulos
	 */
	public List<Discipulos> getListaDiscipulos() {
		return listaDiscipulos;
	}

	/**
	 * @param listaDiscipulos the listaDiscipulos to set
	 */
	public void setListaDiscipulos(List<Discipulos> listaDiscipulos) {
		this.listaDiscipulos = listaDiscipulos;
	}

	public boolean isFotoEdit() {
		return fotoEdit;
	}

	public void setFotoEdit(boolean fotoEdit) {
		this.fotoEdit = fotoEdit;
	}
	
	/**
	 * GET SET ARVORE GERAÇÕES
	 */
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
}
