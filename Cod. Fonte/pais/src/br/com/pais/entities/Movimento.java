package br.com.pais.entities;

// Generated 24/11/2010 14:37:05 by Hibernate Tools 3.4.0.Beta1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Movimento generated by hbm2java
 */
@Entity
@Table(name = "movimento", catalog = "wwwpais_sistema")
public class Movimento implements java.io.Serializable {

	private int movCod;
	private Celulas celulas;
	private Bases bases;
	private Date movData;
	private Date movHora;
	private String movTipo;
	private Double movValor;
	private String movRecebido;
	private String movProtocolo;
	private String movProtocoloPai;

	private List<Repasse> repasses = new ArrayList<Repasse>();

	public Movimento() {
	}

	public Movimento(int movCod) {
		this.movCod = movCod;
	}

	public Movimento(int movCod, Celulas celulas, Bases bases, Date movData, Date movHora,
			String movTipo, Double movValor, String movRecebido, String movProtocolo,
			String movProtocoloPai,
			List<Repasse> repasses) {
		this.movCod = movCod;
		this.celulas = celulas;
		this.bases = bases;
		this.movData = movData;
		this.movHora = movHora;
		this.movTipo = movTipo;
		this.movValor = movValor;
		this.movRecebido = movRecebido;
		this.movProtocolo = movProtocolo;
		this.movProtocoloPai = movProtocoloPai;
		this.repasses = repasses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "movCod", unique = true, nullable = false)
	public int getMovCod() {
		return this.movCod;
	}

	public void setMovCod(int movCod) {
		this.movCod = movCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "celCod")
	public Celulas getCelulas() {
		return this.celulas;
	}

	public void setCelulas(Celulas celulas) {
		this.celulas = celulas;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "basCod")
	public Bases getBases() {
		return this.bases;
	}

	public void setBases(Bases bases) {
		this.bases = bases;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "movData", length = 10)
	public Date getMovData() {
		return this.movData;
	}

	public void setMovData(Date movData) {
		this.movData = movData;
	}
	
	@Temporal(TemporalType.TIME)
	@Column(name = "movHora")
	public Date getMovHora() {
		return movHora;
	}
	
	public void setMovHora(Date movHora) {
		this.movHora = movHora;
	}

	@Column(name = "movTipo", length = 60)
	public String getMovTipo() {
		return this.movTipo;
	}

	public void setMovTipo(String movTipo) {
		this.movTipo = movTipo;
	}

	@Column(name = "movValor")
	public Double getMovValor() {
		return this.movValor;
	}

	public void setMovValor(Double movValor) {
		this.movValor = movValor;
	}
	
	@Column(name = "movRecebido")
	public String getMovRecebido() {
		return movRecebido;
	}

	public void setMovRecebido(String movRecebido) {
		this.movRecebido = movRecebido;
	}
	
	@Column(name = "movProtocolo")
	public String getMovProtocolo() {
		return movProtocolo;
	}

	public void setMovProtocolo(String movProtocolo) {
		this.movProtocolo = movProtocolo;
	}
	
	@Column(name = "movProtocoloPai")
	public String getMovProtocoloPai() {
		return movProtocoloPai;
	}

	public void setMovProtocoloPai(String movProtocoloPai) {
		this.movProtocoloPai = movProtocoloPai;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "movimento")
	public List<Repasse> getRepasses() {
		return this.repasses;
	}

	public void setRepasses(List<Repasse> repasses) {
		this.repasses = repasses;
	}

}
