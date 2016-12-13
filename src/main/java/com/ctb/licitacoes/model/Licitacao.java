package com.ctb.licitacoes.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Licitacao {
	@Id
	private Integer Id_licitacao;
	private String numeroLicitacao;
	private Modalidade modalidade;
	private float valor;
	private String observacao;
	private String objeto;
	
	public String getNumeroLicitacao() {
		return numeroLicitacao;
	}
	public void setNumeroLicitacao(String numeroLicitacao) {
		this.numeroLicitacao = numeroLicitacao;
	}
	public Modalidade getModalidade() {
		return modalidade;
	}
	public void setModalidade(Modalidade modalidade) {
		this.modalidade = modalidade;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	
	
	

}
