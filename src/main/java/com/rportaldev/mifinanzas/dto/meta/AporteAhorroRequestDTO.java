package com.rportaldev.mifinanzas.dto.meta;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class AporteAhorroRequestDTO {

	@DecimalMin(value = "0.01", 
			message = "El monto del aporte debe ser un número mayor a cero")
	private BigDecimal monto;
	
	@NotNull(message = "La fecha del aporte es obligatoria")
	private LocalDate fecha;
}
