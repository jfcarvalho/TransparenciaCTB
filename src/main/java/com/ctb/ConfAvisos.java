package com.ctb;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity

public class ConfAvisos {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_aviso;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern= "dd/MM/yyyy")
	private Date data_aviso;
	private String tipo_aviso;
	
	public Integer getId_aviso()
	{
		return this.id_aviso;
	}
	public void setId_aviso(Integer id_aviso)
	{
		this.id_aviso = id_aviso;
	}
	public String getTipo_aviso() {
		return tipo_aviso;
	}
	public void setTipo_aviso(String tipo_aviso) {
		this.tipo_aviso = tipo_aviso;
	}
	
	public Date getData_aviso()
	{
		return this.data_aviso;
	}
	public void setData_aviso(Date data_aviso)
	{
		this.data_aviso = data_aviso;
	}
}
