package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Encontrostatus;

public interface EncontrosStatusDao extends DaoGenerico<Encontrostatus, Integer> {
	public List<Encontrostatus> listaEncontrosStatus();
}
