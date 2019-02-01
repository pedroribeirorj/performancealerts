package utils;

public class Constants {
//	public static final String RECUPERA_JORNADAS_POR_SUITES = 
//			"select r.data as data_execucao,\r\n"
//			+ "rt.id as id_run_test,\r\n" + "case when rt.status = 0 then\r\n" + "			'Sucesso' \r\n"
//			+ "		else\r\n" + "			'Falha' end as status,\r\n"
//			+ "t.name_test,t.project_id,t.test_cycle_id as id_suite_teste,\r\n" + "task.task_name as suite_teste,\r\n"
//			+ "tl.message as mensagem_resultado_suite\r\n" +
//			"from ta_framework.tbl_runs r \r\n"
//			+ "inner join ta_framework.tbl_runs_tests rt on rt.id_run = r.id_run\r\n"
//			+ "inner join ta_framework.tbl_test t on rt.id_test = t.id\r\n"
//			+ "inner join ta_framework.tbl_tasks task on task.test_cycle_id = t.test_cycle_id\r\n"
//			+ "inner join ta_framework.tbl_task_log tl on tl.taskid = task.task_id\r\n" + "order by r.data desc ";

	public static final String RECUPERA_JORNADAS_POR_SUITES = "select r,rt,t "
			+ "from Run r, RunTest rt, Test t" 
			+ " where rt.id_run = r.id_run "
			+ " and rt.id_test = t.id " 
			+ " and t.test_cycle_id = :test_cycle_id"
			+ " order by t.test_cycle_id, r.data desc ";
	
	public static final int MAXIMO_FALHAS_SEGUIDAS_PERMITIDAS = 3;
	
	public static final int STATUS_PASSED = 1;
	public static final int STATUS_FAILED = 0;
}
