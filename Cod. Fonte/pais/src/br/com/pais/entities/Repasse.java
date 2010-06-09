package br.com.pais.entities;

// Generated 14/05/2010 14:52:28 by Hibernate Tools 3.3.0.GA

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
@Table(name = "repasse")
public class Repasse implements java.io.Serializable {

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "movCod", column = @Column(name = "movCod", nullable = false)),
			@AttributeOverride(name = "disCod", column = @Column(name = "disCod", nullable = false)) })
	private RepasseId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movCod", nullable = false, insertable = false, updatable = false)
	private Movimento movimento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disCod", nullable = false, insertable = false, updatable = false)
	private Discipulos discipulos;
	
	@Column(name = "resValor", nullable = false, precision = 2, scale = 0)
	private byte resValor;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "resData", nullable = false, length = 10)
	private Date resData;
	
	@Column(name = "resLocal", length = 60)
	private String resLocal;

	/**
	 * @return the id
	 */
	public RepasseId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(RepasseId id) {
		this.id = id;
	}

	/**
	 * @return the movimento
	 */
	public Movimento getMovimento() {
		return movimento;
	}

	/**
	 * @param movimento the movimento to set
	 */
	public void setMovimento(Movimento movimento) {
		this.movimento = movimento;
	}

	/**
	 * @return the discipulos
	 */
	public Discipulos getDiscipulos() {
		return discipulos;
	}

	/**
	 * @param discipulos the discipulos to set
	 */
	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	/**
	 * @return the resValor
	 */
	public byte getResValor() {
		return resValor;
	}

	/**
	 * @param resValor the resValor to set
	 */
	public void setResValor(byte resValor) {
		this.resValor = resValor;
	}

	/**
	 * @return the resData
	 */
	public Date getResData() {
		return resData;
	}

	/**
	 * @param resData the resData to set
	 */
	public void setResData(Date resData) {
		this.resData = resData;
	}

	/**
	 * @return the resLocal
	 */
	public String getResLocal() {
		return resLocal;
	}

	/**
	 * @param resLocal the resLocal to set
	 */
	public void setResLocal(String resLocal) {
		this.resLocal = resLocal;
	}

	
}
