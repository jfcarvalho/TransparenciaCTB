package com.ctb.contratos.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import com.ctb.Processo;
import com.ctb.licitacoes.model.Licitacao;

@Entity
public class Contrato {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_contrato;
	@Size(min=2, max=100, message="Tamanho do campo numero deve ser entre 1 e 30")
	private String numero;
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_assinatura;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	private Date data_vencimento;
	private String objeto;
	@Enumerated(EnumType.STRING)
	private Uso uso;
	@Enumerated(EnumType.STRING)
	private Recurso recurso;
	@Enumerated(EnumType.STRING)
	private Fonte fonte;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	private Date ultima_atualizacao;
	private Integer duracao_meses;
	private Integer meses_vencimento;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	private Date vencimento_garantia;
	@DecimalMin(value = "0.00", message = "Valor não pode ser menor que 0,00")
	@DecimalMax(value = "99999999999.99", message = "Valor não pode ser maior que 9.999.9999,99")
   @NumberFormat(pattern = "#,##0.00")
	private BigDecimal saldo_contrato;
	@DecimalMin(value = "0.00", message = "Valor não pode ser menor que 0,00")
	@DecimalMax(value = "9999999999999.99", message = "Valor não pode ser maior que 9.999.999,99")
   @NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor_contrato;
	@ManyToOne(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST})
	@JoinColumn(name="contrato_id_contrato")
	private Contratado contratado;
	
	
	
	@ManyToOne(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST})
	@JoinColumn(name="contrato_id_gestor")
	private Usuario gestor;
			
	@ManyToOne(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST})
	@JoinColumn(name="contrato_id_fiscal")
	private Usuario fiscal;
	
	@OneToMany(mappedBy="contrato")
	private List<Lancamento> lancamentos;
	@Size(min=2, max=30, message="Tamanho do campo objeto deve ser entre 1 e 50")
	private String nomeResponsavel;
	@Size(min=2, max=30, message="Tamanho do campo objeto deve ser entre 1 e 50")
	private String cpfResponsavel;
	private boolean [] avisos_dias;
	@OneToOne
	@JoinColumn(name="contrato_id_processo")
	private Processo processo; //Processo de renovação
	
	@OneToOne
	@JoinColumn(name="contrato_id_licitacao")
	private Licitacao licitacao; //Liitação
	
	private boolean riscofinanceiro;
	
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date doe;
	
	@OneToOne
	@JoinColumn(name="contrato_id_comissao")
	private Comissao comissao;
	
	
	
	
	public Integer getId_contrato()
	{
		return this.id_contrato;
	}
	public void setId_contrato(Integer id_contrato)
	{
		this.id_contrato = id_contrato;
	}
	
	public String getNumero()
	{
		return this.numero;
	}
	
	public void setNumero(String numero)
	{
		this.numero = numero;
	}
	
	public Date getDoe()
	{
		return this.doe;
	}
	
	public void setDoe(Date doe)
	{
		this.doe = doe;
	}
	
	public Date getData_assinatura()
	{
		return this.data_assinatura;
	}
	
	public void setData_assinatura(Date data_assinatura)
	{
		this.data_assinatura = data_assinatura;
	}
	public Date getData_vencimento()
	{
		return this.data_vencimento;
	}
	
	public void setData_vencimento(Date data_vencimento)
	{
		this.data_vencimento= data_vencimento;
	}
	
	public String getObjeto()
	{
		return this.objeto;
	}
	
	public void setObjeto(String objeto)
	{
		this.objeto = objeto;
	}
	
	public Uso getUso()
	{
		return this.uso;
	}
	
	public void setUso(Uso uso)
	{
		this.uso = uso;
	}
	
	public Recurso getRecurso()
	{
		return this.recurso;
	}
	
	public void setRecurso(Recurso recurso)
	{
		this.recurso = recurso;
	}
	
	public Fonte getFonte()
	{
		return this.fonte;
	}
	
	public void setFonte(Fonte fonte)
	{
		this.fonte = fonte;
	}
	
	public Date getUltima_atualizacao()
	{
		return this.ultima_atualizacao;
	}
	
	public void setUltima_atualizacao(Date ultima_atualizacao)
	{
		this.ultima_atualizacao = ultima_atualizacao;
	}
	
	public Integer getDuracao_meses()
	{
		return this.duracao_meses;
	}
	
	public void setDuracao_meses(Integer duracao_meses)
	{
		this.duracao_meses = duracao_meses;
	}
	
	public Date getVencimento_garantia()
	{
		return this.vencimento_garantia;
	}
	
	public void setVencimento_garantia(Date vencimento_garantia)
	{
		this.vencimento_garantia= vencimento_garantia;
	}
	
	public BigDecimal getSaldo_contrato()
	{
		return this.saldo_contrato;
	}
	
	public void setSaldo_contrato(BigDecimal bigDecimal)
	{
		this.saldo_contrato= bigDecimal;
	}
	
	public Integer getMeses_vencimento()
	{
		return this.meses_vencimento;
	}
	
	public void setMeses_vencimento(Integer meses_vencimento)
	{
		this.meses_vencimento= meses_vencimento;
	}
	
	public Contratado getContratado()
	{
		return this.contratado;
	}
	
	public void setContratado(Contratado contratado)
	{
		this.contratado= contratado;
	}
	public Usuario getFiscal()
	{
		return this.fiscal;
	}
	
	public void setFiscal(Usuario fiscal)
	{
		this.fiscal = fiscal;
	}
	
	public Usuario getGestor()
	{
		return this.gestor;
	}
	
	public void setGestor(Usuario gestor)
	{
		this.gestor= gestor;
	}
	
	
	public List<Lancamento> getLancamentos()
	{
		return this.lancamentos;
	}
	
	public void setLancamentos(List<Lancamento> lancamentos)
	{
		this.lancamentos = lancamentos;
	}
	public String getNomeResponsavel() {
		return nomeResponsavel;
	}
	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}
	public String getCpfResponsavel() {
		return cpfResponsavel;
	}
	public void setCpfResponsavel(String cpfResponsavel) {
		this.cpfResponsavel = cpfResponsavel;
	}
	public BigDecimal getValor_contrato() {
		return valor_contrato;
	}
	public void setValor_contrato(BigDecimal valor_contrato) {
		this.valor_contrato = valor_contrato;
	}
	public boolean [] getAvisos_dias() {
		return avisos_dias;
	}
	public void setAvisos_dias(boolean [] avisos_dias) {
		this.avisos_dias = avisos_dias;
	}
	public Processo getProcesso() {
		return processo;
	}
	public void setProcesso(Processo processo) {
		this.processo = processo;
	}
	
	public Licitacao getLicitacao()
	{
		return this.licitacao;
	}
	
	public void setLicitacao(Licitacao licitacao)
	{
		this.licitacao = licitacao;
	}
	public boolean getRiscofinanceiro() {
		return riscofinanceiro;
	}
	public void setRiscofinanceiro(boolean riscofinanceiro) {
		this.riscofinanceiro = riscofinanceiro;
	}
	
	public Comissao getComissao()
	{
		return this.comissao;
	}
	
	public void setComissao(Comissao comissao)
	{
		this.comissao = comissao;
	}
	
	
	
	
}
