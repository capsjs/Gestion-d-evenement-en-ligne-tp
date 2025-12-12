package com.eventplatform.event_service.exception;

/**
 * Exception levée lorsqu'un événement n'est pas trouvé.
 */
public class EventNotFoundException extends RuntimeException {
    
    public EventNotFoundException(Long eventId) {
        super("Événement non trouvé avec l'ID: " + eventId);
    }
    
    public EventNotFoundException(String message) {
        super(message);
    }
}
