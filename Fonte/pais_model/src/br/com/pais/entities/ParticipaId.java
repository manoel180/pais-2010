package br.com.pais.entities;

// Generated 06/05/2011 15:49:37 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * ParticipaId generated by hbm2java
 */
@Embeddable
public class ParticipaId implements java.io.Serializable {

	private int disCod;
	private int dadosencontrosDadenccod;

	public ParticipaId() {
	}

	public ParticipaId(int disCod, int dadosencontrosDadenccod) {
		this.disCod = disCod;
		this.dadosencontrosDadenccod = dadosencontrosDadenccod;
	}

	@Column(name = "disCod", nullable = false)
	public int getDisCod() {
		return this.disCod;
	}

	public void setDisCod(int disCod) {
		this.disCod = disCod;
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
		if (!(other instanceof ParticipaId))
			return false;
		ParticipaId castOther = (ParticipaId) other;

		return (this.getDisCod() == castOther.getDisCod())
				&& (this.getDadosencontrosDadenccod() == castOther
						.getDadosencontrosDadenccod());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getDisCod();
		result = 37 * result + this.getDadosencontrosDadenccod();
		return result;
	}

}