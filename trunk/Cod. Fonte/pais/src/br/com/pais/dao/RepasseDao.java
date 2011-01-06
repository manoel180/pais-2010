package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Movimento;
import br.com.pais.entities.Repasse;

public interface RepasseDao extends DaoGenerico<Repasse,Integer> {
	List<Movimento> listarMovimentos(int lider, int geracao, String tipo, String DataInicio, String DataFim);
	
	List<Movimento> totalMovimento(int celula, String tipo, String DataInicio, String DataFim);
	
	List<Movimento> listarMovimentosTodosPeriodo(int celula, String tipo, String DataInicio, String DataFim);
}
