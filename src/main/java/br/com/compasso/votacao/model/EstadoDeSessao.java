package br.com.compasso.votacao.model;

public enum EstadoDeSessao {

	ABERTA("ABERTA"),
	FECHADA("FECHADA");
	private final String descricao;

	EstadoDeSessao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
