package com.rportaldev.mifinanzas.dto.dashboard;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GastoCategoriaDTO {

	private String categoria;
	private BigDecimal total;
}
