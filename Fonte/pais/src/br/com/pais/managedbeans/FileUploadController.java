package br.com.pais.managedbeans;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;



public class FileUploadController {
	  private String nomeArquivoSelecionado;
	    private StreamedContent imagem;

	    public StreamedContent getImagem() {
	        return imagem;
	    }

	    public void setImagem(StreamedContent imagem) {
	        this.imagem = imagem;
	    }

	    public String getNomeArquivoSelecionado() {
	        return nomeArquivoSelecionado;
	    }

	    public void setNomeArquivoSelecionado(String nomeArquivoSelecionado) {
	        this.nomeArquivoSelecionado = nomeArquivoSelecionado;
	    }

	    public void fileUploadAction(FileUploadEvent event) {
	        try {
	            setNomeArquivoSelecionado(event.getFile().getFileName());
	            imagem = new DefaultStreamedContent(event.getFile().getInputstream());
	        } catch (IOException ex) {
	            Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
}
