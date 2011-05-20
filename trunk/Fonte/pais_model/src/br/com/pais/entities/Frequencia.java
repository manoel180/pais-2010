package br.com.pais.entities;

// Generated 11/05/2011 14:16:49 by Hibernate Tools 3.4.0.CR1

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
 * Frequencia generated by hbm2java
 */
@Entity
@Table(name = "frequencia", catalog = "wwwpais_sistema")
public class Frequencia implements java.io.Serializable {

	private FrequenciaId id;
	private Encontrospalestras encontrospalestras;
	private Discipulos discipulos;

	public Frequencia() {
	}

	public Frequencia(FrequenciaId id, Encontrospalestras encontrospalestras,
			Discipulos discipulos) {
		this.id = id;
		this.encontrospalestras = encontrospalestras;
		this.discipulos = discipulos;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "discipulosDisCod", column = @Column(name = "discipulos_disCod", nullable = false)),
			@AttributeOverride(name = "palestrasHasEncontrosPalestrasPalCod", column = @Column(name = "Palestras_has_encontros_Palestras_PalCod", nullable = false)),
			@AttributeOverride(name = "palestrasHasEncontrosEncontrosEncCod", column = @Column(name = "Palestras_has_encontros_encontros_EncCod", nullable = false)) })
	public FrequenciaId getId() {
		return this.id;
	}

	public void setId(FrequenciaId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Palestras_has_encontros_Palestras_PalCod", nullable = false, insertable = false, updatable = false)
	public Encontrospalestras getEncontrospalestras() {
		return this.encontrospalestras;
	}

	public void setEncontrospalestras(Encontrospalestras encontrospalestras) {
		this.encontrospalestras = encontrospalestras;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "discipulos_disCod", nullable = false, insertable = false, updatable = false)
	public Discipulos getDiscipulos() {
		return this.discipulos;
	}

	public void setDiscipulos(Discipulos discipulos) {
		this.discipulos = discipulos;
	}

}