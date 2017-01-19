package com.ctb.contratos.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.ctb.Processo;


@Entity

public class Lancamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_lancamento;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	
	private Date data;
	
	private String numero_nota_fiscal;

	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
   @NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor;
	private Integer aditivo_n;
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	 @NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor_aditivo;
	private String observacao;
	private boolean possui_aditivo;
	@Enumerated(EnumType.STRING)
	private TipoAditivo tipoaditivo;
	private boolean liquidado;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	private Date dataliquidacao;
	
	private Integer meses_prorrogacao;
	private String medicao;
	
	
	
	@OneToOne
	@JoinColumn(name="lancamento_id_processo")
	private Processo processo;
	
	@ManyToOne
	@JoinColumn(name= "lancamento_id_contrato")
	private Contrato contrato;
	private BigDecimal saldo_contrato;
	private String hora;
	
	
	
	
	public Integer getId_lancamento()
	{
		return this.id_lancamento;
	}
	public void setId_lancamento(Integer id_lancamento)
	{
		this.id_lancamento = id_lancamento;
	}
	
	public Date getData()
	{
		return this.data;
	}
	public void setData(Date data)
	{
		this.data= data;
	}
	
	
	public String getNumero_nota_fiscal()
	{
		return this.numero_nota_fiscal;
	}
	
	public void setNumero_nota_fiscal(String numero_nota_fiscal)
	{
		this.numero_nota_fiscal = numero_nota_fiscal;
	}
	
	
	
	public BigDecimal getValor()
	{
		return this.valor;
	}
	public void setValor(BigDecimal valor)
	{
		this.valor = valor;
	}
	
	public BigDecimal getValor_aditivo()
	{
		return this.valor_aditivo;
	}
	public void setValor_aditivo(BigDecimal valor_aditivo)
	{
		this.valor_aditivo = valor_aditivo;
	}
	
	public Integer getAditivo_n()
	{
		return this.aditivo_n;
	}
	public void setAditivo_n(Integer aditivo_n)
	{
		this.aditivo_n = aditivo_n;
	}
	public String getObservacao()
	
	{
		return this.observacao;
	}
	public Processo getProcesso()
	{
		return this.processo;
	}
	public void setProcesso(Processo processo)
	{
		this.processo = processo;
	}
	
	public void setObservacao(String observacao)
	{
		this.observacao = observacao;
	}
	
	public Contrato getContrato()
	{
		return this.contrato;
	}
	public void setContrato(Contrato contrato)
	{
		this.contrato= contrato;
	}
	public boolean getPossui_aditivo() {
		return possui_aditivo;
	}
	public void setPossui_aditivo(boolean possui_aditivo) {
		this.possui_aditivo = possui_aditivo;
	}
	public TipoAditivo getTipoAditivo()
	{
		return tipoaditivo;
	}
	public void setTipoAditivo(TipoAditivo tipoaditivo)
	{
		this.tipoaditivo = tipoaditivo;
	}
	public boolean getLiquidado() {
		return liquidado;
	}
	public void setLiquidado(boolean liquidado) {
		this.liquidado = liquidado;
	}
	public Date getDataliquidacao() {
		return dataliquidacao;
	}
	public void setDataliquidacao(Date dataliquidacao) {
		this.dataliquidacao = dataliquidacao;
	}
	public Integer getMeses_prorrogacao() {
		return meses_prorrogacao;
	}
	public void setMeses_prorrogacao(Integer meses_prorrogacao) {
		this.meses_prorrogacao = meses_prorrogacao;
	}
	public BigDecimal getSaldo_contrato() {
		return saldo_contrato;
	}
	public void setSaldo_contrato(BigDecimal d) {
		this.saldo_contrato = d;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getMedicao() {
		return medicao;
	}
	public void setMedicao(String medicao) {
		this.medicao = medicao;
	}
}
