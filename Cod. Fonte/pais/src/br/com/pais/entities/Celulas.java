package br.com.pais.entities;

// Generated 24/11/2010 14:37:05 by Hibernate Tools 3.4.0.Beta1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
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
	private String celNome;
	private Date celHorarioReuniao;
	private String celDiaReuniao;
	private String celNuEndereco;
	private String celEndComplemento;
	private String celStatus;
	private String celTelFixo;
	private String celTelCelular;
	private Set<Discipulos> discipuloses = new HashSet<Discipulos>(0);
	private Set<Movimento> movimentos = new HashSet<Movimento>(0);

	public Celulas() {
	}

	public Celulas(Discipulos discipulos, Logradouro logradouro,
			String celNuEndereco) {
		this.discipulos = discipulos;
		this.logradouro = logradouro;
		this.celNuEndereco = celNuEndereco;
	}

	public Celulas(Discipulos discipulos, Bases bases, Logradouro logradouro,
			String celNome, Date celHorarioReuniao, String celDiaReuniao,
			String celNuEndereco, String celEndComplemento, String celStatus,
			String celTelFixo, String celTelCelular,
			Set<Discipulos> discipuloses, Set<Movimento> movimentos) {
		this.discipulos = discipulos;
		this.bases = bases;
		this.logradouro = logradouro;
		this.celNome = celNome;
		this.celHorarioReuniao = celHorarioReuniao;
		this.celDiaReuniao = celDiaReuniao;
		this.celNuEndereco = celNuEndereco;
		this.celEndComplemento = celEndComplemento;
		this.celStatus = celStatus;
		this.celTelFixo = celTelFixo;
		this.celTelCelular = celTelCelular;
		this.discipuloses = discipuloses;
		this.movimentos = movimentos;
	}

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
	public Set<Discipulos> getDiscipuloses() {
		return this.discipuloses;
	}

	public void setDiscipuloses(Set<Discipulos> discipuloses) {
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