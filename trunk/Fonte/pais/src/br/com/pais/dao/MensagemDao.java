package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Discipulos;
import br.com.pais.entities.Mensagem;

public interface MensagemDao extends DaoGenerico<Mensagem, Integer>{
	List<Mensagem> listarMensagensRecebidas(Discipulos discipulo);
	List<Mensagem> listarCaixaEntrada(Discipulos discipulo);
	List<Mensagem> listarCaixaSaida(Discipulos discipulo);
}
