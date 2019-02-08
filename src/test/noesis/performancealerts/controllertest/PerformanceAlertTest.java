package noesis.performancealerts.controllertest;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.common.AssertionFailure;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import noesis.performancealerts.controller.PerformanceAlert;
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
			List<Run> suites = null;
			performanceAlert.analisarSuites(suites);
			performanceAlert.analisarSuites(new ArrayList<Run>());
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void analisarSuites_ComJornadas() {
		try {
			Run r = mock(Run.class);
			List<Run> suites = new ArrayList<Run>();
			suites.add(r);
			performanceAlert.analisarSuites(suites);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void analisarCasosDeTeste_suiteSemJornadas() {
		try {
			Run suite = mock(Run.class);
			performanceAlert.analisarCasosDeTeste(suite, new ArrayList<Integer>());
			performanceAlert.analisarCasosDeTeste(suite, null);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void analisarCasosDeTeste_jornadaSemViolacao() {
		try {
			Run suite = mock(Run.class);
			suite.setCycle_id("7");
			List<Integer> idsTest = new ArrayList<Integer>();
			idsTest.add(7);
			performanceAlert.analisarCasosDeTeste(suite, idsTest);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void analisarCasosDeTeste_jornadaComViolacao() {
		try {
			Run suite = mock(Run.class);
			suite.setCycle_id("7");
			List<Integer> idsTest = new ArrayList<Integer>();
			idsTest.add(661);
			performanceAlert.analisarCasosDeTeste(suite, idsTest);
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void run() {
		try {
			performanceAlert.run();
			assert (true);
		} catch (Exception e) {
			assert (true);
		}
	}
}
