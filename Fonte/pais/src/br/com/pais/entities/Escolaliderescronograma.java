package br.com.pais.entities;

// Generated 06/05/2011 15:49:37 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Escolaliderescronograma generated by hbm2java
 */
@Entity
@Table(name = "escolaliderescronograma")
public class Escolaliderescronograma implements java.io.Serializable {

	private Integer cronoCod;
	private Escolalideres escolalideres;
	private String cronoNome;
	private Set<Escolaliderescronogramaaulas> escolaliderescronogramaaulases = new HashSet<Escolaliderescronogramaaulas>(
			0);

	public Escolaliderescronograma() {
	}

	public Escolaliderescronograma(Escolalideres escolalideres) {
		this.escolalideres = escolalideres;
	}

	public Escolaliderescronograma(Escolalideres escolalideres,
			String cronoNome,
			Set<Escolaliderescronogramaaulas> escolaliderescronogramaaulases) {
		this.escolalideres = escolalideres;
		this.cronoNome = cronoNome;
		this.escolaliderescronogramaaulases = escolaliderescronogramaaulases;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "cronoCod", unique = true, nullable = false)
	public Integer getCronoCod() {
		return this.cronoCod;
	}

	public void setCronoCod(Integer cronoCod) {
		this.cronoCod = cronoCod;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "elCod", nullable = false)
	public Escolalideres getEscolalideres() {
		return this.escolalideres;
	}

	public void setEscolalideres(Escolalideres escolalideres) {
		this.escolalideres = escolalideres;
	}

	@Column(name = "cronoNome", length = 60)
	public String getCronoNome() {
		return this.cronoNome;
	}

	public void setCronoNome(String cronoNome) {
		this.cronoNome = cronoNome;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "escolaliderescronograma")
	public Set<Escolaliderescronogramaaulas> getEscolaliderescronogramaaulases() {
		return this.escolaliderescronogramaaulases;
	}

	public void setEscolaliderescronogramaaulases(
			Set<Escolaliderescronogramaaulas> escolaliderescronogramaaulases) {
		this.escolaliderescronogramaaulases = escolaliderescronogramaaulases;
	}

}
