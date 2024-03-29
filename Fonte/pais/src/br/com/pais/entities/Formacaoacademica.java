package br.com.pais.entities;

// Generated 11/05/2011 14:16:49 by Hibernate Tools 3.4.0.CR1

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
 * Formacaoacademica generated by hbm2java
 */
@Entity
@Table(name = "formacaoacademica", catalog = "wwwpais_sistema", uniqueConstraints = @UniqueConstraint(columnNames = "ForAcDescricao"))
public class Formacaoacademica implements java.io.Serializable {

	private Integer forAcCod;
	private String forAcDescricao;
	private Set<Discipulos> discipuloses = new HashSet<Discipulos>(0);

	public Formacaoacademica() {
	}

	public Formacaoacademica(String forAcDescricao) {
		this.forAcDescricao = forAcDescricao;
	}

	public Formacaoacademica(String forAcDescricao, Set<Discipulos> discipuloses) {
		this.forAcDescricao = forAcDescricao;
		this.discipuloses = discipuloses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ForAcCod", unique = true, nullable = false)
	public Integer getForAcCod() {
		return this.forAcCod;
	}

	public void setForAcCod(Integer forAcCod) {
		this.forAcCod = forAcCod;
	}

	@Column(name = "ForAcDescricao", unique = true, nullable = false, length = 60)
	public String getForAcDescricao() {
		return this.forAcDescricao;
	}

	public void setForAcDescricao(String forAcDescricao) {
		this.forAcDescricao = forAcDescricao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "formacaoacademica")
	public Set<Discipulos> getDiscipuloses() {
		return this.discipuloses;
	}

	public void setDiscipuloses(Set<Discipulos> discipuloses) {
		this.discipuloses = discipuloses;
	}

}
