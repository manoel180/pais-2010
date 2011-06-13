package br.com.pais.dao;

import br.com.pais.entities.Discipulosbatismo;
import br.com.pais.entities.DiscipulosbatismoId;

public interface DiscipulosBatismoDao extends DaoGenerico<Discipulosbatismo, Integer> {
	public void excluirPorChaves(DiscipulosbatismoId id);
}
