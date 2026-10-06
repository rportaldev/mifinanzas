package com.rportaldev.mifinanzas.dto.meta;

import java.math.BigDecimal;
import java.time.LocalDate;

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

public class MetaAhorroRequestDTO {

	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;
	
	@DecimalMin(value = "0.01", 
			message = "El monto del objetivo debe ser un número mayor a cero")
	private BigDecimal montoObjetivo;
	
	@NotNull(message = "La fecha del objetivo es obligatoria")
	private LocalDate fechaObjetivo;
	
}
