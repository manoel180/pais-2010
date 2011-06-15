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
 * Participa generated by hbm2java
 */
@Entity
@Table(name = "participa", catalog = "wwwpais_sistema")
public class Participa implements java.io.Serializable {

	private Integer participaCod;
	private Discipulos discipulos;
	private Dadosencontros dadosencontros;

	public Participa() {
	}

	public Participa(Discipulos discipulos, Dadosencontros dadosencontros) {
		this.discipulos = discipulos;
		this.dadosencontros = dadosencontros;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "participaCod", unique = true, nullable = false)
	public Integer getParticipaCod() {
		return this.participaCod;
	}

	public void setParticipaCod(Integer participaCod) {
		this.participaCod = participaCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "disCod", nullable = false)
	public Discipulos getDiscipulos() {
		return this.discipulos;
	}

	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dadosencontros_dadenccod", nullable = false)
	public Dadosencontros getDadosencontros() {
		return this.dadosencontros;
	}

	public void setDadosencontros(Dadosencontros dadosencontros) {
		this.dadosencontros = dadosencontros;
	}

}