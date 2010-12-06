package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Discipulos;


public interface DiscipuloDao extends DaoGenerico<Discipulos, Integer> {
	public Discipulos encontrarPorCPF(String cpf);

	List<Discipulos> listarM12(int discipulador, int geracao);
	
	List<Discipulos> listarM12Cadastrados(int celula);
	
	List<Discipulos> listarM12NaoCadastrados(int discipulador, int geracao);

	List<Discipulos> listarDiscipulos(int discipulador);

	List<Discipulos> listarLideresCelulas(int discipulador);
}
