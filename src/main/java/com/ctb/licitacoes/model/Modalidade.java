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
	
	private String modalidade;
	
	Modalidade(String modalidade)
	{
		this.modalidade = modalidade;
	}
	
}
