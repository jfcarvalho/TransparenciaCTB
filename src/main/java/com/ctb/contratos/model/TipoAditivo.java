package com.ctb.contratos.model;

public enum TipoAditivo {
	Valor("Valor"),
	Tempo("Prazo"),
	ValorTempo("Valor e Prazo");
	private String tipoaditivo;


 TipoAditivo (String tipoaditivo)
{
	this.tipoaditivo= tipoaditivo;
}

public String getTipoaditivo()
{
	return tipoaditivo;
}

public void setTipoaditivo(String tipoaditivo)
{
	this.tipoaditivo= tipoaditivo;
}

}

