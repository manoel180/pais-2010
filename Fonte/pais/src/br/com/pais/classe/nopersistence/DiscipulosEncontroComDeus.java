package br.com.pais.classe.nopersistence;

import br.com.pais.entities.Discipulos;

public class DiscipulosEncontroComDeus {
	private Discipulos discipulo;
	private int aulas;
	private boolean pago;
	
	public DiscipulosEncontroComDeus(){
		
	}

	public DiscipulosEncontroComDeus(Discipulos discipulo, int aulas,
			boolean pago) {
		super();
		this.discipulo = discipulo;
		this.aulas = aulas;
		this.pago = pago;
	}

	public Discipulos getDiscipulo() {
		return discipulo;
	}

	public void setDiscipulo(Discipulos discipulo) {
		this.discipulo = discipulo;
	}

	public int getAulas() {
		return aulas;
	}

	public void setAulas(int aulas) {
		this.aulas = aulas;
	}

	public boolean isPago() {
		return pago;
	}

	public void setPago(boolean pago) {
		this.pago = pago;
	}
}
