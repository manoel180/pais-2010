package br.com.pais.dao;

import br.com.pais.entities.Discipulos;


public interface participaDao extends DaoGenerico<Discipulos, Integer> {
	public Discipulos encontrarPorCPF(String cpf) ;
	
}
