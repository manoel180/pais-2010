package br.com.pais.domain;

// Generated 27/04/2010 23:53:17 by Hibernate Tools 3.3.0.GA

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.stereotype.Repository;

/**
 * Celulas generated by hbm2java
 */

@Entity
@Table(name = "tbl_Celulas", catalog = "pais")
public class Celulas implements java.io.Serializable {

	private CelulasId id;
	private Logradouro logradouro;
	private FunEclesiasticasDiscipulos funEclesiasticasDiscipulos;
	private Date celHorarioReuniao;
	private Date celDiaReuniao;
	private Set<Movimento> movimentos = new HashSet<Movimento>(0);
	private Set<TelCelulas> tblTelCelulases = new HashSet<TelCelulas>(0);

	public Celulas() {
	}

	public Celulas(CelulasId id, Logradouro logradouro,
			FunEclesiasticasDiscipulos funEclesiasticasDiscipulos) {
		this.id = id;
		this.logradouro = logradouro;
		this.funEclesiasticasDiscipulos = funEclesiasticasDiscipulos;
	}

	public Celulas(CelulasId id, Logradouro logradouro,
			FunEclesiasticasDiscipulos funEclesiasticasDiscipulos,
			Date celHorarioReuniao, Date celDiaReuniao,
			Set<Movimento> movimentos, Set<TelCelulas> tblTelCelulases) {
		this.id = id;
		this.logradouro = logradouro;
		this.funEclesiasticasDiscipulos = funEclesiasticasDiscipulos;
		this.celHorarioReuniao = celHorarioReuniao;
		this.celDiaReuniao = celDiaReuniao;
		this.movimentos = movimentos;
		this.tblTelCelulases = tblTelCelulases;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "celEndereco", nullable = false)
	public Logradouro getTblLogradouro() {
		return this.logradouro;
	}

	public void setTblLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns( {
			@JoinColumn(name = "funCod", referencedColumnName = "funCod", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "disCod", referencedColumnName = "disCod", nullable = false, insertable = false, updatable = false) })
	public FunEclesiasticasDiscipulos getTblFunEclesiasticasDiscipulos() {
		return this.funEclesiasticasDiscipulos;
	}

	public void setTblFunEclesiasticasDiscipulos(
			FunEclesiasticasDiscipulos funEclesiasticasDiscipulos) {
		this.funEclesiasticasDiscipulos = funEclesiasticasDiscipulos;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tblCelulas")
	public Set<Movimento> getTblMovimentos() {
		return this.movimentos;
	}

	public void setTblMovimentos(Set<Movimento> movimentos) {
		this.movimentos = movimentos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tblCelulas")
	public Set<TelCelulas> getTblTelCelulases() {
		return this.tblTelCelulases;
	}

	public void setTblTelCelulases(Set<TelCelulas> tblTelCelulases) {
		this.tblTelCelulases = tblTelCelulases;
	}

}