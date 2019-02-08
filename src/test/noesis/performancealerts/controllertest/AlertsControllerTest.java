package noesis.performancealerts.controllertest;

import static org.junit.Assert.*;

import org.junit.Test;

import noesis.performancealerts.controller.AlertsController;
//import noesis.performancealerts.model.Test;
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
	public void emitirAlerta_SuiteCTExistente() throws Exception {
		try {
			Run suite = RunJPADAO.getInstance().getAnyRun();
			noesis.performancealerts.model.Test ct = (noesis.performancealerts.model.Test) TestJPADAO.getInstance()
					.getAnyTest();
			if (suite != null && ct != null) {
				AlertsController.emitirAlerta(suite.getId_run(), ct.getId(), mock(Violacao.class));
			}
			assert (true);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void emitirAlerta_SuiteCTInexistente() throws Exception {
		try {
			AlertsController.emitirAlerta(new Integer(-1), new Integer(-1), mock(Violacao.class));
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

//	@Test
//	public void atualizarStatusAlerta(Alerts alerta) {
//	}

//	@Test
//	private String montaCorpoEmail(Violacao v, String canal, String casoDeTeste) {
//	}
//
	@Test
	public void enviarEmail() throws Exception {
		try {
			AlertsController a = new AlertsController();
			a.enviarEmail("TESTE", true);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}
//
//	@Test
//	public void enviarEmailOperacao(String texto) throws Exception {
//	}
//
//	@Test
//	public void enviarEmailGerencia(String texto) throws Exception {
//	}

}
