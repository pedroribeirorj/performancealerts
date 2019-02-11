package noesis.performancealerts.model;

public enum ViolacaoTipo {
	SEM_VIOLACAO, VIOLACAO_POR_MAXIMO_FALHAS_SEGUIDAS, VIOLACAO_POR_INDISPONIBILIDADE, VIOLACAO_POR_LATENCIA;

	public static int procurarOpcao(int opcao) {
		for (ViolacaoTipo a : ViolacaoTipo.values()) {
			if (a.ordinal() == opcao) {
				return opcao;
			}
		}
		return -1;
	}
}