package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.GeracaoDao;
import br.com.pais.entities.Geracoes;



public class GeracaoDaoImp extends DaoGenericoImp<Geracoes, Integer> implements GeracaoDao{

	@Override
	public List<Geracoes> listarGeracoes() { 
		try {
			Query query =  getEntityManager().createQuery("From Geracoes order by gerCod"); 
			
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			//MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro", "erro.discipulo.naoencontrado");
			return null;
		} finally {
			getEntityManager().close();
		}
	}	
}
