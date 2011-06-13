package br.com.pais.dao;

import br.com.pais.entities.Usuarios;


public interface UsuarioDao extends DaoGenerico<Usuarios, Integer> {
	public Usuarios encontrarPorlogin(String login);

}
