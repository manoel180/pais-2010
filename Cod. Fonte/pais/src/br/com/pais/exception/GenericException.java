package br.com.pais.exception;


public class GenericException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8275122096296509180L;

	public GenericException(String msg) {
		//super(msg);
		//MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR,	"erro", msg);
	}

	public GenericException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public GenericException(Throwable throwable) {
		super(throwable);
	}

}
