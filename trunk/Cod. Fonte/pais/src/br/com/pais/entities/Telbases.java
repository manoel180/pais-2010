package br.com.pais.entities;

// Generated 13/09/2010 13:58:41 by Hibernate Tools 3.3.0.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Telbases generated by hbm2java
 */
@Entity
@Table(name = "telbases", catalog = "wwwpais_sistema")
public class Telbases implements java.io.Serializable {

	private Integer telBasCod;
	private Bases bases;
	private String telBasDescricao;
	private String telBasNumero;

	public Telbases() {
	}

	public Telbases(Bases bases, String telBasDescricao, String telBasNumero) {
		this.bases = bases;
		this.telBasDescricao = telBasDescricao;
		this.telBasNumero = telBasNumero;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "telBasCod", unique = true, nullable = false)
	public Integer getTelBasCod() {
		return this.telBasCod;
	}

	public void setTelBasCod(Integer telBasCod) {
		this.telBasCod = telBasCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "basCod", nullable = false)
	public Bases getBases() {
		return this.bases;
	}

	public void setBases(Bases bases) {
		this.bases = bases;
	}

	@Column(name = "telBasDescricao", nullable = false, length = 15)
	public String getTelBasDescricao() {
		return this.telBasDescricao;
	}

	public void setTelBasDescricao(String telBasDescricao) {
		this.telBasDescricao = telBasDescricao;
	}

	@Column(name = "telBasNumero", nullable = false, length = 15)
	public String getTelBasNumero() {
		return this.telBasNumero;
	}

	public void setTelBasNumero(String telBasNumero) {
		this.telBasNumero = telBasNumero;
	}

}
