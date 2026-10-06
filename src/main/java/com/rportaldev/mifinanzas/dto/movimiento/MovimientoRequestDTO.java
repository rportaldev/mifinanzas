package com.rportaldev.mifinanzas.dto.movimiento;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.rportaldev.mifinanzas.enums.TipoMovimiento;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovimientoRequestDTO {

	@NotNull(message = "El Tipo de movimiento es obligatorio")
	private TipoMovimiento tipo;
	
	@DecimalMin(value = "0.01", 
				message = "El precio debe ser un número mayor a cero")
	private BigDecimal monto;
	
	@NotNull(message = "La fecha del movimiento es obligatoria")
	private LocalDate fecha;
	
	@NotBlank(message = "La descripcion es obligatoria")
	private String descripcion;
	
	@NotNull(message = "La categoria es obligatoria")
	private Long categoriaId;
}
