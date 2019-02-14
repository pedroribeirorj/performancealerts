package noesis.performancealerts.model;

import static org.junit.Assert.*;

import org.junit.Test;

import noesis.performancealerts.utils.Constants;

public class ViolacaoTest {

	@Test(expected = IllegalArgumentException.class)
	public void violacao_criarRegistroIndevido() {
		new Violacao(4, 4, "asd");
	}

	public void violacao_criarRegistroDevido() {
		new Violacao(0, 0, "asd");
		assert (true);
	}

	@Test
	public void existeViolacao() {
		Violacao v = new Violacao(0, 0, "7.8");
		boolean validacao = v.getTipoViolacao() != ViolacaoTipo.SEM_VIOLACAO.ordinal();
		assert (v.existeViolacao() == validacao);
	}

	@Test
	public void gravidadeCritica() {
		Violacao v = new Violacao(0, 0, "7.8");
		assert (v.getGravidadeViolacao() == ViolacaoGravidade.GRAVIDADE_VIOLACAO_CRITICA.ordinal());
	}

	@Test
	public void violacaoIndisponibilidade() {
		Violacao v = new Violacao(2, 0, "7.8");
		assert (v.getTipoViolacao() == ViolacaoTipo.VIOLACAO_POR_INDISPONIBILIDADE.ordinal());
	}
}
