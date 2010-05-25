package br.com.pais.exception;

import javax.faces.application.FacesMessage;

import br.com.pais.mensagens.MessageManagerImpl;

public class ValidarCPFException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8275122096296509180L;

	public ValidarCPFException(String msg) {
		super(msg);
		MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR,
				"erro", "erro.cpf.invalido");
	}

	public ValidarCPFException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ValidarCPFException(Throwable throwable) {
		super(throwable);
	}

}
