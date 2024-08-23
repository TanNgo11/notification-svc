package com.shadcn.notificationservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shadcn.notificationservice.dto.request.EmailRequest;
import com.shadcn.notificationservice.dto.request.SendEmailRequest;
import com.shadcn.notificationservice.dto.request.Sender;
import com.shadcn.notificationservice.dto.response.EmailResponse;
import com.shadcn.notificationservice.exception.AppException;
import com.shadcn.notificationservice.exception.ErrorCode;
import com.shadcn.notificationservice.repository.httpclient.EmailClient;

import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    EmailClient emailClient;

    @Value("${notification.email.brevo-apikey}")
    @NonFinal
    String apiKey;

    public EmailResponse sendEmail(SendEmailRequest request) {
        EmailRequest emailRequest = EmailRequest.builder()
                .sender(Sender.builder()
                        .name("Devteria DotCom")
                        .email("tan.ngo.cit20@eiu.edu.vn")
                        .build())
                .to(List.of(request.getTo()))
                .subject(request.getSubject())
                .htmlContent(request.getHtmlContent())
                .build();
        try {
            return emailClient.sendEmail(apiKey, emailRequest);
        } catch (FeignException e) {
            throw new AppException(ErrorCode.CANNOT_SEND_EMAIL);
        }
    }
}
