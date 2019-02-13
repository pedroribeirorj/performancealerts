package noesis.performancealerts.controllertest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import noesis.performancealerts.controller.Regra;
import noesis.performancealerts.model.RunTest;
import utils.Constants;

public class RegraTest {
	Regra regra;

	public RegraTest() {
		regra = new Regra();
	}

	@Test
	public void disponibilidadeOK() {
		try {
			assertTrue(regra.disponibilidadeOK(100));
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void rangeDisponibilidadeLatencia_0() {
		try {
			assertTrue(regra.rangeDisponibilidadeLatencia(0));
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void rangeDisponibilidadeLatencia_100() {
		try {
			assertTrue(regra.rangeDisponibilidadeLatencia(100));
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void rangeDisponibilidadeLatencia_numeroNegativo() {
		try {
			regra.rangeDisponibilidadeLatencia(-7);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void rangeDisponibilidadeLatencia_numeroMaiorQueCem() {
		try {
			regra.rangeDisponibilidadeLatencia(101);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void disponibilidadeOKTest_numeroNegativo() {
		try {
			regra.disponibilidadeOK(-7);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void disponibilidadeOKTest_numeroMaiorQueCem() {
		try {
			regra.disponibilidadeOK(101);
			assert (false);
		} catch (Exception e) {
			assert (true);
		}
	}

	@Test
	public void disponibilidadeCritica() {
		try {
			assertTrue(regra.disponibilidadeCritica(Constants.THRESHOLD_DISPONIBILIDADE_MINIMA - 0.1));
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void latenciaCritica() throws Exception {
		assertTrue(regra.latenciaCritica(Constants.THRESHOLD_LATENCIA_MINIMA - 0.1));
	}

	@Test
	public void latenciaCritica_minima() throws Exception {
		assertFalse(regra.latenciaCritica(Constants.THRESHOLD_LATENCIA_MINIMA));
	}

	@Test
	public void latenciaOK() throws Exception {
		assertTrue(regra.latenciaOK(Constants.THRESHOLD_LATENCIA));
	}

	@Test
	public void latenciaNOK() throws Exception {
		assertFalse(regra.latenciaOK(Constants.THRESHOLD_LATENCIA_MINIMA - 0.1));
	}

	@Test
	public void getDisponibilidade_JornadaVazia() throws Exception {

		assertEquals(new Double(100), (double) regra.getDisponibilidade(new ArrayList<RunTest>()), 0);
	}
}
