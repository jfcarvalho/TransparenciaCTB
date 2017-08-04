package com.ctb.contratos.controller;

import java.math.BigDecimal;
import java.util.Date;

public class PrestacaoContas {
	private String cuo;
	private String nuo;
	private String cug;
	private String nug;
	private String contratado;
	private String cnpj;
	private String cpf;
	private String numero;
	private String objeto;
	private Date doe;
	private String licitacao_numero;
	private String licitacao_modalidade;
	private Date contrato_vigencia;
	private Date contrato_fim;
	private BigDecimal valor;
	private BigDecimal valor_com_aditivo;
	private Integer n_aditivos;
	private BigDecimal pagoExercicio;
	private BigDecimal pagoAcumulado;
	private String observacoes;
	
	public String getCuo() {
		return cuo;
	}
	public void setCuo(String cuo) {
		this.cuo = cuo;
	}
	public String getNuo() {
		return nuo;
	}
	public void setNuo(String nuo) {
		this.nuo = nuo;
	}
	public String getCug() {
		return cug;
	}
	public void setCug(String cug) {
		this.cug = cug;
	}
	public String getNug() {
		return nug;
	}
	public void setNug(String nug) {
		this.nug = nug;
	}
	public String getContratado() {
		return contratado;
	}
	public void setContratado(String contratado) {
		this.contratado = contratado;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	public Date getDoe() {
		return doe;
	}
	public void setDoe(Date doe) {
		this.doe = doe;
	}
	public String getLicitacao_numero() {
		return licitacao_numero;
	}
	public void setLicitacao_numero(String licitacao_numero) {
		this.licitacao_numero = licitacao_numero;
	}
	public String getLicitacao_modalidade() {
		return licitacao_modalidade;
	}
	public void setLicitacao_modalidade(String licitacao_modalidade) {
		this.licitacao_modalidade = licitacao_modalidade;
	}
	public Date getContrato_vigencia() {
		return contrato_vigencia;
	}
	public void setContrato_vigencia(Date contrato_vigencia) {
		this.contrato_vigencia = contrato_vigencia;
	}
	public Date getContrato_fim() {
		return contrato_fim;
	}
	public void setContrato_fim(Date contrato_fim) {
		this.contrato_fim = contrato_fim;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getValor_com_aditivo() {
		return valor_com_aditivo;
	}
	public void setValor_com_aditivo(BigDecimal valor_com_aditivo) {
		this.valor_com_aditivo = valor_com_aditivo;
	}
	public Integer getN_aditivos() {
		return n_aditivos;
	}
	public void setN_aditivos(Integer n_aditivos) {
		this.n_aditivos = n_aditivos;
	}
	public BigDecimal getPagoExercicio() {
		return pagoExercicio;
	}
	public void setPagoExercicio(BigDecimal pagoExercicio) {
		this.pagoExercicio = pagoExercicio;
	}
	public BigDecimal getPagoAcumulado() {
		return pagoAcumulado;
	}
	public void setPagoAcumulado(BigDecimal pagoAcumulado) {
		this.pagoAcumulado = pagoAcumulado;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
}
