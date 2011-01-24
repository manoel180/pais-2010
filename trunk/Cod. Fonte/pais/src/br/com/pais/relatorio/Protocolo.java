package br.com.pais.relatorio;

public class Protocolo {
	private String discipulo;
	private String discipulador;
	private String tipo;
	private String inicio;
	private String fim;
	private String valor;
	private String data;
	private String protocolo;
	private String local;
	private String logo;
	private String fotoDiscipulo;
	private String fotoDiscipulador;

	public Protocolo(){
		
	}
	
	public Protocolo(String discipulo, String discipulador, String tipo,
			String inicio, String fim, String valor, String data,
			String protocolo, String local, String logo, 
			String fotoDiscipulo,String fotoDiscipulador) {
		super();
		this.discipulo = discipulo;
		this.discipulador = discipulador;
		this.tipo = tipo;
		this.inicio = inicio;
		this.fim = fim;
		this.valor = valor;
		this.data = data;
		this.protocolo = protocolo;
		this.local = local;
		this.logo = logo;
		this.fotoDiscipulo = fotoDiscipulo;
		this.fotoDiscipulador = fotoDiscipulador;
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
	public String getInicio() {
		return inicio;
	}
	public void setInicio(String inicio) {
		this.inicio = inicio;
	}
	public String getFim() {
		return fim;
	}
	public void setFim(String fim) {
		this.fim = fim;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
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
}
