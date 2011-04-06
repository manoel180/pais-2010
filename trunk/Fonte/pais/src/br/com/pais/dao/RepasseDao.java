package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Movimento;
import br.com.pais.entities.Repasse;

public interface RepasseDao extends DaoGenerico<Repasse,Integer> {
	
	//Receber Movimentos
	List<Movimento> listarMovimentosReceber(int lider, int geracao, String recebido, String tipo, String especie, String DataInicio, String DataFim);
	List<Object> totalMovimentosReceber(int celula, String tipo, String especie, String recebido, String DataInicio, String DataFim);
	List<Movimento> listarMovimentosTodosReceber(int celula, String tipo, String especie, String recebido, String DataInicio, String DataFim);
	//Enviados
	List<Movimento> listarMovimentosEnviados(int lider, int geracao, String tipo, String especie, String DataInicio, String DataFim);
	List<Object> totalMovimentosEnviados(int celula, String tipo, String especie, String DataInicio, String DataFim);
	List<Movimento> listarMovimentosTodosEnviados(int celula, String tipo, String especie, String DataInicio, String DataFim);
	//Recebidos
	List<Movimento> listarMovimentosRecebdidos(int lider, int geracao, String recebido, String tipo, String especie, String DataInicio, String DataFim);
	List<Object> totalMovimentosRecebidos(int celula, String tipo, String especie, String recebido, String DataInicio, String DataFim);
	List<Movimento> listarMovimentosTodosRecebidos(int celula, String tipo, String especie, String recebido, String DataInicio, String DataFim);
	
	List<Movimento> listarMovimentoRelatorio(int movimento);
	List<Movimento> listarMovimentosLembrarPrincipal(int discipulo);
	
	List<Object> trasCodigoDiscipuloRepasse(int movimento);
}
