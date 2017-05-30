package com.ctb.contratos.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Documento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_documento;
	
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date data_cadastro;
	
	
	public Integer getId_documento()
	{
		return this.id_documento;
	}
	
	public void setId_documento(Integer id_documento)
	{
		this.id_documento = id_documento;
	}
	
	public Date getData_cadastro()
	{
		return this.data_cadastro;
	}
	
	public void setData_cadastro(Date data_cadastro)
	{
		this.data_cadastro = data_cadastro;
	}
}
