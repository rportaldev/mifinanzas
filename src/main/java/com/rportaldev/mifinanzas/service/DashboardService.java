package com.rportaldev.mifinanzas.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rportaldev.mifinanzas.dto.dashboard.GastoCategoriaDTO;
import com.rportaldev.mifinanzas.dto.dashboard.ResumenFinancieroDTO;
import com.rportaldev.mifinanzas.entity.Usuario;
import com.rportaldev.mifinanzas.enums.TipoMovimiento;
import com.rportaldev.mifinanzas.exception.MontoInvalidoException;
import com.rportaldev.mifinanzas.exception.RecursoNoEncontradoException;
import com.rportaldev.mifinanzas.repository.AporteAhorroRepository;
import com.rportaldev.mifinanzas.repository.MovimientoRepository;
import com.rportaldev.mifinanzas.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardService {

	private final MovimientoRepository movimientoRepository;
	private final AporteAhorroRepository aporteAhorroRepository;
	private final UsuarioRepository usuarioRepository;
	
	public ResumenFinancieroDTO obtenerResumen(String correoUsuario, Integer mes, Integer anio) {
		
		Usuario usuario = obtenerUsuario(correoUsuario);
		
		YearMonth periodo = resolverPeriodo(mes, anio);
		LocalDate desde = periodo.atDay(1);
		LocalDate hasta = periodo.atEndOfMonth();
		
		BigDecimal ingresos = movimientoRepository
				.sumarMontoPorUsuarioYTipo(usuario.getId(), TipoMovimiento.INGRESO, desde, hasta);

		BigDecimal gastos = movimientoRepository
				.sumarMontoPorUsuarioYTipo(usuario.getId(), TipoMovimiento.GASTO, desde, hasta);

		BigDecimal aportes = aporteAhorroRepository
				.sumarAportesPorUsuarioYFecha(usuario.getId(), desde, hasta);

		BigDecimal disponible = ingresos.subtract(gastos).subtract(aportes);
		
		return new ResumenFinancieroDTO(ingresos, gastos, aportes, disponible);
	}
	
	
	public List<GastoCategoriaDTO> obtenerGastosPorCategoria(String correoUsuario, Integer mes, Integer anio) {

		Usuario usuario = obtenerUsuario(correoUsuario);

		YearMonth periodo = resolverPeriodo(mes, anio);
		LocalDate desde = periodo.atDay(1);
		LocalDate hasta = periodo.atEndOfMonth();

		return movimientoRepository
				.sumarGastosPorCategoria(usuario.getId(), TipoMovimiento.GASTO, desde, hasta)
				.stream()
				.map(fila -> new GastoCategoriaDTO(
						(String) fila[0],
						(BigDecimal) fila[1]))
				.toList();
	}


	private YearMonth resolverPeriodo(Integer mes, Integer anio) {

		YearMonth actual = YearMonth.now();

		int mesFinal = (mes != null) ? mes : actual.getMonthValue();
		int anioFinal = (anio != null) ? anio : actual.getYear();

		if (mesFinal < 1 || mesFinal > 12) {
			throw new MontoInvalidoException("El mes debe estar entre 1 y 12");
		}

		return YearMonth.of(anioFinal, mesFinal);
	}
	
	private Usuario obtenerUsuario(String correoUsuario) {

		return usuarioRepository.findByCorreo(correoUsuario)
				.orElseThrow(() -> new RecursoNoEncontradoException(
						"Usuario no encontrado con correo: " + correoUsuario));
	}
}
