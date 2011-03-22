package br.com.pais.classe.nopersistence;

import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Geracoes;

public class ArvoreDiscipulos {
	private Discipulos discipulo;
	private Geracoes geracao;
	
	private boolean mostrarDiscipulo;
	private boolean mostrarGeracao;
	private boolean mostrarGeracao2;
	private boolean mostrarBotoes;
	
	public ArvoreDiscipulos(){
		
	}

	public ArvoreDiscipulos(Discipulos discipulo, Geracoes geracao,
			boolean mostrarDiscipulo, boolean mostrarGeracao,
			boolean mostrarGeracao2, boolean mostrarBotoes) {
		super();
		this.discipulo = discipulo;
		this.geracao = geracao;
		this.mostrarDiscipulo = mostrarDiscipulo;
		this.mostrarGeracao = mostrarGeracao;
		this.mostrarGeracao2 = mostrarGeracao2;
		this.mostrarBotoes = mostrarBotoes;
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

	public boolean isMostrarDiscipulo() {
		return mostrarDiscipulo;
	}

	public void setMostrarDiscipulo(boolean mostrarDiscipulo) {
		this.mostrarDiscipulo = mostrarDiscipulo;
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

	public boolean isMostrarBotoes() {
		return mostrarBotoes;
	}

	public void setMostrarBotoes(boolean mostrarBotoes) {
		this.mostrarBotoes = mostrarBotoes;
	}
}
