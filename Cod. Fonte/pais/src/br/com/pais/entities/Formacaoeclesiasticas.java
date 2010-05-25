package br.com.pais.entities;

// Generated 14/05/2010 14:52:28 by Hibernate Tools 3.3.0.GA

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Formacaoeclesiasticas generated by hbm2java
 */
@Entity
@Table(name = "formacaoeclesiasticas", catalog = "wwwpais_pais", uniqueConstraints = @UniqueConstraint(columnNames = "forDescricao"))
public class Formacaoeclesiasticas implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "forCod", unique = true, nullable = false)
	private Integer forCod;
	
	@Column(name = "forDescricao", unique = true, nullable = false, length = 60)
	private String forDescricao;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "formacaoeclesiasticases")
	private Set<Discipulos> discipuloses = new HashSet<Discipulos>(0);

	/**
	 * @return the forCod
	 */
	public Integer getForCod() {
		return forCod;
	}

	/**
	 * @param forCod the forCod to set
	 */
	public void setForCod(Integer forCod) {
		this.forCod = forCod;
	}

	/**
	 * @return the forDescricao
	 */
	public String getForDescricao() {
		return forDescricao;
	}

	/**
	 * @param forDescricao the forDescricao to set
	 */
	public void setForDescricao(String forDescricao) {
		this.forDescricao = forDescricao;
	}

	/**
	 * @return the discipuloses
	 */
	public Set<Discipulos> getDiscipuloses() {
		return discipuloses;
	}

	/**
	 * @param discipuloses the discipuloses to set
	 */
	public void setDiscipuloses(Set<Discipulos> discipuloses) {
		this.discipuloses = discipuloses;
	}

}
