package com.ctb.contratos.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity

public class Contratado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_contratado;
	private String nome;
	private String cnpj;
	@OneToMany(mappedBy="contratado")
	private List<Contrato> contratos;
	//private Contrato contrato;
	
	public Integer getId_contratado()
	{
		return this.id_contratado;
	}
	
	public void setId_contratado(Integer id_contratado)
	{
		this.id_contratado = id_contratado;
	}
	
	public String getNome()
	{
		return this.nome;
	}
	
	public void setNome(String nome)
	{
		this.nome = nome;
	}
	
	public String getCnpj()
	{
		return this.cnpj;
	}
	
	public void setCnpj(String cnpj)
	{
		this.cnpj = cnpj;
	}
	
	public List<Contrato> getContratos()
	{
		return this.contratos;
	}
	public void setContratos(List<Contrato> contratos)
	{
		this.contratos = contratos;
	}
}
