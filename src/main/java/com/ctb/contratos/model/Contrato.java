package com.ctb.contratos.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Contrato {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_contrato;
	private String numero;
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_assinatura;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	private Date data_vencimento;
	private String objeto;
	private String uso;
	private String recurso;
	private String fonte;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	private Date ultima_atualizacao;
	private Integer duracao_meses;
	private Integer meses_vencimento;
	private Integer vencimento_garantia;
	private float saldo_contrato;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cotrato_id_contrato")
	private Contratado contratado;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cotrato_id_gestor")
	private Usuario gestor;
			
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cotrato_id_fiscal")
	private Usuario fiscal;
	
	@OneToMany(mappedBy="contrato")
	private List<Lancamento> lancamentos;
	
	
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
	
	public String getUso()
	{
		return this.uso;
	}
	
	public void setUso(String uso)
	{
		this.uso = uso;
	}
	
	public String getRecurso()
	{
		return this.recurso;
	}
	
	public void setRecurso(String recurso)
	{
		this.recurso = recurso;
	}
	
	public String getFonte()
	{
		return this.fonte;
	}
	
	public void setFonte(String fonte)
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
	
	public Integer getVencimento_garantia()
	{
		return this.vencimento_garantia;
	}
	
	public void setVencimento_garantia(Integer vencimento_garantia)
	{
		this.vencimento_garantia= vencimento_garantia;
	}
	
	public Float getSaldo_contrato()
	{
		return this.saldo_contrato;
	}
	
	public void setSaldo_contrato(Float saldo_contrato)
	{
		this.saldo_contrato= saldo_contrato;
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
	
}
