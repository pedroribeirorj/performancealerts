package utils;

public class Constants {
	//evitar envio de emails ou persistências desnecessárias
	public static final boolean TST_MODE = true;

	public static final String RECUPERA_JORNADAS_POR_SUITES = 
			"select r,rt,t " + "from Run r, RunTest rt, Test t "
					+ " where rt.id_run = r.id_run " 
					+ " and rt.id_test = t.id "
					+ " and t.test_cycle_id = ?1"
					+ " and t.id = ?2 "
					+ " and (t.id ,r.id_run, r.data) not in (select a.id_test ,    a.id_run , a.data from Alerts a)" 
					+ " order by t.test_cycle_id, "
					+ "r.data desc " ;
	
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
	public static final String emailsOperacao = "pedroribeiro@id.uff.br";
//	public static final String emailsGerencia = "pedro.h.silva@noesis.pt, renata.l.pinto@noesis.net.br, jose.cardoso@noesis.net.br";
	public static final String emailsGerencia = "pedro.h.silva@noesis.pt";
	public static final boolean MAIL_DEBUG_MODE = true;
	public static final boolean MAIL_PRD_MODE = false;
	
}
