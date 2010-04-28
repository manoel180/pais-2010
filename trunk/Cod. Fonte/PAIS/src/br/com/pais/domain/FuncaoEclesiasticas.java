package br.com.pais.domain;

// Generated 27/04/2010 23:53:17 by Hibernate Tools 3.3.0.GA

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
 * FuncaoEclesiasticas generated by hbm2java
 */
@Entity
@Table(name = "tbl_FuncaoEclesiasticas", catalog = "pais", uniqueConstraints = @UniqueConstraint(columnNames = "funDescricao"))
public class FuncaoEclesiasticas implements java.io.Serializable {

	private int funCod;
	private String funDescricao;
	private Set<FunEclesiasticasDiscipulos> tblFunEclesiasticasDiscipuloses = new HashSet<FunEclesiasticasDiscipulos>(
			0);

	public FuncaoEclesiasticas() {
	}

	public FuncaoEclesiasticas(int funCod) {
		this.funCod = funCod;
	}

	public FuncaoEclesiasticas(int funCod, String funDescricao,
			Set<FunEclesiasticasDiscipulos> tblFunEclesiasticasDiscipuloses) {
		this.funCod = funCod;
		this.funDescricao = funDescricao;
		this.tblFunEclesiasticasDiscipuloses = tblFunEclesiasticasDiscipuloses;
	}

	@Id
	@Column(name = "funCod", unique = true, nullable = false)
	public int getFunCod() {
		return this.funCod;
	}

	public void setFunCod(int funCod) {
		this.funCod = funCod;
	}

	@Column(name = "funDescricao", unique = true, length = 60)
	public String getFunDescricao() {
		return this.funDescricao;
	}

	public void setFunDescricao(String funDescricao) {
		this.funDescricao = funDescricao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tblFuncaoEclesiasticas")
	public Set<FunEclesiasticasDiscipulos> getTblFunEclesiasticasDiscipuloses() {
		return this.tblFunEclesiasticasDiscipuloses;
	}

	public void setTblFunEclesiasticasDiscipuloses(
			Set<FunEclesiasticasDiscipulos> tblFunEclesiasticasDiscipuloses) {
		this.tblFunEclesiasticasDiscipuloses = tblFunEclesiasticasDiscipuloses;
	}

}