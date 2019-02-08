package utils;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Mail {

	public static Properties getProperties() {
		Properties props = new Properties();
		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		return props;
	}

	public static Session getSession(Properties props, final String auth) {
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("noesisportugal0@gmail.com", auth);
			}
		});
		return session;
	}

	public static Message montarMensagem(Session session, String remetente, String destinatarios, String assunto,
			String corpo) throws Exception {
		try {
			if (validaDadosEmail(session, remetente, destinatarios, assunto, corpo)) {
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(remetente)); // Remetente
				Address[] toUser = InternetAddress // Destinatário(s)
						.parse(destinatarios);
				message.setRecipients(Message.RecipientType.TO, toUser);
				message.setSubject(assunto);// Assunto
				message.setText(corpo);
				return message;
			}
			throw new Exception("[PerformanceTest] Dados incompletos para envio de email.");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean validaDadosEmail(Session session, String remetente, String destinatarios, String assunto,
			String corpo) {
		return session != null && !remetente.isEmpty() && !destinatarios.isEmpty() && !assunto.isEmpty()
				&& !corpo.isEmpty();
	}

	public static boolean enviarEmail(String corpo, String destinatarios, String assunto, boolean debug)
			throws Exception {
		final String remetente = "noesisportugal0@gmail.com";
		final String auth = "TimNoesis";
		Properties props = getProperties();
		Session session = getSession(props, auth);
		/** Ativa Debug para sessão */
		session.setDebug(debug);
		try {
			Message message = montarMensagem(session, remetente, destinatarios, assunto, corpo);
			/** Método para enviar a mensagem criada */
			if (Constants.TST_MODE)
				return true;
			Transport.send(message);
			return true;
		} catch (AuthenticationFailedException e) {
			throw new AuthenticationFailedException(
					"O acesso a conta do remetente foi bloqueado pelo Google ou encontra-se com credenciais de acesso incorretas.");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}
