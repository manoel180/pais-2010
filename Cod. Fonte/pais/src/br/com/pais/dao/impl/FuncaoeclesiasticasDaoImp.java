package br.com.pais.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.pais.dao.FuncaoeclesiasticasDao;
import br.com.pais.entities.Funcaoeclesiasticas;



public class FuncaoeclesiasticasDaoImp extends DaoGenericoImp<Funcaoeclesiasticas, Integer> implements FuncaoeclesiasticasDao{

	@Override
	public List<Funcaoeclesiasticas> listarFuncaoPorSexo(char sexo, int nivel) { 
try{
		Query query =  getEntityManager().createQuery("From Funcaoeclesiasticas where funSexo= '"+sexo+"' and (funCod between  1 and "+ nivel+")"); 
		
		return query.getResultList(); 
		}
	catch (NoResultException nre) {
		// TODO: handle exception
		return null;
	}finally{
		getEntityManager().close();
	}}

	@Override
	public List<Funcaoeclesiasticas> listarFuncaoPorSexo(char sexo) {
		// TODO Auto-generated method stub
		try{
		Query query =  getEntityManager().createQuery("From Funcaoeclesiasticas where funSexo= '"+sexo+"'"); 
		return query.getResultList(); 
		}catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		}finally{
			getEntityManager().close();
		}
	} 
}
