package noesis.performancealerts.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import noesis.performancealerts.controller.Regra;

public class RegraTest {
	Regra regra;

	public RegraTest() {
		regra = new Regra();
	}

	@Test
	public void disponibilidadeOKTest_numeroNegativo() {
		try {
			regra.disponibilidadeOK(-7);
			assert(false);
		} catch (Exception e) {
			assert(true);
		}
	}
	
	@Test
	public void disponibilidadeOKTest_numeroMaiorQueCem() {
		try {
			regra.disponibilidadeOK(101);
			assert(false);
		} catch (Exception e) {
			assert(true);
		}
	}
}
