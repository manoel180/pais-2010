package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Frequencia;

public interface FrequenciaDao extends DaoGenerico<Frequencia, Integer> {
	public List<Frequencia> listaFrequenciaDiscipuloEncontro(int encontro, int discipulo);
}
