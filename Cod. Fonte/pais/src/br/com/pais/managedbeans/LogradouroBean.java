/**
 * 
 */
package br.com.pais.managedbeans;

import br.com.pais.dao.LogradouroDao;
import br.com.pais.dao.impl.LogradouroDaoImp;
import br.com.pais.entities.Bairro;
import br.com.pais.entities.Estado;
import br.com.pais.entities.Localidade;
import br.com.pais.entities.Logradouro;

/**
 * @author manoel
 * 
 */
public class LogradouroBean extends DiscipuloBean{

	private Estado estado = new Estado();
	private Localidade cidade = new Localidade();
	private Bairro bairro = new Bairro();
	private Logradouro logradouro = new Logradouro();


	// Objetos Daos
	private LogradouroDao logradouroDao = new LogradouroDaoImp(); 

	//Buscar pelo cep
	public void buscarCEP(){
	
		String cep = getLogradouro().getCep2().substring(0, 5);
		cep += getLogradouro().getCep2().substring(6, 9);
		logradouro = logradouroDao.encontrarPorCEP(cep);
		//logradouro.getBairro().;
		//cidade = bairro.getLocalidade();
		//estado = cidade.getEstado();
		
	}
	

	/**
	 * GETTERS and SETTERS
	 */

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Localidade getCidade() {
		return cidade;
	}

	public void setCidade(Localidade cidade) {
		this.cidade = cidade;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}




	
}