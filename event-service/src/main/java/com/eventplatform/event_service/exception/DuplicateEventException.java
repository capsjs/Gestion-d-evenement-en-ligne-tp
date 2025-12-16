package com.eventplatform.event_service.exception;

/**
 * Exception levée lorsqu'un événement existe déjà avec les mêmes
 * caractéristiques.
 */
public class DuplicateEventException extends RuntimeException {

    public DuplicateEventException(String titre, String lieu, String dateDebut) {
        super(String.format("Un événement avec le titre '%s' existe déjà au lieu '%s' à la date '%s'",
                titre, lieu, dateDebut));
    }

    public DuplicateEventException(String message) {
        super(message);
    }
}
