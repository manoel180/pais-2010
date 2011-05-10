package br.com.pais.relatorio;

import java.util.List;

import br.com.pais.entities.Discipulos;
import br.com.pais.relatorio.imp.Protocolo;

public interface RelatorioDao{
	void gerarProtocoloDinheiro(List<Protocolo> listaProtocolo);
	void gerarProtocoloCheque(List<Protocolo> listaProtocolo);
	void gerarProtocoloDinheiroCheque(List<Protocolo> listaProtocolo);
/*	void gerarRelatorioGeracoes(int Discipulador);*/
	void gerarRelatorioGeracoes(List<Discipulos> discipulos);
}
