package com.ctb.contratos.model;

import java.math.BigDecimal;

public class AnoResumo {
	
	private BigDecimal [] valores = new BigDecimal[12];
	private BigDecimal [] aditivos = new BigDecimal[12];
	private BigDecimal [] saldos = new BigDecimal[12];
	
	public AnoResumo()
	{
		
	}
	public BigDecimal [] getValores() {
		return valores;
	}
	public void setValores(BigDecimal [] valores) {
		this.valores = valores;
	}
	public BigDecimal [] getAditivos() {
		return aditivos;
	}
	public void setAditivos(BigDecimal [] aditivos) {
		this.aditivos = aditivos;
	}
	public BigDecimal [] getSaldos() {
		return saldos;
	}
	public void setSaldos(BigDecimal [] saldos) {
		this.saldos = saldos;
	}

}
