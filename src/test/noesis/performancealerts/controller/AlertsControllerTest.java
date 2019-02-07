package noesis.performancealerts.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import noesis.performancealerts.dao.AlertsJPADAO;
import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.dao.TestJPADAO;
import noesis.performancealerts.model.Alerts;
import noesis.performancealerts.model.Run;
import noesis.performancealerts.model.Violacao;
import utils.Constants;
import utils.Mail;
import static org.mockito.Mockito.*;

public class AlertsControllerTest {

	@Test
	public void emitirAlerta() throws Exception {
		try {
			AlertsController.emitirAlerta(mock(Integer.class), mock(Integer.class), mock(Violacao.class));
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

//	@Test
//	public void atualizarStatusAlerta(Alerts alerta) {
//	}

//	@Test
//	private String montaCorpoEmail(Violacao v, String canal, String casoDeTeste) {
//	}
//
//	@Test
//	public void enviarEmail(String texto, boolean gravidadeCritica) throws Exception {
//
//	}
//
//	@Test
//	public void enviarEmailOperacao(String texto) throws Exception {
//	}
//
//	@Test
//	public void enviarEmailGerencia(String texto) throws Exception {
//	}

}
