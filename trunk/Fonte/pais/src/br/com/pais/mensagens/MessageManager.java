/**
 * 
 */
package br.com.pais.mensagens;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;



/**
 * @author Cl� dos Souza
 * 
 */
public class MessageManager {

	protected void setMensagem(FacesMessage.Severity severidade, String mensagem, String detalhes) {
		FacesContext contexto = FacesContext.getCurrentInstance();
		FacesMessage facesMensagem = new FacesMessage(
				severidade, PropertiesLoaderImpl
						.getValor(mensagem), PropertiesLoaderImpl
						.getValor(detalhes));
		contexto.addMessage(null, facesMensagem);
		
	}

}
