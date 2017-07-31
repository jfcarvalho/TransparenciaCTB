package com.ctb.contratos.model;

public enum Recurso {

	Convenio("Convênio"),
	Proprio("Próprio");
	
	
	private String recurso;


 Recurso(String recurso)
{
	this.recurso= recurso;
}

public String getRecurso()
{
	return recurso;
}

public void setRecurso(String recurso)
{
	this.recurso= recurso;
}

}
