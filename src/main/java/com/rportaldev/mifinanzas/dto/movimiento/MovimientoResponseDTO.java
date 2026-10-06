package com.rportaldev.mifinanzas.dto.movimiento;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.rportaldev.mifinanzas.enums.TipoMovimiento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class MovimientoResponseDTO {

	private Long id;
	private TipoMovimiento tipo;
	private BigDecimal monto;
	private LocalDate fecha;
	private String descripcion;
	private String categoriaNombre;
}
