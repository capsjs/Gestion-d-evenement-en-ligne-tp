package com.eventplatform.notificationservice.domain;

public record EventNotification(
        Long targetUserId,
        String message
) {}
