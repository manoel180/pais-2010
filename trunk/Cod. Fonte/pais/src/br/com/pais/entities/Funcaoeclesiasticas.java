package br.com.pais.entities;

// Generated 24/11/2010 14:37:05 by Hibernate Tools 3.4.0.Beta1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Funcaoeclesiasticas generated by hbm2java
 */
@Entity
@Table(name = "funcaoeclesiasticas", catalog = "wwwpais_sistema", uniqueConstraints = @UniqueConstraint(columnNames = {
		"funDescricao", "funSexo" }))
public class Funcaoeclesiasticas implements java.io.Serializable {

	private int funCod;
	private String funDescricao;
	private char funSexo;
	private Set<Discipulos> discipuloses = new HashSet<Discipulos>(0);

	public Funcaoeclesiasticas() {
	}

	public Funcaoeclesiasticas(int funCod, String funDescricao, char funSexo) {
		this.funCod = funCod;
		this.funDescricao = funDescricao;
		this.funSexo = funSexo;
	}

	public Funcaoeclesiasticas(int funCod, String funDescricao, char funSexo,
			Set<Discipulos> discipuloses) {
		this.funCod = funCod;
		this.funDescricao = funDescricao;
		this.funSexo = funSexo;
		this.discipuloses = discipuloses;
	}

	@Id
	@Column(name = "funCod", unique = true, nullable = false)
	public int getFunCod() {
		return this.funCod;
	}

	public void setFunCod(int funCod) {
		this.funCod = funCod;
	}

	@Column(name = "funDescricao", nullable = false, length = 60)
	public String getFunDescricao() {
		return this.funDescricao;
	}

	public void setFunDescricao(String funDescricao) {
		this.funDescricao = funDescricao;
	}

	@Column(name = "funSexo", nullable = false, length = 1)
	public char getFunSexo() {
		return this.funSexo;
	}

	public void setFunSexo(char funSexo) {
		this.funSexo = funSexo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "funcaoeclesiasticas")
	public Set<Discipulos> getDiscipuloses() {
		return this.discipuloses;
	}

	public void setDiscipuloses(Set<Discipulos> discipuloses) {
		this.discipuloses = discipuloses;
	}

}
