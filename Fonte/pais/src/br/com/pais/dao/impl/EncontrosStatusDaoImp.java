package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.EncontrosStatusDao;
import br.com.pais.entities.Encontrostatus;

public class EncontrosStatusDaoImp extends DaoGenericoImp<Encontrostatus, Integer> implements EncontrosStatusDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Encontrostatus> listaEncontrosStatus() {
		try {
			Query query = getEntityManager().createQuery("From Encontrostatus es");
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

}
