package br.com.pais.dao;

import java.util.List;

import br.com.pais.relatorio.Protocolo;

public interface RelatorioDao{
	void gerarProtocoloDinheiro(List<Protocolo> listaProtocolo);
	void gerarProtocoloCheque(List<Protocolo> listaProtocolo);
	void gerarProtocoloDinheiroCheque(List<Protocolo> listaProtocolo);
}
