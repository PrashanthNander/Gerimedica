package com.gerimedica.assignment.exception;

/**
 * @author Prashanth Nander
 */
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
