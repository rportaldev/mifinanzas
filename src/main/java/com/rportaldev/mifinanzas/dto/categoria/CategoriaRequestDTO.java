package com.rportaldev.mifinanzas.dto.categoria;

import com.rportaldev.mifinanzas.enums.TipoMovimiento;

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

public class CategoriaRequestDTO {

	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;
	
	@NotNull(message = "El Tipo de movimiento es obligatorio")
	private TipoMovimiento tipo;
}
