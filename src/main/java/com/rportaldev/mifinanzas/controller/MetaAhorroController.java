package com.rportaldev.mifinanzas.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.rportaldev.mifinanzas.dto.meta.MetaAhorroRequestDTO;
import com.rportaldev.mifinanzas.dto.meta.MetaAhorroResponseDTO;
import com.rportaldev.mifinanzas.service.MetaAhorroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/metas")
@RequiredArgsConstructor
public class MetaAhorroController {

	private final MetaAhorroService metaAhorroService;
	
	@PostMapping
	public ResponseEntity<MetaAhorroResponseDTO> crearMeta(
			@RequestBody @Valid MetaAhorroRequestDTO dto, Authentication authentication){
		
		String correo = authentication.getName();
		MetaAhorroResponseDTO response = metaAhorroService.crearMeta(correo, dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<MetaAhorroResponseDTO> actualizarMeta(
			@PathVariable Long id,
			@RequestBody @Valid MetaAhorroRequestDTO dto,
			Authentication authentication){
		
		String correo = authentication.getName();
		MetaAhorroResponseDTO response = metaAhorroService.actualizarMeta(correo, id, dto);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarMeta(
			@PathVariable Long id, Authentication authentication){
		
		String correo = authentication.getName();
		metaAhorroService.eliminarMeta(correo, id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<MetaAhorroResponseDTO>> listarMetas(
			Authentication authentication){
		
		String correo = authentication.getName();
		List<MetaAhorroResponseDTO> metas = metaAhorroService.listarMetas(correo);
		return ResponseEntity.ok(metas);
		
	}
}
