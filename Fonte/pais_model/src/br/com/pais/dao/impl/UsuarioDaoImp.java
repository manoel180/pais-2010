package br.com.pais.dao.impl;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.UsuarioDao;
import br.com.pais.entities.Usuarios;

public class UsuarioDaoImp extends DaoGenericoImp<Usuarios, Integer> implements UsuarioDao{

	@Override
	public Usuarios encontrarPorlogin(String login) {
		try {
			Query query = getEntityManager().createQuery(
					"From Usuarios where login = :login");
			query.setParameter("login", login);
			return (Usuarios) query.getSingleResult();
		} catch (NoResultException nre) {
			// TODO: handle exception
			//MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",	"erro.discipulo.naoencontrado");
			return null;
		} 
		finally {
			getEntityManager().close();
		}
	}

	
}
