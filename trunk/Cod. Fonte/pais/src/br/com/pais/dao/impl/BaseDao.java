package br.com.pais.dao.impl;



public class BaseDao {
	/*private static EntityManagerFactory emf;

	private EntityManager getEntityManager(){
		return emf.createEntityManager();
	}

	public BaseDao(){
		emf =
			Persistence.createEntityManagerFactory("tutorial");
	}


	// METODO PARA PERSISTIR
	public boolean create(T obj){
		EntityManager em = getEntityManager();
		try{
			em.getTransaction().begin();
			em.persist(obj);
			em.getTransaction().commit();
			return true;
		}
		catch (Exception ex){
			ex.printStackTrace();
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}
	}


	// METODO PARA ATUALIZAR
	public boolean update(T obj){
		EntityManager em = getEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(obj);
			em.getTransaction().commit();
			return true;
		}
		catch (Exception ex){
			ex.printStackTrace();
			em.getTransaction().rollback();
			return false;
		}
		finally {
			em.close();
		}
	}


	// METODO PARA EXCLUIR
	public boolean delete(T obj){
		EntityManager em = getEntityManager();
		try{
			em.getTransaction().begin();
			obj = em.merge(obj);
			em.remove(obj);
			em.getTransaction().commit();
			return true;
		}
		catch (Exception ex){
			ex.printStackTrace();
			em.getTransaction().rollback();
			return false;
		} finally {
			em.close();
		}
	}


	// METODO QUE RETORNA UMA LISTA DE CLIENT
	public List allClient(){
		EntityManager em = getEntityManager();
		try{
			Query usu = em.createQuery("select object(c) from Client as c");
			return usu.getResultList();
		}catch(Exception e){
			return null;
		}finally {
			em.close();
		}
	}



	// METODO QUE RETORNA UMA LISTA DE CITY
	public List allCity(){
		EntityManager em = getEntityManager();
		try{
			Query cty = em.createQuery("select object(ct) from City as ct");
			return cty.getResultList();
		}catch(Exception e){
			return null;
		}finally {
			em.close();
		}
	}



	// METODO QUE RETORNA UMA LISTA DE NOMES DE CIDADES
	@SuppressWarnings("unchecked")
	public List listCity(){
		EntityManager em = getEntityManager();
		try{
			Query city = em.createQuery("select ct.city from City as ct");
			List list0 = city.getResultList();
			List list = new ArrayList();
			int i;
			for (i=0; i <>



			// METODO QUE RETORNA UM OBJETO CITY PEGANDO COMO PARAMETO O NOME DA CIDADE
			@SuppressWarnings("unchecked")
			public T findCityByName(String cty){
				EntityManager em = getEntityManager();
				try{
					Query city = em.createQuery("select object(ct) from City as ct where ct.city

							like ?1" ).setParameter(1,cty+"%");
									return (T) city.getSingleResult();
				}catch(Exception e){
					return null;
				}finally {
					em.close();
				}
			}*/
		}