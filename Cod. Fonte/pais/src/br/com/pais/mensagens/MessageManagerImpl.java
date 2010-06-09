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
	private static MessageManager messageManager =  new MessageManager();
	
	public static void setMensagem(FacesMessage.Severity severidade, String mensagem, String detalhes){
		messageManager.setMensagem(severidade, mensagem, detalhes);
	}

}
