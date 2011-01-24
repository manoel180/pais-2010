package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.RepasseDao;
import br.com.pais.entities.Movimento;
import br.com.pais.entities.Repasse;

public class RepasseDaoImp extends DaoGenericoImp<Repasse, Integer> implements RepasseDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Movimento> listarMovimentos(int lider, int geracao, String recebido, String tipo, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where " +
			" m.celulas.discipulos.discipulos.disCod = "+ lider +"" +
			" and m.celulas.geracoes.gerCod = "+ geracao +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movRecebido = '"+ recebido +"'" +
			" and m.movData BETWEEN '"+ DataInicio +"' and '"+ DataFim +"'" +
			" group by m.celulas.celCod");
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
	public List<Movimento> totalMovimento(int celula, String tipo, String recebido, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where" +
			" m.celulas.celCod = "+ celula +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movRecebido = '"+ recebido +"'" +
			" and m.movData BETWEEN '"+ DataInicio +"' and '"+ DataFim +"'");
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
	public List<Movimento> listarMovimentosTodosPeriodo(int celula,
			String tipo, String recebido, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where" +
			" m.celulas.celCod = "+ celula +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movRecebido = '"+ recebido +"'" +
			" and m.movData BETWEEN '"+ DataInicio +"' and '"+ DataFim +"'");
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
	public List<Movimento> listarMovimentosEnviados(int lider, int geracao, String tipo, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where " +
			" m.celulas.discipulos.disCod = "+ lider +"" +
			" and m.celulas.geracoes.gerCod = "+ geracao +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movData BETWEEN '"+ DataInicio +"' and '"+ DataFim +"'" +
			" group by m.celulas.celCod");
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
	public List<Movimento> totalMovimentoEnviados(int celula, String tipo, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where" +
			" m.celulas.celCod = "+ celula +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movData BETWEEN '"+ DataInicio +"' and '"+ DataFim +"'");
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
	public List<Movimento> listarMovimentosTodosPeriodoDetalhe(int celula,
			String tipo, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where" +
			" m.celulas.celCod = "+ celula +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movData BETWEEN '"+ DataInicio +"' and '"+ DataFim +"'");
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
	public List<Movimento> listarMovimentoRelatorio(int movimento) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where" +
			" m.movCod = "+ movimento +"");
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}
}
