package br.com.pais.entities;

// Generated 06/05/2011 15:49:37 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MinistraId generated by hbm2java
 */
@Embeddable
public class MinistraId implements java.io.Serializable {

	private int discipulosDisCod;
	private int dadosencontrosDadenccod;

	public MinistraId() {
	}

	public MinistraId(int discipulosDisCod, int dadosencontrosDadenccod) {
		this.discipulosDisCod = discipulosDisCod;
		this.dadosencontrosDadenccod = dadosencontrosDadenccod;
	}

	@Column(name = "discipulos_disCod", nullable = false)
	public int getDiscipulosDisCod() {
		return this.discipulosDisCod;
	}

	public void setDiscipulosDisCod(int discipulosDisCod) {
		this.discipulosDisCod = discipulosDisCod;
	}

	@Column(name = "dadosencontros_dadenccod", nullable = false)
	public int getDadosencontrosDadenccod() {
		return this.dadosencontrosDadenccod;
	}

	public void setDadosencontrosDadenccod(int dadosencontrosDadenccod) {
		this.dadosencontrosDadenccod = dadosencontrosDadenccod;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MinistraId))
			return false;
		MinistraId castOther = (MinistraId) other;

		return (this.getDiscipulosDisCod() == castOther.getDiscipulosDisCod())
				&& (this.getDadosencontrosDadenccod() == castOther
						.getDadosencontrosDadenccod());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getDiscipulosDisCod();
		result = 37 * result + this.getDadosencontrosDadenccod();
		return result;
	}

}
