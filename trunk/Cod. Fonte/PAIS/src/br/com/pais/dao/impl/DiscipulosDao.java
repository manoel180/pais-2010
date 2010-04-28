/**
 * 
 */
package br.com.pais.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.pais.dao.IDiscipuloDao;
import br.com.pais.domain.Discipulos;

/**
 * @author manoel
 *
 */
@Repository
public class DiscipulosDao implements IDiscipuloDao {

	@Autowired(required=true)
	private SessionFactory sessionFactory;
	
	
	/* (non-Javadoc)
	 * @see br.com.pais.dao.IDiscipuloDao#listar()
	 */
	@Override
	public List<Discipulos> listar() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.com.pais.dao.IDiscipuloDao#procurarPorNome(java.lang.String)
	 */
	@Override
	public Discipulos procurarPorNome(String Nome) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see br.com.pais.dao.IDiscipuloDao#salvar(br.com.pais.domain.Discipulos)
	 */
	@Override
	public void salvar(Discipulos discipulo) {
		// TODO Auto-generated method stub
	
			this.sessionFactory.getCurrentSession().saveOrUpdate(discipulo);
	
	}

}
