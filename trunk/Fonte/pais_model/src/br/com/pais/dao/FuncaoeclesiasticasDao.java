package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Funcaoeclesiasticas;


public interface FuncaoeclesiasticasDao extends DaoGenerico<Funcaoeclesiasticas, Integer> {

	List<Funcaoeclesiasticas> listarFuncaoPorSexo(char sexo);

	List<Funcaoeclesiasticas> listarFuncaoPorSexo(char sexo, int nivel);
	
}
