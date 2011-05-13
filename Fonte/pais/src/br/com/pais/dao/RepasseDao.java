package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Movimento;
import br.com.pais.entities.Repasse;

public interface RepasseDao extends DaoGenerico<Repasse,Integer> {
	
	/*-------------------------------------------CELULAS Á RECEBER--------------------------------------------------------*/
	List<Movimento> listarMovimentosReceberCelulas(int lider, int geracao, String recebido, String tipo, String especie, String DataInicio, String DataFim);
	List<Object> totalMovimentosReceberCelulas(int celula, String tipo, String especie, String recebido, String DataInicio, String DataFim);
	List<Movimento> listarMovimentosTodosReceberCelulas(int celula, String tipo, String especie, String recebido, String DataInicio, String DataFim);
	/*-------------------------------------------BASES Á RECEBER--------------------------------------------------------*/
	List<Movimento> listarMovimentosReceberBases(int lider, int geracao, String recebido, String tipo, String especie, String DataInicio, String DataFim);
	List<Object> totalMovimentosReceberBases(int base, String tipo, String especie, String recebido, String DataInicio, String DataFim);
	List<Movimento> listarMovimentosTodosReceberBases(int base, String tipo, String especie, String recebido, String DataInicio, String DataFim);
	
	/*-------------------------------------------CELULAS RECEBIDOS--------------------------------------------------------*/
	List<Movimento> listarMovimentosRecebidosCelulas(int lider, int geracao, String recebido, String tipo, String especie, String DataInicio, String DataFim);
	List<Object> totalMovimentosRecebidosCelulas(int celula, String tipo, String especie, String recebido, String DataInicio, String DataFim);
	List<Movimento> listarMovimentosTodosRecebidosCelulas(int celula, String tipo, String especie, String recebido, String DataInicio, String DataFim);
	/*-------------------------------------------BASES RECEBIDOS--------------------------------------------------------*/
	List<Movimento> listarMovimentosRecebidosBases(int lider, int geracao, String recebido, String tipo, String especie, String DataInicio, String DataFim);
	List<Object> totalMovimentosRecebidosBases(int base, String tipo, String especie, String recebido, String DataInicio, String DataFim);
	List<Movimento> listarMovimentosTodosRecebidosBases(int base, String tipo, String especie, String recebido, String DataInicio, String DataFim);
	
	/*-------------------------------------------CELULAS ENVIADOS--------------------------------------------------------*/
	List<Movimento> listarMovimentosEnviadosCelulas(int lider, int geracao, String tipo, String especie, String DataInicio, String DataFim);
	List<Object> totalMovimentosEnviadosCelulas(int celula, String tipo, String especie, String DataInicio, String DataFim);
	List<Movimento> listarMovimentosTodosEnviadosCelulas(int celula, String tipo, String especie, String DataInicio, String DataFim);
	/*-------------------------------------------BASES ENVIADOS--------------------------------------------------------*/
	List<Movimento> listarMovimentosEnviadosBases(int lider, String tipo, String especie, String DataInicio, String DataFim);
	List<Object> totalMovimentosEnviadosBases(int base, String tipo, String especie, String DataInicio, String DataFim);
	List<Movimento> listarMovimentosTodosEnviadosBases(int base, String tipo, String especie, String DataInicio, String DataFim);
	
	List<Movimento> listarMovimentoRelatorio(int movimento);
	List<Movimento> listarMovimentosLembrarCelulas(int discipulo);
	List<Movimento> listarMovimentosLembrarBases(int discipulo);
	
	List<Object> trasCodigoDiscipuloRepassePorCelula(int movimento);
	List<Object> trasCodigoDiscipuloRepassePorBase(int movimento);
}
