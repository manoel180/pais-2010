package br.com.pais.domain;

// Generated 27/04/2010 23:53:17 by Hibernate Tools 3.3.0.GA

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Repasse generated by hbm2java
 */
@Entity
@Table(name = "tbl_Repasse", catalog = "pais")
public class Repasse implements java.io.Serializable {

	private RepasseId id;
	private Movimento movimento;
	private Discipulos discipulos;
	private byte resValor;
	private Date resData;
	private String resLocal;

	public Repasse() {
	}

	public Repasse(RepasseId id, Movimento movimento,
			Discipulos discipulos, byte resValor, Date resData) {
		this.id = id;
		this.movimento = movimento;
		this.discipulos = discipulos;
		this.resValor = resValor;
		this.resData = resData;
	}

	public Repasse(RepasseId id, Movimento movimento,
			Discipulos discipulos, byte resValor, Date resData,
			String resLocal) {
		this.id = id;
		this.movimento = movimento;
		this.discipulos = discipulos;
		this.resValor = resValor;
		this.resData = resData;
		this.resLocal = resLocal;
	}

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "movCod", column = @Column(name = "movCod", nullable = false)),
			@AttributeOverride(name = "disCod", column = @Column(name = "disCod", nullable = false)) })
	public RepasseId getId() {
		return this.id;
	}

	public void setId(RepasseId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movCod", nullable = false, insertable = false, updatable = false)
	public Movimento getTblMovimento() {
		return this.movimento;
	}

	public void setTblMovimento(Movimento movimento) {
		this.movimento = movimento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disCod", nullable = false, insertable = false, updatable = false)
	public Discipulos getTblDiscipulos() {
		return this.discipulos;
	}

	public void setTblDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	@Column(name = "resValor", nullable = false, precision = 2, scale = 0)
	public byte getResValor() {
		return this.resValor;
	}

	public void setResValor(byte resValor) {
		this.resValor = resValor;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "resData", nullable = false, length = 10)
	public Date getResData() {
		return this.resData;
	}

	public void setResData(Date resData) {
		this.resData = resData;
	}

	@Column(name = "resLocal", length = 60)
	public String getResLocal() {
		return this.resLocal;
	}

	public void setResLocal(String resLocal) {
		this.resLocal = resLocal;
	}

}
