package br.com.pais.dao.impl;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.LogradouroDao;
import br.com.pais.entities.Logradouro;


public class LogradouroDaoImp extends DaoGenericoImp<Logradouro, Integer> implements LogradouroDao{
	
	FacesContext facesContext = FacesContext.getCurrentInstance();  
	FacesMessage message;
	
	@Override
	public Logradouro encontrarPorCEP(String cep) { 
		FacesContext context = FacesContext.getCurrentInstance();
		try{
			Query query =  getEntityManager().createQuery("From Logradouro where cep = :cep"); 
			query.setParameter("cep", cep);  
			return (Logradouro) query.getSingleResult();		
		}catch (NoResultException nre) {
			// TODO: handle exception
			context.addMessage(null, new FacesMessage("Erro","CEP não encontrado. "));
			
			//message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Senha salva com sucesso","Senha salva com sucesso");
			//facesContext.addMessage(null, message);
			return new Logradouro();
		}finally{
			getEntityManager().close();
		}
	 
		} 
}
