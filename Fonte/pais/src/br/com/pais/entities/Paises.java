package br.com.pais.entities;

// Generated 11/05/2011 14:16:49 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Paises generated by hbm2java
 */
@Entity
@Table(name = "paises", catalog = "wwwpais_sistema")
public class Paises implements java.io.Serializable {

	private int paiscod;
	private String paisdescricao;
	private Set<Igrejas> igrejases = new HashSet<Igrejas>(0);

	public Paises() {
	}

	public Paises(int paiscod) {
		this.paiscod = paiscod;
	}

	public Paises(int paiscod, String paisdescricao, Set<Igrejas> igrejases) {
		this.paiscod = paiscod;
		this.paisdescricao = paisdescricao;
		this.igrejases = igrejases;
	}

	@Id
	@Column(name = "paiscod", unique = true, nullable = false)
	public int getPaiscod() {
		return this.paiscod;
	}

	public void setPaiscod(int paiscod) {
		this.paiscod = paiscod;
	}

	@Column(name = "paisdescricao", length = 100)
	public String getPaisdescricao() {
		return this.paisdescricao;
	}

	public void setPaisdescricao(String paisdescricao) {
		this.paisdescricao = paisdescricao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paises")
	public Set<Igrejas> getIgrejases() {
		return this.igrejases;
	}

	public void setIgrejases(Set<Igrejas> igrejases) {
		this.igrejases = igrejases;
	}

}
