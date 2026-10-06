package com.rportaldev.mifinanzas.exception;

public class MontoInvalidoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public MontoInvalidoException(String mensaje) {
		
		super(mensaje);
	}
}
