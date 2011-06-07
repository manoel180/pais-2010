package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.DadosEncontrosDao;
import br.com.pais.entities.Dadosencontros;

public class DadosEncontrosDaoImp extends DaoGenericoImp<Dadosencontros, Integer> implements DadosEncontrosDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Dadosencontros> listarPreEncontros(int responsavel, int status) {
		try {
			Query query = getEntityManager().createQuery(
			"from Dadosencontros de " +
			"where de.discipulos.disCod = " +responsavel+
			" and de.encontros.encCod = 1 " +
			"and de.encontrostatus.encstacod = "+ status);
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
	public List<Dadosencontros> listarEncontrosFilhos(int encontropai) {
		try {
			Query query = getEntityManager().createQuery(
			"from Dadosencontros de " +
			"where de.dadosencontros.dadenccod = " +encontropai);
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
	public List<Dadosencontros> listarEncontrosComDeus(int responsavel,
			int status) {
		try {
			Query query = getEntityManager().createQuery(
			"from Dadosencontros de " +
			"where de.discipulos.disCod = " +responsavel+
			" and de.encontros.encCod = 2 " +
			"and de.encontrostatus.encstacod = "+ status);
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
	public List<Dadosencontros> listarPosEncontros(int responsavel, int status) {
		try {
			Query query = getEntityManager().createQuery(
			"from Dadosencontros de " +
			"where de.discipulos.disCod = " +responsavel+
			" and de.encontros.encCod = 3 " +
			"and de.encontrostatus.encstacod = "+ status);
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

}
