package com.ctb;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.model.Fonte;
import com.ctb.contratos.model.Lancamento;

@Entity
public class Processo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_processo;
	private String numero_processo;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	private Date data_abertura;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	private Date data_pagamento;
	private boolean pago;
	private String numero_ci;
	@Enumerated(EnumType.STRING)
	private TipoProcesso tipo_processo;
	@OneToOne
	@JoinColumn(name="processo_id_lancamento")
	private Lancamento lancamento;
	
	@OneToOne
	@JoinColumn(name="processo_id_contrato")
	private Contrato contrato;
	
	
	
	
	
	public Integer getId_processo()
	{
		return this.id_processo;
	}
	public void setId_processo(Integer id_processo)
	{
		this.id_processo = id_processo;
	}
	
	public String getNumero_processo()
	{
		return this.numero_processo;
	}
	
	public void setNumero_processo(String numero_processo)
	{
		this.numero_processo = numero_processo;
	}
	public Date getData_abertura()
	{
		return this.data_abertura;
	}
	public void setData_abertura(Date data_abertura)
	{
		this.data_abertura = data_abertura;
	}
	public Date getData_pagamento()
	{
		return this.data_pagamento;
	}
	public void setData_pagamento(Date data_pagamento)
	{
		this.data_pagamento = data_pagamento;
	}
	public boolean getPago()
	{
		return this.pago;
	}
	public void setPago(boolean pago)
	{
		this.pago = pago;
	}
	public String getNumero_ci() {
		return numero_ci;
	}
	public void setNumero_ci(String numero_ci) {
		this.numero_ci = numero_ci;
	}
	
	public TipoProcesso getTipo_processo()
	{
		return this.tipo_processo;
	}
	
	public void setTipo_processo(TipoProcesso tipo_processo)
	{
		this.tipo_processo = tipo_processo;
	}
	
	public Lancamento getLancamento()
	{
		return this.lancamento;
	}
	
	public void setLancamento(Lancamento lancamento)
	{
		this.lancamento = lancamento;
	}
	public Contrato getContrato()
	{
		return this.contrato;
	}
	
	public void setContrato(Contrato contrato)
	{
		this.contrato = contrato;
	}
	
	
}
