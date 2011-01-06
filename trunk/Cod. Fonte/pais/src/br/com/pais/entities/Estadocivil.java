package br.com.pais.entities;

// Generated 06/01/2011 15:01:09 by Hibernate Tools 3.4.0.Beta1

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
@Table(name = "estadocivil", catalog = "wwwpais_sistema", uniqueConstraints = @UniqueConstraint(columnNames = "Estdescricao"))
public class Estadocivil implements java.io.Serializable {

	private Integer estCod;
	private String estdescricao;
	private Set<Discipulos> discipuloses = new HashSet<Discipulos>(0);

	public Estadocivil() {
	}

	public Estadocivil(String estdescricao, Set<Discipulos> discipuloses) {
		this.estdescricao = estdescricao;
		this.discipuloses = discipuloses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "EstCod", unique = true, nullable = false)
	public Integer getEstCod() {
		return this.estCod;
	}

	public void setEstCod(Integer estCod) {
		this.estCod = estCod;
	}

	@Column(name = "Estdescricao", unique = true, length = 60)
	public String getEstdescricao() {
		return this.estdescricao;
	}

	public void setEstdescricao(String estdescricao) {
		this.estdescricao = estdescricao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "estadocivil")
	public Set<Discipulos> getDiscipuloses() {
		return this.discipuloses;
	}

	public void setDiscipuloses(Set<Discipulos> discipuloses) {
		this.discipuloses = discipuloses;
	}

}
