package utils;

import static utils.Constants.*;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slack {
	static Logger logger = LoggerFactory.getLogger(Slack.class.getName());

	private String buildSlackMessage(String canal, String suite, String jornada, int idExecucao, String erroObs,
			String dataHora, String gravidade) {
		return String.format(MSG_FORMAT_SLACK, canal, suite, jornada, idExecucao, erroObs, dataHora,gravidade,
				"#" + Constants.CHANNEL_NAME_SLACK);
	}

	private static JSONObject getJSONMessageToSlack(String message) {
		JSONObject json = new JSONObject();
		json.put("channel", CHANNEL_NAME_SLACK);
		json.put("text", message);
		json.put("username", USERNAME_SLACK);
		return json;
	}

	private static PostMethod getPostMessageToSlack(JSONObject json) {
		PostMethod post = new PostMethod(URL_SLACK);
		post.addParameter("payload", json.toString());
		post.getParams().setContentCharset("UTF-8");
		return post;
	}

	private static void sendSlackMessage(String message) {
		PostMethod post = null;
		HttpClient client = new HttpClient();
		JSONObject json = getJSONMessageToSlack(message);
		post = getPostMessageToSlack(json);
		getSlackResponse(client, post);
		post.releaseConnection();
	}

	private static void getSlackResponse(HttpClient client, PostMethod post) {
		try {
			int responseCode = client.executeMethod(post);
			String response = post.getResponseBodyAsString();
			if (responseCode != HttpStatus.SC_OK) {
				logger.error("Slack post may have failed. Response: %s \n Response Code: %s", response, responseCode);
			}
		} catch (JSONException e) {
			logger.error("JSONException posting to Slack " + e);
		} catch (IllegalArgumentException e) {
			logger.error("IllegalArgumentException posting to Slack " + e);
		} catch (IOException e) {
			logger.error("IOException posting to Slack " + e);
		}
	}

	public static void sendSlackMessage(String canalCliente, String suite, String casoDeTeste, int idRun,
			String msgErro, String dataHora,String gravidade) {
		Slack s = new Slack();
		Slack.sendSlackMessage(
				s.buildSlackMessage(canalCliente,suite,casoDeTeste,idRun,msgErro,dataHora,gravidade));
	}
}
