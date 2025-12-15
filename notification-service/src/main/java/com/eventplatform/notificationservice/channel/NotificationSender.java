package com.eventplatform.notificationservice.channel;

import org.springframework.stereotype.Component;

@Component
public class NotificationSender {

    public void send(String targetId, String message) {
        System.out.println("[NOTIFICATION] " + targetId + " → " + message);
    }
}
