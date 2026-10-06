package com.rportaldev.mifinanzas.exception;

public class MetaNoActivaException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public MetaNoActivaException(String mensaje) {
		
		super(mensaje);
	}
}
