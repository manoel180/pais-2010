package br.com.pais.managedbeans;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.StreamedContent;

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
import br.com.pais.util.SendEMail;
import br.com.pais.util.ValidarCPF;
import br.com.pais.util.ValidarTituloEleitor;

/**
 * @author manoel
 */

public class DiscipuloBean {

	protected boolean editar = true;

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
	// private List<Encontros> ListaEncontros = new ArrayList<Encontros>();
	private List<Funcaoeclesiasticas> listaFuncaoEclesiasticas = new ArrayList<Funcaoeclesiasticas>();
	private DualListModel<Encontros> ListaEncontros = new DualListModel<Encontros>();
	private DualListModel<Formacaoeclesiasticas> ListaFormacaoEclesiasticas = new DualListModel<Formacaoeclesiasticas>();
	private List<Encontros> source = new ArrayList<Encontros>();
	private List<Encontros> target = new ArrayList<Encontros>();

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
	
	
	
	
	// Buscar pelo cep
	public void buscarCEP(AjaxBehaviorEvent event) {
		// String cep = getLogradouro().getCep().substring(0, 5);
		// cep += getLogradouro().getCep().substring(6, 9);
		logradouro = logradouroDao.encontrarPorCEP(getLogradouro().getCep());
		if (logradouro == null) {
			// logradouro.setCep("");
			discipulos.setDisEndNumero(null);
			discipulos.setDisEndComplemento(null);
		}
		// logradouro.getBairro().;
		// cidade = bairro.getLocalidade();
		// estado = cidade.getEstado();
		// return cep;
	}

	public void isCasado(AjaxBehaviorEvent event) {
		if (estadocivil.getEstCod() == 2) {
			editar = false;
		} else {
			editar = true;
			discipulos.setDisconjuge(null);
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
			setNomeArquivoSelecionado(event.getFile().getFileName());
			imagem = new DefaultStreamedContent(event.getFile()
					.getInputstream());

			// Para pegar direto o caminho da imagem
			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) faces
					.getExternalContext().getRequest();
			String path = request.getSession().getServletContext()
					.getRealPath("/fotos");
			// String webDirPath = urlArquivo.substring(0,
			// urlArquivo.indexOf(webDir)+webDir.length());
			// System.out.println(webDirPath);

			conteudo = event.getFile().getContents();

			String caminho = path + "\\" + discipulos.getDisCpf() + ".jpg";// event.getFile().getFileName();
			// //new
			// ApplicationSecurityManager().getDiscipulos().getDisCpf();//

			/*FileOutputStream fos = new FileOutputStream(caminho);

			fos.write(conteudo);

			fos.close();
*/
		} catch (IOException ex) {
			Logger.getLogger(FileUploadController.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		return nomeArquivoSelecionado;
	}

	public String prepararDiscipulo() {
		listaFuncaoEclesiasticas = funcaoeclesiasticasDao.listarFuncaoPorSexo(
				discipuloSessao.getDiscipulos().getDisSexo(), discipuloSessao
						.getDiscipulos().getFuncaoeclesiasticas().getFunCod());
		editar = false;
		editarSenha = true;
		source = new ArrayList<Encontros>();
		target = new ArrayList<Encontros>();
		sourceFormacaoEclesiasticas = new ArrayList<Formacaoeclesiasticas>();
		targetFormacaoEclesiasticas = new ArrayList<Formacaoeclesiasticas>();
		nomeArquivoSelecionado = null;
		sourceFormacaoEclesiasticas.addAll(new FormacaoeclesiasticasDaoImp()
				.todos());
		source.addAll(new EncontrosDaoImp().todos());

		funcaoeclesiasticas = new Funcaoeclesiasticas();
		geracoes = new Geracoes();

		ListaFormacaoEclesiasticas = new DualListModel<Formacaoeclesiasticas>(
				sourceFormacaoEclesiasticas, targetFormacaoEclesiasticas);
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

	public void salvar(ActionEvent event) {
		FacesContext context = FacesContext.getCurrentInstance();
		if (conteudo == null) {
			discipulos.setDisfoto("/img/sem_foto.png"); // Falta Verificar
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
		discipulos.setFormacaoeclesiasticases(ListaFormacaoEclesiasticas
				.getTarget());
		discipulos.setDiscipulos(discipuloSessao.getDiscipulos());

		discipulos.setEstadocivil(estadocivil);
		discipulos.setFormacaoacademica(formacaoacademica);
		discipulos.setFuncaoeclesiasticas(funcaoeclesiasticas);
		discipulos.setGeracoes(geracoes);

		if (discipuloDao.salvar(discipulos) == (true)) {
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
						discipulos.getDisSenha(), discipulos.getDisCpf());
			} 
		} else {
			context.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "ERRO!!!",
					"Erro ao cadastrar!"));
		}
	}

	public String salvarFoto() {
		String Foto = null;
		try {

			// imagem = new
			// DefaultStreamedContent(event.getFile().getInputstream());

			// Para pegar direto o caminho da imagem
			FacesContext faces = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) faces
					.getExternalContext().getRequest();
			String path = request.getSession().getServletContext()
					.getRealPath("/fotos/");

			// conteudo = event.getFile().getContents();
			Foto = discipulos.getDisCpf() + ".jpg";
			String caminho = path + "/" + Foto;// event.getFile().getFileName();

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
	public void setFormacaoeclesiastica(
			Formacaoeclesiasticas formacaoeclesiastica) {
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
	public void setListaFormacaoEclesiasticas(
			DualListModel<Formacaoeclesiasticas> listaFormacaoEclesiasticas) {
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
	public void setListaFuncaoEclesiasticas(
			List<Funcaoeclesiasticas> listaFuncaoEclesiasticas) {
		this.listaFuncaoEclesiasticas = listaFuncaoEclesiasticas;
	}

}
