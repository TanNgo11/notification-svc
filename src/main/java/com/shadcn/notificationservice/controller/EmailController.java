package com.shadcn.notificationservice.controller;

import com.shadcn.notificationservice.dto.ApiResponse;
import com.shadcn.notificationservice.dto.request.SendEmailRequest;
import com.shadcn.notificationservice.dto.response.EmailResponse;
import com.shadcn.notificationservice.service.EmailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailController {
    EmailService emailService;

    @PostMapping("/email/send")
    ApiResponse<EmailResponse> sendEmail(@RequestBody SendEmailRequest request) {
        return ApiResponse.<EmailResponse>builder()
                .result(emailService.sendEmail(request))
                .build();
    }

    @KafkaListener(topics = "onboard-successful")
    public void listenNotificationDelivery(String message) {
        log.info("Message received: {}", message);

    }
}