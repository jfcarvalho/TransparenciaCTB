package com.ctb.contratos.util;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

public class ContratosAVencer {
	private String numero;
	private String empresa;
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_vencimento;
	private String saldo;
	private String valor;
	private String total_pago;
	private String dias_vencimento;
	
	public ContratosAVencer(String numero, String empresa, Date data_vencimento, String saldo, String valor, String total_pago, String dias_vencimento)
	{
		this.numero = numero;
		this.empresa = empresa;
		this.data_vencimento = data_vencimento;
		this.saldo = saldo;
		this.total_pago = total_pago;
		this.dias_vencimento = dias_vencimento;
		
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getSaldo() {
		return saldo;
	}
	public void setSaldo(String saldo) {
		this.saldo = saldo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getTotal_pago() {
		return total_pago;
	}
	public void setTotal_pago(String total_pago) {
		this.total_pago = total_pago;
	}
	public String getDias_vencimento() {
		return dias_vencimento;
	}
	public void setDias_vencimento(String dias_vencimento) {
		this.dias_vencimento = dias_vencimento;
	}
	
	public Date getData_vencimento() {
		return data_vencimento;
	}
	public void setData_vencimento(Date data_vencimento) {
		this.data_vencimento = data_vencimento;
	}
}
