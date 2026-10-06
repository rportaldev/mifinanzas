package com.rportaldev.mifinanzas.exception;

public class CategoriaInvalidaException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public CategoriaInvalidaException(String mensaje) {
		
		super(mensaje);
	}
}
