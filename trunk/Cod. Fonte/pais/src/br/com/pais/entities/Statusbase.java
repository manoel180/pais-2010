package br.com.pais.entities;

// Generated 17/01/2011 11:25:41 by Hibernate Tools 3.4.0.Beta1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Statusbase generated by hbm2java
 */
@Entity
@Table(name = "statusbase", catalog = "wwwpais_sistema")
public class Statusbase implements java.io.Serializable {

	private int stBasCod;
	private String stBasDescricao;
	private Set<Bases> baseses = new HashSet<Bases>(0);

	public Statusbase() {
	}

	public Statusbase(int stBasCod, String stBasDescricao) {
		this.stBasCod = stBasCod;
		this.stBasDescricao = stBasDescricao;
	}

	public Statusbase(int stBasCod, String stBasDescricao, Set<Bases> baseses) {
		this.stBasCod = stBasCod;
		this.stBasDescricao = stBasDescricao;
		this.baseses = baseses;
	}

	@Id
	@Column(name = "stBasCod", unique = true, nullable = false)
	public int getStBasCod() {
		return this.stBasCod;
	}

	public void setStBasCod(int stBasCod) {
		this.stBasCod = stBasCod;
	}

	@Column(name = "stBasDescricao", nullable = false, length = 15)
	public String getStBasDescricao() {
		return this.stBasDescricao;
	}

	public void setStBasDescricao(String stBasDescricao) {
		this.stBasDescricao = stBasDescricao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "statusbase")
	public Set<Bases> getBaseses() {
		return this.baseses;
	}

	public void setBaseses(Set<Bases> baseses) {
		this.baseses = baseses;
	}

}
