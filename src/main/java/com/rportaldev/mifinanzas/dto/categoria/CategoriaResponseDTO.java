package com.rportaldev.mifinanzas.dto.categoria;

import com.rportaldev.mifinanzas.enums.TipoMovimiento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaResponseDTO {

	private Long id;
	private String nombre;
	private TipoMovimiento tipo;
}
