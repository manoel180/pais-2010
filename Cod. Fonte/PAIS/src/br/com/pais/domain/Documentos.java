package br.com.pais.domain;

// Generated 27/04/2010 23:53:17 by Hibernate Tools 3.3.0.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Documentos generated by hbm2java
 */
@Entity
@Table(name = "tbl_Documentos", catalog = "pais", uniqueConstraints = {
		@UniqueConstraint(columnNames = "docTituloEleitor"),
		@UniqueConstraint(columnNames = "docCPF"),
		@UniqueConstraint(columnNames = "docRG") })
public class Documentos implements java.io.Serializable {

	private Integer docCod;
	private Discipulos discipulos;
	private String docRg;
	private String docRgemissor;
	private String docTituloEleitor;
	private Integer docSecao;
	private Integer docZona;
	private String docCpf;

	public Documentos() {
	}

	public Documentos(Discipulos discipulos, String docCpf) {
		this.discipulos = discipulos;
		this.docCpf = docCpf;
	}

	public Documentos(Discipulos discipulos, String docRg,
			String docRgemissor, String docTituloEleitor, Integer docSecao,
			Integer docZona, String docCpf) {
		this.discipulos = discipulos;
		this.docRg = docRg;
		this.docRgemissor = docRgemissor;
		this.docTituloEleitor = docTituloEleitor;
		this.docSecao = docSecao;
		this.docZona = docZona;
		this.docCpf = docCpf;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "docCod", unique = true, nullable = false)
	public Integer getDocCod() {
		return this.docCod;
	}

	public void setDocCod(Integer docCod) {
		this.docCod = docCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "docDisCod", nullable = false)
	public Discipulos getTblDiscipulos() {
		return this.discipulos;
	}

	public void setTblDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

	@Column(name = "docRG", unique = true, length = 10)
	public String getDocRg() {
		return this.docRg;
	}

	public void setDocRg(String docRg) {
		this.docRg = docRg;
	}

	@Column(name = "docRGEmissor", length = 5)
	public String getDocRgemissor() {
		return this.docRgemissor;
	}

	public void setDocRgemissor(String docRgemissor) {
		this.docRgemissor = docRgemissor;
	}

	@Column(name = "docTituloEleitor", unique = true, length = 10)
	public String getDocTituloEleitor() {
		return this.docTituloEleitor;
	}

	public void setDocTituloEleitor(String docTituloEleitor) {
		this.docTituloEleitor = docTituloEleitor;
	}

	@Column(name = "docSecao")
	public Integer getDocSecao() {
		return this.docSecao;
	}

	public void setDocSecao(Integer docSecao) {
		this.docSecao = docSecao;
	}

	@Column(name = "docZona")
	public Integer getDocZona() {
		return this.docZona;
	}

	public void setDocZona(Integer docZona) {
		this.docZona = docZona;
	}

	@Column(name = "docCPF", unique = true, nullable = false, length = 11)
	public String getDocCpf() {
		return this.docCpf;
	}

	public void setDocCpf(String docCpf) {
		this.docCpf = docCpf;
	}

}
