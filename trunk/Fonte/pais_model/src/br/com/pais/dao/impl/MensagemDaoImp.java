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
					"From Mensagem where discipulosByMensDisCodRecebe = :discipulo " +
					"and mensCaixa = 'E' and mensLida = 'N'");
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
	public List<Mensagem> listarCaixaEntrada(int discipulo) {
		try {
			Query query = getEntityManager().createQuery(
					"From Mensagem m where m.discipulosByMensDisCodRecebe.disCod = :discipulo "+
					"and m.mensCaixa = 'E' order by m.mensData DESC, m.mensCod DESC");
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
	public List<Mensagem> listarCaixaSaida(int discipulo) {
		try {
			Query query = getEntityManager().createQuery(
					"From Mensagem m where m.discipulosByMensDisCod.disCod = :discipulo " +
					"and m.mensCaixa = 'S' order by m.mensData DESC, m.mensCod DESC");
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
	public List<Object> discipulosEnviaRecebeMensagem(int mensagem){
		try {
			Query query = getEntityManager().createQuery(
					"select m.discipulosByMensDisCod.disCod, m.discipulosByMensDisCodRecebe.disCod " +
			        "from Mensagem m where m.mensCod = :mensagem");
			query.setParameter("mensagem", mensagem);
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

}
