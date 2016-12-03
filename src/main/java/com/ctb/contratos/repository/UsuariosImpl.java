package com.ctb.contratos.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ctb.contratos.model.Usuario;

public class UsuariosImpl implements UsuariosQueries {
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public Optional<Usuario> porEmail(String email) {
		return manager.createQuery("from Usuario where lower(email) = lower(:email)", Usuario.class).setParameter("email", email).getResultList().stream().findFirst();
	}
}
