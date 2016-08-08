package com.ctb.contratos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

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
	private float valor;
	private Integer aditivo_n;
	private String observacao;
	
	
	
	@OneToOne
	@JoinColumn(name="lancamento_id_processo")
	private Processo processo;
	
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
	
	
	
	public float getValor()
	{
		return this.valor;
	}
	public void setValor(float valor)
	{
		this.valor = valor;
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
	
}
