package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.CelulaDao;
import br.com.pais.entities.Celulas;
import br.com.pais.entities.Discipulos;

public class CelulaDaoImp extends DaoGenericoImp<Celulas, Integer> implements CelulaDao{

	@Override
	public List<Celulas> listarCelulas(int discipulador) {
		try {

			Query query = getEntityManager().createQuery(
					"From Celulas c " + "where c.discipulos.disCod ="+ discipulador);
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

}
