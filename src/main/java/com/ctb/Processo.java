package com.ctb;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

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
	
}
