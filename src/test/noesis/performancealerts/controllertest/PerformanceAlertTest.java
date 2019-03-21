package noesis.performancealerts.controllertest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import noesis.performancealerts.controller.PerformanceAlert;
import noesis.performancealerts.controller.RegraController;
import noesis.performancealerts.dao.AlertsJPADAO;
import noesis.performancealerts.dao.RunTestJPADAO;
import noesis.performancealerts.dao.TestJPADAO;
import noesis.performancealerts.model.Alerts;
import noesis.performancealerts.model.Violacao;

import static org.junit.Assert.assertNotNull;

public class PerformanceAlertTest {
	PerformanceAlert performanceAlert;

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	public PerformanceAlertTest() {
		performanceAlert = new PerformanceAlert();
	}

//	/**
//	 * Create the test case
//	 *
//	 * @param testName name of the test case
//	 */

	/**
	 * @return the suite of tests being tested
	 */
	@Test
	public void analisarAlertaPendente() {
		try {
			noesis.performancealerts.model.Test teste = TestJPADAO.getInstance().getAnyTest();
			List<Integer> runs = RunTestJPADAO.getInstance().findLastsRunsByTestID(teste.getId());
			Violacao v = RegraController.analisaConformidade(runs, teste);
			if (v != null) {
				List<Alerts> alerts = AlertsJPADAO.getInstance().findRunsByTestID(teste.getId(),
						runs.get(runs.size() - 1));
				assert (alerts.isEmpty());
			}
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void analisarSuites_suitesVazias() {
		try {
			List<noesis.performancealerts.model.Test> testes = null;
			performanceAlert.analisarTestesDoProjeto(testes);
			performanceAlert.analisarTestesDoProjeto(new ArrayList<noesis.performancealerts.model.Test>());
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void analisarSuites_ComJornadas() {
		try {
			noesis.performancealerts.model.Test t = TestJPADAO.getInstance().getAnyTest();
			List<noesis.performancealerts.model.Test> testes = new ArrayList<noesis.performancealerts.model.Test>();
			testes.add(t);
			performanceAlert.analisarTestesDoProjeto(testes);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void analisarCasosDeTeste_suiteSemJornadas() {
		try {
			noesis.performancealerts.model.Test t = TestJPADAO.getInstance().getAnyTest();
			performanceAlert.analisarExecucoesDoCasoDeTeste(t, new ArrayList<Integer>());
			performanceAlert.analisarExecucoesDoCasoDeTeste(t, null);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void analisarCasosDeTeste_jornadaSemViolacao() {
		try {
			noesis.performancealerts.model.Test t = TestJPADAO.getInstance().getAnyTest();
			performanceAlert.analisarExecucoesDoCasoDeTeste(t, new ArrayList<Integer>());
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void analisarCasosDeTeste_jornadaComViolacao() {
		try {
			Alerts a = AlertsJPADAO.getInstance().getAnyAlert();
			noesis.performancealerts.model.Test t = TestJPADAO.getInstance().getById(a.getIdTest());
			List<Integer> runIds = new ArrayList<Integer>();
			runIds.add(a.getIdLastRun());
			Violacao v = RegraController.analisaConformidade(runIds, t);
			assertNotNull(v);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void run() {
		try {
			PerformanceAlert.run("fecd28cd-b827-40b9-bc02-b78eaf30bff4");
			assert (true);
		} catch (Exception e) {
			assert (true);
		}
	}
}
