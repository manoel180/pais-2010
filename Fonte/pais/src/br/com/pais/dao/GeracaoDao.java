package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Geracoes;


public interface GeracaoDao extends DaoGenerico<Geracoes, Integer> {

	List<Geracoes> listarGeracoes();
	
	
}
