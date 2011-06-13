package br.com.pais.entities;

// Generated 25/01/2011 16:14:58 by Hibernate Tools 3.4.0.Beta1

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Mensagem generated by hbm2java
 */
@Entity
@Table(name = "mensagem")
public class Mensagem implements java.io.Serializable {

	private Integer mensCod;
	private Discipulos discipulosByMensDisCodRecebe;
	private Discipulos discipulosByMensDisCod;
	private String mensTexto;
	private Date mensData;
	private String mensLida;
	private String mensCaixa;

	private List<Mensagemanexos> mensagemanexoses = new ArrayList<Mensagemanexos>();

	public Mensagem() {
	}

	public Mensagem(Discipulos discipulosByMensDisCodRecebe,
			Discipulos discipulosByMensDisCod, String mensTexto, Date mensData,
			String mensLida, String mensCaixa, List<Mensagemanexos> mensagemanexoses) {
		this.discipulosByMensDisCodRecebe = discipulosByMensDisCodRecebe;
		this.discipulosByMensDisCod = discipulosByMensDisCod;
		this.mensTexto = mensTexto;
		this.mensData = mensData;
		this.mensLida = mensLida;
		this.mensCaixa = mensCaixa;
		this.mensagemanexoses = mensagemanexoses;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mensCod", unique = true, nullable = false)
	public Integer getMensCod() {
		return this.mensCod;
	}

	public void setMensCod(Integer mensCod) {
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
	public String getMensLida() {
		return this.mensLida;
	}

	public void setMensLida(String string) {
		this.mensLida = string;
	}
	
	@Column(name = "mensCaixa", nullable = false, length = 1)
	public String getMensCaixa() {
		return mensCaixa;
	}

	public void setMensCaixa(String string) {
		this.mensCaixa = string;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mensagem", orphanRemoval = true)
	public List<Mensagemanexos> getMensagemanexoses() {
		return this.mensagemanexoses;
	}

	public void setMensagemanexoses(List<Mensagemanexos> mensagemanexoses) {
		this.mensagemanexoses = mensagemanexoses;
	}

}
