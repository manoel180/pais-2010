/**
 * 
 */
package br.com.pais.managedbeans;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.com.pais.dao.DiscipuloDao;
import br.com.pais.dao.impl.DiscipuloDaoImp;
import br.com.pais.entities.Discipulos;
import br.com.pais.exception.ValidarCPFException;
import br.com.pais.util.ValidarCPF;





/**
 * @author manoel
 */

@ManagedBean(name = "discipuloBean")
@SessionScoped()
public class DiscipuloBean {

	protected boolean editar = true; 
	
	protected Discipulos discipulos = new Discipulos();
	protected DiscipuloDao discipuloDao = new DiscipuloDaoImp();
	private StreamedContent image;  
	/*
	InputStream stream;// = this.getClass().getResourceAsStream("optimusprime.jpg");  
    image = new DefaultStreamedContent(stream, "image/jpeg");*/
	
	 public void handleFileUpload(FileUploadEvent event)throws IOException  {  
		// InputStream stream = event.getFile().getInputstream();  
		 //InputStream stream = this.getClass().getResourceAsStream(event.getFile().getFileName()); 
		//InputStream stream = this.getClass().getResourceAsStream("/img/ap_rene.jpg");
		 discipulos.setDisfoto(event.getFile().getContentType());
		 //image = new DefaultStreamedContent(event.getFile().getFileName(), "image/jpeg");
	}
	 	 
	public void validarcpf(ActionEvent ev){
		/*public void verificacpf(){*/
		try {
			if(ValidarCPF.validarCPF(discipulos.getDisCPF()) == true){		
				/*ev.getComponent().getId();*/
				
				
			}else{
				
				
			}
		} catch (ValidarCPFException e) {
			// TODO Auto-generated catch block
			
		}
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
	 * @param discipulos the discipulos to set
	 */
	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	/**
	 * @return the image
	 */
	public StreamedContent getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(StreamedContent image) {
		this.image = image;
	}

	
	}
