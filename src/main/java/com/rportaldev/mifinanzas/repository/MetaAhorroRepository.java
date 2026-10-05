package com.rportaldev.mifinanzas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rportaldev.mifinanzas.entity.MetaAhorro;

public interface MetaAhorroRepository extends JpaRepository<MetaAhorro, Long>{

	List<MetaAhorro> findByUsuarioId(Long usuarioId);
	Optional<MetaAhorro> findByIdAndUsuarioId(Long id, Long usuarioId);
}
