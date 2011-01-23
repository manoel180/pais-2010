package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.BasesDao;
import br.com.pais.entities.Bases;

public class BasesDaoImp extends DaoGenericoImp<Bases, Integer> implements
		BasesDao {

	@Override
	public List<Bases> listarBases(int discipulador) {
		try {

			Query query = getEntityManager().createQuery(
					"From Bases b " + "where b.discipulosByBasDisCod.disCod ="
							+ discipulador);
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}
}
