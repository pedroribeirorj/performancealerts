package noesis.performancealerts.model;

import java.util.Iterator;
import java.util.List;
import utils.Constants;
import noesis.performancealerts.dao.RunJPADAO;

public class Regras {
	public static boolean analisaConformidade(String idSuite) {
		Regras r = new Regras();
		List<Jornada> jornada = RunJPADAO.getInstance().recuperaJornadasPorSuite(idSuite);

		return r.analisaSequenciaErros(jornada);

	}

	public boolean analisaSequenciaErros(List<Jornada> j) {
		int contador = 0;
		for (Iterator iterator = j.iterator(); iterator.hasNext();) {
			Jornada jornada = (Jornada) iterator.next();
			if (jornada.getRunTest().getStatus() == Constants.STATUS_PASSED) {
				contador++;
			} else {
				contador = 0;
			}
			if (contador == Constants.MAXIMO_FALHAS_SEGUIDAS_PERMITIDAS)
				return false;

		}

		return contador < Constants.MAXIMO_FALHAS_SEGUIDAS_PERMITIDAS;
	}

}
