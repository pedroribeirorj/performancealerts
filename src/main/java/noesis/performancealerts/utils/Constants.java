package noesis.performancealerts.utils;

import noesis.performancealerts.model.RunTest;

public class Constants {
	private Constants() {
	}

	// evitar envio de emails, slack e persistências
	public static final boolean TST_MODE = false;
	public static final boolean ALERTA_SLACK = false;

	// limites
	public static final int MAXIMO_FALHAS_SEGUIDAS_PERMITIDAS = 3;
	public static final double THRESHOLD_DISPONIBILIDADE = 95.0;
	public static final double THRESHOLD_DISPONIBILIDADE_MINIMA = 90.0;
	public static final double THRESHOLD_LATENCIA = 95.0;
	public static final double THRESHOLD_LATENCIA_MINIMA = 90.0;
	public static final int VOLUME_AMOSTRAL = 100;

	public static final int STATUS_PASSED = 1;
	public static final int STATUS_FAILED = 0;

	// indices do vetor de inconformidade
	public static final int INCONFORMIDADE_CAUSA = 0;
	public static final int INCONFORMIDADE_GRAVIDADE = 1;

	// tipos de violação de regras
	public static final int SEM_VIOLACAO = 0;
	public static final int VIOLACAO_POR_MAXIMO_FALHAS_SEGUIDAS = 1;
	public static final int VIOLACAO_POR_INDISPONIBILIDADE = 2;
	public static final int VIOLACAO_POR_LATENCIA = 3;

	// níveis de violação de regras
	public static final int GRAVIDADE_VIOLACAO_CRITICA = 0;
	public static final int GRAVIDADE_VIOLACAO_NAO_CRITICA = 1;

	// listas de emails
	public static final String EMAILS_OPERACAO = "pedroribeiro@id.uff.br";
	public static final String EMAILS_GERENCIA = "pedro.h.silva@noesis.pt";
	public static final boolean MAIL_DEBUG_MODE = true;
	public static final boolean MAIL_PRD_MODE = false;

	public static final int REPOSITORY_TYPE_ZEPHYR = 1;
	public static final int REPOSITORY_TYPE_TFS = 2;
	public static final int REPOSITORY_TYPE_XRAY = 3;

	//queries
	public static final String QUERY_FIND_TESTS_BY_RUN_ID = "select idTest FROM " + RunTest.class.getName()
			+ " where idRun = :idRun and projectId = :projectId";

	
	public static final String QUERY_FIND_TESTS_BY_LASTS_RUNS_BY_RUN_ID = "SELECT r.idRun " + "	FROM RunTest rt, Run r "
			+ "	where  r.idRun = rt.idRun and rt.idTest = :idTest "
			+ "order by r.data desc ";

	public static final String QUERY_FIND_RUNS_BY_TEST_ID = "select distinct idTest FROM " + RunTest.class.getName()
			+ " where idRun = :idRun";

	// acessos slack
	public static final String URL_SLACK = "https://hooks.slack.com/services/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";
	public static final String CHANNEL_NAME_SLACK = "#noesismessengertest";
	public static final String TEAM_NAME_SLACK = "tim-noesis";
	public static final String USERNAME_SLACK = "Pedro Ribeiro";
}
