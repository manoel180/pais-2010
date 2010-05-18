package br.com.pais.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.pais.dao.DaoGenerico;

// Default is read only

public class DaoGenericoImp<T, ID extends Serializable> implements DaoGenerico<T, ID> {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pais");

	private EntityManager entityManager = emf.createEntityManager();

	private final Class<T> oClass;// object class	

	//fica
	private EntityManager getEntityManager() {
		return emf.createEntityManager();
	}


	public Class<T> getObjectClass() {
		return this.oClass;
	}

	@SuppressWarnings("unchecked")
	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}
	
	//Não Mecher
	@SuppressWarnings("unchecked")
	public DaoGenericoImp() {
		this.oClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

	}

	//Falta Atualizar
	@Override
	public T atualizar(T object) {
		getEntityManager().merge(object);
		return object;

	}

	//Falta Atualizar
	@Override
	public void excluir(T object) {
		object = getEntityManager().merge(object);
		getEntityManager().remove(object);
	}

	//Falta Atualizar
	@Override
	public T pesquisarPorId(ID id) {
		return (T) getEntityManager().find(oClass, id);
	}

	//Falta Finalizar
	@Override
	public T salvar(T object) {
		/*
		 * getEntityManager().clear(); getEntityManager().persist(object);
		 */EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(object);
			em.getTransaction().commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();

		} finally {
			em.close();
		}
		return object;
	}

	//Falta Atualizar
	@SuppressWarnings("unchecked")
	public List<T> todos() {
		String queryS = "SELECT obj FROM " + oClass.getSimpleName() + " obj";
		Query query = getEntityManager().createQuery(queryS);
		return query.getResultList();

	}

	//Falta Atualizar
	@SuppressWarnings("unchecked")
	public List<T> listPesqParam(String query, Map<String, Object> params) {
		Query q = getEntityManager().createQuery(query);
		for (String chave : params.keySet()) {
			q.setParameter(chave, params.get(chave));

		}
		return q.getResultList();
	}

	//Falta Atualizar
	@SuppressWarnings("unchecked")
	public List<T> listPesqParam(String query, Map<String, Object> params,
			int maximo, int atual) {
		Query q = getEntityManager().createQuery(query).setMaxResults(maximo)
				.setFirstResult(atual);

		for (String chave : params.keySet()) {
			q.setParameter(chave, params.get(chave));

		}
		return q.getResultList();
	}

	//Falta Atualizar
	@SuppressWarnings("unchecked")
	public List<T> listPesq(String query) {
		Query q = getEntityManager().createQuery(query);
		return q.getResultList();
	}

	//Falta Atualizar
	@SuppressWarnings("unchecked")
	public T pesqParam(String query, Map<String, Object> params) {
		Query q = getEntityManager().createQuery(query);
		for (String chave : params.keySet()) {
			q.setParameter(chave, params.get(chave));

		}
		try {
			return (T) q.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

}
