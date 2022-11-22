package com.greplfa.web.domain.iot.part.helper;

import com.greplfa.web.domain.iot.device.dto.DeviceId;
import com.greplfa.web.domain.iot.part.Part;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class DefaultPartMessageSender implements PartMessageSender {
    private final static String MESSAGE_PREFIX = "/iot/part/";
    private final SimpMessagingTemplate websocket;


    @Override
    public void create(long deviceId, Part part, MemberDetails memberDetails) {
        Map<String, Object> data = new HashMap<>();
        data.put("device", new DeviceId(deviceId));
        data.put("part", part);

        this.websocket.convertAndSendToUser(memberDetails.getUsername(), MESSAGE_PREFIX + "create", data);
    }

    @Override
    public void update(long deviceId, Part part, MemberDetails memberDetails) {
        Map<String, Object> data = new HashMap<>();
        data.put("device", new DeviceId(deviceId));
        data.put("part", part);

        this.websocket.convertAndSendToUser(memberDetails.getUsername(), MESSAGE_PREFIX + "update", data);
    }

    @Override
    public void delete(long deviceId, long partId, MemberDetails memberDetails) {
        Map<String, Object> data = new HashMap<>();
        data.put("device", new DeviceId(deviceId));
        data.put("part", "{\"id\" : " + partId + "}");

        this.websocket.convertAndSendToUser(memberDetails.getUsername(), MESSAGE_PREFIX + "delete", data);
    }
}
