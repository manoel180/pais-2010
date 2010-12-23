package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.MensagemDao;
import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Mensagem;

public class MensagemDaoImp extends DaoGenericoImp<Mensagem, Integer> implements MensagemDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Mensagem> listarMensagensRecebidas(Discipulos discipulo) {
		try {
			Query query = getEntityManager().createQuery(
					"From Mensagem where discipulosByMensDisCodRecebe = :discipulo and mensLida = 'N'");
			query.setParameter("discipulo", discipulo);
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
	public List<Mensagem> listarCaixaEntrada(Discipulos discipulo) {
		try {
			Query query = getEntityManager().createQuery(
					"From Mensagem where discipulosByMensDisCodRecebe = :discipulo order by mensData DESC");
			query.setParameter("discipulo", discipulo);
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
	public List<Mensagem> listarCaixaSaida(Discipulos discipulo) {
		try {
			Query query = getEntityManager().createQuery(
					"From Mensagem where discipulosByMensDisCod = :discipulo order by mensData DESC");
			query.setParameter("discipulo", discipulo);
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

}
