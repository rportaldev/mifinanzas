package com.rportaldev.mifinanzas.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(RecursoNoEncontradoException.class)
	public ResponseEntity<Map<String, Object>> handlerRecursoNoEncontrado(
			RecursoNoEncontradoException ex){
		
		return construirRespuesta(HttpStatus.NOT_FOUND, ex.getMessage());
	}
	
	
	@ExceptionHandler(CorreoYaExisteException.class)
	public ResponseEntity<Map<String, Object>> handlerCorreoYaExiste(
			CorreoYaExisteException ex){
		
		return construirRespuesta(HttpStatus.CONFLICT, ex.getMessage());
	}
	
	@ExceptionHandler(CategoriaInvalidaException.class)
	public ResponseEntity<Map<String, Object>> handlerCategoriaInvalida(
			CategoriaInvalidaException ex){
		
		return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
	}
	
	
	@ExceptionHandler(MontoInvalidoException.class)
	public ResponseEntity<Map<String, Object>> handlerMontoInvalido(
			MontoInvalidoException ex){
		
		return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
	}
	
	
	@ExceptionHandler(MetaNoActivaException.class)
	public ResponseEntity<Map<String, Object>> handlerMetaNoActiva(
			MetaNoActivaException ex){
		
		return construirRespuesta(HttpStatus.BAD_REQUEST, ex.getMessage());
	}
	
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Map<String, Object>> handlerBadCredentials(
			BadCredentialsException ex){
		
		return construirRespuesta(HttpStatus.UNAUTHORIZED, 
				"Correo o contraseña incorrectos");
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handlerValidacion(
			MethodArgumentNotValidException ex) {

		Map<String, String> errores = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach(error ->
			errores.put(error.getField(), error.getDefaultMessage())
		);

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", HttpStatus.BAD_REQUEST.value());
		body.put("errores", errores);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}

	private ResponseEntity<Map<String, Object>> construirRespuesta(HttpStatus status, String mensaje) {
		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("status", status.value());
		body.put("mensaje", mensaje);
		return ResponseEntity.status(status).body(body);
	}

}
