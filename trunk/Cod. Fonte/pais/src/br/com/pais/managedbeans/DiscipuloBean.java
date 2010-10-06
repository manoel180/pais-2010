/**
 * 
 */
package br.com.pais.managedbeans;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.FormacaoeclesiasticasDao;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.dao.impl.EncontrosDaoImp;
import br.com.pais.dao.impl.FormacaoeclesiasticasDaoImp;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Encontros;
import br.com.pais.entities.Estadocivil;
import br.com.pais.entities.Formacaoeclesiasticas;
import br.com.pais.entities.Teldiscipulos;
import br.com.pais.exception.ValidarCPFException;
import br.com.pais.exception.ValidarTEException;
import br.com.pais.util.ValidarCPF;
import br.com.pais.util.ValidarTituloEleitor;


/**
 * @author manoel
 */

public class DiscipuloBean {

	protected boolean editar = true;

	private Discipulos discipulos = new Discipulos();
	private Estadocivil estadocivil = new Estadocivil();
	private Formacaoeclesiasticas formacaoeclesiastica = new Formacaoeclesiasticas();
	private Teldiscipulos telefone = new Teldiscipulos();
	
	
	private List<Formacaoeclesiasticas> ListaFormacoesEclesiasticas = new ArrayList<Formacaoeclesiasticas>();
	private List<Encontros> ListaEncontros = new ArrayList<Encontros>();
	
	private Formacaoeclesiasticas[] SelecaoFormacoesEclesiasticas ;
	private Encontros[] SelecaoEncontros;

	private FormacaoeclesiasticasDao formacaoeclesiasticasDao = new FormacaoeclesiasticasDaoImp();	
	private DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private String nomeArquivoSelecionado;
    private StreamedContent imagem;

	public void isCasado() {
		if (estadocivil.getEstCod() == 2) {
			editar = false;
		} else {
			editar = true;
			discipulos.setDisconjuge(null);
		}

	}

	public void validarcpf(ActionEvent ev) {
		try {
			if (ValidarCPF.validarCPF(getDiscipulos().getDisCpf()) == false) {

				discipulos.setDisCpf(null);
			}
		} catch (ValidarCPFException e) {
			// TODO Auto-generated catch block
			discipulos.setDisCpf(null);
		}
	}

	public void validartituloeleitor(ActionEvent ev) {
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
			discipulos.setDisTitEleitor(null);
		}
	}

	 public void fileUploadAction(FileUploadEvent event) {
	        try {
	            setNomeArquivoSelecionado(event.getFile().getFileName());
	            imagem = new DefaultStreamedContent(event.getFile().getInputstream());
	             
	            // Para pegar direto o caminho da imagem
	            FacesContext faces = FacesContext.getCurrentInstance();
	            HttpServletRequest request = (HttpServletRequest) faces.getExternalContext().getRequest();
	            String path = request.getSession().getServletContext().getRealPath("/fotos");  
	                    // String webDirPath = urlArquivo.substring(0, urlArquivo.indexOf(webDir)+webDir.length());  
	                     //System.out.println(webDirPath);  
	                       
	            byte[] conteudo = event.getFile().getContents();

	    		String caminho = path+"\\"+"528.467.172-49";//event.getFile().getFileName(); //new ApplicationSecurityManager().getDiscipulos().getDisCpf();//

	    		FileOutputStream fos = new FileOutputStream(caminho);

	    		fos.write(conteudo);

	    		fos.close();
	    		
	            
	        } catch (IOException ex) {
	            Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
	 
	public String prepararDiscipulo() {
		ListaEncontros = new EncontrosDaoImp().todos();
		ListaFormacoesEclesiasticas = formacaoeclesiasticasDao.todos();
		
		return "/cad/discipulos.mir";

	}

	public void salvar() {
		
		discipuloDao.salvar(discipulos);

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
	 * @param listaFormacoesEclesiasticas the listaFormacoesEclesiasticas to set
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
	 * @param selecaoFormacoesEclesiasticas the selecaoFormacoesEclesiasticas to set
	 */
	public void setSelecaoFormacoesEclesiasticas(
			Formacaoeclesiasticas[] selecaoFormacoesEclesiasticas) {
		SelecaoFormacoesEclesiasticas = selecaoFormacoesEclesiasticas;
	}

	/**
	 * @return the telefone
	 */
	public Teldiscipulos getTelefone() {
		return telefone;
	}

	/**
	 * @param telefone the telefone to set
	 */
	public void setTelefone(Teldiscipulos telefone) {
		this.telefone = telefone;
	}

	/**
	 * @return the nomeArquivoSelecionado
	 */
	public String getNomeArquivoSelecionado() {
		return nomeArquivoSelecionado;
	}

	/**
	 * @param nomeArquivoSelecionado the nomeArquivoSelecionado to set
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
	 * @param imagem the imagem to set
	 */
	public void setImagem(StreamedContent imagem) {
		this.imagem = imagem;
	}

	/**
	 * @return the listaEncontros
	 */
	public List<Encontros> getListaEncontros() {
		return ListaEncontros;
	}

	/**
	 * @param listaEncontros the listaEncontros to set
	 */
	public void setListaEncontros(List<Encontros> listaEncontros) {
		ListaEncontros = listaEncontros;
	}

	/**
	 * @return the selecaoEncontros
	 */
	public Encontros[] getSelecaoEncontros() {
		return SelecaoEncontros;
	}

	/**
	 * @param selecaoEncontros the selecaoEncontros to set
	 */
	public void setSelecaoEncontros(Encontros[] selecaoEncontros) {
		SelecaoEncontros = selecaoEncontros;
	}



}
