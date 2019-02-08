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

	public static void emitirAlerta(int idSuite, int idCasoDeTeste, Violacao v) throws Exception {
		Run suite = RunJPADAO.getInstance().getById(idSuite);
		Test ct = TestJPADAO.getInstance().getById(idCasoDeTeste);
		if (suite == null || ct == null)
			throw new Exception("[PerformanceAlert] Suíte ou caso de teste não encontrados.");
		AlertsController alerts = new AlertsController();

		String canal = suite.getCycle_id();
		String casoDeTeste = ct.getName_test();

		String texto = alerts.montaCorpoEmail(v, canal, casoDeTeste);
		Alerts alerta = new Alerts(v.getValue(), String.valueOf(idSuite), String.valueOf(ct.getId()),
				String.valueOf(suite.getData()), String.valueOf(v.getTipoViolacao()),
				String.valueOf(v.getGravidadeViolacao()));

		alerts.enviarEmail(texto, v.gravidadeCritica());
		atualizarStatusAlerta(alerta);
	}

	private static void atualizarStatusAlerta(Alerts alerta) {
		if (Constants.TST_MODE)
			return;
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

	public void enviarEmail(String texto, boolean gravidadeCritica) throws Exception {
		if (texto.isEmpty())
			throw new Exception("[PerformanceAlerts] Texto do email precisa ser preenchido.");
		if (gravidadeCritica) {
			enviarEmailGerencia(texto);
		} else {
			enviarEmailOperacao(texto);
		}
	}

	private void enviarEmailOperacao(String texto) throws Exception {
		if (Constants.TST_MODE)
			return;
		Mail.enviarEmail(texto, Constants.emailsOperacao, "[MONITORIA-TIM] Erro de Performance",
				Constants.MAIL_PRD_MODE);
		logger.error("[PerformanceAlerts] E-mail enviado para equipe operacional.");
	}

	private void enviarEmailGerencia(String texto) throws Exception {
		if (Constants.TST_MODE)
			return;
		Mail.enviarEmail(texto, Constants.emailsOperacao + "," + Constants.emailsGerencia,
				"[MONITORIA-TIM] Erro de Performance", Constants.MAIL_PRD_MODE);
		logger.error("[PerformanceAlerts] E-mail enviado para equipe operacional e gerencial.");
	}
}
