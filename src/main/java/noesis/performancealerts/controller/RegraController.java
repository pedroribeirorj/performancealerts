package noesis.performancealerts.controller;

import java.rmi.UnexpectedException;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import noesis.performancealerts.dao.RunTestJPADAO;
import noesis.performancealerts.model.RunTest;
import noesis.performancealerts.model.Test;
import noesis.performancealerts.model.Violacao;
import noesis.performancealerts.utils.Constants;
import noesis.performancealerts.utils.Utils;

public class RegraController {
	static Logger logger = LoggerFactory.getLogger(RegraController.class.getName());

	public static Violacao analisaConformidade(List<Integer> runIds, Test t) {
		// analisa se há inconformidade para um caso de teste de uma suíte considerando
		// um volume amostral e regras de negócio
		RegraController r = new RegraController();
		if (t == null)
			return null;

		int idTest = t.getId();

		List<RunTest> rts = RunTestJPADAO.getInstance().findByRunsAndTest(runIds, idTest);

		// valida existencia de sequencia de erros além do permitido
		Violacao obj = r.analisaSequenciaErros(rts);
		if (obj != null) {
			logger.info("[PerformanceAlerts] Identificado caso de teste com  erros em sequência");
			return obj;
		}

		// valida violacao de regra de indisponibilidade
		obj = r.analisaDisponibilidade(rts);
		if (obj != null) {
			logger.info("[PerformanceAlerts] Identificado caso de teste com erro de indisponibilidade.");
			return obj;
		}
		// valida violacao de regra de latência - aguardando desenvolvimento Portugal
		obj = r.analisaLatencia(rts, t);
		if (obj != null) {
			logger.info("[PerformanceAlerts] Identificado caso de teste com erro de latência.");
			return obj;
		}
		return null;
	}

	static List<RunTest> atualizaRunTest(List<RunTest> rt, List<RunTest> rts) throws UnexpectedException {
		switch (rt.size()) {
		case 1:
			rts.add(rt.get(0));
			return rts;
		case 0:
			break;
		default:
			throw new UnexpectedException(
					"[PerformanceAlerts] Erro na base de dados: Existem mais de um par de idRun-idTeste na tabela RunTest");
		}
		return null;
	}

	private Violacao analisaLatencia(List<RunTest> rts, Test t) {
		/*
		 * recupera a latencia de cada runTest. contabiliza quantos com estão fora do
		 * esperado e incrementa num contador descobre percentual se abaixo do esperado,
		 * gerar violacao
		 */
		if (t.getThreshold().isEmpty())
			return null;
		double latencia = calculaLatencia(rts, t);

		if (latencia == -1 || latenciaOK(latencia))
			return null;
		else {
			if (latenciaCritica(latencia)) {
				return new Violacao(Constants.VIOLACAO_POR_LATENCIA, Constants.GRAVIDADE_VIOLACAO_CRITICA,
						String.valueOf(latencia));
			}
			return new Violacao(Constants.VIOLACAO_POR_LATENCIA, Constants.GRAVIDADE_VIOLACAO_NAO_CRITICA,
					String.valueOf(latencia));
		}
	}

	private double calculaLatencia(List<RunTest> rts, Test t) {
		/*
		 * Se caso teve sucesso e o tempo de execução foi maior do que o esperado, então
		 * houve erro de latencia Método retorna o percentual de acerto da latencia,
		 * assim como o dashboard
		 */

		if (rts.isEmpty() || t == null)
			throw new IllegalArgumentException(
					"Informar Lista de RunTests com pelo menos um registro e caso de teste não nulo.");

		int threshold = Integer.parseInt(t.getThreshold());
		int total = 0;
		int erroLatencia = 0;
		for (Iterator iterator = rts.iterator(); iterator.hasNext();) {
			RunTest runTest = (RunTest) iterator.next();
			if (runTest.getStatus() == Constants.STATUS_PASSED && !runTest.getExecutionTime().isEmpty()) {
				total++;
				if (Double.parseDouble(runTest.getExecutionTime()) > threshold) {
					erroLatencia++;
				}
			}
		}
		if (total != 0) {
			return (1 - (double) erroLatencia / total) * 100;
		}
		logger.info("Nenhum caso obteve sucesso para que a latência seja calculada para o teste " + t.getDescription()
				+ ".");
		return -1;
	}

