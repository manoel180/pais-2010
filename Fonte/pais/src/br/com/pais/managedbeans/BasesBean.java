package br.com.pais.managedbeans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.TreeNode;

import br.com.pais.dao.BasesDao;
import br.com.pais.dao.CondicaoBasesDao;
import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.FotosBasesDao;
import br.com.pais.dao.LogradouroDao;
import br.com.pais.dao.StatusBasesDao;
import br.com.pais.dao.TipoBasesDao;
import br.com.pais.dao.ZonasDao;
import br.com.pais.dao.impl.BasesDaoImp;
import br.com.pais.dao.impl.CelulaDaoImp;
import br.com.pais.dao.impl.CondicaoBasesDaoImp;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.FotosBasesDaoImp;
import br.com.pais.dao.impl.LogradouroDaoImp;
import br.com.pais.dao.impl.StatusBasesDaoImp;
import br.com.pais.dao.impl.TipoBasesDaoImp;
import br.com.pais.dao.impl.ZonasDaoImp;
import br.com.pais.entities.Bairro;
import br.com.pais.entities.Bases;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Condicaobase;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Estado;
import br.com.pais.entities.Fotosbases;
import br.com.pais.entities.Localidade;
import br.com.pais.entities.Logradouro;
import br.com.pais.entities.Statusbase;
import br.com.pais.entities.Tipobases;
import br.com.pais.entities.Zona;
import br.com.pais.util.ApplicationSecurityManager;

/**
 * @author manoel
 */

public class BasesBean {

	protected boolean editar = true;
	private int cod = 0;
	private UUID uuid;
	
	private Bases bases = new Bases();
	private List<Fotosbases> listFotosbases = new ArrayList<Fotosbases>();
	private List<Fotosbases> listFotosbasesExcluir = new ArrayList<Fotosbases>();
	private Fotosbases fotosbases = new Fotosbases();
	private Discipulos liderBase = new Discipulos();
	private Discipulos liderGovernoJusto = new Discipulos();
	private Discipulos liderAcaoSocial = new Discipulos();
	private TreeNode root;
	private int index;
	private DefaultTreeNode treeNode;
	
	private int opcao;
	private Discipulos selectedLider;
	private ArrayList<TreeNode> nodes = new ArrayList<TreeNode>();
	private ApplicationSecurityManager discipuloSessao = new ApplicationSecurityManager();

	// Objetos Daos
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private BasesDao basesDao = new BasesDaoImp();
	private TipoBasesDao tipoBasesDao = new TipoBasesDaoImp();
	private StatusBasesDao statusBasesDao = new StatusBasesDaoImp();
	private CondicaoBasesDao condicaoBasesDao = new CondicaoBasesDaoImp();
	private FotosBasesDao fotosBasesDao = new FotosBasesDaoImp();
	private ZonasDao zonasDao = new ZonasDaoImp();

	byte[] conteudo;
	String path;

	private DualListModel<Celulas> ListaCelulas = new DualListModel<Celulas>();
	private List<Celulas> source = new ArrayList<Celulas>();
	private List<Celulas> target = new ArrayList<Celulas>();

	private List<Discipulos> listDiscipulos = new ArrayList<Discipulos>();
	private List<Tipobases> listtipobases = new ArrayList<Tipobases>();
	private List<Statusbase> listStatusbases = new ArrayList<Statusbase>();
	private List<Condicaobase> listCondicaobases = new ArrayList<Condicaobase>();
	private List<Zona> listZonas = new ArrayList<Zona>();
	private List<Bases> listaBases = new ArrayList<Bases>();

	private Tipobases tipobases = new Tipobases();
	private Statusbase statusbase = new Statusbase();
	private Condicaobase condicaobase = new Condicaobase();
	private Zona zona = new Zona();

	private String nomeArquivoSelecionado;
	private StreamedContent imagem;

	private Estado estado = new Estado();
	private Localidade cidade = new Localidade();
	private Bairro bairro = new Bairro();
	private Logradouro logradouro = new Logradouro();

	// Objetos Daos
	private LogradouroDao logradouroDao = new LogradouroDaoImp();

	private boolean editarM12 = false;

	// Buscar pelo cep
	public void buscarCEP(AjaxBehaviorEvent event) {
		logradouro = logradouroDao.encontrarPorCEP(getLogradouro().getCep());
		if (logradouro == null) {
			bases.setBasNuEndereco(null);
			bases.setBasEndComplemento(null);
		}
	}

