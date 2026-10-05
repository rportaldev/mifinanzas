package com.rportaldev.mifinanzas.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rportaldev.mifinanzas.entity.Movimiento;
import com.rportaldev.mifinanzas.enums.TipoMovimiento;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

	Page<Movimiento> findByUsuarioId(Long usuarioId, Pageable pageable);
	Page<Movimiento> findByUsuarioIdAndTipo(Long usuarioId, TipoMovimiento tipo, Pageable pageable);
	Optional<Movimiento> findByIdAndUsuarioId(Long id, Long usuarioId);
	
	@Query("SELECT COALESCE(SUM(m.monto), 0) FROM Movimiento m " +
		       "WHERE m.usuario.id = :usuarioId AND m.tipo = :tipo " +
		       "AND m.fecha BETWEEN :desde AND :hasta")
	BigDecimal sumarMontoPorUsuarioYTipo(Long usuarioId, TipoMovimiento tipo, LocalDate desde, LocalDate hasta);
	
	@Query("SELECT c.nombre, SUM(m.monto) FROM Movimiento m " +
		       "JOIN m.categoria c " +
		       "WHERE m.usuario.id = :usuarioId AND m.tipo = 'GASTO' " +
		       "GROUP BY c.nombre")
	List<Object[]> sumarGastosPorCategoria(Long usuarioId);
}
