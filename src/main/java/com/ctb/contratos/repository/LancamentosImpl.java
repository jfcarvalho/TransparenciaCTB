package com.ctb.contratos.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.ctb.contratos.model.Contrato;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.Usuario;

public abstract class LancamentosImpl {
	
	@Autowired
	private Usuarios usuarios;
	

	public List<Lancamento> porNota(String nota_fiscal, Integer id_usuario) {
		Usuario us = usuarios.findOne(id_usuario);
		List<Contrato> contratos_usuario = us.getContratosGeridos();
		List<Lancamento> lancamentos_limitados = new ArrayList<Lancamento>();
		for(Contrato c: contratos_usuario)
		{
			List<Lancamento> lancs = c.getLancamentos();
			for(Lancamento l: lancs)
			{
				if(l.getNumero_nota_fiscal().contains(nota_fiscal))
				{
					lancamentos_limitados.add(l);
				}
			}
		}
		return lancamentos_limitados;
	}
	

}
