package br.com.pais.entities;

// Generated 06/05/2011 15:49:37 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Encontrospalestras generated by hbm2java
 */
@Entity
@Table(name = "encontrospalestras")
public class Encontrospalestras implements java.io.Serializable {

	private int palestrasPalCod;
	private Discipulos discipulos;
	private Palestras palestras;
	private Date dataRealizacao;
	private int dadosencontrosDadenccod;
	private Set<Frequencia> frequencias = new HashSet<Frequencia>(0);

	public Encontrospalestras() {
	}

	public Encontrospalestras(Discipulos discipulos, Palestras palestras,
			int dadosencontrosDadenccod) {
		this.discipulos = discipulos;
		this.palestras = palestras;
		this.dadosencontrosDadenccod = dadosencontrosDadenccod;
	}

	public Encontrospalestras(Discipulos discipulos, Palestras palestras,
			Date dataRealizacao, int dadosencontrosDadenccod,
			Set<Frequencia> frequencias) {
		this.discipulos = discipulos;
		this.palestras = palestras;
		this.dataRealizacao = dataRealizacao;
		this.dadosencontrosDadenccod = dadosencontrosDadenccod;
		this.frequencias = frequencias;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "palestras"))
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "Palestras_PalCod", unique = true, nullable = false)
	public int getPalestrasPalCod() {
		return this.palestrasPalCod;
	}

	public void setPalestrasPalCod(int palestrasPalCod) {
		this.palestrasPalCod = palestrasPalCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "discipulos_disCod", nullable = false)
	public Discipulos getDiscipulos() {
		return this.discipulos;
	}

	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Palestras getPalestras() {
		return this.palestras;
	}

	public void setPalestras(Palestras palestras) {
		this.palestras = palestras;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "dataRealizacao", length = 10)
	public Date getDataRealizacao() {
		return this.dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	@Column(name = "dadosencontros_dadenccod", nullable = false)
	public int getDadosencontrosDadenccod() {
		return this.dadosencontrosDadenccod;
	}

	public void setDadosencontrosDadenccod(int dadosencontrosDadenccod) {
		this.dadosencontrosDadenccod = dadosencontrosDadenccod;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "encontrospalestras")
	public Set<Frequencia> getFrequencias() {
		return this.frequencias;
	}

	public void setFrequencias(Set<Frequencia> frequencias) {
		this.frequencias = frequencias;
	}

}
