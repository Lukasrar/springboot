package com.example.curso.exceptions;

public class ResourceNotFoundException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ResourceNotFoundException(Object id) {
		super("Resource not found. id" + id);
	}

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
