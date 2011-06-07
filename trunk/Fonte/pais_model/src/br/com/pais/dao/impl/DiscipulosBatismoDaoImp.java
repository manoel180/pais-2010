package br.com.pais.dao.impl;

import javax.persistence.EntityManager;

import br.com.pais.dao.DiscipulosBatismoDao;
import br.com.pais.entities.Discipulosbatismo;
import br.com.pais.entities.DiscipulosbatismoId;

public class DiscipulosBatismoDaoImp extends DaoGenericoImp<Discipulosbatismo, Integer> implements DiscipulosBatismoDao{
	
	public void excluirPorChaves(DiscipulosbatismoId id) {
		EntityManager em = getEntityManager();
		 try {
			em.getTransaction().begin();
			em.createQuery("delete from Discipulosbatismo"+
			" where discipulos = " +id.getDiscipulo()+
			" and batismo = "+id.getBatismo()).executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
