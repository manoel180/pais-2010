package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.EncontroFinanceiroDao;
import br.com.pais.entities.Encontrofinanceiro;

public class EncontroFinanceiroDaoImp extends DaoGenericoImp<Encontrofinanceiro, Integer> implements EncontroFinanceiroDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Encontrofinanceiro> listarFinanceiroEncontro(int encontro,
			int tipo) {
		try {
			Query query = getEntityManager().createQuery(
			"from Encontrofinanceiro ef " +
			"where ef.dadosencontros.dadenccod = " +encontro+
			" and ef.encontrostipomovimento = " +tipo);
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

}
