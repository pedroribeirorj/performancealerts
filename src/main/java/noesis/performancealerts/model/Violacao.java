package noesis.performancealerts.model;

import noesis.performancealerts.utils.Constants;

public class Violacao {
	int tipoViolacao;
	int gravidadeViolacao;
	String value;

	public Violacao(int tipoViolacao, int gravidadeViolacao, String value) {
		this.tipoViolacao = ViolacaoTipo.procurarOpcao(tipoViolacao);
		this.gravidadeViolacao = ViolacaoGravidade.procurarOpcao(gravidadeViolacao);
		this.value = value;
		if (this.tipoViolacao == -1 || this.gravidadeViolacao == -1 || value.isEmpty())
			throw new IllegalArgumentException(
					"[PerformanceAlerts] Violacao não pode ser criada com parâmetros informados.");
	}

	private Violacao() {
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getTipoViolacao() {
		return tipoViolacao;
	}

	public void setTipoViolacao(int tipoViolacao) {
		this.tipoViolacao = tipoViolacao;
	}

	public int getGravidadeViolacao() {
		return gravidadeViolacao;
	}

	public void setGravidadeViolacao(int gravidadeViolacao) {
		this.gravidadeViolacao = gravidadeViolacao;
	}

	public boolean existeViolacao() {
		return tipoViolacao != Constants.SEM_VIOLACAO;
	}

	public boolean gravidadeCritica() {
		return gravidadeViolacao == Constants.GRAVIDADE_VIOLACAO_CRITICA;
	}

	public boolean violacaoIndisponibilidade() {
		return tipoViolacao == Constants.VIOLACAO_POR_INDISPONIBILIDADE;
	}

	public boolean violacaoFalhasSeguidas() {
		return tipoViolacao == Constants.VIOLACAO_POR_MAXIMO_FALHAS_SEGUIDAS;
	}
}
