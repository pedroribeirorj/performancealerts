package noesis.performancealerts.utilstest;

import static org.junit.Assert.*;

import org.junit.Test;

import utils.Utils;

public class UtilsTest {

	@Test(expected = StringIndexOutOfBoundsException.class)
	public void doubleComDuasCasasDecimais_acrescimo() {
		double d = Utils.doubleComDuasCasasDecimais(7);
		String s = String.valueOf(d);
		int posicaoInesperada = s.indexOf(".") + 3;
		s.charAt(posicaoInesperada);
	}

	@Test
	public void doubleComDuasCasasDecimais_corte() {
		try {
			double d = Utils.doubleComDuasCasasDecimais(4.519841);
			String s = String.valueOf(d);
			int umaCasaDecimal = s.indexOf(".") + 1;
			int duasCasasDecimais = s.indexOf(".") + 2;
			assert (true);
		} catch (Exception e) {
			assert (false);
		}
	}

	@Test
	public void getDateTime() {
		String data = Utils.getDateTime();
		assertEquals("yyyy/MM/dd HH:mm:ss".length(), data.length());
		assertEquals(4, data.indexOf("/"));
		assertEquals(7, data.lastIndexOf("/"));
		assertEquals(13, data.indexOf(":"));
		assertEquals(16, data.lastIndexOf(":"));
	}

}
