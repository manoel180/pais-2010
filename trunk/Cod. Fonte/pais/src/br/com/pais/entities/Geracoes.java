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
 * Geracoes generated by hbm2java
 */
@Entity
@Table(name = "geracoes", catalog = "wwwpais_pais", uniqueConstraints = @UniqueConstraint(columnNames = "gerDescricao"))
public class Geracoes implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "gerCod", unique = true, nullable = false)
	private Integer gerCod;
	
	@Column(name = "gerDescricao", unique = true, nullable = false, length = 60)
	private String gerDescricao;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "geracoes")	
	private Set<Discipulos> discipuloses = new HashSet<Discipulos>(0);

	/**
	 * @return the gerCod
	 */
	public Integer getGerCod() {
		return gerCod;
	}

	/**
	 * @param gerCod the gerCod to set
	 */
	public void setGerCod(Integer gerCod) {
		this.gerCod = gerCod;
	}

	/**
	 * @return the gerDescricao
	 */
	public String getGerDescricao() {
		return gerDescricao;
	}

	/**
	 * @param gerDescricao the gerDescricao to set
	 */
	public void setGerDescricao(String gerDescricao) {
		this.gerDescricao = gerDescricao;
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