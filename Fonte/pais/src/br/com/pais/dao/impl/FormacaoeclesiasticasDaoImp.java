package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.FormacaoeclesiasticasDao;
import br.com.pais.entities.Formacaoeclesiasticas;

public class FormacaoeclesiasticasDaoImp extends DaoGenericoImp<Formacaoeclesiasticas, Integer> implements FormacaoeclesiasticasDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Formacaoeclesiasticas> listaFormacaoPorTipo(int formacao) {
		try {
			Query query =  getEntityManager().createQuery("from Formacaoeclesiasticas f " +
			"where f.forEcCod = "+formacao); 
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
