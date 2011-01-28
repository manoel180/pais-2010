package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.CelulaDao;
import br.com.pais.entities.Celulas;

public class CelulaDaoImp extends DaoGenericoImp<Celulas, Integer> implements CelulaDao{

	@SuppressWarnings("unchecked")
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Celulas> listarCelulasGeracao(int discipulador, int geracao) {
		try {

			Query query = getEntityManager().createQuery(
					"From Celulas c where" +
					" c.discipulos.disCod = "+ discipulador +
					" and c.geracoes.gerCod = "+ geracao +"");
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

	public List<Celulas> listarCelulasSemBasePorZona(int idzona) {
		try {

			Query query = getEntityManager().createQuery(
					"select celulas from Celulas as celulas " +
					"where celulas.bases is null " +
					"and celulas.zona.idzona = " + idzona);
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

}
