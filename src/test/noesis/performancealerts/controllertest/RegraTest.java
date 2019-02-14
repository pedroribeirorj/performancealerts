package noesis.performancealerts.controllertest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import noesis.performancealerts.controller.RegraController;
import noesis.performancealerts.model.RunTest;
import noesis.performancealerts.utils.Constants;

public class RegraTest {
	RegraController regraController;

	public RegraTest() {
		regraController = new RegraController();
	}

	@Test
	public void disponibilidadeOK() {
		try {
			assertTrue(regraController.disponibilidadeOK(100));
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void rangeDisponibilidadeLatencia_0() {
		try {
			assertTrue(regraController.rangeDisponibilidadeLatencia(0));
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void rangeDisponibilidadeLatencia_100() {
		try {
			assertTrue(regraController.rangeDisponibilidadeLatencia(100));
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void rangeDisponibilidadeLatencia_numeroNegativo() {
		try {
			regraController.rangeDisponibilidadeLatencia(-7);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void rangeDisponibilidadeLatencia_numeroMaiorQueCem() {
		try {
			regraController.rangeDisponibilidadeLatencia(101);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void disponibilidadeOKTest_numeroNegativo() {
		try {
			regraController.disponibilidadeOK(-7);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void disponibilidadeOKTest_numeroMaiorQueCem() {
		try {
			regraController.disponibilidadeOK(101);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void disponibilidadeCritica() {
		try {
			assertTrue(regraController.disponibilidadeCritica(Constants.THRESHOLD_DISPONIBILIDADE_MINIMA - 0.1));
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void latenciaCritica() throws Exception {
		assertTrue(regraController.latenciaCritica(Constants.THRESHOLD_LATENCIA_MINIMA - 0.1));
	}

	@Test
	public void latenciaCritica_minima() throws Exception {
		assertFalse(regraController.latenciaCritica(Constants.THRESHOLD_LATENCIA_MINIMA));
	}

	@Test
	public void latenciaOK() throws Exception {
		assertTrue(regraController.latenciaOK(Constants.THRESHOLD_LATENCIA));
	}

	@Test
	public void latenciaNOK() throws Exception {
		assertFalse(regraController.latenciaOK(Constants.THRESHOLD_LATENCIA_MINIMA - 0.1));
	}

	@Test
	public void getDisponibilidade_JornadaVazia() throws Exception {

		assertEquals(new Double(100), (double) regraController.getDisponibilidade(new ArrayList<RunTest>()), 0);
	}
}
