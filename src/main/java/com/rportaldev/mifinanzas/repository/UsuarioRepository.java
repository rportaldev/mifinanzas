package com.rportaldev.mifinanzas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rportaldev.mifinanzas.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByCorreo(String correo);
	boolean existsByCorreo (String correo);
}
