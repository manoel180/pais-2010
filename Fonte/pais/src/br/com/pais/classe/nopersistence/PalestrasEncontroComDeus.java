package br.com.pais.classe.nopersistence;

import br.com.pais.entities.Encontrospalestras;

public class PalestrasEncontroComDeus {
	private Encontrospalestras palestra;
	private boolean atualizada;
	
	public PalestrasEncontroComDeus(){
		
	}

	public PalestrasEncontroComDeus(Encontrospalestras palestra,
			boolean atualizada) {
		super();
		this.palestra = palestra;
		this.atualizada = atualizada;
	}

	public Encontrospalestras getPalestra() {
		return palestra;
	}

	public void setPalestra(Encontrospalestras palestra) {
		this.palestra = palestra;
	}

	public boolean isAtualizada() {
		return atualizada;
	}

	public void setAtualizada(boolean atualizada) {
		this.atualizada = atualizada;
	}
}
