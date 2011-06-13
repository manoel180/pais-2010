package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Formacaoeclesiasticas;


public interface FormacaoeclesiasticasDao extends DaoGenerico<Formacaoeclesiasticas, Integer> {
	public List<Formacaoeclesiasticas> listaFormacaoPorTipo(int formacao);
}
