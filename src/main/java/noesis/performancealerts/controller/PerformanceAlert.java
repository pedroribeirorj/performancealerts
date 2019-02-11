package noesis.performancealerts.controller;

import java.util.Iterator;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.dao.RunTestJPADAO;
import noesis.performancealerts.model.Run;
import noesis.performancealerts.model.Violacao;

public class PerformanceAlert {
	static Logger logger = LoggerFactory.getLogger(PerformanceAlert.class.getName());

	public static void run() {
		BasicConfigurator.configure();
		// identificando todas as suítes
		try {
			PerformanceAlert pa = new PerformanceAlert();
			logger.info("[PerformanceAlerts] Iniciando análise de envio de alertas.");
			List<Run> suites = RunJPADAO.getInstance().findAll();
			pa.analisarSuites(suites);
			logger.info("[PerformanceAlerts] Fim da análise de envio de alertas.");
		} catch (Exception e) {
			logger.error("[PerformanceAlerts] Erro no módulo de alertas: %s", e.getMessage());
		}
	}

	public void analisarSuites(List<Run> suites) throws Exception {
		if (suites != null && !suites.isEmpty()) {
			for (Iterator iterator = suites.iterator(); iterator.hasNext();) {
				Run suite = (Run) iterator.next();
				// identificando os testes de uma suíte
				logger.info("--------------------------------------------------------------------------");
				logger.info("[PerformanceAlerts] Identificando casos de teste da suite " + suite.getIdRun() + ".");
				List<Integer> testsIds = RunTestJPADAO.getInstance().findTestsByRunID(suite.getIdRun());
				analisarCasosDeTeste(suite, testsIds);
			}
		}
	}

	public void analisarCasosDeTeste(Run suite, List<Integer> testsIds) throws Exception {
		// para cada teste, analisa violação de regras
		if (suite != null && testsIds != null && !testsIds.isEmpty())
			for (Iterator iterator2 = testsIds.iterator(); iterator2.hasNext();) {
				int testId = (Integer) iterator2.next();
				logger.info("[PerformanceAlerts] Validando regras de envio de alertas.");
				Violacao v = Regra.analisaConformidade(suite.getCycleId(), testId);
				if (v != null && v.existeViolacao()) {
					logger.info("[PerformanceAlerts] Emitindo alerta para caso de teste %s.", testId);
					AlertsController.emitirAlerta(suite.getIdRun(), testId, v);
				} else {
					logger.info("[PerformanceAlerts] Caso de teste %s da suite %s não apresenta violações de regras.",
							testId, suite.getCycleId());
				}
			}
	}

	public static void main(String[] args) {
		PerformanceAlert.run();
	}
}