	public int getSequenciaErros(List<RunTest> rts) {
		int contador = 0;
		for (Iterator<RunTest> iterator = rts.iterator(); iterator.hasNext();) {
			RunTest rt = iterator.next();
			if (rt.getStatus() == Constants.STATUS_FAILED) {
				contador++;
			} else {
				contador = 0;
			}
			if (contador > Constants.MAXIMO_FALHAS_SEGUIDAS_PERMITIDAS) {
				return contador;
			}
		}
		return -1;
	}

	public Violacao analisaSequenciaErros(List<RunTest> rts) {
		int falhasSeguidas = getSequenciaErros(rts);
		if (falhasSeguidas != -1) {
			logger.error("[PerformanceAlerts] Identificado caso de teste com erros em sequência.");
			return new Violacao(Constants.VIOLACAO_POR_MAXIMO_FALHAS_SEGUIDAS, Constants.GRAVIDADE_VIOLACAO_CRITICA,
					String.valueOf(falhasSeguidas));
		}
		return null;
	}

	public double getDisponibilidade(List<RunTest> rts) {
		int contadorPassed = 0;
		int totalCasos = rts.size();
		if (totalCasos == 0)
			return 100.00;

		for (Iterator<RunTest> iterator = rts.iterator(); iterator.hasNext();) {
			RunTest rt = iterator.next();
			if (rt.isPassed()) {
				contadorPassed++;
			}
		}
		double disponibilidade = ((double) contadorPassed / totalCasos * 100);
		disponibilidade = Utils.doubleComDuasCasasDecimais(disponibilidade);
		return disponibilidade;

	}

	public Violacao analisaDisponibilidade(List<RunTest> rt) {
		double disponibilidade = getDisponibilidade(rt);
		int tipoViolacao = -1;
		int gravidadeViolacao = -1;
		if (disponibilidadeOK(disponibilidade)) {
			return null;
		}
		tipoViolacao = Constants.VIOLACAO_POR_INDISPONIBILIDADE;
		if (disponibilidadeCritica(disponibilidade)) {
			gravidadeViolacao = Constants.GRAVIDADE_VIOLACAO_CRITICA;
		} else {
			gravidadeViolacao = Constants.GRAVIDADE_VIOLACAO_NAO_CRITICA;
		}
		return new Violacao(tipoViolacao, gravidadeViolacao, String.valueOf(disponibilidade));
	}

	public boolean rangeDisponibilidadeLatencia(double valor) {
		if (valor >= 0 && valor <= 100)
			return true;
		else
			throw new IllegalArgumentException("Valor deve corresponder ao intervalo [0,100].");
	}

	public boolean disponibilidadeOK(double disponibilidade) {
		return rangeDisponibilidadeLatencia(disponibilidade) && disponibilidade >= Constants.THRESHOLD_DISPONIBILIDADE;
	}

	public boolean disponibilidadeCritica(double disponibilidade) {
		return rangeDisponibilidadeLatencia(disponibilidade)
				&& disponibilidade < Constants.THRESHOLD_DISPONIBILIDADE_MINIMA;

	}

	public boolean latenciaOK(double latencia) {
		return rangeDisponibilidadeLatencia(latencia) && latencia >= Constants.THRESHOLD_LATENCIA;
	}

	public boolean latenciaCritica(double latencia) {
		return rangeDisponibilidadeLatencia(latencia) && latencia < Constants.THRESHOLD_LATENCIA_MINIMA;
	}

}
