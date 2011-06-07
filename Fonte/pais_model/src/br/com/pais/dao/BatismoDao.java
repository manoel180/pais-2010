package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Batismo;

public interface BatismoDao extends DaoGenerico<Batismo, Integer> {
	public List<Batismo> listarBatismo(int responsavel, int status);
}
