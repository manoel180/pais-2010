package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Celulas;

public interface CelulaDao extends DaoGenerico<Celulas, Integer>{
	
	List<Celulas> listarCelulas(int discipulador);
}
