package br.com.pais.domain;

// Generated 27/04/2010 23:53:17 by Hibernate Tools 3.3.0.GA

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * FunEclesiasticasDiscipulosId generated by hbm2java
 */
@Embeddable
public class FunEclesiasticasDiscipulosId implements java.io.Serializable {

	private int funCod;
	private int disCod;

	public FunEclesiasticasDiscipulosId() {
	}

	public FunEclesiasticasDiscipulosId(int funCod, int disCod) {
		this.funCod = funCod;
		this.disCod = disCod;
	}

	@Column(name = "funCod", nullable = false)
	public int getFunCod() {
		return this.funCod;
	}

	public void setFunCod(int funCod) {
		this.funCod = funCod;
	}

	@Column(name = "disCod", nullable = false)
	public int getDisCod() {
		return this.disCod;
	}

	public void setDisCod(int disCod) {
		this.disCod = disCod;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FunEclesiasticasDiscipulosId))
			return false;
		FunEclesiasticasDiscipulosId castOther = (FunEclesiasticasDiscipulosId) other;

		return (this.getFunCod() == castOther.getFunCod())
				&& (this.getDisCod() == castOther.getDisCod());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getFunCod();
		result = 37 * result + this.getDisCod();
		return result;
	}

}
