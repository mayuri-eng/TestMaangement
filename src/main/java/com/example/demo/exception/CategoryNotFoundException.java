package com.example.demo.exception;

public class CategoryNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoryNotFoundException(String message) {
        super(message);
    }
}
