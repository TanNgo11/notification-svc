package com.shadcn.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationEvent implements Serializable {
    String channel;
    String recipient;
    String templateCode;
    Map<String, Object> param;
    String subject;
    String body;
}
