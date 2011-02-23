package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Movimento;
import br.com.pais.entities.Repasse;

public interface RepasseDao extends DaoGenerico<Repasse,Integer> {
	List<Movimento> listarMovimentos(int lider, int geracao, String recebido, String tipo, String DataInicio, String DataFim);
	
	List<Movimento> totalMovimento(int celula, String tipo, String recebido, String DataInicio, String DataFim);
	
	List<Movimento> listarMovimentosTodosPeriodo(int celula, String tipo, String recebido, String DataInicio, String DataFim);
	
	List<Movimento> listarMovimentosEnviados(int lider, int geracao, String tipo, String DataInicio, String DataFim);
	
	List<Movimento> totalMovimentoEnviados(int celula, String tipo, String DataInicio, String DataFim);
	
	List<Movimento> listarMovimentosTodosPeriodoDetalhe(int celula, String tipo, String DataInicio, String DataFim);
	
	List<Movimento> listarMovimentoRelatorio(int movimento);
}
