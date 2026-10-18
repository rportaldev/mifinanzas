package com.rportaldev.mifinanzas.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rportaldev.mifinanzas.dto.auth.AuthResponseDTO;
import com.rportaldev.mifinanzas.dto.auth.LoginDTO;
import com.rportaldev.mifinanzas.dto.auth.RegisterDTO;
import com.rportaldev.mifinanzas.entity.Usuario;
import com.rportaldev.mifinanzas.enums.RolUsuario;
import com.rportaldev.mifinanzas.exception.CorreoYaExisteException;
import com.rportaldev.mifinanzas.exception.RecursoNoEncontradoException;
import com.rportaldev.mifinanzas.repository.UsuarioRepository;
import com.rportaldev.mifinanzas.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	public AuthResponseDTO registrar(RegisterDTO dto) {
		
		if(usuarioRepository.existsByCorreo(dto.getCorreo())) {
			throw new CorreoYaExisteException("El correo ya esta registrado:" + dto.getCorreo());
		}
		
		Usuario usuario = new Usuario();
		usuario.setNombre(dto.getNombre());
		usuario.setCorreo(dto.getCorreo());
		usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
		usuario.setRol(RolUsuario.ROLE_USER);
		
		usuarioRepository.save(usuario);
		
		String token = jwtService.generarToken(usuario);
		
		return new AuthResponseDTO(
				token,
				"Bearer",
				usuario.getNombre(),
				usuario.getCorreo());
		
	}
	
	
	public AuthResponseDTO login(LoginDTO dto) {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					dto.getCorreo(), 
					dto.getPassword()));
		
		Usuario usuario = usuarioRepository.findByCorreo(dto.getCorreo())
				.orElseThrow(() -> new RecursoNoEncontradoException(
						"Usuario no encontrado con correo: " + dto.getCorreo()));
		
		String token = jwtService.generarToken(usuario);
		
		return new AuthResponseDTO(
				token,
				"Bearer",
				usuario.getNombre(),
				usuario.getCorreo());
	}
}
