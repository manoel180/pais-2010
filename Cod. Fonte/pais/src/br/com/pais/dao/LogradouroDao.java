package br.com.pais.dao;

import br.com.pais.entities.Logradouro;


public interface LogradouroDao extends DaoGenerico<Logradouro, Integer> {

	Logradouro encontrarPorCEP(String cep);

}
