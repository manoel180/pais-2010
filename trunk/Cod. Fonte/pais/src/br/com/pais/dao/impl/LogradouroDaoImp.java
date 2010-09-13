package br.com.pais.dao.impl;

import javax.persistence.Query;

import br.com.pais.dao.LogradouroDao;
import br.com.pais.entities.Logradouro;


public class LogradouroDaoImp extends DaoGenericoImp<Logradouro, Integer> implements LogradouroDao{
	
	
	@Override
	public Logradouro encontrarPorCEP(String cep) { 
		Query query =  getEntityManager().createQuery("From Logradouro where cep2 = :cep"); 
		query.setParameter("cep", cep);  
		return (Logradouro) query.getSingleResult(); 
		} 
}
