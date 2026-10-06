package com.rportaldev.mifinanzas.dto.meta;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AporteAhorroResponseDTO {

	private Long id;
	private BigDecimal monto;
	private LocalDate fecha;
}
