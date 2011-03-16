package br.com.pais.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.pais.dao.MovimentoDao;
import br.com.pais.entities.Movimento;

public class MovimentoDaoImp extends DaoGenericoImp<Movimento, Integer> implements MovimentoDao {
	
	@Override
	public List<Movimento> listarArvoreRepasses(int discipulo, int geracao, String tipo, String especie,
			Date inicio, Date fim) {
		try {
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<Movimento> select = criteriaBuilder.createQuery(Movimento.class);
			Root<Movimento> from = select.from(Movimento.class);
			
			Path<Object> join1 = from.join("celulas").get("discipulos").get("disCod");
			Predicate condition1 = criteriaBuilder.equal(join1, discipulo); 
			Path<Object> join2 = from.join("celulas").get("geracoes").get("gerCod");
			Predicate condition2 = criteriaBuilder.equal(join2, geracao); 
			Predicate condition3 = criteriaBuilder.equal(from.get("movTipo"), tipo);
			Predicate condition4 = criteriaBuilder.equal(from.get("movEspecie"), especie);
			Predicate condition5 = criteriaBuilder.between(from.get("movData").as(Date.class), inicio, fim);
			
			Predicate[] vet = new Predicate[5];
			vet[0] = condition1;
			vet[1] = null;
			vet[2] =  null;
			vet[3] = null;
			vet[4] = condition5;
				
			if(geracao > 0){
				vet[1] = condition2;
			}else{
				vet[1] = join1.isNotNull();
			}
				
			if(tipo == null || tipo == "" || tipo.equals("Todos")){
				vet[2] =  criteriaBuilder.isNotNull(from.get("movTipo"));
			}else{
				vet[2] =  condition3;
			}
				
			if(especie == null || especie == "" || especie.equals("Todos")){
				vet[3] = criteriaBuilder.isNotNull(from.get("movEspecie"));
			}else{
				vet[3] =  condition4;
			}
			
            select.where(vet); //Só aceitou um vetor de Predicate
			TypedQuery<Movimento> typedQuery = getEntityManager().createQuery(select); 
			List<Movimento> result = typedQuery.getResultList();
			return result;
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		}
		finally {
			getEntityManager().close();
		}
	}

	@Override
	public List<Movimento> listarRepassesFilhos(String protocoloPai) {
		try {
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<Movimento> select = criteriaBuilder.createQuery(Movimento.class);
			Root<Movimento> from = select.from(Movimento.class);
			Predicate condition = criteriaBuilder.equal(from.get("movProtocoloPai"), protocoloPai);
			select.where(condition);
			TypedQuery<Movimento> typedQuery = getEntityManager().createQuery(select); 
			List<Movimento> result = typedQuery.getResultList();
			return result;
		} catch (NoResultException nre) {
			// TODO: handle exception
			return null;
		}
		finally {
			getEntityManager().close();
		}
	}
}
