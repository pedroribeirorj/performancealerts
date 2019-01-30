package noesis.performancealerts;

import noesis.performancealerts.dao.Run;
import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.dao.RunTest;
import noesis.performancealerts.dao.RunTestJPADAO;

public class App {
	public static void main(String[] args) {
		/* 
		 * Recuperar a última suíte completada e não analisada:
		 * 	para cada jornada 
		 * 
		 *   
		 */
		Run r = RunJPADAO.getInstance().getById(1);
		System.out.print(r.getData());

		RunTest rt = RunTestJPADAO.getInstance().getById(1);
		System.out.print(rt.getId_test());
	}
}
