package com.rportaldev.mifinanzas.security;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rportaldev.mifinanzas.entity.Usuario;
import com.rportaldev.mifinanzas.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

	private final UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		
		Usuario usuario = usuarioRepository.findByCorreo(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + username));
		
		return User.builder()
				.username(usuario.getCorreo())
				.password(usuario.getPassword())
				.authorities(usuario.getRol().name())
				.build();
	}
}
