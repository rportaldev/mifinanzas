package com.rportaldev.mifinanzas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rportaldev.mifinanzas.dto.meta.AporteAhorroRequestDTO;
import com.rportaldev.mifinanzas.dto.meta.AporteAhorroResponseDTO;
import com.rportaldev.mifinanzas.service.AporteAhorroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/metas/{metaId}/aportes")
@RequiredArgsConstructor
public class AporteAhorroController {

	private final AporteAhorroService aporteAhorroService;
	
	@PostMapping
	public ResponseEntity<AporteAhorroResponseDTO> registrarAporte(
			@PathVariable Long metaId, 
			@RequestBody @Valid AporteAhorroRequestDTO dto, 
			Authentication authentication){
		
		String correo = authentication.getName();
		AporteAhorroResponseDTO response = aporteAhorroService.registrarAporte(correo, metaId, dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	
	@DeleteMapping("/{aporteId}")
	public ResponseEntity<Void> eliminarAporte(
			@PathVariable Long metaId,
			@PathVariable Long aporteId,
			Authentication authentication) {
		
		String correo = authentication.getName();
		aporteAhorroService.eliminarAporte(correo, metaId, aporteId);
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping
	public ResponseEntity<List<AporteAhorroResponseDTO>> listarAportes(
			@PathVariable Long metaId,
			Authentication authentication){
		
		String correo = authentication.getName();
		List<AporteAhorroResponseDTO> aportes = aporteAhorroService.listarAportes(correo, metaId);
		return ResponseEntity.ok(aportes);
	}
	
	
	
}
