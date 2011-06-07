package br.com.pais.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.com.pais.dao.DaoGenerico;
import br.com.pais.mensagens.MessageManagerImpl;

// Default is read only

public class DaoGenericoImp<T, ID extends Serializable> implements DaoGenerico<T, ID> {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("pais");

	private EntityManager entityManager = emf.createEntityManager();

	private final Class<T> oClass;// object class

	// fica
	public EntityManager getEntityManager() {
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

	// NÃ£o Mecher
	@SuppressWarnings("unchecked")
	public DaoGenericoImp() {
		this.oClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

	}

	@Override
	public boolean atualizar(T object) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(object);
			em.getTransaction().commit();
		}
		catch (PersistenceException e) {
			// TODO: handle exception
			e.getCause();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}
		return true;
	}
	
	@Override
	public void excluir(T object, ID id ) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(em.getReference(object.getClass(),id));
			em.flush();
			em.getTransaction().commit();
			//MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO,	"sucesso.exclusao_detail", "sucesso.exclusao_detail");
		}
		catch (PersistenceException ex) {
			ex.printStackTrace();
			//MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro.exclusao_datail", "erro.exclusao_datail");
			em.getTransaction().rollback();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			//MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro.exclusao_datail", "erro.exclusao_datail");
			em.getTransaction().rollback();
		}finally {
			em.close();
		}
	}

	// Falta Atualizar
	@Override
	public T pesquisarPorId(ID id) {
		return getEntityManager().find(oClass, id);
	}

	@Override
	public boolean salvar(T object)  {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(object);
			em.getTransaction().commit();
		}
		catch (PersistenceException e) {
			// TODO: handle exception
			e.getCause();
			em.getTransaction().rollback();
			return false;
		}
		finally {
			em.close();
		}
		return true;
	}

	// Falta Atualizar
	@SuppressWarnings("unchecked")
	public List<T> todos() {
		try {
			String queryS = "SELECT obj FROM " + oClass.getSimpleName()
					+ " obj";
			Query query = getEntityManager().createQuery(queryS);
			return query.getResultList();
		} catch (NoResultException nre) {
			return null;
		}
	}

	// Falta Atualizar
	@SuppressWarnings("unchecked")
	public List<T> listPesqParam(String query, Map<String, Object> params) {
		Query q = getEntityManager().createQuery(query);
		for (String chave : params.keySet()) {
			q.setParameter(chave, params.get(chave));

		}
		return q.getResultList();
	}

	// Falta Atualizar
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

	// Falta Atualizar
	@SuppressWarnings("unchecked")
	public List<T> listPesq(String query) {
		Query q = getEntityManager().createQuery(query);
		return q.getResultList();
	}

	// Falta Atualizar
	@SuppressWarnings("unchecked")
	public T pesqParam(String query, Map<String, String> params) {
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
