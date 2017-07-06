package com.ctb.contratos.model;

public class AditivoSetting {
	private float valor;
	private String periodo;
	
	public AditivoSetting(float valor)
	{
		this.valor = valor;
	}
	
	public float getValor()
	{
		return this.valor;
	}
	
	public void setValor(float valor)
	{
		this.valor = valor;
	}
	public String getPeriodo()
	{
		return this.periodo;
	}
	
	public void setPeriodo(String periodo)
	{
		this.periodo = periodo;
	}

}
