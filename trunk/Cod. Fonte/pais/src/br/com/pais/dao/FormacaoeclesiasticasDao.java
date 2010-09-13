package br.com.pais.dao;

import br.com.pais.entities.Discipulos;


public interface FormacaoeclesiasticasDao extends DaoGenerico<Discipulos, Integer> {
	public Discipulos encontrarPorCPF(String cpf) ;
	
}
