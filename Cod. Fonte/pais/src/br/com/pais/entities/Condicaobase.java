package br.com.pais.entities;

// Generated 23/01/2011 05:07:33 by Hibernate Tools 3.4.0.Beta1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Condicaobase generated by hbm2java
 */
@Entity
@Table(name = "condicaobase", catalog = "wwwpais_sistema")
public class Condicaobase implements java.io.Serializable {

	private int conBasCod;
	private String conBasDescricao;
	private Set<Bases> baseses = new HashSet<Bases>(0);

	public Condicaobase() {
	}

	public Condicaobase(int conBasCod, String conBasDescricao) {
		this.conBasCod = conBasCod;
		this.conBasDescricao = conBasDescricao;
	}

	public Condicaobase(int conBasCod, String conBasDescricao,
			Set<Bases> baseses) {
		this.conBasCod = conBasCod;
		this.conBasDescricao = conBasDescricao;
		this.baseses = baseses;
	}

	@Id
	@Column(name = "conBasCod", unique = true, nullable = false)
	public int getConBasCod() {
		return this.conBasCod;
	}

	public void setConBasCod(int conBasCod) {
		this.conBasCod = conBasCod;
	}

	@Column(name = "conBasDescricao", nullable = false, length = 15)
	public String getConBasDescricao() {
		return this.conBasDescricao;
	}

	public void setConBasDescricao(String conBasDescricao) {
		this.conBasDescricao = conBasDescricao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "condicaobase")
	public Set<Bases> getBaseses() {
		return this.baseses;
	}

	public void setBaseses(Set<Bases> baseses) {
		this.baseses = baseses;
	}

}
