package br.com.pais.dao;

import java.util.List;

import br.com.pais.relatorio.Protocolo;

public interface RelatorioDao{
	void gerarProtocolo(List<Protocolo> listaProtocolo);
}
