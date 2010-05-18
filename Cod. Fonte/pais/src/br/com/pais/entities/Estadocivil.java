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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Estadocivil generated by hbm2java
 */
@Entity
@Table(name = "estadocivil", catalog = "wwwpais_pais", uniqueConstraints = @UniqueConstraint(columnNames = "Estdescricao"))
public class Estadocivil implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "EstCod", unique = true, nullable = false)
	private Integer estCod;
	
	@Column(name = "Estdescricao", unique = true, length = 60)
	private String estdescricao;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estadocivil")
	private Set<Discipulos> discipuloses = new HashSet<Discipulos>(0);

	/**
	 * @return the estCod
	 */
	public Integer getEstCod() {
		return estCod;
	}

	/**
	 * @param estCod the estCod to set
	 */
	public void setEstCod(Integer estCod) {
		this.estCod = estCod;
	}

	/**
	 * @return the estdescricao
	 */
	public String getEstdescricao() {
		return estdescricao;
	}

	/**
	 * @param estdescricao the estdescricao to set
	 */
	public void setEstdescricao(String estdescricao) {
		this.estdescricao = estdescricao;
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