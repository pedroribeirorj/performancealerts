package noesis.performancealerts;

import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.dao.RunTestJPADAO;
import noesis.performancealerts.model.Regras;
import noesis.performancealerts.model.Run;
import noesis.performancealerts.model.RunTest;

public class App {
	
	public static void main(String[] args) {
		/*
		 * Recuperar as 4 últimas execuções de uma suíte não analisada: 
		 * Para cada jornada, verificar se foi failed em todas as execuções. 
		 * 
		 * Se sim, colocar na
		 * lista de alertas a serem emitidos 
		 * Emitir alerta Marcar as últimas 4 execuções
		 * da suíte com já analisadas
		 * 
		 * Recuperar a última suíte completada e não analisada: para cada jornada
		 * 
		 * 
		 */
		String idSuite = "7";
		System.out.print(Regras.analisaConformidade(idSuite));
		
	}
}
