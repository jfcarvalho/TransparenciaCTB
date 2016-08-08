package com.ctb.contratos.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.ctb.contratos.model.Usuario;

public interface Usuarios extends JpaRepository<Usuario, Integer>{
	List<Usuario> findByNomeContaining(String nome);
	
	
}