	public void buscarCelulaZona(AjaxBehaviorEvent event) {
		source = new CelulaDaoImp().listarCelulasSemBasePorZona(zona
				.getIdzona());
		target = new ArrayList<Celulas>();
		ListaCelulas = new DualListModel<Celulas>(source, target);
	}

	public void adicionarfoto(ActionEvent actionEvent) {
		// fotosbases.setImagem(imagem);
		listFotosbases.add(fotosbases);
		fotosbases = new Fotosbases();
		InputStream stream = this.getClass().getResourceAsStream(
				"/br/com/pais/util/sem_foto.jpg");
		imagem = new DefaultStreamedContent(stream, "image/jpeg", "sem_foto");
		nomeArquivoSelecionado = "";
	}

	public void removerfoto() {
		if (fotosbases.getCodFoto() == null) {
			listFotosbases.remove(fotosbases);
			fotosbases = new Fotosbases();
		} else {
			listFotosbasesExcluir.add(fotosbases);
			listFotosbases.remove(fotosbases);
			fotosbases = new Fotosbases();
		}

	}

	public String handleFileUpload(FileUploadEvent event) {

		nomeArquivoSelecionado = event.getFile().getFileName();
		fotosbases.setImagem(event.getFile().getContents());
		try {
			imagem = new DefaultStreamedContent(event.getFile()
					.getInputstream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nomeArquivoSelecionado;
	}

	public void selecionarLider() {
		switch (opcao) {
		case 1:
			liderBase = selectedLider;
			break;
		case 2:
			liderGovernoJusto= selectedLider;
			break;
		case 3:
			liderAcaoSocial = selectedLider;
			break;
		}
		opcao = 0;
	}
		
	public void carregarLideres(Discipulos discipulos, TreeNode pai) {
		treeNode = new DefaultTreeNode();
		listDiscipulos =  discipulos.getDiscipuloses();
			for (Discipulos d : listDiscipulos) {
				treeNode = new DefaultTreeNode(d,pai);
				
				//nodes.add(new DefaultTreeNode(d, pai));
				nodes.add(treeNode);
				index = nodes.indexOf(treeNode);
				System.out.println(d.getDisnome());
				if (!discipulos.getDiscipuloses().isEmpty()) {
					carregarLideres(d, (nodes.get(index)));
				}
			}
	}


	public String prepararBases() {
		nodes = new ArrayList<TreeNode>();
		treeNode = new DefaultTreeNode();
		opcao = 0;
		index = 0;
		
		root = new DefaultTreeNode("root", null);
		nodes.add(new DefaultTreeNode(discipuloSessao.getDiscipulos(), root));
		carregarLideres(discipuloSessao.getDiscipulos(), nodes.get(0));
		if(discipuloSessao.getDiscipulos().getDiscipulosByDisConjugecad()!=null ){
			nodes.add(new DefaultTreeNode(discipuloSessao.getDiscipulos().getDiscipulosByDisConjugecad(), root));
			carregarLideres(discipuloSessao.getDiscipulos().getDiscipulosByDisConjugecad(), nodes.get(0));
		}
		
		zona = new Zona();
		zona.setIdzona(1);
		listFotosbases = new ArrayList<Fotosbases>();
		source = new ArrayList<Celulas>();
		source = new CelulaDaoImp().listarCelulasSemBasePorZona(zona
				.getIdzona());
		target = new ArrayList<Celulas>();
		ListaCelulas = new DualListModel<Celulas>(source, target);
		fotosbases = new Fotosbases();
		liderBase = new Discipulos();
		liderAcaoSocial = new Discipulos();
		liderGovernoJusto = new Discipulos();
		condicaobase = new Condicaobase();
		bases = new Bases();

		statusbase = new Statusbase();
		tipobases = new Tipobases();
		logradouro = new Logradouro();

		listDiscipulos = discipuloDao.listarDiscipulos(discipuloSessao
				.getDiscipulos().getDisCod());
		listZonas = zonasDao.todos();
		listCondicaobases = condicaoBasesDao.todos();
		listStatusbases = statusBasesDao.todos();
		listtipobases = tipoBasesDao.todos();

		InputStream stream = this.getClass().getResourceAsStream(
				"/br/com/pais/util/sem_foto.jpg");
		imagem = new DefaultStreamedContent(stream, "image/jpeg", "sem_foto");

		return "/cad/bases.mir";
	}

	public String prepararListarBases() {
		listaBases = new ArrayList<Bases>();
		listaBases.addAll(basesDao.listarBases(discipuloSessao.getDiscipulos()
				.getDisCod()));
		return "/list/bases.mir";
	}

	public String prepararEdicao() {
		nodes = new ArrayList<TreeNode>();
		opcao = 0;
		
		//selectedNode = new DefaultTreeNode();
		root = new DefaultTreeNode("root", null);
		nodes.add(new DefaultTreeNode(discipuloSessao.getDiscipulos(), root));
		carregarLideres(discipuloSessao.getDiscipulos(), nodes.get(0));
		if(discipuloSessao.getDiscipulos().getDiscipulosByDisConjugecad()!=null ){
			nodes.add(new DefaultTreeNode(discipuloSessao.getDiscipulos().getDiscipulosByDisConjugecad(), root));
			carregarLideres(discipuloSessao.getDiscipulos().getDiscipulosByDisConjugecad(), nodes.get(0));
		}
		
		
		fotosbases = new Fotosbases();

		listZonas = zonasDao.todos();
		listDiscipulos = discipuloDao.listarDiscipulos(discipuloSessao
				.getDiscipulos().getDisCod());
		listZonas = zonasDao.todos();
		listCondicaobases = condicaoBasesDao.todos();
		listStatusbases = statusBasesDao.todos();
		listtipobases = tipoBasesDao.todos();
		logradouro = bases.getLogradouro();
		zona = bases.getZona();
		liderBase = bases.getLiderBase();
		liderAcaoSocial = bases.getDiscipulosByLiderAcaoSocial();
		liderGovernoJusto = bases.getDiscipulosByLiderGovJusto();
		tipobases.setTpbCod(bases.getTipobases().getTpbCod());
		statusbase = bases.getStatusbase();
		condicaobase = bases.getCondicaobase();
		listFotosbases = bases.getFotosbaseses();

		target = bases.getCelulases();
		source = new CelulaDaoImp().listarCelulasSemBasePorZona(zona
				.getIdzona());
		ListaCelulas = new DualListModel<Celulas>(source, target);

		return "/editar/bases.mir";

	}

	public String alterar() {
		
		bases.setCondicaobase(condicaobase);
		bases.setStatusbase(statusbase);
		bases.setTipobases(tipobases);
		bases.setZona(zona);

		bases.setLiderBase(liderBase);
		bases.setDiscipulosByBasDisCod(discipuloSessao.getDiscipulos());
		bases.setDiscipulosByLiderAcaoSocial(liderAcaoSocial);
		bases.setDiscipulosByLiderGovJusto(liderGovernoJusto);

		if (logradouro == null) {
			logradouro.setCep(null);

		} else {
			bases.setLogradouro(logradouro);
		}

		if (basesDao.atualizar(bases) == true) {

			// Salvar celulas
			if (ListaCelulas.getTarget().size() == 0) {
				for (Celulas c : ListaCelulas.getSource()) {
					c.setBases(null);
					new CelulaDaoImp().atualizar(c);
				}
			} else {
				for (Celulas c : ListaCelulas.getTarget()) {
					c.setBases(bases);
					new CelulaDaoImp().atualizar(c);
				}
				for (Celulas c : ListaCelulas.getSource()) {
					c.setBases(null);
					new CelulaDaoImp().atualizar(c);
				}

			}

		}

		// Salvar fotos
		for (Fotosbases fotosbase : listFotosbases) {
			if (fotosbase.getCodFoto() == null) {

				String tmp = uuid.randomUUID().toString() + ".jpg";
				fotosbase.setBases(bases);
				fotosbase.setFoto("/fotos/bases/" + tmp);
				salvarFoto(tmp, fotosbase.getImagem());
				fotosBasesDao.salvar(fotosbase);
			}
		}

		// excluir fotos
		if (listFotosbasesExcluir.size() != 0) {
			for (Fotosbases fotosbases : listFotosbasesExcluir) {
				fotosBasesDao.excluir(fotosbases, fotosbases.getCodFoto());
				excluirFoto(fotosbases.getFoto());
			}
		}
		return prepararListarBases();

	}

	public void excluir(ActionEvent event) {

		for (Fotosbases fotosbase : bases.getFotosbaseses()) {
			excluirFoto(fotosbase.getFoto());
		}
		basesDao.excluir(bases, bases.getBasCod());

		listaBases = new ArrayList<Bases>();
		listaBases.addAll(basesDao.listarBases(discipuloSessao.getDiscipulos()
				.getDisCod()));

	}

	public void salvar(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		bases.setCondicaobase(condicaobase);
		bases.setStatusbase(statusbase);
		bases.setTipobases(tipobases);
		bases.setZona(zona);
		
		bases.setLiderBase(liderBase);
		bases.setDiscipulosByBasDisCod(discipuloSessao.getDiscipulos());
		bases.setDiscipulosByLiderAcaoSocial(liderAcaoSocial);
		bases.setDiscipulosByLiderGovJusto(liderGovernoJusto);

		if (logradouro == null) {
			logradouro.setCep(null);
			context.addMessage(null, new FacesMessage("ERRO", " CEP inválido"));
		} else {
			bases.setLogradouro(logradouro);
		}

		if (basesDao.salvar(bases) == (true)) {

			for (Celulas c : ListaCelulas.getTarget()) {
				c.setBases(bases);
				new CelulaDaoImp().atualizar(c);
			}

			for (Fotosbases fotosbase : listFotosbases) {
				String tmp = uuid.randomUUID().toString() + ".jpg";
				fotosbase.setBases(bases);
				fotosbase.setFoto("/fotos/bases/" + tmp);
				salvarFoto(tmp, fotosbase.getImagem());
				fotosBasesDao.salvar(fotosbase);
			}

			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Salvo com sucesso!",
					"Base cadastrada com sucesso!!!"));
			prepararBases();
		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "ERRO!!!",
					"Erro ao cadastrar!"));
		}

	}

	public void salvarFoto(String Foto, byte[] img) {

		try {
			// Para pegar direto o caminho da imagem
			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) faces
					.getExternalContext().getRequest();
			String path = request.getSession().getServletContext()
					.getRealPath("/fotos/bases/");

			// Foto = discipulos.getDisCpf() + ".jpg";
			String caminho = path + "/" + Foto;

			FileOutputStream fos = new FileOutputStream(caminho);

			fos.write(img);

			fos.close();

		} catch (IOException ex) {
			Logger.getLogger(FileUploadController.class.getName()).log(
					Level.SEVERE, null, ex);
		}

	}

	public void excluirFoto(String Foto) {

		// Para pegar direto o caminho da imagem
		FacesContext faces = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) faces
				.getExternalContext().getRequest();
		String path = request.getSession().getServletContext()
				.getRealPath("/fotos/bases/");

		// Foto = discipulos.getDisCpf() + ".jpg";
		String caminho = path + "/" + Foto;
		Foto = Foto.substring(13, Foto.length());
		File file = new File(path, "/" + Foto);
		file.delete();

	}

	/**
	 * GETTERS and SETTERS
	 */

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

		return imagem;
	}

	/**
	 * @param imagem
	 *            the imagem to set
	 */
	public void setImagem(StreamedContent imagem) {
		this.imagem = imagem;
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
	 * @return the editarM12
	 */
	public boolean isEditarM12() {
		return editarM12;
	}

	/**
	 * @param editarM12
	 *            the editarM12 to set
	 */
	public void setEditarM12(boolean editarM12) {
		this.editarM12 = editarM12;
	}

	/**
	 * @return the bases
	 */
	public Bases getBases() {
		return bases;
	}

	/**
	 * @param bases
	 *            the bases to set
	 */
	public void setBases(Bases bases) {
		this.bases = bases;
	}

	/**
	 * @return the liderGovernoJusto
	 */
	public Discipulos getLiderGovernoJusto() {
		return liderGovernoJusto;
	}

	/**
	 * @return the liderAcaoSocial
	 */
	public Discipulos getLiderAcaoSocial() {
		return liderAcaoSocial;
	}

	/**
	 * @return the listtipobases
	 */
	public List<Tipobases> getListtipobases() {
		return listtipobases;
	}

	/**
	 * @return the listStatusbases
	 */
	public List<Statusbase> getListStatusbases() {
		return listStatusbases;
	}

	/**
	 * @return the listCondicaobases
	 */
	public List<Condicaobase> getListCondicaobases() {
		return listCondicaobases;
	}

	/**
	 * @return the tipobases
	 */
	public Tipobases getTipobases() {
		return tipobases;
	}

	/**
	 * @return the statusbase
	 */
	public Statusbase getStatusbase() {
		return statusbase;
	}

	/**
	 * @return the condicaobase
	 */
	public Condicaobase getCondicaobase() {
		return condicaobase;
	}

	/**
	 * @param liderGovernoJusto
	 *            the liderGovernoJusto to set
	 */
	public void setLiderGovernoJusto(Discipulos liderGovernoJusto) {
		this.liderGovernoJusto = liderGovernoJusto;
	}

	/**
	 * @param liderAcaoSocial
	 *            the liderAcaoSocial to set
	 */
	public void setLiderAcaoSocial(Discipulos liderAcaoSocial) {
		this.liderAcaoSocial = liderAcaoSocial;
	}

	/**
	 * @param listtipobases
	 *            the listtipobases to set
	 */
	public void setListtipobases(List<Tipobases> listtipobases) {
		this.listtipobases = listtipobases;
	}

	/**
	 * @param listStatusbases
	 *            the listStatusbases to set
	 */
	public void setListStatusbases(List<Statusbase> listStatusbases) {
		this.listStatusbases = listStatusbases;
	}

	/**
	 * @param listCondicaobases
	 *            the listCondicaobases to set
	 */
	public void setListCondicaobases(List<Condicaobase> listCondicaobases) {
		this.listCondicaobases = listCondicaobases;
	}

	/**
	 * @param tipobases
	 *            the tipobases to set
	 */
	public void setTipobases(Tipobases tipobases) {
		this.tipobases = tipobases;
	}

	/**
	 * @param statusbase
	 *            the statusbase to set
	 */
	public void setStatusbase(Statusbase statusbase) {
		this.statusbase = statusbase;
	}

	/**
	 * @param condicaobase
	 *            the condicaobase to set
	 */
	public void setCondicaobase(Condicaobase condicaobase) {
		this.condicaobase = condicaobase;
	}

	/**
	 * @return the listDiscipulos
	 */
	public List<Discipulos> getListDiscipulos() {
		return listDiscipulos;
	}

	/**
	 * @param listDiscipulos
	 *            the listDiscipulos to set
	 */
	public void setListDiscipulos(List<Discipulos> listDiscipulos) {
		this.listDiscipulos = listDiscipulos;
	}

	/**
	 * @return the listFotosbases
	 */
	public List<Fotosbases> getListFotosbases() {
		return listFotosbases;
	}

	/**
	 * @param listFotosbases
	 *            the listFotosbases to set
	 */
	public void setListFotosbases(List<Fotosbases> listFotosbases) {
		this.listFotosbases = listFotosbases;
	}

	/**
	 * @return the fotosbases
	 */
	public Fotosbases getFotosbases() {
		return fotosbases;
	}

	/**
	 * @param fotosbases
	 *            the fotosbases to set
	 */
	public void setFotosbases(Fotosbases fotosbases) {
		this.fotosbases = fotosbases;
	}

	/**
	 * @return the listZonas
	 */
	public List<Zona> getListZonas() {
		return listZonas;
	}

	/**
	 * @return the zona
	 */
	public Zona getZona() {
		return zona;
	}

	/**
	 * @param listZonas
	 *            the listZonas to set
	 */
	public void setListZonas(List<Zona> listZonas) {
		this.listZonas = listZonas;
	}

	/**
	 * @param zona
	 *            the zona to set
	 */
	public void setZona(Zona zona) {
		this.zona = zona;
	}

	/**
	 * @return the listaBases
	 */
	public List<Bases> getListaBases() {
		return listaBases;
	}

	/**
	 * @param listaBases
	 *            the listaBases to set
	 */
	public void setListaBases(List<Bases> listaBases) {
		this.listaBases = listaBases;
	}

	/**
	 * @return the listaCelulas
	 */
	public DualListModel<Celulas> getListaCelulas() {
		return ListaCelulas;
	}

	/**
	 * @param listaCelulas
	 *            the listaCelulas to set
	 */
	public void setListaCelulas(DualListModel<Celulas> listaCelulas) {
		ListaCelulas = listaCelulas;
	}

	/**
	 * @return the root
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * @param root
	 *            the root to set
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	

	/**
	 * @return the liderBase
	 */
	public Discipulos getLiderBase() {
		return liderBase;
	}

	/**
	 * @param liderBase the liderBase to set
	 */
	public void setLiderBase(Discipulos liderBase) {
		this.liderBase = liderBase;
	}

	/**
	 * @return the selectedLider
	 */
	public Discipulos getSelectedLider() {
		return selectedLider;
	}

	/**
	 * @param selectedLider the selectedLider to set
	 */
	public void setSelectedLider(Discipulos selectedLider) {
		this.selectedLider = selectedLider;
	}

	/**
	 * @return the opcao
	 */
	public int getOpcao() {
		return opcao;
	}

	/**
	 * @param opcao the opcao to set
	 */
	public void setOpcao(int opcao) {
		this.opcao = opcao;
	}

}
