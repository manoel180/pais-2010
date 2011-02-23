package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.DiscipuloDao;
import br.com.pais.entities.Discipulos;

public class DiscipuloDaoImp extends DaoGenericoImp<Discipulos, Integer> implements DiscipuloDao {

	@Override
	public Discipulos encontrarPorCPF(String cpf) {
		try {
			Query query = getEntityManager().createQuery(
					"From Discipulos where disCPF = :cpf");
			query.setParameter("cpf", cpf);
			return (Discipulos) query.getSingleResult();
		} catch (NoResultException nre) {
			// TODO: handle exception
			//MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",	"erro.discipulo.naoencontrado");
			return null;
		} 
		finally {
			getEntityManager().close();
		}
	}

	@Override
	public List<Discipulos> listarM12(int discipulador, int geracao) {
		try {
			Query query = getEntityManager().createQuery(
					"From Discipulos d " + "where d.discipulos.disCod ="
							+ discipulador + " and (d.dism12 = 's') "
							+ "and (d.geracoes.gerCod = " + geracao + ")");
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.com.pais.dao.DiscipuloDao#listarDiscipulos(int)
	 * 
	 */
	@Override
	public List<Discipulos> listarDiscipulos(int discipulador) {
		try {
			Query query = getEntityManager().createQuery(
					"From Discipulos d " + "where d.discipulos.disCod ="+ discipulador );
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}
	
	/*select  * from discipulos d
	where d.disdiscipulador = 1
	and 2<=(select count(*) from Discipulos d2 where d.discod = d2.disdiscipulador) ;*/
	@Override
	public List<Discipulos> listarLideresCelulas(int discipulador) {
		try {
			Query query = getEntityManager().createQuery(
					"From Discipulos d " + "where d.discipulos.disCod ="+ discipulador +
					" and 3 <= (count(*) From Discipulos d2 where d.discipulos.discod = d2.discipulos.disCod");
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

	@Override
	public List<Discipulos> listarM12NaoCadastrados(int discipulador,int celulas) {
		try {
			Query query = getEntityManager().createQuery(
					"From Discipulos d " +
					"where d.discod not in (From c.dicipulos from discipuloscelulas) " +
					"and (d.dism12 = 's') " +
					"and (d.geracoes.gerCod = " + celulas + ") " +
					"and (d.discipulos.disCod = " + discipulador + ")");
					
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

	@Override
	public List<Discipulos> listarM12Cadastrados(int celula) {
		try {
			Query query = getEntityManager().createQuery(
					"select  c.discipuloses from Celulas c where c.celCod = "+ celula +")");
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
	public List<Discipulos> listarDiscipulador(int discipulo) {
		try {
			Query query = getEntityManager().createQuery(
					"From Discipulos d " + "where d.disCod ="+ discipulo );
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
	public List<Discipulos> listarM12Mensagem(int discipulador, int geracao) {
		try {
			Query query = getEntityManager().createQuery(
					"From Discipulos d " +
					"where d.discipulos.disCod = "+ discipulador + " and (d.dism12 = 's') " +
					"and (d.geracoes.gerCod = " + geracao + ")");
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
	public List<Discipulos> listarDiscipulosLiderPraCima(int discipulador) {
		try {
			Query query = getEntityManager().createQuery(
					"From Discipulos d where " +
					"d.discipulos.disCod = "+ discipulador + " and (d.dism12 = 's') " +
					"and funcaoeclesiasticas.funCod > 2");
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		} finally {
			getEntityManager().close();
		}
	}

	@Override
	public List<Discipulos> listarDiscipulosCasados(String nome, char sexo ){
		try {
			Query query = getEntityManager().createQuery(
					"select discipulos from Discipulos as discipulos " +
					"where discipulos.disnome like '"+nome+"%' " +
					"and discipulos.disSexo <> '"+sexo +"' "+
					" and discipulos.estadocivil.estCod = 2 " +
					" order by discipulos.disnome"
					);
					
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			
			return null;
		} finally {
			getEntityManager().close();
		}
	}

}
