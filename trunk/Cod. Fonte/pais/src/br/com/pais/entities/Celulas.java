package br.com.pais.entities;

// Generated 23/01/2011 05:07:33 by Hibernate Tools 3.4.0.Beta1

import static javax.persistence.GenerationType.IDENTITY;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Celulas generated by hbm2java
 */
@Entity
@Table(name = "celulas", catalog = "wwwpais_sistema")
public class Celulas implements java.io.Serializable {

	private Integer celCod;
	private Discipulos discipulos;
	private Bases bases;
	private Logradouro logradouro;
	private Zona zona;
	private Geracoes geracoes;
	private String celNome;
	private Date celHorarioReuniao;
	private String celDiaReuniao;
	private String celNuEndereco;
	private String celEndComplemento;
	private String celStatus;
	private String celTelFixo;
	private String celTelCelular;
	private List<Discipulos> discipuloses;
	private Set<Movimento> movimentos = new HashSet<Movimento>(0);

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "celCod", unique = true, nullable = false)
	public Integer getCelCod() {
		return this.celCod;
	}

	public void setCelCod(Integer celCod) {
		this.celCod = celCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "celDisCod", nullable = false)
	public Discipulos getDiscipulos() {
		return this.discipulos;
	}

	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "basCod")
	public Bases getBases() {
		return this.bases;
	}

	public void setBases(Bases bases) {
		this.bases = bases;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "celEndereco", nullable = false)
	public Logradouro getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "celZona", nullable = false)
	public Zona getZona() {
		return this.zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "celGeracao", nullable = false)
	public Geracoes getGeracoes() {
		return this.geracoes;
	}

	public void setGeracoes(Geracoes geracoes) {
		this.geracoes = geracoes;
	}

	@Column(name = "celNome", length = 45)
	public String getCelNome() {
		return this.celNome;
	}

	public void setCelNome(String celNome) {
		this.celNome = celNome;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "celHorarioReuniao", length = 8)
	public Date getCelHorarioReuniao() {
		return this.celHorarioReuniao;
	}

	public void setCelHorarioReuniao(Date celHorarioReuniao) {
		this.celHorarioReuniao = celHorarioReuniao;
	}

	@Column(name = "celDiaReuniao", length = 13)
	public String getCelDiaReuniao() {
		return this.celDiaReuniao;
	}

	public void setCelDiaReuniao(String celDiaReuniao) {
		this.celDiaReuniao = celDiaReuniao;
	}

	@Column(name = "celNuEndereco", nullable = false, length = 5)
	public String getCelNuEndereco() {
		return this.celNuEndereco;
	}

	public void setCelNuEndereco(String celNuEndereco) {
		this.celNuEndereco = celNuEndereco;
	}

	@Column(name = "celEndComplemento", length = 45)
	public String getCelEndComplemento() {
		return this.celEndComplemento;
	}

	public void setCelEndComplemento(String celEndComplemento) {
		this.celEndComplemento = celEndComplemento;
	}

	@Column(name = "celStatus", length = 8)
	public String getCelStatus() {
		return this.celStatus;
	}

	public void setCelStatus(String celStatus) {
		this.celStatus = celStatus;
	}

	@Column(name = "celTelFixo", length = 15)
	public String getCelTelFixo() {
		return this.celTelFixo;
	}

	public void setCelTelFixo(String celTelFixo) {
		this.celTelFixo = celTelFixo;
	}

	@Column(name = "celTelCelular", length = 15)
	public String getCelTelCelular() {
		return this.celTelCelular;
	}

	public void setCelTelCelular(String celTelCelular) {
		this.celTelCelular = celTelCelular;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "discipuloscelulas", catalog = "wwwpais_sistema", uniqueConstraints = @UniqueConstraint(columnNames = "discipulos"), joinColumns = { @JoinColumn(name = "celulas", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "discipulos", unique = true, nullable = false, updatable = false) })
	public List<Discipulos> getDiscipuloses() {
		return this.discipuloses;
	}

	public void setDiscipuloses(List<Discipulos> discipuloses) {
		this.discipuloses = discipuloses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "celulas")
	public Set<Movimento> getMovimentos() {
		return this.movimentos;
	}

	public void setMovimentos(Set<Movimento> movimentos) {
		this.movimentos = movimentos;
	}

}
