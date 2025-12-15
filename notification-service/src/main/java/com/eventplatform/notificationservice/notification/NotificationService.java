package com.eventplatform.notificationservice.notification;

import com.eventplatform.notificationservice.channel.NotificationSender;
import com.eventplatform.notificationservice.domain.EventNotification;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationSender sender;

    public NotificationService(NotificationSender sender) {
        this.sender = sender;
    }

    public void send(EventNotification notification) {
        sender.send(
                notification.targetUserId().toString(),
                notification.message()
        );
    }
}
