/**
 * 
 */
package br.com.pais.util;

import javax.faces.application.FacesMessage;

import br.com.caelum.stella.validation.InvalidStateException;
import br.com.caelum.stella.validation.TituloEleitoralValidator;
import br.com.pais.exception.ValidarTEException;
import br.com.pais.mensagens.MessageManagerImpl;

/**
 * @author Manoel
 * 
 */
public class ValidarTituloEleitor {

	
	public static boolean validarTE(String te) throws ValidarTEException  {
		
		TituloEleitoralValidator validator = new TituloEleitoralValidator();
		boolean valido = true;
		try {
			if (te.equals("111111111111") || te.equals("222222222222")
					|| te.equals("333333333333")
					|| te.equals("444444444444")
					|| te.equals("555555555555")
					|| te.equals("666666666666")
					|| te.equals("777777777777")
					|| te.equals("888888888888")
					|| te.equals("999999999999")
					|| te.equals("000000000000")) {
				valido=false;
				MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR,	"erro", "erro.te.invalido");
				throw new ValidarTEException("Título Eleitor Inválido");  
			} else {
				validator.assertValid(te);

			}
		}catch (InvalidStateException e) {
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",
					"erro.te.invalido");
			valido = false;
		}
		return valido;
	}

}