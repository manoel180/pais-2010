package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Bases;


public interface BasesDao extends DaoGenerico<Bases, Integer> {

	public List<Bases> listarBases(int discipulador);
	public List<Bases> listarBasesPorTipo(int discipulador, int tipo);
}
