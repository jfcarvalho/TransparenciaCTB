package com.ctb.contratos.repository;

import java.util.Optional;

import com.ctb.contratos.model.Usuario;

public interface UsuariosQueries {

	public Optional<Usuario> porEmail(String email);
}
