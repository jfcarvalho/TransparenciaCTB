package com.ctb.contratos.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ctb.Processo;
import com.ctb.contratos.model.Contratado;
import com.ctb.contratos.model.Lancamento;
import com.ctb.contratos.model.Usuario;

public interface LancamentosQueries {
	List<Lancamento> porNota(String nota_fiscal, Integer id_usuario);
}
