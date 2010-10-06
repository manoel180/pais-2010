package br.com.pais.entities;

// Generated 13/09/2010 13:58:41 by Hibernate Tools 3.3.0.GA

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

/**
 * Discipulos generated by hbm2java
 */
@Entity
@Table(name = "discipulos", catalog = "wwwpais_sistema")
public class Discipulos implements java.io.Serializable {

	private Integer disCod;
	private Estadocivil estadocivil;
	private Logradouro logradouro;
	private Formacaoacademica formacaoacademica;
	private Discipulos discipulos;
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
	private String disNuEndereco;
	private String disEndComplemento;
	private Set<Repasse> repasses = new HashSet<Repasse>(0);
	private Set<Formacaoeclesiasticas> formacaoeclesiasticases = new HashSet<Formacaoeclesiasticas>(
			0);
	private Set<Bases> basesesForLiderGovJusto = new HashSet<Bases>(0);
	private Set<Bases> basesesForLiderAcaoSocial = new HashSet<Bases>(0);
	private Set<Discipulos> discipuloses = new HashSet<Discipulos>(0);
	private Set<Teldiscipulos> teldiscipuloses = new HashSet<Teldiscipulos>(0);
	private Set<Participa> participas = new HashSet<Participa>(0);
	private Set<Funeclesiasticasdiscipulos> funeclesiasticasdiscipuloses = new HashSet<Funeclesiasticasdiscipulos>(
			0);
	
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
	@JoinColumn(name = "forCod", nullable = false)
	public Formacaoacademica getFormacaoacademica() {
		return this.formacaoacademica;
	}

	public void setFormacaoacademica(Formacaoacademica formacaoacademica) {
		this.formacaoacademica = formacaoacademica;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disdiscipulador", nullable = false)
	public Discipulos getDiscipulos() {
		return this.discipulos;
	}

	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disGeracao", nullable = false)
	public Geracoes getGeracoes() {
		return this.geracoes;
	}

	public void setGeracoes(Geracoes geracoes) {
		this.geracoes = geracoes;
	}

	@Column(name = "disfoto")
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

	@Column(name = "disCPF", nullable = false, length = 11)
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

	@Column(name = "disTitEleitor", length = 11)
	public String getDisTitEleitor() {
		return this.disTitEleitor;
	}

	public void setDisTitEleitor(String disTitEleitor) {
		this.disTitEleitor = disTitEleitor;
	}

	@Column(name = "disNuEndereco", nullable = false, length = 5)
	public String getDisNuEndereco() {
		return this.disNuEndereco;
	}

	public void setDisNuEndereco(String disNuEndereco) {
		this.disNuEndereco = disNuEndereco;
	}

	@Column(name = "disEndComplemento", length = 45)
	public String getDisEndComplemento() {
		return this.disEndComplemento;
	}

	public void setDisEndComplemento(String disEndComplemento) {
		this.disEndComplemento = disEndComplemento;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulos")
	public Set<Repasse> getRepasses() {
		return this.repasses;
	}

	public void setRepasses(Set<Repasse> repasses) {
		this.repasses = repasses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "foreclesiasticasdiscipulos", catalog = "wwwpais_sistema", joinColumns = { @JoinColumn(name = "disCod", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "forCod", nullable = false, updatable = false) })
	public Set<Formacaoeclesiasticas> getFormacaoeclesiasticases() {
		return this.formacaoeclesiasticases;
	}

	public void setFormacaoeclesiasticases(
			Set<Formacaoeclesiasticas> formacaoeclesiasticases) {
		this.formacaoeclesiasticases = formacaoeclesiasticases;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulosByLiderGovJusto")
	public Set<Bases> getBasesesForLiderGovJusto() {
		return this.basesesForLiderGovJusto;
	}

	public void setBasesesForLiderGovJusto(Set<Bases> basesesForLiderGovJusto) {
		this.basesesForLiderGovJusto = basesesForLiderGovJusto;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulosByLiderAcaoSocial")
	public Set<Bases> getBasesesForLiderAcaoSocial() {
		return this.basesesForLiderAcaoSocial;
	}

	public void setBasesesForLiderAcaoSocial(
			Set<Bases> basesesForLiderAcaoSocial) {
		this.basesesForLiderAcaoSocial = basesesForLiderAcaoSocial;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulos")
	public Set<Discipulos> getDiscipuloses() {
		return this.discipuloses;
	}

	public void setDiscipuloses(Set<Discipulos> discipuloses) {
		this.discipuloses = discipuloses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulos")
	public Set<Teldiscipulos> getTeldiscipuloses() {
		return this.teldiscipuloses;
	}

	public void setTeldiscipuloses(Set<Teldiscipulos> teldiscipuloses) {
		this.teldiscipuloses = teldiscipuloses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulos")
	public Set<Participa> getParticipas() {
		return this.participas;
	}

	public void setParticipas(Set<Participa> participas) {
		this.participas = participas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "discipulos")
	public Set<Funeclesiasticasdiscipulos> getFuneclesiasticasdiscipuloses() {
		return this.funeclesiasticasdiscipuloses;
	}

	public void setFuneclesiasticasdiscipuloses(
			Set<Funeclesiasticasdiscipulos> funeclesiasticasdiscipuloses) {
		this.funeclesiasticasdiscipuloses = funeclesiasticasdiscipuloses;
	}

}