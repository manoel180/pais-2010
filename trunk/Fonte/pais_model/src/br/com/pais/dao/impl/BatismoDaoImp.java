package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.BatismoDao;
import br.com.pais.entities.Batismo;

public class BatismoDaoImp extends DaoGenericoImp<Batismo, Integer> implements BatismoDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Batismo> listarBatismo(int responsavel, int status) {
		try {
			Query query = getEntityManager().createQuery("from Batismo b" +
			" where b.discipulos.disCod = "+responsavel+
			" and b.encontrostatus.encstacod = "+status);
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

}
