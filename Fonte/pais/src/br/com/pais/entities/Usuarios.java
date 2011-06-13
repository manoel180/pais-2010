package br.com.pais.entities;

// Generated 11/05/2011 14:16:49 by Hibernate Tools 3.4.0.CR1

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Bairro generated by hbm2java
 */
@Entity
@Table(name = "usuarios")
public class Usuarios implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "idusuarios", unique = true, nullable = false)
	private int idusuarios;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "login", nullable = false, length = 14)	
	private String login;
	
	@Column(name = "email", nullable = false, length = 80)
	private String email;
	
	@Column(name = "senha", nullable = false, length = 32)
	private String senha;
	
	@Column(name = "foto", length = 100)
	private String foto;
	
	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}



	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}



	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}



	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}



	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}



	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}



	/**
	 * @param idusuarios the idusuarios to set
	 */
	public void setIdusuarios(int idusuarios) {
		this.idusuarios = idusuarios;
	}



	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}



	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}



	/**
	 * @return the idusuarios
	 */
	public int getIdusuarios() {
		return idusuarios;
	}



	/**
	 * @param foto the foto to set
	 */
	public void setFoto(String foto) {
		this.foto = foto;
	}



	/**
	 * @return the foto
	 */
	public String getFoto() {
		return foto;
	}

}
