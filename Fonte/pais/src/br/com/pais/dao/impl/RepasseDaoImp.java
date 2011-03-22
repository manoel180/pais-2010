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
	public List<Movimento> listarMovimentosReceber(int lider, int geracao, String recebido, String tipo, String especie, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where " +
			" m.celulas.discipulos.discipulos.disCod = "+ lider +"" +
			" and m.celulas.discipulos.geracoes.gerCod = "+ geracao +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movEspecie = '"+ especie +"'" +
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
	public List<Object> totalMovimentosReceber(int celula, String tipo, String especie, String recebido, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"select sum(m.movValor) from Movimento m where " +
			" m.celulas.celCod = "+ celula +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movEspecie = '"+ especie +"'" +
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
	public List<Movimento> listarMovimentosTodosReceber(int celula,
			String tipo, String especie, String recebido, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where" +
			" m.celulas.celCod = "+ celula +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movEspecie = '"+ especie +"'" +
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
	public List<Movimento> listarMovimentosEnviados(int lider, int geracao, String tipo, String especie, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where " +
			" m.celulas.discipulos.disCod = "+ lider +"" +
			" and m.celulas.geracoes.gerCod = "+ geracao +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movEspecie = '"+ especie +"'" +
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
	public List<Object> totalMovimentosEnviados(int celula, String tipo, String especie, String recebido, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"select sum(m.movValor) from Movimento m where " +
			" m.celulas.celCod = "+ celula +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movEspecie = '"+ especie +"'" +
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
	public List<Movimento> listarMovimentosTodosEnviados(int celula, String tipo, String especie, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where" +
			" m.celulas.celCod = "+ celula +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movEspecie = '"+ especie +"'" +
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
	public List<Movimento> listarMovimentosRecebdidos(int lider, int geracao, String recebido, String tipo, String especie, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where " +
			" m.celulas.discipulos.discipulos.disCod = "+ lider +"" +
			" and m.celulas.discipulos.geracoes.gerCod = "+ geracao +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movEspecie = '"+ especie +"'" +
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
	public List<Object> totalMovimentosRecebidos(int celula, String tipo, String especie, String recebido, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"select sum(m.movValor) from Movimento m where " +
			" m.celulas.celCod = "+ celula +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movEspecie = '"+ especie +"'" +
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
	public List<Movimento> listarMovimentosTodosRecebidos(int celula,
			String tipo, String especie, String recebido, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where" +
			" m.celulas.celCod = "+ celula +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movEspecie = '"+ especie +"'" +
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Movimento> listarMovimentosLembrarPrincipal(int discipulo) {
		try {
			Query query = getEntityManager().createQuery(
			"from Movimento m where" +
			" m.celulas.discipulos.discipulos.disCod = "+ discipulo +
			" and m.movRecebido = 'N'");
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
	public List<Object> trasCodigoDiscipuloRepasse(int movimento){
		try {
			Query query = getEntityManager().createQuery(
			"select m.celulas.discipulos.disCod from Movimento m where m.movCod = "+ movimento +"");
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}
}
