package com.rportaldev.mifinanzas.dto.meta;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.rportaldev.mifinanzas.enums.EstadoMeta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MetaAhorroResponseDTO {

	private Long id;
	private String nombre;
	private BigDecimal montoObjetivo;
	private LocalDate fechaObjetivo;
	private EstadoMeta estado;
	private BigDecimal montoAhorrado;
	private BigDecimal montoFaltante;
}
