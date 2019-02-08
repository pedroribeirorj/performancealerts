package noesis.performancealerts.utilstest;
import static org.junit.Assert.*;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;

import org.junit.Test;

import utils.Constants;
import utils.Mail;

public class MailTest {

	@Test
	public void enviarEmail() throws Exception {		
		String corpo = "EMAIL DE TESTE UNITÁRIO";
		String destinatarios = "pedro.h.silva@noesis.pt";
		String assunto = "TESTE UNITARIO";
		Mail.enviarEmail(corpo, destinatarios, assunto, Constants.MAIL_DEBUG_MODE);
	}

	@Test
	public void montarMensagem_ok() {
		try {
			String corpo = "TESTE";
			String remetente = "pedro.h.silva@noesis.pt";
			String destinatarios = "pedro.h.silva@noesis.pt";
			String assunto = "TESTE UNITARIO";
			Session s = Mail.getSession(Mail.getProperties(), "TimNoesis");
			Message msg = Mail.montarMensagem(s, remetente, destinatarios, assunto, corpo);
			assertNotEquals(msg, null);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void montarMensagem_semCorpo() {
		try {
			String corpo = "";
			String remetente = "pedro.h.silva@noesis.pt";
			String destinatarios = "pedro.h.silva@noesis.pt";
			String assunto = "TESTE UNITARIO";
			Session s = Mail.getSession(Mail.getProperties(), "TimNoesis");
			Message msg = Mail.montarMensagem(s, remetente, destinatarios, assunto, corpo);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void montarMensagem_semRemetente() {
		try {
			String corpo = "TESTE";
			String remetente = "";
			String destinatarios = "pedro.h.silva@noesis.pt";
			String assunto = "TESTE UNITARIO";
			Session s = Mail.getSession(Mail.getProperties(), "TimNoesis");
			Message msg = Mail.montarMensagem(s, remetente, destinatarios, assunto, corpo);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void montarMensagem_semDestinatario() {
		try {
			String corpo = "TESTE";
			String remetente = "pedro.h.silva@noesis.pt";
			String destinatarios = "";
			String assunto = "TESTE UNITARIO";
			Session s = Mail.getSession(Mail.getProperties(), "TimNoesis");
			Message msg = Mail.montarMensagem(s, remetente, destinatarios, assunto, corpo);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void montarMensagem_semAssunto() {
		try {
			String corpo = "TESTE";
			String remetente = "pedro.h.silva@noesis.pt";
			String destinatarios = "pedro.h.silva@noesis.pt";
			String assunto = "";
			Session s = Mail.getSession(Mail.getProperties(), "TimNoesis");
			Message msg = Mail.montarMensagem(s, remetente, destinatarios, assunto, corpo);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void getSession() {
		try {
			String destinatarios = "pedro.h.silva@noesis.pt";
			Session s = Mail.getSession(Mail.getProperties(), "TimNoesis");
			assertNotEquals(s, null);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void getProperties() {
		try {
			Properties p = Mail.getProperties();
			assertNotEquals(p, null);
		} catch (Exception e) {
			assert (false);
		}
	}
}
