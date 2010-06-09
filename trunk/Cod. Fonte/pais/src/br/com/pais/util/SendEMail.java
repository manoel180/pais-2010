/**
 * 
 */
package br.com.pais.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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

import br.com.pais.mensagens.MessageManagerImpl;

/**
 * @author Manoel
 * 
 */
@ManagedBean(name = "email")
@RequestScoped
public class SendEMail {

	private static Multipart createHtmlContent(String conteudo, String path)
			throws MessagingException {
		MimeMultipart multipart = new MimeMultipart("related");

		String html = "";
		html += "<html>" + 
				"<body style='background-color: blue'>"	+ 
				"<img src='cid:image'></img>" + 
				"<hr/>" + 
				"<p>" + conteudo+ "</p>" + 
				"<hr/>" + 
				"<br/>"	+ 
				"Para contato: laerton@pais12.com" + 
				"</body>" + 
				"<br/>"+ 
				"<footer>" + 
					"Favor não responder essa mensagem."+ 
				"</footer>" + 
				"</html>";

		BodyPart mainPart = new MimeBodyPart();
		mainPart.setContent(html, "text/html; charset=UTF-8;"); // Adiciona conteúdo HTML
		multipart.addBodyPart(mainPart);

		BodyPart imagePart = new MimeBodyPart();
		DataSource imgFds = new FileDataSource(path + "/logo.png");
		imagePart.setDataHandler(new DataHandler(imgFds));
		imagePart.setHeader("Content-ID", "<image>"); // Adiciona a imagem ao email
		multipart.addBodyPart(imagePart);

		return multipart;
	}

	public void sendSimpleMail(String email, String texto) {
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
			msg.setSubject("Lembrete de senha!");
			msg.setContent(createHtmlContent(texto, path));

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

}