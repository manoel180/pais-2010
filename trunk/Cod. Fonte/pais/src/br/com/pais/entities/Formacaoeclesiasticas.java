package br.com.pais.entities;

// Generated 12/09/2010 22:48:01 by Hibernate Tools 3.3.0.GA

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
@Table(name = "formacaoeclesiasticas", catalog = "wwwpais_sistema", uniqueConstraints = @UniqueConstraint(columnNames = "forDescricao"))
public class Formacaoeclesiasticas implements java.io.Serializable {

	private Integer forCod;
	private String forDescricao;
	private Set<Discipulos> discipuloses = new HashSet<Discipulos>(0);

	public Formacaoeclesiasticas() {
	}

	public Formacaoeclesiasticas(String forDescricao) {
		this.forDescricao = forDescricao;
	}

	public Formacaoeclesiasticas(String forDescricao,
			Set<Discipulos> discipuloses) {
		this.forDescricao = forDescricao;
		this.discipuloses = discipuloses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "forCod", unique = true, nullable = false)
	public Integer getForCod() {
		return this.forCod;
	}

	public void setForCod(Integer forCod) {
		this.forCod = forCod;
	}

	@Column(name = "forDescricao", unique = true, nullable = false, length = 60)
	public String getForDescricao() {
		return this.forDescricao;
	}

	public void setForDescricao(String forDescricao) {
		this.forDescricao = forDescricao;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "formacaoeclesiasticases")
	public Set<Discipulos> getDiscipuloses() {
		return this.discipuloses;
	}

	public void setDiscipuloses(Set<Discipulos> discipuloses) {
		this.discipuloses = discipuloses;
	}

}
