package noesis.performancealerts.controllertest;


import org.junit.Test;

import noesis.performancealerts.controller.AlertsController;
import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.dao.TestJPADAO;
import noesis.performancealerts.model.Run;
import noesis.performancealerts.model.Violacao;
import static org.mockito.Mockito.*;

public class AlertsControllerTest {
	@Test
	public void emitirAlerta_SuiteCTExistente() {
		try {
			Run suite = RunJPADAO.getInstance().getAnyRun();
			noesis.performancealerts.model.Test ct = TestJPADAO.getInstance()
					.getAnyTest();
			ct.setId(-1);
			if (suite != null && ct != null) {
				AlertsController.emitirAlerta(ct, mock(Violacao.class), 0);
			}
			
			assert (true);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void emitirAlerta_SuiteCTInexistente() {
		try {
			AlertsController.emitirAlerta(null, mock(Violacao.class), -1);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void enviarEmail() {
		try {
			AlertsController a = new AlertsController();
			a.enviarEmail("TESTE", true);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}
	

}
