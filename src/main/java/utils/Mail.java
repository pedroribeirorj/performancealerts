package utils;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

	public static boolean enviarEmail(String corpo, String destinatarios, String assunto) {
		final String remetente = "noesisportugal0@gmail.com";
		Properties props = new Properties();
		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(remetente, "TimNoesis");
			}
		});

		/** Ativa Debug para sessão */
		session.setDebug(false);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(remetente)); // Remetente

			Address[] toUser = InternetAddress // Destinatário(s)
					.parse(destinatarios);

			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(assunto);// Assunto
			message.setText(corpo);
			/** Método para enviar a mensagem criada */
			Transport.send(message);

			return true;

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
