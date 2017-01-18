package com.ctb.licitacoes.model;

public enum Modalidade {
	Concorrencia("Concorrência"),
	Concurso("Concurso"),
	Convite("Convite"),
	Credenciamento("Credenciamento"),
	PregaoE("Pregão Eletrônico"),
	PregaoP("Pregão Presencial"),
	TomadaDePreco("Tomada de Preço"),
	Leilao("Leilão");
	
	private String descricao;
	
	Modalidade(String descricao)
	{
		this.setDescricao(descricao);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
