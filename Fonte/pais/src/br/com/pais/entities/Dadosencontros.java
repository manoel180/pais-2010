package br.com.pais.entities;

// Generated 11/05/2011 14:16:49 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
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
 * Dadosencontros generated by hbm2java
 */
@Entity
@Table(name = "dadosencontros", catalog = "wwwpais_sistema")
public class Dadosencontros implements java.io.Serializable {

	private Integer dadenccod;
	private Encontros encontros;
	private String dadenclocal;
	private char dadencstatus;
	private Date dadenchorario;
	private Date dadencdatainicio;
	private Date dadencdatafim;
	private BigDecimal dadencvalor;
	private Set<Discipulos> discipuloses = new HashSet<Discipulos>(0);
	private Set<Lideres> lidereses = new HashSet<Lideres>(0);
	private Set<Discipulos> discipuloses_1 = new HashSet<Discipulos>(0);
	private Set<Discipulos> discipuloses_2 = new HashSet<Discipulos>(0);
	private Set<Encontrospalestras> encontrospalestrases = new HashSet<Encontrospalestras>(
			0);

	public Dadosencontros() {
	}

	public Dadosencontros(Encontros encontros, String dadenclocal,
			char dadencstatus, Date dadenchorario, Date dadencdatainicio) {
		this.encontros = encontros;
		this.dadenclocal = dadenclocal;
		this.dadencstatus = dadencstatus;
		this.dadenchorario = dadenchorario;
		this.dadencdatainicio = dadencdatainicio;
	}

	public Dadosencontros(Encontros encontros, String dadenclocal,
			char dadencstatus, Date dadenchorario, Date dadencdatainicio,
			Date dadencdatafim, BigDecimal dadencvalor,
			Set<Discipulos> discipuloses, Set<Lideres> lidereses,
			Set<Discipulos> discipuloses_1, Set<Discipulos> discipuloses_2,
			Set<Encontrospalestras> encontrospalestrases) {
		this.encontros = encontros;
		this.dadenclocal = dadenclocal;
		this.dadencstatus = dadencstatus;
		this.dadenchorario = dadenchorario;
		this.dadencdatainicio = dadencdatainicio;
		this.dadencdatafim = dadencdatafim;
		this.dadencvalor = dadencvalor;
		this.discipuloses = discipuloses;
		this.lidereses = lidereses;
		this.discipuloses_1 = discipuloses_1;
		this.discipuloses_2 = discipuloses_2;
		this.encontrospalestrases = encontrospalestrases;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "dadenccod", unique = true, nullable = false)
	public Integer getDadenccod() {
		return this.dadenccod;
	}

	public void setDadenccod(Integer dadenccod) {
		this.dadenccod = dadenccod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "encontros_EncCod", nullable = false)
	public Encontros getEncontros() {
		return this.encontros;
	}

	public void setEncontros(Encontros encontros) {
		this.encontros = encontros;
	}

	@Column(name = "dadenclocal", nullable = false, length = 100)
	public String getDadenclocal() {
		return this.dadenclocal;
	}

	public void setDadenclocal(String dadenclocal) {
		this.dadenclocal = dadenclocal;
	}

	@Column(name = "dadencstatus", nullable = false, length = 1)
	public char getDadencstatus() {
		return this.dadencstatus;
	}

	public void setDadencstatus(char dadencstatus) {
		this.dadencstatus = dadencstatus;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "dadenchorario", nullable = false, length = 8)
	public Date getDadenchorario() {
		return this.dadenchorario;
	}

	public void setDadenchorario(Date dadenchorario) {
		this.dadenchorario = dadenchorario;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dadencdatainicio", nullable = false, length = 10)
	public Date getDadencdatainicio() {
		return this.dadencdatainicio;
	}

	public void setDadencdatainicio(Date dadencdatainicio) {
		this.dadencdatainicio = dadencdatainicio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dadencdatafim", length = 10)
	public Date getDadencdatafim() {
		return this.dadencdatafim;
	}

	public void setDadencdatafim(Date dadencdatafim) {
		this.dadencdatafim = dadencdatafim;
	}

	@Column(name = "dadencvalor", precision = 10)
	public BigDecimal getDadencvalor() {
		return this.dadencvalor;
	}

	public void setDadencvalor(BigDecimal dadencvalor) {
		this.dadencvalor = dadencvalor;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ministra", catalog = "wwwpais_sistema", joinColumns = { @JoinColumn(name = "dadosencontros_dadenccod", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "discipulos_disCod", nullable = false, updatable = false) })
	public Set<Discipulos> getDiscipuloses() {
		return this.discipuloses;
	}

	public void setDiscipuloses(Set<Discipulos> discipuloses) {
		this.discipuloses = discipuloses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dadosencontros")
	public Set<Lideres> getLidereses() {
		return this.lidereses;
	}

	public void setLidereses(Set<Lideres> lidereses) {
		this.lidereses = lidereses;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "inscritos", catalog = "wwwpais_sistema", joinColumns = { @JoinColumn(name = "dadosencontros_dadenccod", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "discipulos_disCod", nullable = false, updatable = false) })
	public Set<Discipulos> getDiscipuloses_1() {
		return this.discipuloses_1;
	}

	public void setDiscipuloses_1(Set<Discipulos> discipuloses_1) {
		this.discipuloses_1 = discipuloses_1;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "dadosencontroses")
	public Set<Discipulos> getDiscipuloses_2() {
		return this.discipuloses_2;
	}

	public void setDiscipuloses_2(Set<Discipulos> discipuloses_2) {
		this.discipuloses_2 = discipuloses_2;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dadosencontros")
	public Set<Encontrospalestras> getEncontrospalestrases() {
		return this.encontrospalestrases;
	}

	public void setEncontrospalestrases(
			Set<Encontrospalestras> encontrospalestrases) {
		this.encontrospalestrases = encontrospalestrases;
	}

}
