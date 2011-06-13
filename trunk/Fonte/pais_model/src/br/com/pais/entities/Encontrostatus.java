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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Encontrostatus generated by hbm2java
 */
@Entity
@Table(name = "encontrostatus", catalog = "wwwpais_sistema")
public class Encontrostatus implements java.io.Serializable {

	private Integer encstacod;
	private String encstadescricao;
	private Set<Dadosencontros> dadosencontroses = new HashSet<Dadosencontros>(
			0);

	public Encontrostatus() {
	}

	public Encontrostatus(String encstadescricao,
			Set<Dadosencontros> dadosencontroses) {
		this.encstadescricao = encstadescricao;
		this.dadosencontroses = dadosencontroses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "encstacod", unique = true, nullable = false)
	public Integer getEncstacod() {
		return this.encstacod;
	}

	public void setEncstacod(Integer encstacod) {
		this.encstacod = encstacod;
	}

	@Column(name = "encstadescricao", length = 45)
	public String getEncstadescricao() {
		return this.encstadescricao;
	}

	public void setEncstadescricao(String encstadescricao) {
		this.encstadescricao = encstadescricao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "encontrostatus")
	public Set<Dadosencontros> getDadosencontroses() {
		return this.dadosencontroses;
	}

	public void setDadosencontroses(Set<Dadosencontros> dadosencontroses) {
		this.dadosencontroses = dadosencontroses;
	}

}