package br.com.pais.fileupload;

import org.primefaces.model.StreamedContent;

public class MensagemAnexoFileUpload {
	private String nomeArquivoSelecionado;
	private StreamedContent streamedContent;
	private byte[] conteudoByte;
	private String fotoTipoArquivo;
	
	public String getNomeArquivoSelecionado() {
		return nomeArquivoSelecionado;
	}
	public void setNomeArquivoSelecionado(String nomeArquivoSelecionado) {
		this.nomeArquivoSelecionado = nomeArquivoSelecionado;
	}
	public StreamedContent getStreamedContent() {
		return streamedContent;
	}
	public void setStreamedContent(StreamedContent streamedContent) {
		this.streamedContent = streamedContent;
	}
	public byte[] getConteudoByte() {
		return conteudoByte;
	}
	public void setConteudoByte(byte[] conteudoByte) {
		this.conteudoByte = conteudoByte;
	}
	public String getFotoTipoArquivo() {
		return fotoTipoArquivo;
	}
	public void setFotoTipoArquivo(String fotoTipoArquivo) {
		this.fotoTipoArquivo = fotoTipoArquivo;
	}
	
}
