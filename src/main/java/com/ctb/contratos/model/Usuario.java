package com.ctb.contratos.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

@Entity

public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id_usuario;
	private String nome;
	private String matricula;
	private String password;
	private String setor;
	private String funcao;
	private String email;
	private String telefone;
	@OneToMany(mappedBy="gestor")
	private List<Contrato> contratosGeridos;
	@OneToMany(mappedBy="fiscal")
	private List<Contrato> contratosFiscalizados;
	
	@Size(min = 1, message = "Selecione pelo menos um grupo")
	@ManyToMany
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "codigo_usuario")
				, inverseJoinColumns = @JoinColumn(name = "codigo_grupo"))	
	private List<Grupo> grupos;

	
	public Integer getId_usuario()
	{
		return this.id_usuario;
	}
	
	public void setId_usuario(Integer id_usuario)
	{
		this.id_usuario = id_usuario;
	}
	public String getMatricula() {
		return this.matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTelefone()
	{
		return this.telefone;
	}
	
	public void setTelefone(String telefone)
	{
		this.telefone= telefone;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public void setEmail(String email)
	{
		this.email= email;
	}
	
	public String getSetor()
	{
		return this.setor;
	}
	
	public void setSetor(String setor)
	{
		this.setor= setor;
	}
	
	public String getFuncao()
	{
		return this.funcao;
	}
	
	public void setFuncao(String funcao)
	{
		this.funcao= funcao;
	}
	public List<Contrato> getContratosGeridos()
	{
		return this.contratosGeridos;
	}
	public void setContratosGeridos(List<Contrato> contratosGeridos)
	{
		this.contratosGeridos = contratosGeridos;
	}
	public List<Contrato> getContratosFiscalizados()
	{
		return this.contratosFiscalizados;
	}
	public void setContratosFiscalizados(List<Contrato> contratosFiscalizados)
	{
		this.contratosFiscalizados= contratosFiscalizados;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	
}
