package br.com.pais.dao.impl;


import javax.persistence.Query;

import br.com.pais.dao.DiscipuloDao;
import br.com.pais.entities.Discipulos;

public class DiscipuloDaoImp extends DaoGenericoImp<Discipulos, Integer> implements DiscipuloDao{
	
	@Override
	public Discipulos encontrarPorCPF(String cpf) { 
		Query query =  getEntityManager().createQuery("From Discipulos where disCPF = :cpf"); 
		query.setParameter("cpf", cpf);  
		return (Discipulos) query.getSingleResult(); 
		} 
}
