package br.com.pais.classe.nopersistence;

import br.com.pais.entities.Movimento;

public class MovimentoCheques {
	private Movimento movimento;
	private Double totalRepasseDinheiro = 0.00;
	private Double totalRepasseCheques = 0.00;
	private int qtdCheques = 0;
	
	public MovimentoCheques(){
		
	}

	public MovimentoCheques(Movimento movimento, Double totalRepasseDinheiro,
			Double totalRepasseCheques, int qtdCheques) {
		super();
		this.movimento = movimento;
		this.totalRepasseDinheiro = totalRepasseDinheiro;
		this.totalRepasseCheques = totalRepasseCheques;
		this.qtdCheques = qtdCheques;
	}

	public Movimento getMovimento() {
		return movimento;
	}

	public void setMovimento(Movimento movimento) {
		this.movimento = movimento;
	}

	public Double getTotalRepasseDinheiro() {
		return totalRepasseDinheiro;
	}

	public void setTotalRepasseDinheiro(Double totalRepasseDinheiro) {
		this.totalRepasseDinheiro = totalRepasseDinheiro;
	}

	public Double getTotalRepasseCheques() {
		return totalRepasseCheques;
	}

	public void setTotalRepasseCheques(Double totalRepasseCheques) {
		this.totalRepasseCheques = totalRepasseCheques;
	}

	public int getQtdCheques() {
		return qtdCheques;
	}

	public void setQtdCheques(int qtdCheques) {
		this.qtdCheques = qtdCheques;
	}
}
