/**
 * 
 */
package br.com.pais.util;

import javax.faces.application.FacesMessage;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.caelum.stella.validation.TituloEleitoralValidator;
import br.com.pais.exception.ValidarCPFException;
import br.com.pais.mensagens.MessageManagerImpl;

/**
 * @author Manoel
 * 
 */
public class ValidarCPF {

	
	public static boolean validarCPF(String cpf) throws ValidarCPFException  {
		
		CPFValidator validator = new CPFValidator();
		boolean valido = true;
		try {
			if (cpf.equals("111.111.111-11") || cpf.equals("222.222.222-22")
					|| cpf.equals("333.333.333-33")
					|| cpf.equals("444.444.444-44")
					|| cpf.equals("555.555.555-55")
					|| cpf.equals("666.666.666-66")
					|| cpf.equals("777.777.777-77")
					|| cpf.equals("888.888.888-88")
					|| cpf.equals("999.999.999-99")
					|| cpf.equals("000.000.000-00")) {
				valido=false;
				MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR,	"erro", "erro.cpf.invalido");
				throw new ValidarCPFException("CPF Inválido");  
			} else {
				validator.assertValid(cpf);

			}
		}catch (InvalidStateException e) {
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_ERROR, "erro",
					"erro.cpf.invalido");
			valido = false;
		}
		return valido;
	}

}