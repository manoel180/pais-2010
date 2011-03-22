package br.com.pais.classe.nopersistence;

public class ArvoreGeracoesNodos {
	private int codigoGeracao;
	private int codigoIndexNodo;
	private int codigoDiscipulador;
	
	public ArvoreGeracoesNodos(){
		
	}

	public ArvoreGeracoesNodos(int codigoGeracao, int codigoIndexNodo,
			int codigoDiscipulador) {
		super();
		this.codigoGeracao = codigoGeracao;
		this.codigoIndexNodo = codigoIndexNodo;
		this.codigoDiscipulador = codigoDiscipulador;
	}

	public int getCodigoGeracao() {
		return codigoGeracao;
	}

	public void setCodigoGeracao(int codigoGeracao) {
		this.codigoGeracao = codigoGeracao;
	}

	public int getCodigoIndexNodo() {
		return codigoIndexNodo;
	}

	public void setCodigoIndexNodo(int codigoIndexNodo) {
		this.codigoIndexNodo = codigoIndexNodo;
	}

	public int getCodigoDiscipulador() {
		return codigoDiscipulador;
	}

	public void setCodigoDiscipulador(int codigoDiscipulador) {
		this.codigoDiscipulador = codigoDiscipulador;
	}
}
