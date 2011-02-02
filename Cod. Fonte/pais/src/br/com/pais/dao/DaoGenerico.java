package br.com.pais.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface DaoGenerico<T, ID extends Serializable> {
	public Class<T> getObjectClass();
	public boolean salvar(T object);
	public T pesquisarPorId(ID id);
	public boolean atualizar(T object);
	void excluir(T object, ID id);
	public List<T> todos();
	public List<T> listPesqParam(String query, Map<String, Object> params);
	public List<T> listPesqParam(String query, Map<String, Object> params, 
			int maximo, int atual);
	public List<T> listPesq(String query);
	public T pesqParam(String query, Map<String, String> params);
}
