package com.ctb.contratos.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.ctb.Processo;

@Entity
public class Garantia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_garantia;
	private String numero;
	private String apolice;
	private String proposta;
	private String modalidade;
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	 @NumberFormat(pattern = "#,##0.00")
	private BigDecimal limite_maximo;
	private String ramo;
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	 @NumberFormat(pattern = "#,##0.00")
	private BigDecimal importancia_segurada;
	private String cobertura;
	private String objeto;
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	 @NumberFormat(pattern = "#,##0.00")
	private BigDecimal premio_liquido;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	private Date inicio_vigencia;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	private Date fim_vigencia;
	private String susep;
	@OneToOne
	@JoinColumn(name="lancamento")
	private Lancamento lancamento;
	
	public Integer getId_garantia() {
		return id_garantia;
	}
	public void setId_garantia(Integer id_garantia) {
		this.id_garantia = id_garantia;
	}
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getApolice() {
		return apolice;
	}
	public void setApolice(String apolice) {
		this.apolice = apolice;
	}
	public String getProposta() {
		return proposta;
	}
	public void setProposta(String proposta) {
		this.proposta = proposta;
	}
	public String getModalidade() {
		return modalidade;
	}
	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}
	public BigDecimal getLimite_maximo() {
		return limite_maximo;
	}
	public void setLimite_maximo(BigDecimal limite_maximo) {
		this.limite_maximo = limite_maximo;
	}
	public String getRamo() {
		return ramo;
	}
	public void setRamo(String ramo) {
		this.ramo = ramo;
	}
	public BigDecimal getImportancia_segurada() {
		return importancia_segurada;
	}
	public void setImportancia_segurada(BigDecimal importancia_segurada) {
		this.importancia_segurada = importancia_segurada;
	}
	public String getCobertura() {
		return cobertura;
	}
	public void setCobertura(String cobertura) {
		this.cobertura = cobertura;
	}
	public String getObjeto() {
		return objeto;
	}
	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}
	public BigDecimal getPremio_liquido() {
		return premio_liquido;
	}
	public void setPremio_liquido(BigDecimal premio_liquido) {
		this.premio_liquido = premio_liquido;
	}
	public Date getInicio_vigencia() {
		return inicio_vigencia;
	}
	public void setInicio_vigencia(Date inicio_vigencia) {
		this.inicio_vigencia = inicio_vigencia;
	}
	public Date getFim_vigencia() {
		return fim_vigencia;
	}
	public void setFim_vigencia(Date fim_vigencia) {
		this.fim_vigencia = fim_vigencia;
	}
	public String getSusep() {
		return susep;
	}
	public void setSusep(String susep) {
		this.susep = susep;
	}
	
	public Lancamento getLancamento()
	{
		return this.lancamento;
	}
	
	public void setLancamento(Lancamento lancamento)
	{
		this.lancamento = lancamento;
	}

}
