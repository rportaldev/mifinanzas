package com.rportaldev.mifinanzas.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rportaldev.mifinanzas.entity.Categoria;
import com.rportaldev.mifinanzas.enums.TipoMovimiento;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	List<Categoria> findByUsuarioId(Long usuarioId);
	Optional<Categoria> findByIdAndUsuarioId(Long id, Long usuarioId);
	boolean existsByNombreAndUsuarioIdAndTipo(String nombre, Long usuarioId, TipoMovimiento tipo);
}
