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

import com.rportaldev.mifinanzas.dto.categoria.CategoriaRequestDTO;
import com.rportaldev.mifinanzas.dto.categoria.CategoriaResponseDTO;
import com.rportaldev.mifinanzas.service.CategoriaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor

public class CategoriaController {

private final CategoriaService categoriaService;
	
	@PostMapping
	public ResponseEntity<CategoriaResponseDTO> crearCategoria(
			@RequestBody @Valid CategoriaRequestDTO dto, Authentication authentication){
		
		String correo = authentication.getName();
		CategoriaResponseDTO response = categoriaService.crearCategoria(correo, dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoriaResponseDTO> actualizarCategoria(
			@PathVariable Long id, @RequestBody @Valid CategoriaRequestDTO dto, Authentication authentication){
		
		String correo = authentication.getName();
		CategoriaResponseDTO response = categoriaService.actualizarCategoria(correo, id, dto);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarCategoria(@PathVariable Long id, Authentication authentication){
	
		String correo = authentication.getName();
		categoriaService.eliminarCategoria(correo, id);
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<CategoriaResponseDTO>> listarCategorias(Authentication authentication){
	
		String correo = authentication.getName();
		List<CategoriaResponseDTO> categorias = categoriaService.listarCategorias(correo);
		
		return ResponseEntity.ok(categorias);
	}

	
}
