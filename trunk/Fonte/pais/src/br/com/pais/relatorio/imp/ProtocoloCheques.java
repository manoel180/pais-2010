package br.com.pais.relatorio.imp;

import java.util.Date;

public class ProtocoloCheques {
	private Double valorcheque;
	private Date datacheque;
	private String predatadocheque;
	
	public ProtocoloCheques(){
		
	}

	public ProtocoloCheques(Double valorcheque, Date datacheque,
			String predatadocheque) {
		super();
		this.valorcheque = valorcheque;
		this.datacheque = datacheque;
		this.predatadocheque = predatadocheque;
	}

	public Double getValorcheque() {
		return valorcheque;
	}

	public void setValorcheque(Double valorcheque) {
		this.valorcheque = valorcheque;
	}

	public Date getDatacheque() {
		return datacheque;
	}

	public void setDatacheque(Date datacheque) {
		this.datacheque = datacheque;
	}

	public String getPredatadocheque() {
		return predatadocheque;
	}

	public void setPredatadocheque(String predatadocheque) {
		this.predatadocheque = predatadocheque;
	}
}
