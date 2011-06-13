package br.com.pais.exception;

public class ValidarCPFException extends Exception {

	private static final long serialVersionUID = 1L;

	public ValidarCPFException(String msg) {
		super(msg);
	}

	public ValidarCPFException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ValidarCPFException(Throwable throwable) {
		super(throwable);
	}

}
