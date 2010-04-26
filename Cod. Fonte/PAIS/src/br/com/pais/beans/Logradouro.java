package br.com.pais.beans;

// Generated 26/04/2010 13:44:43 by Hibernate Tools 3.3.0.GA

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Logradouro generated by hbm2java
 */
@Entity
@Table(name = "tbl_logradouro", catalog = "pais")
public class Logradouro implements java.io.Serializable {

	private int logCod;
	private Bairro tblBairro;
	private String logEstCod;
	private int logLocCod;
	private String logNo;
	private String logNome;
	private String logCep;
	private String logComplemento;
	private String logTipoLogradouro;
	private String logStatusTipoLog;
	private String logKeyDne;
	private String logCep2;
	private Set tblEncontroses = new HashSet(0);
	private Set tblDiscipuloses = new HashSet(0);

	public Logradouro() {
	}

	public Logradouro(int logCod, Bairro tblBairro, String logEstCod,
			int logLocCod, String logNo, String logNome, String logCep,
			String logComplemento, String logTipoLogradouro,
			String logStatusTipoLog, String logKeyDne, String logCep2) {
		this.logCod = logCod;
		this.tblBairro = tblBairro;
		this.logEstCod = logEstCod;
		this.logLocCod = logLocCod;
		this.logNo = logNo;
		this.logNome = logNome;
		this.logCep = logCep;
		this.logComplemento = logComplemento;
		this.logTipoLogradouro = logTipoLogradouro;
		this.logStatusTipoLog = logStatusTipoLog;
		this.logKeyDne = logKeyDne;
		this.logCep2 = logCep2;
	}

	public Logradouro(int logCod, Bairro tblBairro, String logEstCod,
			int logLocCod, String logNo, String logNome, String logCep,
			String logComplemento, String logTipoLogradouro,
			String logStatusTipoLog, String logKeyDne, String logCep2,
			Set tblEncontroses, Set tblDiscipuloses) {
		this.logCod = logCod;
		this.tblBairro = tblBairro;
		this.logEstCod = logEstCod;
		this.logLocCod = logLocCod;
		this.logNo = logNo;
		this.logNome = logNome;
		this.logCep = logCep;
		this.logComplemento = logComplemento;
		this.logTipoLogradouro = logTipoLogradouro;
		this.logStatusTipoLog = logStatusTipoLog;
		this.logKeyDne = logKeyDne;
		this.logCep2 = logCep2;
		this.tblEncontroses = tblEncontroses;
		this.tblDiscipuloses = tblDiscipuloses;
	}

	@Id
	@Column(name = "logCod", unique = true, nullable = false)
	public int getLogCod() {
		return this.logCod;
	}

	public void setLogCod(int logCod) {
		this.logCod = logCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "logBairro", nullable = false)
	public Bairro getTblBairro() {
		return this.tblBairro;
	}

	public void setTblBairro(Bairro tblBairro) {
		this.tblBairro = tblBairro;
	}

	@Column(name = "logEstCod", nullable = false, length = 45)
	public String getLogEstCod() {
		return this.logEstCod;
	}

	public void setLogEstCod(String logEstCod) {
		this.logEstCod = logEstCod;
	}

	@Column(name = "logLocCod", nullable = false)
	public int getLogLocCod() {
		return this.logLocCod;
	}

	public void setLogLocCod(int logLocCod) {
		this.logLocCod = logLocCod;
	}

	@Column(name = "logNo", nullable = false, length = 70)
	public String getLogNo() {
		return this.logNo;
	}

	public void setLogNo(String logNo) {
		this.logNo = logNo;
	}

	@Column(name = "logNome", nullable = false, length = 125)
	public String getLogNome() {
		return this.logNome;
	}

	public void setLogNome(String logNome) {
		this.logNome = logNome;
	}

	@Column(name = "logCep", nullable = false, length = 16)
	public String getLogCep() {
		return this.logCep;
	}

	public void setLogCep(String logCep) {
		this.logCep = logCep;
	}

	@Column(name = "logComplemento", nullable = false, length = 100)
	public String getLogComplemento() {
		return this.logComplemento;
	}

	public void setLogComplemento(String logComplemento) {
		this.logComplemento = logComplemento;
	}

	@Column(name = "logTipoLogradouro", nullable = false, length = 72)
	public String getLogTipoLogradouro() {
		return this.logTipoLogradouro;
	}

	public void setLogTipoLogradouro(String logTipoLogradouro) {
		this.logTipoLogradouro = logTipoLogradouro;
	}

	@Column(name = "logStatusTipoLog", nullable = false, length = 45)
	public String getLogStatusTipoLog() {
		return this.logStatusTipoLog;
	}

	public void setLogStatusTipoLog(String logStatusTipoLog) {
		this.logStatusTipoLog = logStatusTipoLog;
	}

	@Column(name = "logKeyDNE", nullable = false, length = 16)
	public String getLogKeyDne() {
		return this.logKeyDne;
	}

	public void setLogKeyDne(String logKeyDne) {
		this.logKeyDne = logKeyDne;
	}

	@Column(name = "logCep2", nullable = false, length = 8)
	public String getLogCep2() {
		return this.logCep2;
	}

	public void setLogCep2(String logCep2) {
		this.logCep2 = logCep2;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tblLogradouro")
	public Set getTblEncontroses() {
		return this.tblEncontroses;
	}

	public void setTblEncontroses(Set tblEncontroses) {
		this.tblEncontroses = tblEncontroses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tblLogradouro")
	public Set getTblDiscipuloses() {
		return this.tblDiscipuloses;
	}

	public void setTblDiscipuloses(Set tblDiscipuloses) {
		this.tblDiscipuloses = tblDiscipuloses;
	}

}
