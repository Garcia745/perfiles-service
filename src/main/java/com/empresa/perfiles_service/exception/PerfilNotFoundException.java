package com.empresa.perfiles_service.exception;

public class PerfilNotFoundException extends RuntimeException {
    public PerfilNotFoundException(String message) {
        super(message);
    }
}
