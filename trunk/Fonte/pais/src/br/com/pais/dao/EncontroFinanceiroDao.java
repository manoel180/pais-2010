package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Encontrofinanceiro;

public interface EncontroFinanceiroDao extends DaoGenerico<Encontrofinanceiro, Integer> {
	public List<Encontrofinanceiro> listarFinanceiroEncontro(int encontro, int tipo);
}
