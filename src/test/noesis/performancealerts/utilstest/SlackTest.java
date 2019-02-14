package noesis.performancealerts.utilstest;

import static org.junit.Assert.*;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;
import org.junit.Test;

import noesis.performancealerts.utils.Slack;

public class SlackTest {
	@Test(expected = IllegalArgumentException.class)
	public void sendSlackMessage_semSuite() {
		String suite = "";
		String casoDeTeste = "CT94";
		int idRun = 1;
		String msgErro = "Erro Teste";
		String dataHora = "12/02/2019 17:50";
		String gravidade = "Crítico";
		Slack.sendSlackMessage(suite, casoDeTeste, idRun, msgErro, dataHora, gravidade);
	}

	@Test(expected = IllegalArgumentException.class)
	public void sendSlackMessage_semCasoDeTeste() {
		String suite = "124";
		String casoDeTeste = "";
		int idRun = 1;
		String msgErro = "Erro Teste";
		String dataHora = "12/02/2019 17:50";
		String gravidade = "Crítico";
		Slack.sendSlackMessage( suite, casoDeTeste, idRun, msgErro, dataHora, gravidade);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void sendSlackMessage_semGravidade() {
		String suite = "124";
		String casoDeTeste = "354s";
		int idRun = 1;
		String msgErro = "Erro Teste";
		String dataHora = "12/02/2019 17:50";
		String gravidade = "";
		Slack.sendSlackMessage( suite, casoDeTeste, idRun, msgErro, dataHora, gravidade);
	}
}
