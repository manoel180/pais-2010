package br.com.pais.entities;

// Generated 11/05/2011 14:16:49 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Localidade generated by hbm2java
 */
@Entity
@Table(name = "localidade", catalog = "wwwpais_sistema")
public class Localidade implements java.io.Serializable {

	private int locNuSequencial;
	private Estado estado;
	private String locNuSub;
	private String locNo;
	private Set<Bairro> bairros = new HashSet<Bairro>(0);

	public Localidade() {
	}

	public Localidade(int locNuSequencial, Estado estado) {
		this.locNuSequencial = locNuSequencial;
		this.estado = estado;
	}

	public Localidade(int locNuSequencial, Estado estado, String locNuSub,
			String locNo, Set<Bairro> bairros) {
		this.locNuSequencial = locNuSequencial;
		this.estado = estado;
		this.locNuSub = locNuSub;
		this.locNo = locNo;
		this.bairros = bairros;
	}

	@Id
	@Column(name = "LOC_NU_SEQUENCIAL", unique = true, nullable = false)
	public int getLocNuSequencial() {
		return this.locNuSequencial;
	}

	public void setLocNuSequencial(int locNuSequencial) {
		this.locNuSequencial = locNuSequencial;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tbl_estado_UFE_SG", nullable = false)
	public Estado getEstado() {
		return this.estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Column(name = "LOC_NU_SUB", length = 50)
	public String getLocNuSub() {
		return this.locNuSub;
	}

	public void setLocNuSub(String locNuSub) {
		this.locNuSub = locNuSub;
	}

	@Column(name = "LOC_NO", length = 60)
	public String getLocNo() {
		return this.locNo;
	}

	public void setLocNo(String locNo) {
		this.locNo = locNo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "localidade")
	public Set<Bairro> getBairros() {
		return this.bairros;
	}

	public void setBairros(Set<Bairro> bairros) {
		this.bairros = bairros;
	}

}
