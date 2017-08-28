package com.ctb.contratos.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Comissao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_comissao;
	
	@OneToMany(mappedBy="usuario_comissao")
	private List<Usuario> usuarios;
	
	@OneToOne
	@JoinColumn(name="comissao_id_contrato")
	private Contrato contrato;
	
	public Integer getId_comissao()
	{
		return this.id_comissao;
	}
	public void setId_comissao(Integer id_comissao)
	{
		this.id_comissao = id_comissao;
	}
	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
	

}
