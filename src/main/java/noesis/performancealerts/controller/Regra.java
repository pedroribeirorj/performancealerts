package noesis.performancealerts.controller;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.Constants;
import utils.Utils;
import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.model.CasoDeTeste;
import noesis.performancealerts.model.Violacao;

public class Regra {
	static Logger logger = LoggerFactory.getLogger(Regra.class.getName());

	public static Violacao analisaConformidade(String idSuite, int idCasoDeTeste) {

		// analisa se há inconformidade para um caso de teste de uma suíte considerando

		// um volume amostral e regras de negócio
		Regra r = new Regra();
		List<CasoDeTeste> casoDeTeste = RunJPADAO.getInstance().recuperaCasosDeTestePorSuite(idSuite, idCasoDeTeste);
		if (casoDeTeste.isEmpty())
			return null;

		// valida existencia de sequencia de erros além do permitido
		int falhasSeguidas = r.analisaSequenciaErros(casoDeTeste);
		if (falhasSeguidas > Constants.MAXIMO_FALHAS_SEGUIDAS_PERMITIDAS) {
			logger.error("[PerformanceAlerts] Identificado caso de teste com erros em sequência.");
			return new Violacao(Constants.VIOLACAO_POR_MAXIMO_FALHAS_SEGUIDAS, Constants.GRAVIDADE_VIOLACAO_CRITICA,
					String.valueOf(falhasSeguidas));
		}

		// valida violacao de regra de indisponibilidade
		Object obj = r.analisaDisponibilidade(casoDeTeste);
		if (obj != null) {
			logger.info("[PerformanceAlerts] Identificado caso de teste com erro de indisponibilidade.");
			return (Violacao) obj;
		}
		// valida violacao de regra de latência - aguardando desenvolvimento Portugal
		return null;
	}

	public int analisaSequenciaErros(List<CasoDeTeste> j) {

		int contador = 0;
		for (Iterator iterator = j.iterator(); iterator.hasNext();) {
			CasoDeTeste casoDeTeste = (CasoDeTeste) iterator.next();
			if (casoDeTeste.getRunTest().getStatus() == Constants.STATUS_FAILED) {
				contador++;
			} else {
				contador = 0;
			}
		}
		return contador;
	}

	public double getDisponibilidade(List<CasoDeTeste> j) {
		int contadorPassed = 0;
		int totalCasos = j.size();
		if (totalCasos == 0)
			return 100.00;

		for (Iterator iterator = j.iterator(); iterator.hasNext();) {
			CasoDeTeste casoDeTeste = (CasoDeTeste) iterator.next();
			if (casoDeTeste.getRunTest().isPassed()) {
				contadorPassed++;
			}
		}
		double disponibilidade = ((double) contadorPassed / totalCasos * 100);
		disponibilidade = Utils.doubleComDuasCasasDecimais(disponibilidade);
		return disponibilidade;

	}

	public Violacao analisaDisponibilidade(List<CasoDeTeste> j) {
		double disponibilidade = getDisponibilidade(j);
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
