/**
 * 
 */
package br.com.pais.dao;

import java.util.List;

import br.com.pais.domain.Discipulos;

/**
 * @author manoel
 *
 */
public interface IDiscipuloDao {
	
	public void salvar(Discipulos discipulo);
	
	public List<Discipulos> listar();
	
	public Discipulos procurarPorNome(String Nome);
}
