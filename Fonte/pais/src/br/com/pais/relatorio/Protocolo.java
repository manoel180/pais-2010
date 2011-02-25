package br.com.pais.relatorio;

import java.util.Date;
import java.util.List;

public class Protocolo {
	private String discipulo;
	private String discipulador;
	private String tipo;
	private Date inicio;
	private Date fim;
	private Double valormovimento;
	private Date data;
	private String protocolo;
	private String local;
	private String logo;
	private String fotoDiscipulo;
	private String fotoDiscipulador;
	private String especie;
	private List<ProtocoloCheques> listacheques;
	private Double valorcheques;
	private Double valordinheiro;

	public Protocolo(){
		
	}

	public Protocolo(String discipulo, String discipulador, String tipo,
			Date inicio, Date fim, Double valormovimento, Date data,
			String protocolo, String local, String logo, String fotoDiscipulo,
			String fotoDiscipulador, String especie,
			List<ProtocoloCheques> listacheques, Double valorcheques,
			Double valordinheiro) {
		super();
		this.discipulo = discipulo;
		this.discipulador = discipulador;
		this.tipo = tipo;
		this.inicio = inicio;
		this.fim = fim;
		this.valormovimento = valormovimento;
		this.data = data;
		this.protocolo = protocolo;
		this.local = local;
		this.logo = logo;
		this.fotoDiscipulo = fotoDiscipulo;
		this.fotoDiscipulador = fotoDiscipulador;
		this.especie = especie;
		this.listacheques = listacheques;
		this.valorcheques = valorcheques;
		this.valordinheiro = valordinheiro;
	}

	public String getDiscipulo() {
		return discipulo;
	}

	public void setDiscipulo(String discipulo) {
		this.discipulo = discipulo;
	}

	public String getDiscipulador() {
		return discipulador;
	}

	public void setDiscipulador(String discipulador) {
		this.discipulador = discipulador;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	public Double getValormovimento() {
		return valormovimento;
	}

	public void setValormovimento(Double valormovimento) {
		this.valormovimento = valormovimento;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getFotoDiscipulo() {
		return fotoDiscipulo;
	}

	public void setFotoDiscipulo(String fotoDiscipulo) {
		this.fotoDiscipulo = fotoDiscipulo;
	}

	public String getFotoDiscipulador() {
		return fotoDiscipulador;
	}

	public void setFotoDiscipulador(String fotoDiscipulador) {
		this.fotoDiscipulador = fotoDiscipulador;
	}

	public String getEspecie() {
		return especie;
	}

	public void setEspecie(String especie) {
		this.especie = especie;
	}

	public List<ProtocoloCheques> getListacheques() {
		return listacheques;
	}

	public void setListacheques(List<ProtocoloCheques> listacheques) {
		this.listacheques = listacheques;
	}

	public Double getValorcheques() {
		return valorcheques;
	}

	public void setValorcheques(Double valorcheques) {
		this.valorcheques = valorcheques;
	}

	public Double getValordinheiro() {
		return valordinheiro;
	}

	public void setValordinheiro(Double valordinheiro) {
		this.valordinheiro = valordinheiro;
	}
}
