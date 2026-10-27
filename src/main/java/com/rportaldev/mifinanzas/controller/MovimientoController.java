package com.rportaldev.mifinanzas.controller;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rportaldev.mifinanzas.dto.movimiento.MovimientoRequestDTO;
import com.rportaldev.mifinanzas.dto.movimiento.MovimientoResponseDTO;
import com.rportaldev.mifinanzas.enums.TipoMovimiento;
import com.rportaldev.mifinanzas.service.MovimientoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/movimientos")
@RequiredArgsConstructor
public class MovimientoController {


	private final MovimientoService movimientoService;

	
	@PostMapping
	public ResponseEntity<MovimientoResponseDTO> crearMovimiento(
					@RequestBody @Valid MovimientoRequestDTO dto, Authentication authentication ){
		
		String correo = authentication.getName();
		MovimientoResponseDTO response = movimientoService.crearMovimiento(correo, dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<MovimientoResponseDTO> actualizarMovimiento(
			@PathVariable Long id, @RequestBody @Valid MovimientoRequestDTO dto, Authentication authentication){
		
		String correo = authentication.getName();
		MovimientoResponseDTO response = movimientoService.actualizarMovimiento(correo, id, dto);
		return ResponseEntity.ok(response);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarMovimiento(@PathVariable Long id, Authentication authentication){
		
		String correo = authentication.getName();
		movimientoService.eliminarMovimiento(correo, id);
		
		return ResponseEntity.noContent().build();	
	}
	
	
	
	@GetMapping
	public ResponseEntity<Page<MovimientoResponseDTO>> listarMovimientos(
			@RequestParam(required = false) TipoMovimiento tipo,
			@RequestParam(required = false) Long categoriaId,
			@RequestParam(required = false)
				@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
			@RequestParam(required = false)
				@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
			Pageable pageable,
			Authentication authentication) {

		String correo = authentication.getName();
		Page<MovimientoResponseDTO> movimientos = movimientoService
				.listarMovimientos(correo, tipo, categoriaId, desde, hasta, pageable);

		return ResponseEntity.ok(movimientos);
	}
}
