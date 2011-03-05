/**
 * 
 */
package br.com.pais.util;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.com.pais.fileupload.MensagemAnexoFileUpload;
import br.com.pais.mensagens.MessageManagerImpl;

/**
 * @author Manoel
 * 
 */
public class SendEMail {

	private static Multipart createHtmlContent(String conteudo, String path)throws MessagingException {
		MimeMultipart multipart = new MimeMultipart("related");

		String html = "";
		html += conteudo;
		
		BodyPart mainPart = new MimeBodyPart();
		mainPart.setContent(html, "text/html; charset=UTF-8;"); // Adiciona conteúdo HTML
		mainPart.setHeader("X-Priority","1");
		mainPart.setHeader("Priority","Urgent");
		mainPart.setHeader("Importance","high");
		multipart.addBodyPart(mainPart);

		BodyPart imagePart = new MimeBodyPart();
		DataSource imgFds = new FileDataSource(path + "/logo.png");
		imagePart.setDataHandler(new DataHandler(imgFds));
		imagePart.setHeader("Content-ID", "<image>"); // Adiciona a imagem ao email
		multipart.addBodyPart(imagePart);

		return multipart;
	}

	public void sendSimpleMailEnviarSenha(String funcEclesiastica, String nome, String email, String senha, String cpf) {
		String html = "";
		html += "<html>" + 
				"<meta charset=\"UTF-8\">"+
				"<body>"	+ 
				"<img src='cid:image'></img>" + 
				"<hr/>" + 
				"Graça e Paz <b> "+funcEclesiastica+" "+ nome +"</b><br/>" +
				"Seu cadastro foi realizado com sucesso. <br/>" +
				"Para acessar o <b> http://www.pais12.com </b> informe seu: <br/>" +
				
				"<b> CPF:</b> "+ cpf + "<br/>"+
				"<b> Senha:</b> "+ senha + "<br/>"+
				
				"<hr/>" + 
				"<br/>"	+ 
				"</body>" + 
				"<br/>"+ 
				"<footer>" +
				"<b>OBS:</b> Favor não responder essa mensagem."+ 
				"</footer>" + 
				"</html>";
		
		
		Properties config = new Properties();
		//config.setProperty("mail.debug", "true"); // Mostrar passo-a-passo no console
		config.setProperty("mail.transport.protocol", "smtp"); // Indica que será usado SMTPS
		config.setProperty("mail.smtp.host", "mail.pais12.com"); // Host do servidor de envio 
		//config.setProperty("mail.smtp.port", "25"); // Porta do servidor de envio
		config.setProperty("mail.smtp.auth", "true"); // Usa uma conta autenticada
		String path = this.getClass().getResource("/br/com/pais/util/").getPath();
		
		Session session = Session.getInstance(config);
		try {
			
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("info@pais12.com"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
			msg.setSubject("Confirmação de cadastro!");
			msg.setContent(createHtmlContent(html, path));

			Transport transport = session.getTransport();
			transport.connect("info@pais12.com", "06112218");

			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "info",
			"sucesso.email_detail");
		} catch (AddressException e) {
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "erro",
			"erro.emailDestinarioinvalido_detail");
			throw new IllegalArgumentException(
					"Email de destinatário inválido!");		
		} catch (MessagingException e) {
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "erro",
			"erro.email_detail");
		}
	}
	public void sendMailLembreteSenha(String funcEclesiastica, String nome, String email, String senha, String cpf) {
		String html = "";
		html += "<html>" + 
				"<meta charset=\"UTF-8\">"+
				"<body>"	+ 
				"<img src='cid:image'></img>" + 
				"<hr/>" + 
				"Graça e Paz <b> "+funcEclesiastica+" "+ nome +"</b><br/><br/>" +
				
				"Para acessar o <b><a href=\"http://www.pais12.com\">www.pais12.com</a> </b> informe seu: <br/>" +
				
				"<b> CPF:</b> "+ cpf + "<br/>"+
				"<b> Senha:</b> "+ senha + "<br/>"+
				
				"<hr/>" + 
				"<br/>"	+ 
				"</body>" + 
				"<br/>"+ 
				"<footer>" +
				"<b>OBS:</b> Favor não responder essa mensagem."+ 
				"</footer>" + 
				"</html>";
		
		
		Properties config = new Properties();
		//config.setProperty("mail.debug", "true"); // Mostrar passo-a-passo no console
		config.setProperty("mail.transport.protocol", "smtp"); // Indica que será usado SMTPS
		config.setProperty("mail.smtp.host", "mail.pais12.com"); // Host do servidor de envio 
		//config.setProperty("mail.smtp.port", "25"); // Porta do servidor de envio
		config.setProperty("mail.smtp.auth", "true"); // Usa uma conta autenticada
		String path = this.getClass().getResource("/br/com/pais/util/").getPath();
		
		Session session = Session.getInstance(config);
		try {
			
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("info@pais12.com"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(
					email));
			msg.setSubject("Lembrete de Senha!");
			msg.setContent(createHtmlContent(html, path));

			Transport transport = session.getTransport();
			transport.connect("info@pais12.com", "06112218");

			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "info",
			"sucesso.email_detail");
		} catch (AddressException e) {
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "erro",
			"erro.emailDestinarioinvalido_detail");
			throw new IllegalArgumentException(
					"Email de destinatário inválido!");		
		} catch (MessagingException e) {
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "erro",
			"erro.email_detail");
		}
	}
	
	public void sendSimpleMailEnviarMensagem(String diretorioImg, String nomeEnviou, String nomeRecebe, String emailEnviar) {
		String html = "";
		html += "<html>" + 
				"<meta charset=\"UTF-8\">"+
				"<body>"	+ 
				"<img src='cid:image'></img>" + 
				"<hr/>" + 
				"Graça e Paz <b> "+ nomeRecebe +" </b> <br/>" +
				"Você Recebeu uma Mensagem de <b> "+ nomeEnviou +" </b> <br/>" +
				"Para ver a mensagem acesse <b> <a href='http://www.pais12.com'>http://www.pais12.com</a> </b><br/>" +
				
				"<hr/>" + 
				"<br/>"	+ 
				"</body>" + 
				"<br/>"+ 
				"<footer>" +
				"<b>OBS:</b> Favor não responder essa mensagem."+ 
				"</footer>" + 
				"</html>";
		
		Properties config = new Properties();
		config.setProperty("mail.debug", "true"); // Mostrar passo-a-passo no console
		config.setProperty("mail.transport.protocol", "smtp"); // Indica que será usado SMTPS
		config.setProperty("mail.smtp.host", "mail.pais12.com"); // Host do servidor de envio 
		config.setProperty("mail.smtp.port", "25"); // Porta do servidor de envio
		config.setProperty("mail.smtp.auth", "true"); // Usa uma conta autenticada
		
		Session session = Session.getInstance(config);
		try {
			
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("info@pais12.com"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailEnviar));
			msg.setSubject("Mensagem do PAIS!");
			msg.setContent(createHtmlContentEmail(html, diretorioImg));

			Transport transport = session.getTransport();
			transport.connect("info@pais12.com", "06112218");

			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			//MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "info","sucesso.email_detail");
		} catch (AddressException e) {
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "erro","erro.emailDestinarioinvalido_detail");
			throw new IllegalArgumentException("Email de destinatario invalido!");		
		} catch (MessagingException e) {
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "erro","erro.email_detail");
		}
	}
	
	private static Multipart createHtmlContentEmail(String conteudo, String diretorio)throws MessagingException {
		MimeMultipart multipart = new MimeMultipart("related");

		String html = "";
		html += conteudo;

		BodyPart mainPart = new MimeBodyPart();
		mainPart.setContent(html, "text/html; charset=UTF-8;"); // Adiciona conteúdo HTML
		multipart.addBodyPart(mainPart);
		
	    //Anexa a Logo no HTML
		BodyPart imagePart = new MimeBodyPart();
		DataSource imgFds = new FileDataSource(diretorio + "\\logoEmail.png");
		imagePart.setDataHandler(new DataHandler(imgFds));
		imagePart.setHeader("Content-ID", "<image>"); // Adiciona a imagem ao email
		multipart.addBodyPart(imagePart);
		
		return multipart;
	}
	
	public void sendSimpleMailteste(String nomeEnviou, String nomeRecebe, String emailEnviar, 
	String mensagem, List<MensagemAnexoFileUpload> listFileUpload, String diretorio) {
		String html = "";
		html += "<html>" + 
				"<meta charset=\"UTF-8\">"+
				"<body>"	+ 
				"<img src='cid:image'></img>" + 
				"<hr/>" + 
				"Graça e Paz <b> "+ nomeRecebe +" </b> <br/>" +
				"Você Recebeu uma Mensagem de <b> "+ nomeEnviou +" </b> <br/>" +
				"Para ver a mensagem acesse <b> http://www.pais12.com </b><br/>" +
				"<hr/>" + 
				"<br/>"	
				+ mensagem +
				"</body>" + 
				"<br/>"+ 
				"<footer>" +
				"<b>OBS:</b> Favor não responder essa mensagem."+ 
				"</footer>" + 
				"</html>";
		
		Properties config = new Properties();
		config.setProperty("mail.debug", "true"); // Mostrar passo-a-passo no console
		config.setProperty("mail.transport.protocol", "smtp"); // Indica que será usado SMTPS
		config.setProperty("mail.smtp.host", "mail.pais12.com"); // Host do servidor de envio 
		config.setProperty("mail.smtp.port", "25"); // Porta do servidor de envio
		config.setProperty("mail.smtp.auth", "true"); // Usa uma conta autenticada
		String path = "C:\\teste";
		
		Session session = Session.getInstance(config);
		try {
			
			MimeMessage msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress("info@pais12.com"));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailEnviar));
			msg.setSubject("Mensagem do PAIS!");
			msg.setContent(createHtmlContentTeste(html, path));

			Transport transport = session.getTransport();
			transport.connect("info@pais12.com", "06112218");

			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "info","sucesso.email_detail");
		} catch (AddressException e) {
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "erro","erro.emailDestinarioinvalido_detail");
			throw new IllegalArgumentException("Email de destinatario invalido!");		
		} catch (MessagingException e) {
			MessageManagerImpl.setMensagem(FacesMessage.SEVERITY_INFO, "erro","erro.email_detail");
		}
	}
	
	private static Multipart createHtmlContentTeste(String conteudo, String path)throws MessagingException {
		MimeMultipart multipart = new MimeMultipart("related");

		String html = "";
		html += conteudo;

		BodyPart mainPart = new MimeBodyPart();
		mainPart.setContent(html, "text/html; charset=UTF-8;"); // Adiciona conteúdo HTML
		multipart.addBodyPart(mainPart);
		
	    //Anexa a Logo no HTML
		BodyPart imagePart = new MimeBodyPart();
		DataSource imgFds = new FileDataSource(path + "/logo.png");
		imagePart.setDataHandler(new DataHandler(imgFds));
		imagePart.setHeader("Content-ID", "<image>"); // Adiciona a imagem ao email
		multipart.addBodyPart(imagePart);
		
		//Anexos
		File file = new File(path + "/ajax-loader.gif");  
	    MimeBodyPart bodyPartAnexo = new MimeBodyPart();  
	    bodyPartAnexo.setDataHandler(new DataHandler(new FileDataSource(file)));  
	    bodyPartAnexo.setFileName(file.getName());  
	    multipart.addBodyPart(bodyPartAnexo);
		
		return multipart;
	}
	
}