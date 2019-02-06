package noesis.performancealerts.controller;

import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import noesis.performancealerts.dao.AlertsJPADAO;
import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.dao.TestJPADAO;
import noesis.performancealerts.model.Alerts;
import noesis.performancealerts.model.Run;
import noesis.performancealerts.model.Test;
import noesis.performancealerts.model.Violacao;
import utils.Constants;
import utils.Mail;
import utils.Utils;

public class AlertsController {
	static Logger logger = LoggerFactory.getLogger(AlertsController.class.getName());
	
	public static void emitirAlerta(int idSuite, int idCasoDeTeste, Violacao v) {
		Run suite = RunJPADAO.getInstance().getById(idSuite);
		Test ct = TestJPADAO.getInstance().getById(idCasoDeTeste);

		AlertsController alerts = new AlertsController();

		String canal = suite.getCycle_id();
		String casoDeTeste = ct.getName_test();

		String texto = alerts.montaCorpoEmail(v, canal, casoDeTeste);
		Alerts alerta = new Alerts(v.getValue(), String.valueOf(idSuite), String.valueOf(ct.getId()),
				String.valueOf(suite.getData()), String.valueOf(v.getTipoViolacao()), String.valueOf(v.getGravidadeViolacao()));

		alerts.enviarEmail(texto, v.gravidadeCritica());
		atualizarStatusAlerta(alerta);
	}

	private static void atualizarStatusAlerta(Alerts alerta) {
		AlertsJPADAO.getInstance().persist(alerta);
		logger.error("[PerformanceAlerts] Emissão de alerta persistido na base de dados.");
	}

	private String montaCorpoEmail(Violacao v, String canal, String casoDeTeste) {
		String texto = "A Monitoria de Performance identificou um problema no canal " + canal + ", na jornada "
				+ casoDeTeste + ".";
		if (v.violacaoFalhasSeguidas()) {
			texto += "A jornada obteve falhas seguidas acima do permitido.";
		} else {
			if (v.violacaoIndisponibilidade()) {
				texto += "A jornada esteve indisponível acima do percentual permitido.";
			}
		}
		return texto;
	}

	private void enviarEmail(String texto, boolean gravidadeCritica) {
		if (gravidadeCritica) {
			enviarEmailGerencia(texto);
		} else {
			enviarEmailOperacao(texto);
		}
	}

	private void enviarEmailOperacao(String texto) {
		Mail.enviarEmail(texto, Constants.emailsOperacao, "[MONITORIA-TIM] Erro de Performance");
		logger.error("[PerformanceAlerts] E-mail enviado para equipe operacional.");
	}

	private void enviarEmailGerencia(String texto) {
		Mail.enviarEmail(texto, Constants.emailsOperacao + "," + Constants.emailsGerencia,
				"[MONITORIA-TIM] Erro de Performance");
		logger.error("[PerformanceAlerts] E-mail enviado para equipe operacional e gerencial.");
	}
}
