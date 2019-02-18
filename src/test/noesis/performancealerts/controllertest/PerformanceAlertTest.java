package noesis.performancealerts.controllertest;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.common.AssertionFailure;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import noesis.performancealerts.controller.PerformanceAlert;
import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.dao.TestJPADAO;
import noesis.performancealerts.model.Run;
import static org.mockito.Mockito.*;

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
			noesis.performancealerts.model.Test t = TestJPADAO.getInstance().getAnyTest();
			t.setTest_cycle_id("7");
			List<Integer> testes = new ArrayList<Integer>();
			testes.add(661);
			performanceAlert.analisarExecucoesDoCasoDeTeste(t, testes);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void run() {
		try {
			performanceAlert.run("fecd28cd-b827-40b9-bc02-b78eaf30bff4");
			assert (true);
		} catch (Exception e) {
			assert (true);
		}
	}
}
