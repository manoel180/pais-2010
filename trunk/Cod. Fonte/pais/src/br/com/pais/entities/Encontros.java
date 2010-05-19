package br.com.pais.entities;

// Generated 14/05/2010 14:52:28 by Hibernate Tools 3.3.0.GA

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Encontros generated by hbm2java
 */
@Entity
@Table(name = "encontros", catalog = "wwwpais_pais")
public class Encontros implements java.io.Serializable {

	@Id
	@Column(name = "EncCod", unique = true, nullable = false)
	private int encCod;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EncLocal", nullable = false)
	private Logradouro logradouro;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "EncDataRealizacao", length = 10)
	private Date encDataRealizacao;
	
	@Column(name = "EncDescricao")
	private String encDescricao;

	/**
	 * @return the encCod
	 */
	public int getEncCod() {
		return encCod;
	}

	/**
	 * @param encCod the encCod to set
	 */
	public void setEncCod(int encCod) {
		this.encCod = encCod;
	}

	/**
	 * @return the logradouro
	 */
	public Logradouro getLogradouro() {
		return logradouro;
	}

	/**
	 * @param logradouro the logradouro to set
	 */
	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	/**
	 * @return the encDataRealizacao
	 */
	public Date getEncDataRealizacao() {
		return encDataRealizacao;
	}

	/**
	 * @param encDataRealizacao the encDataRealizacao to set
	 */
	public void setEncDataRealizacao(Date encDataRealizacao) {
		this.encDataRealizacao = encDataRealizacao;
	}

	/**
	 * @return the encDescricao
	 */
	public String getEncDescricao() {
		return encDescricao;
	}

	/**
	 * @param encDescricao the encDescricao to set
	 */
	public void setEncDescricao(String encDescricao) {
		this.encDescricao = encDescricao;
	}

	
}