package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Palestras;

public interface PalestrasDao extends DaoGenerico<Palestras, Integer> {
	public List<Palestras> listarPalestrasEncontros(int encontro);
}
