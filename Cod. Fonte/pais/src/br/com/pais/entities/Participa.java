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
 * Participa generated by hbm2java
 */
@Entity
@Table(name = "participa", catalog = "wwwpais_pais")
public class Participa implements java.io.Serializable {

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "disCod", column = @Column(name = "disCod", nullable = false)),
			@AttributeOverride(name = "encCod", column = @Column(name = "EncCod", nullable = false)) })
	private ParticipaId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disCod", nullable = false, insertable = false, updatable = false)
	private Discipulos discipulos;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "parDate", nullable = false, length = 10)
	private Date parDate;

	/**
	 * @return the id
	 */
	public ParticipaId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(ParticipaId id) {
		this.id = id;
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
	 * @return the parDate
	 */
	public Date getParDate() {
		return parDate;
	}

	/**
	 * @param parDate the parDate to set
	 */
	public void setParDate(Date parDate) {
		this.parDate = parDate;
	}


	
}