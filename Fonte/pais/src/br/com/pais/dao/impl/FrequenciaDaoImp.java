package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.FrequenciaDao;
import br.com.pais.entities.Frequencia;

public class FrequenciaDaoImp extends DaoGenericoImp<Frequencia, Integer> implements FrequenciaDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Frequencia> listaFrequenciaDiscipuloEncontro(int encontro,
			int discipulo) {
		try {
			Query query = getEntityManager().createQuery(
					"from Frequencia f " +
					"where f.encontrospalestras.dadosencontros.dadenccod = " + encontro +
					" and f.discipulos.disCod = "+ discipulo);
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

}
