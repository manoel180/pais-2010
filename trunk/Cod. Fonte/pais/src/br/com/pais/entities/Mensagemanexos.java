package br.com.pais.entities;

// Generated 25/01/2011 16:14:58 by Hibernate Tools 3.4.0.Beta1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Mensagemanexos generated by hbm2java
 */
@Entity
@Table(name = "mensagemanexos", catalog = "wwwpais_sistema")
public class Mensagemanexos implements java.io.Serializable {

	private MensagemanexosId id;
	private Mensagem mensagem;

	public Mensagemanexos() {
	}

	public Mensagemanexos(MensagemanexosId id, Mensagem mensagem) {
		this.id = id;
		this.mensagem = mensagem;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "mensagemCod", column = @Column(name = "mensagemCod", nullable = false)),
			@AttributeOverride(name = "mensagemarquivo", column = @Column(name = "mensagemarquivo")) })
	public MensagemanexosId getId() {
		return this.id;
	}

	public void setId(MensagemanexosId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mensagemCod", nullable = false, insertable = false, updatable = false)
	public Mensagem getMensagem() {
		return this.mensagem;
	}

	public void setMensagem(Mensagem mensagem) {
		this.mensagem = mensagem;
	}

}
