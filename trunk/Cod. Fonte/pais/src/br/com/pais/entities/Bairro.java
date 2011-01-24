package br.com.pais.entities;

// Generated 06/01/2011 15:01:09 by Hibernate Tools 3.4.0.Beta1

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
 * Bairro generated by hbm2java
 */
@Entity
@Table(name = "bairro", catalog = "wwwpais_sistema")
public class Bairro implements java.io.Serializable {

	private int baiNuSequencial;
	private Localidade localidade;
	private String ufeSg;
	private String baiNo;
	private Set<Logradouro> logradouros = new HashSet<Logradouro>(0);

	public Bairro() {
	}

	public Bairro(int baiNuSequencial, Localidade localidade, String ufeSg,
			String baiNo) {
		this.baiNuSequencial = baiNuSequencial;
		this.localidade = localidade;
		this.ufeSg = ufeSg;
		this.baiNo = baiNo;
	}

	public Bairro(int baiNuSequencial, Localidade localidade, String ufeSg,
			String baiNo, Set<Logradouro> logradouros) {
		this.baiNuSequencial = baiNuSequencial;
		this.localidade = localidade;
		this.ufeSg = ufeSg;
		this.baiNo = baiNo;
		this.logradouros = logradouros;
	}

	@Id
	@Column(name = "BAI_NU_SEQUENCIAL", unique = true, nullable = false)
	public int getBaiNuSequencial() {
		return this.baiNuSequencial;
	}

	public void setBaiNuSequencial(int baiNuSequencial) {
		this.baiNuSequencial = baiNuSequencial;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOC_NU_SEQUENCIAL", nullable = false)
	public Localidade getLocalidade() {
		return this.localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	@Column(name = "UFE_SG", nullable = false, length = 2)
	public String getUfeSg() {
		return this.ufeSg;
	}

	public void setUfeSg(String ufeSg) {
		this.ufeSg = ufeSg;
	}

	@Column(name = "BAI_No", nullable = false, length = 72)
	public String getBaiNo() {
		return this.baiNo;
	}

	public void setBaiNo(String baiNo) {
		this.baiNo = baiNo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bairro")
	public Set<Logradouro> getLogradouros() {
		return this.logradouros;
	}

	public void setLogradouros(Set<Logradouro> logradouros) {
		this.logradouros = logradouros;
	}

}
