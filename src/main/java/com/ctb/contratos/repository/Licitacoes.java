package com.ctb.contratos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ctb.contratos.model.Lancamento;
import com.ctb.licitacoes.model.Licitacao;
	
	public interface Licitacoes extends JpaRepository<Licitacao, Integer>{


}
