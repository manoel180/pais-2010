package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Bancos;

public interface BancosDao extends DaoGenerico<Bancos, Integer> {
	List<Bancos> listarBancosAtivos();
	List<Bancos> listarBancoSelecionado(int banco);
}
