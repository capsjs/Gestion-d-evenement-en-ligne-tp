package com.eventplatform.event_service.exception;

/**
 * Exception levée lorsque les données d'un événement sont invalides.
 */
public class InvalidEventDataException extends RuntimeException {
    
    public InvalidEventDataException(String message) {
        super(message);
    }
}
