package br.com.pais.entities;

// Generated 24/05/2011 10:42:25 by Hibernate Tools 3.4.0.Beta1

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

/**
 * Encontrostipomovimento generated by hbm2java
 */
@Entity
@Table(name = "encontrostipomovimento", catalog = "wwwpais_sistema")
public class Encontrostipomovimento implements java.io.Serializable {

	private Integer enctipmovCod;
	private Encontros encontros;
	private Encontrosindicadorfinanceiro encontrosindicadorfinanceiro;
	private String enctipmovdescricao;
	private Set<Encontrofinanceiro> encontrofinanceiros = new HashSet<Encontrofinanceiro>(
			0);

	public Encontrostipomovimento() {
	}

	public Encontrostipomovimento(Encontros encontros,
			Encontrosindicadorfinanceiro encontrosindicadorfinanceiro,
			String enctipmovdescricao) {
		this.encontros = encontros;
		this.encontrosindicadorfinanceiro = encontrosindicadorfinanceiro;
		this.enctipmovdescricao = enctipmovdescricao;
	}

	public Encontrostipomovimento(Encontros encontros,
			Encontrosindicadorfinanceiro encontrosindicadorfinanceiro,
			String enctipmovdescricao,
			Set<Encontrofinanceiro> encontrofinanceiros) {
		this.encontros = encontros;
		this.encontrosindicadorfinanceiro = encontrosindicadorfinanceiro;
		this.enctipmovdescricao = enctipmovdescricao;
		this.encontrofinanceiros = encontrofinanceiros;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "enctipmovCod", unique = true, nullable = false)
	public Integer getEnctipmovCod() {
		return this.enctipmovCod;
	}

	public void setEnctipmovCod(Integer enctipmovCod) {
		this.enctipmovCod = enctipmovCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipoencontro", nullable = false)
	public Encontros getEncontros() {
		return this.encontros;
	}

	public void setEncontros(Encontros encontros) {
		this.encontros = encontros;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "indicador", nullable = false)
	public Encontrosindicadorfinanceiro getEncontrosindicadorfinanceiro() {
		return this.encontrosindicadorfinanceiro;
	}

	public void setEncontrosindicadorfinanceiro(
			Encontrosindicadorfinanceiro encontrosindicadorfinanceiro) {
		this.encontrosindicadorfinanceiro = encontrosindicadorfinanceiro;
	}

	@Column(name = "enctipmovdescricao", nullable = false, length = 100)
	public String getEnctipmovdescricao() {
		return this.enctipmovdescricao;
	}

	public void setEnctipmovdescricao(String enctipmovdescricao) {
		this.enctipmovdescricao = enctipmovdescricao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "encontrostipomovimento")
	public Set<Encontrofinanceiro> getEncontrofinanceiros() {
		return this.encontrofinanceiros;
	}

	public void setEncontrofinanceiros(
			Set<Encontrofinanceiro> encontrofinanceiros) {
		this.encontrofinanceiros = encontrofinanceiros;
	}

}
