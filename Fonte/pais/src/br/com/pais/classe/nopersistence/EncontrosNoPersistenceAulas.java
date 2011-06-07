package br.com.pais.classe.nopersistence;

import br.com.pais.entities.Encontrospalestras;

public class EncontrosNoPersistenceAulas {
	private Encontrospalestras palestras;
	private boolean liberada;
	private int aula;
	
	public EncontrosNoPersistenceAulas(){
		
	}

	public EncontrosNoPersistenceAulas(Encontrospalestras palestras,
			boolean liberada, int aula) {
		super();
		this.palestras = palestras;
		this.liberada = liberada;
		this.aula = aula;
	}

	public Encontrospalestras getPalestras() {
		return palestras;
	}

	public void setPalestras(Encontrospalestras palestras) {
		this.palestras = palestras;
	}

	public boolean isLiberada() {
		return liberada;
	}

	public void setLiberada(boolean liberada) {
		this.liberada = liberada;
	}

	public int getAula() {
		return aula;
	}

	public void setAula(int aula) {
		this.aula = aula;
	}
}
