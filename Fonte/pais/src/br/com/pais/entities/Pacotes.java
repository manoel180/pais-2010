package br.com.pais.entities;

// Generated 11/05/2011 14:16:49 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Pacotes generated by hbm2java
 */
@Entity
@Table(name = "pacotes", catalog = "wwwpais_sistema")
public class Pacotes implements java.io.Serializable {

	private int idpacotes;
	private BigDecimal valor;
	private String descricao;
	private Integer quantidadediscipulos;
	private Set<Financeiroigrejas> financeiroigrejases = new HashSet<Financeiroigrejas>(
			0);

	public Pacotes() {
	}

	public Pacotes(int idpacotes) {
		this.idpacotes = idpacotes;
	}

	public Pacotes(int idpacotes, BigDecimal valor, String descricao,
			Integer quantidadediscipulos,
			Set<Financeiroigrejas> financeiroigrejases) {
		this.idpacotes = idpacotes;
		this.valor = valor;
		this.descricao = descricao;
		this.quantidadediscipulos = quantidadediscipulos;
		this.financeiroigrejases = financeiroigrejases;
	}

	@Id
	@Column(name = "idpacotes", unique = true, nullable = false)
	public int getIdpacotes() {
		return this.idpacotes;
	}

	public void setIdpacotes(int idpacotes) {
		this.idpacotes = idpacotes;
	}

	@Column(name = "valor", precision = 10)
	public BigDecimal getValor() {
		return this.valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	@Column(name = "descricao", length = 100)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "quantidadediscipulos")
	public Integer getQuantidadediscipulos() {
		return this.quantidadediscipulos;
	}

	public void setQuantidadediscipulos(Integer quantidadediscipulos) {
		this.quantidadediscipulos = quantidadediscipulos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pacotes")
	public Set<Financeiroigrejas> getFinanceiroigrejases() {
		return this.financeiroigrejases;
	}

	public void setFinanceiroigrejases(
			Set<Financeiroigrejas> financeiroigrejases) {
		this.financeiroigrejases = financeiroigrejases;
	}

}
