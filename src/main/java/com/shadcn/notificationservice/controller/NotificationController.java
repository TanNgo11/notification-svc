package com.shadcn.notificationservice.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.shadcn.event.dto.NotificationEvent;
import com.shadcn.notificationservice.dto.request.Recipient;
import com.shadcn.notificationservice.dto.request.SendEmailRequest;
import com.shadcn.notificationservice.service.EmailService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationController {

    EmailService emailService;

    @KafkaListener(topics = "notification-delivery")
    public void listenNotificationDelivery(NotificationEvent message) {
        log.info("Message received: {}", message);
        emailService.sendEmail(SendEmailRequest.builder()
                .to(Recipient.builder().email(message.getRecipient()).build())
                .subject(message.getSubject())
                .htmlContent(message.getBody())
                .build());
    }
}
