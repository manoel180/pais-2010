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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Bases generated by hbm2java
 */
@Entity
@Table(name = "bases", catalog = "wwwpais_sistema", uniqueConstraints = @UniqueConstraint(columnNames = "basNuEndereco"))
public class Bases implements java.io.Serializable {

	private Integer basCod;
	private Discipulos discipulosByLiderGovJusto;
	private Discipulos discipulosByBasDisCod;
	private Logradouro logradouro;
	private Statusbase statusbase;
	private Discipulos discipulosByLiderAcaoSocial;
	private Condicaobase condicaobase;
	private Tipobases tipobases;
	private Date basHorReuniao;
	private String basDtReuniao;
	private Date basDtAbertura;
	private String basNuEndereco;
	private String basEndComplemento;
	private String basTelFixo;
	private String basTelCelular;
	private Set<Celulas> celulases = new HashSet<Celulas>(0);
	private Set<Movimento> movimentos = new HashSet<Movimento>(0);

	public Bases() {
	}

	public Bases(Discipulos discipulosByBasDisCod, Tipobases tipobases) {
		this.discipulosByBasDisCod = discipulosByBasDisCod;
		this.tipobases = tipobases;
	}

	public Bases(Discipulos discipulosByLiderGovJusto,
			Discipulos discipulosByBasDisCod, Logradouro logradouro,
			Statusbase statusbase, Discipulos discipulosByLiderAcaoSocial,
			Condicaobase condicaobase, Tipobases tipobases, Date basHorReuniao,
			String basDtReuniao, Date basDtAbertura, String basNuEndereco,
			String basEndComplemento, String basTelFixo, String basTelCelular,
			Set<Celulas> celulases, Set<Movimento> movimentos) {
		this.discipulosByLiderGovJusto = discipulosByLiderGovJusto;
		this.discipulosByBasDisCod = discipulosByBasDisCod;
		this.logradouro = logradouro;
		this.statusbase = statusbase;
		this.discipulosByLiderAcaoSocial = discipulosByLiderAcaoSocial;
		this.condicaobase = condicaobase;
		this.tipobases = tipobases;
		this.basHorReuniao = basHorReuniao;
		this.basDtReuniao = basDtReuniao;
		this.basDtAbertura = basDtAbertura;
		this.basNuEndereco = basNuEndereco;
		this.basEndComplemento = basEndComplemento;
		this.basTelFixo = basTelFixo;
		this.basTelCelular = basTelCelular;
		this.celulases = celulases;
		this.movimentos = movimentos;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "basCod", unique = true, nullable = false)
	public Integer getBasCod() {
		return this.basCod;
	}

	public void setBasCod(Integer basCod) {
		this.basCod = basCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "liderGovJusto")
	public Discipulos getDiscipulosByLiderGovJusto() {
		return this.discipulosByLiderGovJusto;
	}

	public void setDiscipulosByLiderGovJusto(
			Discipulos discipulosByLiderGovJusto) {
		this.discipulosByLiderGovJusto = discipulosByLiderGovJusto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "basDisCod", nullable = false)
	public Discipulos getDiscipulosByBasDisCod() {
		return this.discipulosByBasDisCod;
	}

	public void setDiscipulosByBasDisCod(Discipulos discipulosByBasDisCod) {
		this.discipulosByBasDisCod = discipulosByBasDisCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "basEndereco")
	public Logradouro getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "basStatus")
	public Statusbase getStatusbase() {
		return this.statusbase;
	}

	public void setStatusbase(Statusbase statusbase) {
		this.statusbase = statusbase;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "liderAcaoSocial")
	public Discipulos getDiscipulosByLiderAcaoSocial() {
		return this.discipulosByLiderAcaoSocial;
	}

	public void setDiscipulosByLiderAcaoSocial(
			Discipulos discipulosByLiderAcaoSocial) {
		this.discipulosByLiderAcaoSocial = discipulosByLiderAcaoSocial;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "basCondicao")
	public Condicaobase getCondicaobase() {
		return this.condicaobase;
	}

	public void setCondicaobase(Condicaobase condicaobase) {
		this.condicaobase = condicaobase;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tpbCod", nullable = false)
	public Tipobases getTipobases() {
		return this.tipobases;
	}

	public void setTipobases(Tipobases tipobases) {
		this.tipobases = tipobases;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "basHorReuniao", length = 8)
	public Date getBasHorReuniao() {
		return this.basHorReuniao;
	}

	public void setBasHorReuniao(Date basHorReuniao) {
		this.basHorReuniao = basHorReuniao;
	}

	@Column(name = "basDtReuniao", length = 13)
	public String getBasDtReuniao() {
		return this.basDtReuniao;
	}

	public void setBasDtReuniao(String basDtReuniao) {
		this.basDtReuniao = basDtReuniao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "basDtAbertura", length = 10)
	public Date getBasDtAbertura() {
		return this.basDtAbertura;
	}

	public void setBasDtAbertura(Date basDtAbertura) {
		this.basDtAbertura = basDtAbertura;
	}

	@Column(name = "basNuEndereco", unique = true, length = 5)
	public String getBasNuEndereco() {
		return this.basNuEndereco;
	}

	public void setBasNuEndereco(String basNuEndereco) {
		this.basNuEndereco = basNuEndereco;
	}

	@Column(name = "basEndComplemento", length = 45)
	public String getBasEndComplemento() {
		return this.basEndComplemento;
	}

	public void setBasEndComplemento(String basEndComplemento) {
		this.basEndComplemento = basEndComplemento;
	}

	@Column(name = "basTelFixo", length = 15)
	public String getBasTelFixo() {
		return this.basTelFixo;
	}

	public void setBasTelFixo(String basTelFixo) {
		this.basTelFixo = basTelFixo;
	}

	@Column(name = "basTelCelular", length = 15)
	public String getBasTelCelular() {
		return this.basTelCelular;
	}

	public void setBasTelCelular(String basTelCelular) {
		this.basTelCelular = basTelCelular;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bases")
	public Set<Celulas> getCelulases() {
		return this.celulases;
	}

	public void setCelulases(Set<Celulas> celulases) {
		this.celulases = celulases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bases")
	public Set<Movimento> getMovimentos() {
		return this.movimentos;
	}

	public void setMovimentos(Set<Movimento> movimentos) {
		this.movimentos = movimentos;
	}

}