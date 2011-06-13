package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Encontrospalestras;

public interface EncontrosPalestrasDao extends DaoGenerico<Encontrospalestras, Integer> {
	public List<Encontrospalestras> listarPalestrasEncontro(int encontro);
	public List<Encontrospalestras> listarPalestrasEncontroPorCodigo(int palestra);
}
