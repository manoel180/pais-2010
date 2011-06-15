package br.com.pais.entities;

// Generated 28/05/2011 17:51:02 by Hibernate Tools 3.4.0.Beta1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Lideres generated by hbm2java
 */
@Entity
@Table(name = "lideres", catalog = "wwwpais_sistema")
public class Lideres implements java.io.Serializable {

	private Integer liderCod;
	private Dadosencontros dadosencontros;
	private Discipulos discipulos;
	private Encontrostipolideres encontrostipolideres;

	public Lideres() {
	}

	public Lideres(Dadosencontros dadosencontros, Discipulos discipulos,
			Encontrostipolideres encontrostipolideres) {
		this.dadosencontros = dadosencontros;
		this.discipulos = discipulos;
		this.encontrostipolideres = encontrostipolideres;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "liderCod", unique = true, nullable = false)
	public Integer getLiderCod() {
		return this.liderCod;
	}

	public void setLiderCod(Integer liderCod) {
		this.liderCod = liderCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dadosencontros_dadenccod", nullable = false)
	public Dadosencontros getDadosencontros() {
		return this.dadosencontros;
	}

	public void setDadosencontros(Dadosencontros dadosencontros) {
		this.dadosencontros = dadosencontros;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "discipulos_disCod", nullable = false)
	public Discipulos getDiscipulos() {
		return this.discipulos;
	}

	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo", nullable = false)
	public Encontrostipolideres getEncontrostipolideres() {
		return this.encontrostipolideres;
	}

	public void setEncontrostipolideres(
			Encontrostipolideres encontrostipolideres) {
		this.encontrostipolideres = encontrostipolideres;
	}

}