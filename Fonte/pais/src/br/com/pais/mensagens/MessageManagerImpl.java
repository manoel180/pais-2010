package br.com.pais.mensagens;

import javax.faces.application.FacesMessage;

public class MessageManagerImpl {
	
	public static void setMensagem(FacesMessage.Severity severidade, String mensagem, String detalhes){
		MessageManager messageManager =  new MessageManager();
		messageManager.setMensagem(severidade, mensagem, detalhes);
	}
}
