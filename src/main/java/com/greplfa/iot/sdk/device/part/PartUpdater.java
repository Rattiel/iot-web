package com.greplfa.iot.sdk.device.part;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class PartUpdater {
    private final PartUpdatedCallBack partUpdatedCallBack;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel", requiresReply = "mqttReplyChannel")
    public MessageHandler callback() {
        return message -> {
            String data = (String) message.getPayload();
            try {
                PartFeedback feedback = objectMapper.readValue(data, PartFeedback.class);
                partUpdatedCallBack.execute(feedback);
            } catch (JsonProcessingException e) {
                log.error("파츠 업데이트 이벤트 처리 에러(이유 : 파츠 데이터 형식 틀림)");
            }
        };
    }
}
