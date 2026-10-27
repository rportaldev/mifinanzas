package com.rportaldev.mifinanzas.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rportaldev.mifinanzas.dto.dashboard.GastoCategoriaDTO;
import com.rportaldev.mifinanzas.dto.dashboard.ResumenFinancieroDTO;
import com.rportaldev.mifinanzas.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

	private final DashboardService dashboardService;

	@GetMapping("/resumen")
	public ResponseEntity<ResumenFinancieroDTO> obtenerResumen(
			@RequestParam(required = false) Integer mes,
			@RequestParam(required = false) Integer anio,
			Authentication authentication) {

		String correo = authentication.getName();
		ResumenFinancieroDTO response = dashboardService.obtenerResumen(correo, mes, anio);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/gastos-por-categoria")
	public ResponseEntity<List<GastoCategoriaDTO>> gastosPorCategoria(
			@RequestParam(required = false) Integer mes,
			@RequestParam(required = false) Integer anio,
			Authentication authentication) {

		String correo = authentication.getName();
		List<GastoCategoriaDTO> response =
				dashboardService.obtenerGastosPorCategoria(correo, mes, anio);
		return ResponseEntity.ok(response);
	}
}
