package noesis.performancealerts.controller;

import java.rmi.UnexpectedException;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import noesis.performancealerts.dao.AlertsJPADAO;
import noesis.performancealerts.dao.RunTestJPADAO;
import noesis.performancealerts.dao.TestJPADAO;
import noesis.performancealerts.model.Test;
import noesis.performancealerts.model.Violacao;

public class PerformanceAlert {
	static Logger logger = LoggerFactory.getLogger(PerformanceAlert.class.getName());

	public static void run(String projectId) {
		BasicConfigurator.configure();
		// identificando todos os testes
		try {
			PerformanceAlert pa = new PerformanceAlert();
			logger.info("[PerformanceAlerts] Iniciando análise de envio de alertas.");
			// resgata todos os testes do projeto
			
			//melhoria -- resgatar todos os casos do projeto e que possua runTest
			List<Test> testes = TestJPADAO.getInstance().findAllTestsByProject(projectId);
			pa.analisarTestesDoProjeto(testes);
			logger.info("[PerformanceAlerts] Fim da análise de envio de alertas.");
		} catch (Exception e) {
			logger.error("[PerformanceAlerts] Erro no módulo de alertas: "+e.getMessage());
		}
	}

	public void analisarTestesDoProjeto(List<Test> testes) throws MessagingException, UnexpectedException {
		if (testes != null && !testes.isEmpty()) {
			for (Iterator iterator = testes.iterator(); iterator.hasNext();) {
				Test teste = (Test) iterator.next();
				// identificando as execuções do teste
				logger.info("--------------------------------------------------------------------------");
				logger.info("[PerformanceAlerts] Identificando caso de teste " + teste.getName_test() + ".");
				// Recuperar os ids das últimas execuções do caso de teste
				List<Integer> runsIds = RunTestJPADAO.getInstance().findLastsRunsByTestID(teste.getId());
				if (!runsIds.isEmpty()) {
					int ultimaExecucaoAvaliada = runsIds.get(runsIds.size() - 1);
					if (AlertsJPADAO.getInstance().findRunsByTestID(teste.getId(), ultimaExecucaoAvaliada).isEmpty())
						analisarExecucoesDoCasoDeTeste(teste, runsIds);
				} else {
					logger.info("[PerformanceAlerts] Execuções já analisadas anteriormente.");
				}
				logger.info("[PerformanceAlerts] Fim de análise de caso de teste " + teste.getName_test() + ".");
			}
		}
	}

	public void analisarExecucoesDoCasoDeTeste(Test teste, List<Integer> runIds)
			throws MessagingException, UnexpectedException {
		// para cada teste, analisa violação de regras
		if (teste != null && runIds != null && !runIds.isEmpty()) {
			int lastRun = runIds.get(runIds.size() - 1);
			int casoDeTesteID = teste.getId();
			logger.info("[PerformanceAlerts] Validando regras de envio de alertas.");
			String suiteDeTeste = teste.getTest_cycle_id();
			Violacao v = RegraController.analisaConformidade(runIds, teste);
			if (v != null && v.existeViolacao()) {
				logger.info("[PerformanceAlerts] Violação de regra identificada.");
				logger.info("[PerformanceAlerts] Emitindo alerta para caso de teste " + casoDeTesteID + ".");
				AlertsController.emitirAlerta(teste, v, lastRun);
			} else {
				String msgLog = "[PerformanceAlerts] Caso de teste " + teste.getName_test() + " da suite "
						+ suiteDeTeste + " não apresenta violações de regras.";
				logger.info(msgLog);
			}
		}
	}

	public static void main(String[] args) {
		String projectId = "fecd28cd-b827-40b9-bc02-b78eaf30bff4";
		PerformanceAlert.run(projectId);
	}
}
