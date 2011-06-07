package br.com.pais.entities;

// Generated 11/05/2011 14:16:49 by Hibernate Tools 3.4.0.CR1

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Palestras generated by hbm2java
 */
@Entity
@Table(name = "palestras", catalog = "wwwpais_sistema")
public class Palestras implements java.io.Serializable {

	private Integer palCod;
	private Encontros encontros;
	private String palDescricao;
	private Set<Encontrospalestras> encontrospalestras = new HashSet<Encontrospalestras>(0);

	public Palestras() {
	}

	public Palestras(Encontros encontros) {
		this.encontros = encontros;
	}

	public Palestras(Encontros encontros, String palDescricao,
			Set<Encontrospalestras> encontrospalestras) {
		this.encontros = encontros;
		this.palDescricao = palDescricao;
		this.encontrospalestras = encontrospalestras;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "PalCod", unique = true, nullable = false)
	public Integer getPalCod() {
		return this.palCod;
	}

	public void setPalCod(Integer palCod) {
		this.palCod = palCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "encontros_EncCod", nullable = false)
	public Encontros getEncontros() {
		return this.encontros;
	}

	public void setEncontros(Encontros encontros) {
		this.encontros = encontros;
	}

	@Column(name = "PalDescricao", length = 45)
	public String getPalDescricao() {
		return this.palDescricao;
	}

	public void setPalDescricao(String palDescricao) {
		this.palDescricao = palDescricao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "palestras")
	public Set<Encontrospalestras> getEncontrospalestras() {
		return this.encontrospalestras;
	}

	public void setEncontrospalestras(Set<Encontrospalestras> encontrospalestras) {
		this.encontrospalestras = encontrospalestras;
	}

}
