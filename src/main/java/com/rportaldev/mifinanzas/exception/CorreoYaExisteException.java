package com.rportaldev.mifinanzas.exception;

public class CorreoYaExisteException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public CorreoYaExisteException(String mensaje) {
		
		super(mensaje);
	}
}
