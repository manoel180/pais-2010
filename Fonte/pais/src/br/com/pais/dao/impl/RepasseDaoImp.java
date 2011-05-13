package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.RepasseDao;
import br.com.pais.entities.Movimento;
import br.com.pais.entities.Repasse;

public class RepasseDaoImp extends DaoGenericoImp<Repasse, Integer> implements RepasseDao {

	/*-------------------------------------------CELULAS RECEBER--------------------------------------------------------*/
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Movimento> listarMovimentosReceberCelulas(int lider, int geracao, String recebido, String tipo, String especie, String DataInicio, String DataFim) {
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
	public List<Object> totalMovimentosReceberCelulas(int celula, String tipo, String especie, String recebido, String DataInicio, String DataFim) {
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
	public List<Movimento> listarMovimentosTodosReceberCelulas(int celula,
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
	
	/*-------------------------------------------BASES RECEBER--------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Movimento> listarMovimentosReceberBases(int lider, int geracao, String recebido, String tipo, String especie, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where " +
			" m.bases.liderBase.discipulos.disCod = "+ lider +"" +
			" and m.bases.liderBase.geracoes.gerCod = "+ geracao +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movEspecie = '"+ especie +"'" +
			" and m.movRecebido = '"+ recebido +"'" +
			" and m.movData BETWEEN '"+ DataInicio +"' and '"+ DataFim +"'" +
			" group by m.bases.basCod");
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
	public List<Object> totalMovimentosReceberBases(int base, String tipo, String especie, String recebido, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"select sum(m.movValor) from Movimento m where " +
			" m.bases.basCod = "+ base +"" +
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
	public List<Movimento> listarMovimentosTodosReceberBases(int base,
			String tipo, String especie, String recebido, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where" +
			" m.bases.basCod = "+ base +"" +
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
	
	/*-------------------------------------------CELULAS RECEBIDOS--------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Movimento> listarMovimentosRecebidosCelulas(int lider, int geracao, String recebido, String tipo, String especie, String DataInicio, String DataFim) {
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
	public List<Object> totalMovimentosRecebidosCelulas(int celula, String tipo, String especie, String recebido, String DataInicio, String DataFim) {
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
	public List<Movimento> listarMovimentosTodosRecebidosCelulas(int celula,
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
	
	/*-------------------------------------------BASES RECEBIDOS--------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Movimento> listarMovimentosRecebidosBases(int lider,
			int geracao, String recebido, String tipo, String especie,
			String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where " +
			" m.bases.liderBase.discipulos.disCod = "+ lider +"" +
			" and m.bases.liderBase.geracoes.gerCod = "+ geracao +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movEspecie = '"+ especie +"'" +
			" and m.movRecebido = '"+ recebido +"'" +
			" and m.movData BETWEEN '"+ DataInicio +"' and '"+ DataFim +"'" +
			" group by m.bases.basCod");
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
	public List<Object> totalMovimentosRecebidosBases(int base, String tipo,
			String especie, String recebido, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"select sum(m.movValor) from Movimento m where " +
			" m.bases.basCod = "+ base +"" +
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
	public List<Movimento> listarMovimentosTodosRecebidosBases(int base,
			String tipo, String especie, String recebido, String DataInicio,
			String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where" +
			" m.bases.basCod = "+ base +"" +
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
	
	
	/*-------------------------------------------CELULAS ENVIADOS--------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Movimento> listarMovimentosEnviadosCelulas(int lider, int geracao, String tipo, String especie, String DataInicio, String DataFim) {
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
	public List<Object> totalMovimentosEnviadosCelulas(int celula, String tipo, String especie, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"select sum(m.movValor) from Movimento m where " +
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
	public List<Movimento> listarMovimentosTodosEnviadosCelulas(int celula, String tipo, String especie, String DataInicio, String DataFim) {
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
	
	/*-------------------------------------------BASES ENVIADOS--------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Movimento> listarMovimentosEnviadosBases(int lider, String tipo, String especie, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where " +
			" m.bases.liderBase.disCod = "+ lider +"" +
			" and m.movTipo = '"+ tipo +"'" +
			" and m.movEspecie = '"+ especie +"'" +
			" and m.movData BETWEEN '"+ DataInicio +"' and '"+ DataFim +"'" +
			" group by m.bases.basCod");
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
	public List<Object> totalMovimentosEnviadosBases(int base, String tipo,
			String especie, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"select sum(m.movValor) from Movimento m where " +
			" m.bases.basCod = "+ base +"" +
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
	public List<Movimento> listarMovimentosTodosEnviadosBases(int base, String tipo, String especie, String DataInicio, String DataFim) {
		try {
			Query query = getEntityManager().createQuery(
			"From Movimento m where" +
			" m.bases.basCod = "+ base +"" +
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
	public List<Movimento> listarMovimentosLembrarCelulas(int discipulo) {
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
	public List<Movimento> listarMovimentosLembrarBases(int discipulo) {
		try {
			Query query = getEntityManager().createQuery(
			"from Movimento m where" +
			" m.bases.liderBase.discipulos = "+ discipulo +
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
	public List<Object> trasCodigoDiscipuloRepassePorCelula(int movimento){
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> trasCodigoDiscipuloRepassePorBase(int movimento){
		try {
			Query query = getEntityManager().createQuery(
			"select m.bases.liderBase.disCod from Movimento m where m.movCod = "+ movimento +"");
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}
}
