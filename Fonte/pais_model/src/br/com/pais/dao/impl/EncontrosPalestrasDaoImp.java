package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.EncontrosPalestrasDao;
import br.com.pais.entities.Encontrospalestras;

public class EncontrosPalestrasDaoImp extends DaoGenericoImp<Encontrospalestras, Integer> implements EncontrosPalestrasDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Encontrospalestras> listarPalestrasEncontro(int encontro) {
		try {
			Query query = getEntityManager().createQuery(
					"from Encontrospalestras ep " +
					"where ep.dadosencontros.dadenccod = "+ encontro);
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
	public List<Encontrospalestras> listarPalestrasEncontroPorCodigo(
			int palestra) {
		try {
			Query query = getEntityManager().createQuery(
					"from Encontrospalestras ep " +
					"where ep.encpalestrasCod = "+ palestra);
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

}
