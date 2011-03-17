package br.com.pais.entities;

// Generated 08/02/2011 13:37:18 by Hibernate Tools 3.4.0.Beta1

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
 * Bancos generated by hbm2java
 */
@Entity
@Table(name = "bancos")
public class Bancos implements java.io.Serializable {

	private Integer codBanco;
	private String descBanco;
	private String logoBanco;
	private String ativoSistema;
	private Set<Movimentocheque> movimentocheques = new HashSet<Movimentocheque>(0);

	public Bancos() {
	}

	public Bancos(String descBanco, String logoBanco, String ativoSistema,
			Set<Movimentocheque> movimentocheques) {
		this.descBanco = descBanco;
		this.logoBanco = logoBanco;
		this.ativoSistema = ativoSistema;
		this.movimentocheques = movimentocheques;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "CodBanco", unique = true, nullable = false)
	public Integer getCodBanco() {
		return this.codBanco;
	}

	public void setCodBanco(Integer codBanco) {
		this.codBanco = codBanco;
	}

	@Column(name = "DescBanco")
	public String getDescBanco() {
		return this.descBanco;
	}

	public void setDescBanco(String descBanco) {
		this.descBanco = descBanco;
	}

	@Column(name = "LogoBanco", length = 100)
	public String getLogoBanco() {
		return this.logoBanco;
	}

	public void setLogoBanco(String logoBanco) {
		this.logoBanco = logoBanco;
	}

	@Column(name = "AtivoSistema", length = 1)
	public String getAtivoSistema() {
		return this.ativoSistema;
	}

	public void setAtivoSistema(String ativoSistema) {
		this.ativoSistema = ativoSistema;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bancos")
	public Set<Movimentocheque> getMovimentocheques() {
		return this.movimentocheques;
	}

	public void setMovimentocheques(Set<Movimentocheque> movimentocheques) {
		this.movimentocheques = movimentocheques;
	}

}
