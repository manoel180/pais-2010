package br.com.pais.dao;

import java.util.List;

import br.com.pais.entities.Dadosencontros;

public interface DadosEncontrosDao extends DaoGenerico<Dadosencontros, Integer> {
	public List<Dadosencontros> listarPreEncontros(int responsavel, int status);
	public List<Dadosencontros> listarEncontrosFilhos(int encontropai);
	public List<Dadosencontros> listarEncontrosComDeus(int responsavel, int status);
	public List<Dadosencontros> listarPosEncontros(int responsavel, int status);
}
