package noesis.performancealerts.model;

public enum ViolacaoGravidade {
	GRAVIDADE_VIOLACAO_CRITICA, GRAVIDADE_VIOLACAO_NAO_CRITICA;
	public static int procurarOpcao(int opcao) {
		for (ViolacaoGravidade a : ViolacaoGravidade.values()) {
			if (a.ordinal() == opcao) {
				return opcao;
			}
		}
		return -1;
	}
}
