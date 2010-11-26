package br.com.pais.exception;



public class ValidarTEException extends Exception {

	/**
	 * 
	 */
//	private static final long serialVersionUID = 8275122096296509180L;

	public ValidarTEException(String msg) {
		super(msg);
	
	}

	public ValidarTEException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ValidarTEException(Throwable throwable) {
		super(throwable);
	}

}
