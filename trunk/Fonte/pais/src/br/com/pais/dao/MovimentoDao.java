package br.com.pais.dao;

import java.util.Date;
import java.util.List;

import br.com.pais.entities.Movimento;

public interface MovimentoDao extends DaoGenerico<Movimento,Integer>{
	public List<Movimento> listarArvoreRepasses(int discipulo, int geracao, String tipo, String especie, Date inicio, Date fim);
	public List<Movimento> listarRepassesFilhos(String protocoloPai);
}
