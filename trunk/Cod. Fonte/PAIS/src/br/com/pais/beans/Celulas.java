package br.com.pais.beans;

// Generated 26/04/2010 13:45:45 by Hibernate Tools 3.3.0.GA

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Celulas generated by hbm2java
 */
@Entity
@Table(name = "tbl_celulas", catalog = "pais")
public class Celulas implements java.io.Serializable {

	private CelulasId id;
	private Date celHorarioReuniao;
	private Date celDiaReuniao;
	private int celEndereco;

	public Celulas() {
	}

	public Celulas(CelulasId id, int celEndereco) {
		this.id = id;
		this.celEndereco = celEndereco;
	}

	public Celulas(CelulasId id, Date celHorarioReuniao,
			Date celDiaReuniao, int celEndereco) {
		this.id = id;
		this.celHorarioReuniao = celHorarioReuniao;
		this.celDiaReuniao = celDiaReuniao;
		this.celEndereco = celEndereco;
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "celCod", column = @Column(name = "celCod", nullable = false)),
			@AttributeOverride(name = "funCod", column = @Column(name = "funCod", nullable = false)),
			@AttributeOverride(name = "disCod", column = @Column(name = "disCod", nullable = false)) })
	public CelulasId getId() {
		return this.id;
	}

	public void setId(CelulasId id) {
		this.id = id;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "celHorarioReuniao", length = 8)
	public Date getCelHorarioReuniao() {
		return this.celHorarioReuniao;
	}

	public void setCelHorarioReuniao(Date celHorarioReuniao) {
		this.celHorarioReuniao = celHorarioReuniao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "celDiaReuniao", length = 10)
	public Date getCelDiaReuniao() {
		return this.celDiaReuniao;
	}

	public void setCelDiaReuniao(Date celDiaReuniao) {
		this.celDiaReuniao = celDiaReuniao;
	}

	@Column(name = "celEndereco", nullable = false)
	public int getCelEndereco() {
		return this.celEndereco;
	}

	public void setCelEndereco(int celEndereco) {
		this.celEndereco = celEndereco;
	}

}
