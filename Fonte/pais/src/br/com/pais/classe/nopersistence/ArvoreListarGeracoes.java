package br.com.pais.classe.nopersistence;

import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Geracoes;
import br.com.pais.entities.Movimento;

public class ArvoreListarGeracoes {
	private Discipulos discipulo;
	private Geracoes geracao;
	private Movimento movimento;
	
	private boolean mostrarFoto;
	private boolean mostrarFoto2;
	private boolean mostrarGeracao;
	private boolean mostrarGeracao2;
	private boolean mostrarDetalhe;
	
	public ArvoreListarGeracoes(){
		
	}

	public ArvoreListarGeracoes(Discipulos discipulo, Geracoes geracao,
			Movimento movimento, boolean mostrarFoto, boolean mostrarFoto2, boolean mostrarGeracao,
			boolean mostrarGeracao2, boolean mostrarDetalhe) {
		super();
		this.discipulo = discipulo;
		this.geracao = geracao;
		this.movimento = movimento;
		this.mostrarFoto = mostrarFoto;
		this.mostrarFoto2 = mostrarFoto2;
		this.mostrarGeracao = mostrarGeracao;
		this.mostrarGeracao2 = mostrarGeracao2;
		this.mostrarDetalhe = mostrarDetalhe;
	}

	public Discipulos getDiscipulo() {
		return discipulo;
	}

	public void setDiscipulo(Discipulos discipulo) {
		this.discipulo = discipulo;
	}

	public Geracoes getGeracao() {
		return geracao;
	}

	public void setGeracao(Geracoes geracao) {
		this.geracao = geracao;
	}

	public Movimento getMovimento() {
		return movimento;
	}

	public void setMovimento(Movimento movimento) {
		this.movimento = movimento;
	}

	public boolean isMostrarFoto() {
		return mostrarFoto;
	}

	public void setMostrarFoto(boolean mostrarFoto) {
		this.mostrarFoto = mostrarFoto;
	}
	
	public boolean isMostrarFoto2() {
		return mostrarFoto2;
	}

	public void setMostrarFoto2(boolean mostrarFoto2) {
		this.mostrarFoto2 = mostrarFoto2;
	}

	public boolean isMostrarGeracao() {
		return mostrarGeracao;
	}

	public void setMostrarGeracao(boolean mostrarGeracao) {
		this.mostrarGeracao = mostrarGeracao;
	}

	public boolean isMostrarGeracao2() {
		return mostrarGeracao2;
	}

	public void setMostrarGeracao2(boolean mostrarGeracao2) {
		this.mostrarGeracao2 = mostrarGeracao2;
	}

	public boolean isMostrarDetalhe() {
		return mostrarDetalhe;
	}

	public void setMostrarDetalhe(boolean mostrarDetalhe) {
		this.mostrarDetalhe = mostrarDetalhe;
	}
}
