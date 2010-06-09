package br.com.pais.entities;

// Generated 14/05/2010 14:52:28 by Hibernate Tools 3.3.0.GA

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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

	
	@Id
	@Column(name = "BAI_NU_SEQUENCIAL", unique = true, nullable = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int baiNuSequencial;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LOC_NU_SEQUENCIAL", nullable = false)
	private Localidade localidade;

	@Column(name = "UFE_SG", nullable = false, length = 2)
	private String ufeSg;
	
	@Column(name = "BAI_No", nullable = false, length = 72)
	private String baiNo;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bairro")
	private Set<Logradouro> logradouros = new HashSet<Logradouro>(0);


	
	public int getBaiNuSequencial() {
		return this.baiNuSequencial;
	}

	public void setBaiNuSequencial(int baiNuSequencial) {
		this.baiNuSequencial = baiNuSequencial;
	}

	
	public Localidade getLocalidade() {
		return this.localidade;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}

	
	public String getUfeSg() {
		return this.ufeSg;
	}

	public void setUfeSg(String ufeSg) {
		this.ufeSg = ufeSg;
	}

	
	public String getBaiNo() {
		return this.baiNo;
	}

	public void setBaiNo(String baiNo) {
		this.baiNo = baiNo;
	}

	/**
	 * @param logradouros the logradouros to set
	 */
	public void setLogradouros(Set<Logradouro> logradouros) {
		this.logradouros = logradouros;
	}

	/**
	 * @return the logradouros
	 */
	public Set<Logradouro> getLogradouros() {
		return logradouros;
	}

}
