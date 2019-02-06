package noesis.performancealerts.controller;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.Constants;
import utils.Utils;
import noesis.performancealerts.dao.RunJPADAO;
import noesis.performancealerts.model.CasoDeTeste;
import noesis.performancealerts.model.Violacao;

public class Regras {
	static Logger logger = LoggerFactory.getLogger(Regras.class.getName());

	public static Violacao analisaConformidade(String idSuite, int idCasoDeTeste) {

		// analisa se há inconformidade para um caso de teste de uma suíte considerando

		// um volume amostral e regras de negócio
		Regras r = new Regras();
		List<CasoDeTeste> casoDeTeste = RunJPADAO.getInstance().recuperaCasosDeTestePorSuite(idSuite, idCasoDeTeste);
		if (casoDeTeste.size() == 0)
			return null;
		Violacao v = new Violacao();

		// valida existencia de sequencia de erros além do permitido
		if (r.analisaSequenciaErros(casoDeTeste)) {
			logger.error("[PerformanceAlerts] Identificado caso de teste com erros em sequência.");
			v.setGravidadeViolacao(Constants.GRAVIDADE_VIOLACAO_CRITICA);
			v.setTipoViolacao(Constants.VIOLACAO_POR_MAXIMO_FALHAS_SEGUIDAS);
			return v;
		}

		// valida violacao de regra de indisponibilidade
		Object obj = r.analisaDisponibilidade(casoDeTeste);
		if (obj != null) {
			logger.info("[PerformanceAlerts] Identificado caso de teste com erro de indisponibilidade.");
			return (Violacao) obj;
		}

		// valida violacao de regra de latência - aguardando desenvolvimento Portugal	
		
		return v;

	}

	public boolean analisaSequenciaErros(List<CasoDeTeste> j) {
		int contador = 0;
		for (Iterator iterator = j.iterator(); iterator.hasNext();) {
			CasoDeTeste casoDeTeste = (CasoDeTeste) iterator.next();
			if (casoDeTeste.getRunTest().getStatus() == Constants.STATUS_FAILED) {
				contador++;
			} else {
				contador = 0;
			}
			if (contador > Constants.MAXIMO_FALHAS_SEGUIDAS_PERMITIDAS)
				return true;
		}
		return contador > Constants.MAXIMO_FALHAS_SEGUIDAS_PERMITIDAS;
	}

	public double getDisponibilidade(List<CasoDeTeste> j) {
		double disponibilidade = 0.0;

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
		disponibilidade = (contadorPassed / totalCasos) * 100;
		disponibilidade = Utils.doubleComDuasCasasDecimais(disponibilidade);
		return disponibilidade;

	}

	public Violacao analisaDisponibilidade(List<CasoDeTeste> j) {
		double disponibilidade = getDisponibilidade(j);
		Violacao v = new Violacao();
		if (disponibilidadeOK(disponibilidade)) {
			return null;
		}
		v.setTipoViolacao(Constants.VIOLACAO_POR_INDISPONIBILIDADE);
		if (disponibilidadeCritica(disponibilidade)) {
			v.setGravidadeViolacao(Constants.GRAVIDADE_VIOLACAO_CRITICA);
		} else {
			v.setGravidadeViolacao(Constants.GRAVIDADE_VIOLACAO_NAO_CRITICA);
		}
		v.setValue(String.valueOf(disponibilidade));
		return v;
	}

	public boolean disponibilidadeOK(double disponibilidade) {
		return disponibilidade >= Constants.THRESHOLD_DISPONIBILIDADE;
	}

	public boolean disponibilidadeCritica(double disponibilidade) {
		return disponibilidade < Constants.THRESHOLD_DISPONIBILIDADE_MINIMA;
	}

	public boolean latenciaOK(double latencia) {
		return latencia >= Constants.THRESHOLD_LATENCIA;
	}

	public boolean latenciaCritica(double latencia) {
		return latencia < Constants.THRESHOLD_LATENCIA_MINIMA;
	}
}
