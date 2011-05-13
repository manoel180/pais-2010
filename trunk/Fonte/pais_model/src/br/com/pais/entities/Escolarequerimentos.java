package br.com.pais.entities;

// Generated 11/05/2011 14:16:49 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
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
 * Escolarequerimentos generated by hbm2java
 */
@Entity
@Table(name = "escolarequerimentos", catalog = "wwwpais_sistema")
public class Escolarequerimentos implements java.io.Serializable {

	private Integer reqCod;
	private String reqNom;
	private BigDecimal reqValor;
	private Set<Escolalideresfinanceiroalunos> escolalideresfinanceiroalunoses = new HashSet<Escolalideresfinanceiroalunos>(
			0);

	public Escolarequerimentos() {
	}

	public Escolarequerimentos(String reqNom, BigDecimal reqValor,
			Set<Escolalideresfinanceiroalunos> escolalideresfinanceiroalunoses) {
		this.reqNom = reqNom;
		this.reqValor = reqValor;
		this.escolalideresfinanceiroalunoses = escolalideresfinanceiroalunoses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "reqCod", unique = true, nullable = false)
	public Integer getReqCod() {
		return this.reqCod;
	}

	public void setReqCod(Integer reqCod) {
		this.reqCod = reqCod;
	}

	@Column(name = "reqNom", length = 60)
	public String getReqNom() {
		return this.reqNom;
	}

	public void setReqNom(String reqNom) {
		this.reqNom = reqNom;
	}

	@Column(name = "reqValor", precision = 10)
	public BigDecimal getReqValor() {
		return this.reqValor;
	}

	public void setReqValor(BigDecimal reqValor) {
		this.reqValor = reqValor;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "escolarequerimentos")
	public Set<Escolalideresfinanceiroalunos> getEscolalideresfinanceiroalunoses() {
		return this.escolalideresfinanceiroalunoses;
	}

	public void setEscolalideresfinanceiroalunoses(
			Set<Escolalideresfinanceiroalunos> escolalideresfinanceiroalunoses) {
		this.escolalideresfinanceiroalunoses = escolalideresfinanceiroalunoses;
	}

}
