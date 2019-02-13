package noesis.performancealerts.controller;

import javax.mail.MessagingException;
import javax.persistence.NoResultException;
import static utils.Constants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import noesis.performancealerts.dao.AlertsJPADAO;
import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.dao.TestJPADAO;
import noesis.performancealerts.model.Alerts;
import noesis.performancealerts.model.Run;
import noesis.performancealerts.model.Test;
import noesis.performancealerts.model.Violacao;
import noesis.performancealerts.model.ViolacaoTipo;
import utils.Constants;
import utils.Mail;
import utils.Slack;
import utils.Utils;

public class AlertsController {
	static Logger logger = LoggerFactory.getLogger(AlertsController.class.getName());

	public static void emitirAlerta(Test teste, Violacao v, int lastRun) throws MessagingException {
		AlertsController alerts = new AlertsController();
		String idSuite = teste.getTest_cycle_id();
		String casoDeTeste = teste.getName_test();
		String texto = alerts.montaCorpoEmail(v, casoDeTeste);
		Alerts alerta = new Alerts(v.getValue(), teste.getId(), Utils.getDateTime(),
				String.valueOf(v.getTipoViolacao()), String.valueOf(v.getGravidadeViolacao()),lastRun );

		alerts.enviarEmail(texto, v.gravidadeCritica());
		if (ALERTA_SLACK && !TST_MODE) {
			String msgErro = messageErroSlack(v);
			String gravidade = v.gravidadeCritica() ? "Crítica" : "Não Crítica";
			alerts.enviarSlack(idSuite, casoDeTeste, teste.getId(), msgErro, Utils.getDateTime(), gravidade);
		}
		atualizarStatusAlerta(alerta);
	}

	private static String messageErroSlack(Violacao v) {
		String msgErro = "";
		int gravidade = ViolacaoTipo.procurarOpcao(v.getTipoViolacao());
		switch (gravidade) {
		case Constants.VIOLACAO_POR_MAXIMO_FALHAS_SEGUIDAS:
			msgErro = v.getValue() + " falhas ocorridas em sequência";
			break;
		case Constants.VIOLACAO_POR_INDISPONIBILIDADE:
			msgErro = "Falha por indisponibilidade (" + v.getValue() + "%)";
			break;
		case Constants.VIOLACAO_POR_LATENCIA:
			msgErro = "Falha por latência (" + v.getValue() + "%)";
			break;
		default:
			msgErro = "";
		}
		return msgErro;
	}

	private void enviarSlack(String suite, String casoDeTeste, int idRun, String msgErro, String dataHora,
			String gravidade) {
		Slack.sendSlackMessage(suite, casoDeTeste, idRun, msgErro, dataHora, gravidade);
	}

	private static void atualizarStatusAlerta(Alerts alerta) {
		if (Constants.TST_MODE) {
			logger.warn("[PerformanceAlerts] Alerta não persistido por estar em modo debug.");
			return;
		}
		AlertsJPADAO.getInstance().persist(alerta);
		logger.error("[PerformanceAlerts] Emissão de alerta persistido na base de dados.");
	}

	private String montaCorpoEmail(Violacao v, String casoDeTeste) {
		String texto = "A Monitoria de Performance identificou um problema na jornada " + casoDeTeste + ".";
		if (v.violacaoFalhasSeguidas()) {
			texto += "A jornada obteve falhas seguidas acima do permitido (" + v.getValue() + "%).";
		} else {
			if (v.violacaoIndisponibilidade()) {
				texto += "A jornada esteve indisponível acima do percentual permitido (" + v.getValue() + "%).";
			}
		}
		return texto;
	}

	public void enviarEmail(String texto, boolean gravidadeCritica) throws MessagingException {
		if (texto.isEmpty())
			throw new IllegalArgumentException("[PerformanceAlerts] Texto do email precisa ser preenchido.");
		if (gravidadeCritica) {
			enviarEmailGerencia(texto);
		} else {
			enviarEmailOperacao(texto);
		}
	}

	private void enviarEmailOperacao(String texto) throws MessagingException {
		if (Constants.TST_MODE) {
			logger.warn("[PerformanceAlerts] E-mail não enviado por estar em modo debug.");
			return;
		}

		Mail.enviarEmail(texto, Constants.EMAILS_OPERACAO, "[MONITORIA-TIM] Erro de Performance",
				Constants.MAIL_PRD_MODE);
		logger.error("[PerformanceAlerts] E-mail enviado para equipe operacional.");
	}

	private void enviarEmailGerencia(String texto) throws MessagingException {
		if (Constants.TST_MODE) {
			logger.warn("[PerformanceAlerts] E-mail não enviado por estar em modo debug.");
			return;
		}
		Mail.enviarEmail(texto, Constants.EMAILS_OPERACAO + "," + Constants.EMAILS_GERENCIA,
				"[MONITORIA-TIM] Erro de Performance", Constants.MAIL_PRD_MODE);
		logger.error("[PerformanceAlerts] E-mail enviado para equipe operacional e gerencial.");
	}
}
