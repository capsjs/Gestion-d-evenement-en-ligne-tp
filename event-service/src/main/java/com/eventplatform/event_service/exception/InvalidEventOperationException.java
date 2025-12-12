package com.eventplatform.event_service.exception;

/**
 * Exception levée lors d'une opération invalide sur un événement.
 */
public class InvalidEventOperationException extends RuntimeException {
    
    public InvalidEventOperationException(String message) {
        super(message);
    }
}
