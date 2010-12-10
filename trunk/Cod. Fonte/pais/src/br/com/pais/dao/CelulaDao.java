package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Celulas;
import br.com.pais.entities.Geracoes;

public interface CelulaDao extends DaoGenerico<Celulas, Integer>{
	
	List<Celulas> listarCelulas(int discipulador);
}
