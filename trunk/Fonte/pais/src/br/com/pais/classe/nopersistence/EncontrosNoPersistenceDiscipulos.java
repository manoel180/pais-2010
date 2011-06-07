package br.com.pais.classe.nopersistence;

import br.com.pais.entities.Discipulos;

public class EncontrosNoPersistenceDiscipulos {
	private Discipulos discipulo;
	private boolean possuiPreEncontro;
	private boolean possuiBatismoNasAguas;
	private boolean presenteAulaPreEncontro;
	
	public EncontrosNoPersistenceDiscipulos(){
		
	}

	public EncontrosNoPersistenceDiscipulos(Discipulos discipulo,
			boolean possuiPreEncontro, boolean possuiBatismoNasAguas,
			boolean presenteAulaPreEncontro) {
		super();
		this.discipulo = discipulo;
		this.possuiPreEncontro = possuiPreEncontro;
		this.possuiBatismoNasAguas = possuiBatismoNasAguas;
		this.presenteAulaPreEncontro = presenteAulaPreEncontro;
	}

	public Discipulos getDiscipulo() {
		return discipulo;
	}

	public void setDiscipulo(Discipulos discipulo) {
		this.discipulo = discipulo;
	}

	public boolean isPossuiPreEncontro() {
		return possuiPreEncontro;
	}

	public void setPossuiPreEncontro(boolean possuiPreEncontro) {
		this.possuiPreEncontro = possuiPreEncontro;
	}

	public boolean isPossuiBatismoNasAguas() {
		return possuiBatismoNasAguas;
	}

	public void setPossuiBatismoNasAguas(boolean possuiBatismoNasAguas) {
		this.possuiBatismoNasAguas = possuiBatismoNasAguas;
	}

	public boolean isPresenteAulaPreEncontro() {
		return presenteAulaPreEncontro;
	}

	public void setPresenteAulaPreEncontro(boolean presenteAulaPreEncontro) {
		this.presenteAulaPreEncontro = presenteAulaPreEncontro;
	}
}
