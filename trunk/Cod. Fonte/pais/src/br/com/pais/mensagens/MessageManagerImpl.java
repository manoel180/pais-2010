/**
 * 
 */
package br.com.pais.mensagens;

import javax.faces.application.FacesMessage;

/**
 * @author Manoel
 *
 */
public class MessageManagerImpl {
//	private 
	
	public static void setMensagem(FacesMessage.Severity severidade, String mensagem, String detalhes){
		MessageManager messageManager =  new MessageManager();
		messageManager.setMensagem(severidade, mensagem, detalhes);
	}

}
