package com.ctb.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ctb.contratos.model.Usuario;
import com.ctb.contratos.repository.Usuarios;

@Service
public class AppUserDetailsService implements UserDetailsService  {

	@Autowired
	private Usuarios usuarios;
	public static UserDetails cusuario = null;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = usuarios.porEmail(email);
		Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
		
		UserDetails aux = new UsuarioSistema(usuario, getPermissoes(usuario));
		
		AppUserDetailsService.cusuario = aux;
		return aux;
	}
	
	private Collection<? extends GrantedAuthority> getPermissoes(Usuario usuario)
	{
			Set<SimpleGrantedAuthority> authorities = new HashSet<>();
			
			List<String> permissoes = usuarios.permissoes(usuario);
			permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
			
			return authorities;
	
	}
}
