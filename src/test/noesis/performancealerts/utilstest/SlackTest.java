package noesis.performancealerts.utilstest;

import static org.junit.Assert.*;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONObject;
import org.junit.Test;

import utils.Slack;

public class SlackTest {

//	@Test
//	public String buildSlackMessage(String canal, String suite, String jornada, int idExecucao, String erroObs,
//			String dataHora, String gravidade) {
//
//	}
//
//	@Test
//	public static JSONObject getJSONMessageToSlack(String message) {
//
//	}
//
//	@Test
//	public static PostMethod getPostMessageToSlack(JSONObject json) {
//
//	}
//
//	@Test
//	public static void sendSlackMessage(String message) {
//
//	}
//
//	@Test
//	public static void getSlackResponse(HttpClient client, PostMethod post) {
//
//	}

	@Test(expected = IllegalArgumentException.class)
	public void sendSlackMessage_semSuite() {
		String canalCliente = "Meu Tim";
		String suite = "";
		String casoDeTeste = "CT94";
		int idRun = 1;
		String msgErro = "Erro Teste";
		String dataHora = "12/02/2019 17:50";
		String gravidade = "Crítico";
		Slack.sendSlackMessage(canalCliente, suite, casoDeTeste, idRun, msgErro, dataHora, gravidade);
	}

	@Test(expected = IllegalArgumentException.class)
	public void sendSlackMessage_semCasoDeTeste() {
		String canalCliente = "Meu Tim";
		String suite = "124";
		String casoDeTeste = "";
		int idRun = 1;
		String msgErro = "Erro Teste";
		String dataHora = "12/02/2019 17:50";
		String gravidade = "Crítico";
		Slack.sendSlackMessage(canalCliente, suite, casoDeTeste, idRun, msgErro, dataHora, gravidade);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void sendSlackMessage_semGravidade() {
		String canalCliente = "Meu Tim";
		String suite = "124";
		String casoDeTeste = "354s";
		int idRun = 1;
		String msgErro = "Erro Teste";
		String dataHora = "12/02/2019 17:50";
		String gravidade = "";
		Slack.sendSlackMessage(canalCliente, suite, casoDeTeste, idRun, msgErro, dataHora, gravidade);
	}
}
