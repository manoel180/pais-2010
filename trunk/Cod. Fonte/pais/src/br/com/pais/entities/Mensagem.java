package br.com.pais.entities;

// Generated 23/01/2011 05:07:33 by Hibernate Tools 3.4.0.Beta1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Mensagem generated by hbm2java
 */
@Entity
@Table(name = "mensagem", catalog = "wwwpais_sistema")
public class Mensagem implements java.io.Serializable {

	private int mensCod;
	private Discipulos discipulosByMensDisCodRecebe;
	private Discipulos discipulosByMensDisCod;
	private String mensTexto;
	private Date mensData;
	private char mensLida;

	public Mensagem() {
	}

	public Mensagem(int mensCod, Discipulos discipulosByMensDisCodRecebe,
			Discipulos discipulosByMensDisCod, String mensTexto, Date mensData,
			char mensLida) {
		this.mensCod = mensCod;
		this.discipulosByMensDisCodRecebe = discipulosByMensDisCodRecebe;
		this.discipulosByMensDisCod = discipulosByMensDisCod;
		this.mensTexto = mensTexto;
		this.mensData = mensData;
		this.mensLida = mensLida;
	}

	@Id
	@Column(name = "mensCod", unique = true, nullable = false)
	public int getMensCod() {
		return this.mensCod;
	}

	public void setMensCod(int mensCod) {
		this.mensCod = mensCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mensDisCodRecebe", nullable = false)
	public Discipulos getDiscipulosByMensDisCodRecebe() {
		return this.discipulosByMensDisCodRecebe;
	}

	public void setDiscipulosByMensDisCodRecebe(
			Discipulos discipulosByMensDisCodRecebe) {
		this.discipulosByMensDisCodRecebe = discipulosByMensDisCodRecebe;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mensDisCod", nullable = false)
	public Discipulos getDiscipulosByMensDisCod() {
		return this.discipulosByMensDisCod;
	}

	public void setDiscipulosByMensDisCod(Discipulos discipulosByMensDisCod) {
		this.discipulosByMensDisCod = discipulosByMensDisCod;
	}

	@Column(name = "mensTexto", nullable = false)
	public String getMensTexto() {
		return this.mensTexto;
	}

	public void setMensTexto(String mensTexto) {
		this.mensTexto = mensTexto;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "mensData", nullable = false, length = 10)
	public Date getMensData() {
		return this.mensData;
	}

	public void setMensData(Date mensData) {
		this.mensData = mensData;
	}

	@Column(name = "mensLida", nullable = false, length = 1)
	public char getMensLida() {
		return this.mensLida;
	}

	public void setMensLida(char mensLida) {
		this.mensLida = mensLida;
	}

}
