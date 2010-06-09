package br.com.pais.entities;

// Generated 14/05/2010 14:52:28 by Hibernate Tools 3.3.0.GA

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

/**
 * Celulas generated by hbm2java
 */
@Entity
@Table(name = "celulas", catalog = "wwwpais_sistema")
public class Celulas implements java.io.Serializable {

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "celCod", column = @Column(name = "celCod", nullable = false)),
			@AttributeOverride(name = "funCod", column = @Column(name = "funCod", nullable = false)),
			@AttributeOverride(name = "disCod", column = @Column(name = "disCod", nullable = false)) })
	private CelulasId id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "celEndereco", nullable = false)
	private Logradouro logradouro;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns( {
			@JoinColumn(name = "funCod", referencedColumnName = "funCod", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "disCod", referencedColumnName = "disCod", nullable = false, insertable = false, updatable = false) })
	private Funeclesiasticasdiscipulos funeclesiasticasdiscipulos;

	@Temporal(TemporalType.TIME)
	@Column(name = "celHorarioReuniao", length = 8)
	private Date celHorarioReuniao;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "celDiaReuniao", length = 10)
	private Date celDiaReuniao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "celulas")
	private Set<Movimento> movimento = new HashSet<Movimento>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "celulas")
	private Set<Telcelulas> telcelulas = new HashSet<Telcelulas>(0);

	/**
	 * @return the id
	 */
	public CelulasId getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(CelulasId id) {
		this.id = id;
	}

	/**
	 * @return the logradouro
	 */
	public Logradouro getLogradouro() {
		return logradouro;
	}

	/**
	 * @param logradouro the logradouro to set
	 */
	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	/**
	 * @return the funeclesiasticasdiscipulos
	 */
	public Funeclesiasticasdiscipulos getFuneclesiasticasdiscipulos() {
		return funeclesiasticasdiscipulos;
	}

	/**
	 * @param funeclesiasticasdiscipulos the funeclesiasticasdiscipulos to set
	 */
	public void setFuneclesiasticasdiscipulos(
			Funeclesiasticasdiscipulos funeclesiasticasdiscipulos) {
		this.funeclesiasticasdiscipulos = funeclesiasticasdiscipulos;
	}

	/**
	 * @return the celHorarioReuniao
	 */
	public Date getCelHorarioReuniao() {
		return celHorarioReuniao;
	}

	/**
	 * @param celHorarioReuniao the celHorarioReuniao to set
	 */
	public void setCelHorarioReuniao(Date celHorarioReuniao) {
		this.celHorarioReuniao = celHorarioReuniao;
	}

	/**
	 * @return the celDiaReuniao
	 */
	public Date getCelDiaReuniao() {
		return celDiaReuniao;
	}

	/**
	 * @param celDiaReuniao the celDiaReuniao to set
	 */
	public void setCelDiaReuniao(Date celDiaReuniao) {
		this.celDiaReuniao = celDiaReuniao;
	}

	/**
	 * @return the movimento
	 */
	public Set<Movimento> getMovimento() {
		return movimento;
	}

	/**
	 * @param movimento the movimento to set
	 */
	public void setMovimento(Set<Movimento> movimento) {
		this.movimento = movimento;
	}

	/**
	 * @return the telcelulas
	 */
	public Set<Telcelulas> getTelcelulas() {
		return telcelulas;
	}

	/**
	 * @param telcelulas the telcelulas to set
	 */
	public void setTelcelulas(Set<Telcelulas> telcelulas) {
		this.telcelulas = telcelulas;
	}

	
}
