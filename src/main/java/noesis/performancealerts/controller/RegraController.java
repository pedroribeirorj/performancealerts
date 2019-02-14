package noesis.performancealerts.controller;

import java.rmi.UnexpectedException;
import java.util.ArrayList;
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

	public static Violacao analisaConformidade(List<Integer> runIds, Test teste) throws UnexpectedException {
		// analisa se há inconformidade para um caso de teste de uma suíte considerando
		// um volume amostral e regras de negócio
		RegraController r = new RegraController();
		if (teste == null)
			return null;

		List<RunTest> rts = new ArrayList<RunTest>();
		int idTest = teste.getId();
		for (Iterator<Integer> iterator = runIds.iterator(); iterator.hasNext();) {
			int idRun = (Integer) iterator.next();
			List<RunTest> rt = RunTestJPADAO.getInstance().findByRunAndTest(idRun, idTest);
			if (rt.size() == 1)
				rts.add(rt.get(0));
			else
				throw new UnexpectedException(
						"[PerformanceAlerts] Erro na base de dados: Existem mais de um par de idRun-idTeste na tabela RunTest");
		}

		// valida existencia de sequencia de erros além do permitido
		int falhasSeguidas = r.analisaSequenciaErros(rts);
		if (falhasSeguidas != -1) {
			logger.error("[PerformanceAlerts] Identificado caso de teste com erros em sequência.");
			return new Violacao(Constants.VIOLACAO_POR_MAXIMO_FALHAS_SEGUIDAS, Constants.GRAVIDADE_VIOLACAO_CRITICA,
					String.valueOf(falhasSeguidas));
		}

		// valida violacao de regra de indisponibilidade
		Object obj = r.analisaDisponibilidade(rts);
		if (obj != null) {
			logger.info("[PerformanceAlerts] Identificado caso de teste com erro de indisponibilidade.");
			return (Violacao) obj;
		}
		// valida violacao de regra de latência - aguardando desenvolvimento Portugal
		return null;
	}

	public int analisaSequenciaErros(List<RunTest> rts) {
		int contador = 0;
		int maxFalhasOcorridas = 0;
		boolean naoEstourouLimite = false;
		for (Iterator<RunTest> iterator = rts.iterator(); iterator.hasNext();) {
			RunTest rt = (RunTest) iterator.next();
			if (rt.getStatus() == Constants.STATUS_FAILED) {
				contador++;
			} else {
				contador = 0;
				if (maxFalhasOcorridas > Constants.MAXIMO_FALHAS_SEGUIDAS_PERMITIDAS) {
					return maxFalhasOcorridas;
				}
			}
			if (contador > Constants.MAXIMO_FALHAS_SEGUIDAS_PERMITIDAS && !naoEstourouLimite) {
				maxFalhasOcorridas = contador;
				naoEstourouLimite = !naoEstourouLimite;
			}
		}
		return -1;
	}

	public double getDisponibilidade(List<RunTest> rts) {
		int contadorPassed = 0;
		int totalCasos = rts.size();
		if (totalCasos == 0)
			return 100.00;

		for (Iterator<RunTest> iterator = rts.iterator(); iterator.hasNext();) {
			RunTest rt = (RunTest) iterator.next();
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
