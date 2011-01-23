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
 * Discipulos generated by hbm2java
 */
@Entity
@Table(name = "discipulos", catalog = "wwwpais_sistema", uniqueConstraints = @UniqueConstraint(columnNames = "disCPF"))
public class Discipulos implements java.io.Serializable {

	private Integer disCod;
	private Estadocivil estadocivil;
	private Logradouro logradouro;
	private Discipulos discipulos;
	private Funcaoeclesiasticas funcaoeclesiasticas;
	private Formacaoacademica formacaoacademica;
	private Geracoes geracoes;
	private String disfoto;
	private String disemail;
	private String disnome;
	private String dispai;
	private String disconjuge;
	private String dismae;
	private Date disdatanascimento;
	private char dism12;
	private String disSenha;
	private String disCpf;
	private String disRg;
	private String disTitEleitor;
	private Integer disEndNumero;
	private String disEndComplemento;
	private String disTelFixo;
	private String disTelCelular;
	private String disTelComercial;
	private char disSexo;
	private Set<Repasse> repasses = new HashSet<Repasse>(0);
	private List<Formacaoeclesiasticas> formacaoeclesiasticases;
	private Set<Celulas> celulases = new HashSet<Celulas>(0);
	private Set<Celulas> celulases_1 = new HashSet<Celulas>(0);
	private Set<Bases> basesesForLiderGovJusto = new HashSet<Bases>(0);
	private Set<Bases> basesesForBasDisCod = new HashSet<Bases>(0);
	private Set<Bases> basesesForLiderAcaoSocial = new HashSet<Bases>(0);
	private List<Encontros> encontroses;
	private Set<Discipulos> discipuloses = new HashSet<Discipulos>(0);
	private Set<Mensagem> mensagemsForMensDisCodRecebe = new HashSet<Mensagem>(
			0);
	private Set<Mensagem> mensagemsForMensDisCod = new HashSet<Mensagem>(0);

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "disCod", unique = true, nullable = false)
	public Integer getDisCod() {
		return this.disCod;
	}

	public void setDisCod(Integer disCod) {
		this.disCod = disCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disEstCivil", nullable = false)
	public Estadocivil getEstadocivil() {
		return this.estadocivil;
	}

	public void setEstadocivil(Estadocivil estadocivil) {
		this.estadocivil = estadocivil;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disEndereco", nullable = false)
	public Logradouro getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disdiscipulador")
	public Discipulos getDiscipulos() {
		return this.discipulos;
	}

	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disFunCod", nullable = false)
	public Funcaoeclesiasticas getFuncaoeclesiasticas() {
		return this.funcaoeclesiasticas;
	}

	public void setFuncaoeclesiasticas(Funcaoeclesiasticas funcaoeclesiasticas) {
		this.funcaoeclesiasticas = funcaoeclesiasticas;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forCod", nullable = false)
	public Formacaoacademica getFormacaoacademica() {
		return this.formacaoacademica;
	}

	public void setFormacaoacademica(Formacaoacademica formacaoacademica) {
		this.formacaoacademica = formacaoacademica;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disGeracao")
	public Geracoes getGeracoes() {
		return this.geracoes;
	}

	public void setGeracoes(Geracoes geracoes) {
		this.geracoes = geracoes;
	}

	@Column(name = "disfoto", length = 100)
	public String getDisfoto() {
		return this.disfoto;
	}

	public void setDisfoto(String disfoto) {
		this.disfoto = disfoto;
	}

	@Column(name = "disemail", length = 60)
	public String getDisemail() {
		return this.disemail;
	}

	public void setDisemail(String disemail) {
		this.disemail = disemail;
	}

	@Column(name = "disnome", nullable = false, length = 60)
	public String getDisnome() {
		return this.disnome;
	}

	public void setDisnome(String disnome) {
		this.disnome = disnome;
	}

	@Column(name = "dispai", length = 60)
	public String getDispai() {
		return this.dispai;
	}

	public void setDispai(String dispai) {
		this.dispai = dispai;
	}

	@Column(name = "disconjuge", length = 60)
	public String getDisconjuge() {
		return this.disconjuge;
	}

	public void setDisconjuge(String disconjuge) {
		this.disconjuge = disconjuge;
	}

	@Column(name = "dismae", nullable = false, length = 60)
	public String getDismae() {
		return this.dismae;
	}

	public void setDismae(String dismae) {
		this.dismae = dismae;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "disdatanascimento", length = 10)
	public Date getDisdatanascimento() {
		return this.disdatanascimento;
	}

	public void setDisdatanascimento(Date disdatanascimento) {
		this.disdatanascimento = disdatanascimento;
	}

	@Column(name = "dism12", nullable = false, length = 1)
	public char getDism12() {
		return this.dism12;
	}

	public void setDism12(char dism12) {
		this.dism12 = dism12;
	}

	@Column(name = "disSenha", length = 23)
	public String getDisSenha() {
		return this.disSenha;
	}

	public void setDisSenha(String disSenha) {
		this.disSenha = disSenha;
	}

	@Column(name = "disCPF", unique = true, nullable = false, length = 14)
	public String getDisCpf() {
		return this.disCpf;
	}

	public void setDisCpf(String disCpf) {
		this.disCpf = disCpf;
	}

	@Column(name = "disRG", length = 11)
	public String getDisRg() {
		return this.disRg;
	}

	public void setDisRg(String disRg) {
		this.disRg = disRg;
	}

	@Column(name = "disTitEleitor", length = 12)
	public String getDisTitEleitor() {
		return this.disTitEleitor;
	}

	public void setDisTitEleitor(String disTitEleitor) {
		this.disTitEleitor = disTitEleitor;
	}

	@Column(name = "disEndNumero")
	public Integer getDisEndNumero() {
		return this.disEndNumero;
	}

	public void setDisEndNumero(Integer disEndNumero) {
		this.disEndNumero = disEndNumero;
	}

	@Column(name = "disEndComplemento", length = 45)
	public String getDisEndComplemento() {
		return this.disEndComplemento;
	}

	public void setDisEndComplemento(String disEndComplemento) {
		this.disEndComplemento = disEndComplemento;
	}

	@Column(name = "disTelFixo", length = 14)
	public String getDisTelFixo() {
		return this.disTelFixo;
	}

	public void setDisTelFixo(String disTelFixo) {
		this.disTelFixo = disTelFixo;
	}

	@Column(name = "disTelCelular", length = 14)
	public String getDisTelCelular() {
		return this.disTelCelular;
	}

	public void setDisTelCelular(String disTelCelular) {
		this.disTelCelular = disTelCelular;
	}

	@Column(name = "disTelComercial", length = 14)
	public String getDisTelComercial() {
		return this.disTelComercial;
	}

	public void setDisTelComercial(String disTelComercial) {
		this.disTelComercial = disTelComercial;
	}

	@Column(name = "disSexo", nullable = false, length = 1)
	public char getDisSexo() {
		return this.disSexo;
	}

	public void setDisSexo(char disSexo) {
		this.disSexo = disSexo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulos")
	public Set<Repasse> getRepasses() {
		return this.repasses;
	}

	public void setRepasses(Set<Repasse> repasses) {
		this.repasses = repasses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "foreclesiasticasdiscipulos", catalog = "wwwpais_sistema", joinColumns = { @JoinColumn(name = "disCod", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "forEcCod", nullable = false, updatable = false) })
	public List<Formacaoeclesiasticas> getFormacaoeclesiasticases() {
		return this.formacaoeclesiasticases;
	}

	public void setFormacaoeclesiasticases(
			List<Formacaoeclesiasticas> formacaoeclesiasticases) {
		this.formacaoeclesiasticases = formacaoeclesiasticases;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "discipuloscelulas", catalog = "wwwpais_sistema", uniqueConstraints = @UniqueConstraint(columnNames = "discipulos"), joinColumns = { @JoinColumn(name = "discipulos", unique = true, nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "celulas", nullable = false, updatable = false) })
	public Set<Celulas> getCelulases() {
		return this.celulases;
	}

	public void setCelulases(Set<Celulas> celulases) {
		this.celulases = celulases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulos")
	public Set<Celulas> getCelulases_1() {
		return this.celulases_1;
	}

	public void setCelulases_1(Set<Celulas> celulases_1) {
		this.celulases_1 = celulases_1;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulosByLiderGovJusto")
	public Set<Bases> getBasesesForLiderGovJusto() {
		return this.basesesForLiderGovJusto;
	}

	public void setBasesesForLiderGovJusto(Set<Bases> basesesForLiderGovJusto) {
		this.basesesForLiderGovJusto = basesesForLiderGovJusto;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulosByBasDisCod")
	public Set<Bases> getBasesesForBasDisCod() {
		return this.basesesForBasDisCod;
	}

	public void setBasesesForBasDisCod(Set<Bases> basesesForBasDisCod) {
		this.basesesForBasDisCod = basesesForBasDisCod;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulosByLiderAcaoSocial")
	public Set<Bases> getBasesesForLiderAcaoSocial() {
		return this.basesesForLiderAcaoSocial;
	}

	public void setBasesesForLiderAcaoSocial(
			Set<Bases> basesesForLiderAcaoSocial) {
		this.basesesForLiderAcaoSocial = basesesForLiderAcaoSocial;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "participa", catalog = "wwwpais_sistema", joinColumns = { @JoinColumn(name = "disCod", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "EncCod", nullable = false, updatable = false) })
	public List<Encontros> getEncontroses() {
		return this.encontroses;
	}

	public void setEncontroses(List<Encontros> encontroses) {
		this.encontroses = encontroses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulos")
	public Set<Discipulos> getDiscipuloses() {
		return this.discipuloses;
	}

	public void setDiscipuloses(Set<Discipulos> discipuloses) {
		this.discipuloses = discipuloses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulosByMensDisCodRecebe")
	public Set<Mensagem> getMensagemsForMensDisCodRecebe() {
		return this.mensagemsForMensDisCodRecebe;
	}

	public void setMensagemsForMensDisCodRecebe(
			Set<Mensagem> mensagemsForMensDisCodRecebe) {
		this.mensagemsForMensDisCodRecebe = mensagemsForMensDisCodRecebe;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulosByMensDisCod")
	public Set<Mensagem> getMensagemsForMensDisCod() {
		return this.mensagemsForMensDisCod;
	}

	public void setMensagemsForMensDisCod(Set<Mensagem> mensagemsForMensDisCod) {
		this.mensagemsForMensDisCod = mensagemsForMensDisCod;
	}

}
