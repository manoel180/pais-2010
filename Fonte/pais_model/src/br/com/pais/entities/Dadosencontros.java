package br.com.pais.entities;

// Generated 24/05/2011 10:42:25 by Hibernate Tools 3.4.0.Beta1

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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

import br.com.pais.entities.Discipulosbatismo;

/**
 * Dadosencontros generated by hbm2java
 */
@Entity
@Table(name = "dadosencontros", catalog = "wwwpais_sistema")
public class Dadosencontros implements java.io.Serializable {

	private Integer dadenccod;
	private Encontros encontros;
	private Dadosencontros dadosencontros;
	private Encontrostatus encontrostatus;
	private Discipulos discipulos;
	private String dadenclocal;
	private Date dadenchorario;
	private Date dadencdatainicio;
	private Date dadencdatafim;
	private Integer dadencqtdinscritos;
	private List<Encontrofinanceiro> encontrofinanceiros = new ArrayList<Encontrofinanceiro>(0);
	private List<Ministra> ministras = new ArrayList<Ministra>(0);
	private Set<Dadosencontros> dadosencontroses = new HashSet<Dadosencontros>(0);
	private List<Lideres> lidereses = new ArrayList<Lideres>(0);
	private List<Inscritos> inscritoses = new ArrayList<Inscritos>(0);
	private List<Participa> participas = new ArrayList<Participa>(0);
	private List<Encontrospalestras> encontrospalestrases = new ArrayList<Encontrospalestras>(0);

	public Dadosencontros() {
	}

	public Dadosencontros(Encontros encontros, Encontrostatus encontrostatus,
			Discipulos discipulos, String dadenclocal, Date dadenchorario,
			Date dadencdatainicio) {
		this.encontros = encontros;
		this.encontrostatus = encontrostatus;
		this.discipulos = discipulos;
		this.dadenclocal = dadenclocal;
		this.dadenchorario = dadenchorario;
		this.dadencdatainicio = dadencdatainicio;
	}

	public Dadosencontros(Encontros encontros, Dadosencontros dadosencontros,
			Encontrostatus encontrostatus, Discipulos discipulos,
			String dadenclocal, Date dadenchorario, Date dadencdatainicio,
			Date dadencdatafim, Integer dadencqtdinscritos,
			List<Encontrofinanceiro> encontrofinanceiros,
			List<Ministra> ministras, Set<Dadosencontros> dadosencontroses,
			List<Lideres> lidereses, List<Inscritos> inscritoses,
			List<Participa> participas,
			List<Encontrospalestras> encontrospalestrases) {
		this.encontros = encontros;
		this.dadosencontros = dadosencontros;
		this.encontrostatus = encontrostatus;
		this.discipulos = discipulos;
		this.dadenclocal = dadenclocal;
		this.dadenchorario = dadenchorario;
		this.dadencdatainicio = dadencdatainicio;
		this.dadencdatafim = dadencdatafim;
		this.dadencqtdinscritos = dadencqtdinscritos;
		this.encontrofinanceiros = encontrofinanceiros;
		this.ministras = ministras;
		this.dadosencontroses = dadosencontroses;
		this.lidereses = lidereses;
		this.inscritoses = inscritoses;
		this.participas = participas;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "encontro_pai")
	public Dadosencontros getDadosencontros() {
		return this.dadosencontros;
	}

	public void setDadosencontros(Dadosencontros dadosencontros) {
		this.dadosencontros = dadosencontros;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dadencstatus", nullable = false)
	public Encontrostatus getEncontrostatus() {
		return this.encontrostatus;
	}

	public void setEncontrostatus(Encontrostatus encontrostatus) {
		this.encontrostatus = encontrostatus;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dadencresponsavel", nullable = false)
	public Discipulos getDiscipulos() {
		return this.discipulos;
	}

	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	@Column(name = "dadenclocal", nullable = false, length = 100)
	public String getDadenclocal() {
		return this.dadenclocal;
	}

	public void setDadenclocal(String dadenclocal) {
		this.dadenclocal = dadenclocal;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "dadenchorario", nullable = true, length = 8)
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

	@Column(name = "dadencqtdinscritos")
	public Integer getDadencqtdinscritos() {
		return this.dadencqtdinscritos;
	}

	public void setDadencqtdinscritos(Integer dadencqtdinscritos) {
		this.dadencqtdinscritos = dadencqtdinscritos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dadosencontros")
	public List<Encontrofinanceiro> getEncontrofinanceiros() {
		return this.encontrofinanceiros;
	}

	public void setEncontrofinanceiros(
			List<Encontrofinanceiro> encontrofinanceiros) {
		this.encontrofinanceiros = encontrofinanceiros;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dadosencontros")
	public List<Ministra> getMinistras() {
		return this.ministras;
	}

	public void setMinistras(List<Ministra> ministras) {
		this.ministras = ministras;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dadosencontros")
	public Set<Dadosencontros> getDadosencontroses() {
		return this.dadosencontroses;
	}

	public void setDadosencontroses(Set<Dadosencontros> dadosencontroses) {
		this.dadosencontroses = dadosencontroses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dadosencontros")
	public List<Lideres> getLidereses() {
		return this.lidereses;
	}

	public void setLidereses(List<Lideres> lidereses) {
		this.lidereses = lidereses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dadosencontros")
	public List<Inscritos> getInscritoses() {
		return this.inscritoses;
	}

	public void setInscritoses(List<Inscritos> inscritoses) {
		this.inscritoses = inscritoses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dadosencontros")
	public List<Participa> getParticipas() {
		return this.participas;
	}

	public void setParticipas(List<Participa> participas) {
		this.participas = participas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dadosencontros")
	public List<Encontrospalestras> getEncontrospalestrases() {
		return this.encontrospalestrases;
	}

	public void setEncontrospalestrases(
			List<Encontrospalestras> encontrospalestrases) {
		this.encontrospalestrases = encontrospalestrases;
	}

}