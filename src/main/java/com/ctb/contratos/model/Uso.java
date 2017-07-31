package com.ctb.contratos.model;

public enum Uso {
	Continuo("Continuo"),
	DemandaEspecifica("Demanda Específica");
	
	private String uso;


 Uso (String uso)
{
	this.uso= uso;
}

public String getUso()
{
	return uso;
}

public void setUso(String uso)
{
	this.uso= uso;
}

}

