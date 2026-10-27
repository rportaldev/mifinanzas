package com.rportaldev.mifinanzas.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rportaldev.mifinanzas.entity.AporteAhorro;

public interface AporteAhorroRepository extends JpaRepository<AporteAhorro, Long>{

	List<AporteAhorro> findByMetaAhorroId(Long metaAhorroId);
	boolean existsByMetaAhorroId(Long metaAhorroId);
	Optional<AporteAhorro> findByIdAndMetaAhorroId(Long id, Long metaAhorroId);
	
	@Query("SELECT COALESCE(SUM(a.monto), 0) "
			+ "FROM AporteAhorro a WHERE a.metaAhorro.id = :metaAhorroId")
	BigDecimal sumarAportesPorMeta(Long metaAhorroId);

	@Query("SELECT COALESCE(SUM(a.monto), 0) FROM AporteAhorro a "
			+ "WHERE a.metaAhorro.usuario.id = :usuarioId "
			+ "AND a.fecha BETWEEN :desde AND :hasta")
	BigDecimal sumarAportesPorUsuarioYFecha(@Param("usuarioId") Long usuarioId,
			@Param("desde") LocalDate desde,
			@Param("hasta") LocalDate hasta);
}
