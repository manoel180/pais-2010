package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.BancosDao;
import br.com.pais.entities.Bancos;

public class BancosDaoImp extends DaoGenericoImp<Bancos, Integer> implements BancosDao{

	@SuppressWarnings("unchecked")
	@Override
	public List<Bancos> listarBancosAtivos() {
		try {
			Query query =  getEntityManager().createQuery("from Bancos b where b.ativoSistema = 'S' order by b.descBanco"); 
			
			return query.getResultList();
		} catch (NoResultException nre) {
			// TODO: handle exception
			//MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro", "erro.discipulo.naoencontrado");
			return null;
		} finally {
			getEntityManager().close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bancos> listarBancoSelecionado(int banco) {
		try {
			Query query =  getEntityManager().createQuery("from Bancos b " +
					"where b.ativoSistema = 'S' and b.codBanco = :banco"); 
			query.setParameter("banco", banco);
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
