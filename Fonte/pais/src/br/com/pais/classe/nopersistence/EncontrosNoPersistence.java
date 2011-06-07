package br.com.pais.classe.nopersistence;

import br.com.pais.entities.Dadosencontros;
import br.com.pais.entities.Discipulos;

public class EncontrosNoPersistence {
	private Dadosencontros dadosEncontros;
	private Discipulos ministrador;
	private EncontrosNoPersistenceAulas aula1;
	private EncontrosNoPersistenceAulas aula2;
	private EncontrosNoPersistenceAulas aula3;
	private EncontrosNoPersistenceAulas aula4;
	
	public EncontrosNoPersistence(){
		
	}

	public EncontrosNoPersistence(Dadosencontros dadosEncontros,
			Discipulos ministrador, EncontrosNoPersistenceAulas aula1,
			EncontrosNoPersistenceAulas aula2,
			EncontrosNoPersistenceAulas aula3, EncontrosNoPersistenceAulas aula4) {
		super();
		this.dadosEncontros = dadosEncontros;
		this.ministrador = ministrador;
		this.aula1 = aula1;
		this.aula2 = aula2;
		this.aula3 = aula3;
		this.aula4 = aula4;
	}

	public Dadosencontros getDadosEncontros() {
		return dadosEncontros;
	}

	public void setDadosEncontros(Dadosencontros dadosEncontros) {
		this.dadosEncontros = dadosEncontros;
	}

	public Discipulos getMinistrador() {
		return ministrador;
	}

	public void setMinistrador(Discipulos ministrador) {
		this.ministrador = ministrador;
	}

	public EncontrosNoPersistenceAulas getAula1() {
		return aula1;
	}

	public void setAula1(EncontrosNoPersistenceAulas aula1) {
		this.aula1 = aula1;
	}

	public EncontrosNoPersistenceAulas getAula2() {
		return aula2;
	}

	public void setAula2(EncontrosNoPersistenceAulas aula2) {
		this.aula2 = aula2;
	}

	public EncontrosNoPersistenceAulas getAula3() {
		return aula3;
	}

	public void setAula3(EncontrosNoPersistenceAulas aula3) {
		this.aula3 = aula3;
	}

	public EncontrosNoPersistenceAulas getAula4() {
		return aula4;
	}

	public void setAula4(EncontrosNoPersistenceAulas aula4) {
		this.aula4 = aula4;
	}
}
