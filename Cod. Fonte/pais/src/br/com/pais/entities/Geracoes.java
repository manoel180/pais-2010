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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Geracoes generated by hbm2java
 */
@Entity
@Table(name = "geracoes", catalog = "wwwpais_sistema", uniqueConstraints = @UniqueConstraint(columnNames = "gerDescricao"))
public class Geracoes implements java.io.Serializable {

	private Integer gerCod;
	private String gerDescricao;
	private Set<Discipulos> discipuloses = new HashSet<Discipulos>(0);

	public Geracoes() {
	}

	public Geracoes(String gerDescricao) {
		this.gerDescricao = gerDescricao;
	}

	public Geracoes(String gerDescricao, Set<Discipulos> discipuloses) {
		this.gerDescricao = gerDescricao;
		this.discipuloses = discipuloses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "gerCod", unique = true, nullable = false)
	public Integer getGerCod() {
		return this.gerCod;
	}

	public void setGerCod(Integer gerCod) {
		this.gerCod = gerCod;
	}

	@Column(name = "gerDescricao", unique = true, nullable = false, length = 60)
	public String getGerDescricao() {
		return this.gerDescricao;
	}

	public void setGerDescricao(String gerDescricao) {
		this.gerDescricao = gerDescricao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "geracoes")
	public Set<Discipulos> getDiscipuloses() {
		return this.discipuloses;
	}

	public void setDiscipuloses(Set<Discipulos> discipuloses) {
		this.discipuloses = discipuloses;
	}

}
