package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.PalestrasDao;
import br.com.pais.entities.Palestras;

public class PalestrasDaoImp extends DaoGenericoImp<Palestras, Integer> implements PalestrasDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Palestras> listarPalestrasEncontros(int encontro) {
		try {
			Query query = getEntityManager().createQuery(
			"from Palestras p where p.encontros.encCod = "+ encontro);
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

}
