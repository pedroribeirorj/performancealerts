package noesis.performancealerts.controller;

import java.util.Iterator;
import java.util.List;

import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.dao.RunTestJPADAO;
import noesis.performancealerts.model.Alerts;
import noesis.performancealerts.model.Regras;
import noesis.performancealerts.model.Run;
import noesis.performancealerts.model.RunTest;
import noesis.performancealerts.model.Violacao;
import utils.Constants;

public class App {

	public static void main(String[] args) {
		/*
		 * Recuperar as 4 últimas execuções de uma suíte não analisada: Para cada
		 * jornada, verificar se foi failed em todas as execuções.
		 * 
		 * Se sim, colocar na lista de alertas a serem emitidos Emitir alerta Marcar as
		 * últimas 4 execuções da suíte com já analisadas
		 * 
		 * Recuperar a última suíte completada e não analisada: para cada jornada
		 */

		// identificando todas as suítes
		List<Run> suites = RunJPADAO.getInstance().findAll();
		for (Iterator iterator = suites.iterator(); iterator.hasNext();) {
			Run suite = (Run) iterator.next();
			// identificando os testes de uma suíte
			List<Integer> testsIds = RunTestJPADAO.getInstance().findTestsByRunID(suite.getId_run());

			//para cada teste, analisa violação de regras
			for (Iterator iterator2 = testsIds.iterator(); iterator2.hasNext();) {
				int testId = (Integer) iterator2.next();

				Violacao v = Regras.analisaConformidade(suite.getCycle_id(), testId);
				if (v != null && v.existeViolacao()) {
					AlertsController.emitirAlerta(suite.getId_run(), testId, v);
				}
			}

		}
	}
}
