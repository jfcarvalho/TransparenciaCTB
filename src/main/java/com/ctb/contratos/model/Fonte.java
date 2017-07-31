package com.ctb.contratos.model;

public enum Fonte {
	Custeio("Custeio"),
	Investimento("Investimento");
	
	private String fonte;


 Fonte (String fonte)
{
	this.fonte= fonte;
}

public String getFonte()
{
	return fonte;
}

public void setFonte(String fonte)
{
	this.fonte= fonte;
}

}

