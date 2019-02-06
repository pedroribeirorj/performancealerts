package noesis.performancealerts.controller;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.dao.RunTestJPADAO;
import noesis.performancealerts.model.Alerts;
import noesis.performancealerts.model.Run;
import noesis.performancealerts.model.RunTest;
import noesis.performancealerts.model.Violacao;
import utils.Constants;

public class App {
	static Logger logger = LoggerFactory.getLogger(App.class.getName());

	public static void main(String[] args) {
		BasicConfigurator.configure();
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
		try {			
			logger.info("[PerformanceAlerts] Iniciando análise de envio de alertas.");
			List<Run> suites = RunJPADAO.getInstance().findAll();
			for (Iterator iterator = suites.iterator(); iterator.hasNext();) {
				Run suite = (Run) iterator.next();
				// identificando os testes de uma suíte
				logger.info("--------------------------------------------------------------------------");
				logger.info(
						"[PerformanceAlerts] Identificando casos de teste da suite " + suite.getId_run() + ".");
				List<Integer> testsIds = RunTestJPADAO.getInstance().findTestsByRunID(suite.getId_run());

				// para cada teste, analisa violação de regras
				for (Iterator iterator2 = testsIds.iterator(); iterator2.hasNext();) {
					int testId = (Integer) iterator2.next();
					logger.info("[PerformanceAlerts] Validando regras de envio de alertas.");
					Violacao v = Regras.analisaConformidade(suite.getCycle_id(), testId);
					if (v != null && v.existeViolacao()) {
						logger.info("[PerformanceAlerts] Emitindo alerta para caso de teste " + testId + ".");
						AlertsController.emitirAlerta(suite.getId_run(), testId, v);
					} else
						logger.info("[PerformanceAlerts] Caso de teste " + testId + " da suite " + suite.getCycle_id()
								+ " não apresenta violações de regras.");
				}

			}
			logger.info("[PerformanceAlerts] Fim da análise de envio de alertas.");
		} catch (Exception e) {
			logger.error("[PerformanceAlerts] Erro no módulo de alertas: ", e.getMessage());
		}
	}
}
