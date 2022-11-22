package com.greplfa.web.domain.iot.device.helper;

import com.greplfa.web.domain.iot.device.Device;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DefaultDeviceMessageSender implements DeviceMessageSender {
    private final static String MESSAGE_PREFIX = "/iot/device/";
    private final SimpMessagingTemplate websocket;

    @Override
    public void create(Device device, MemberDetails memberDetails) {
        this.websocket.convertAndSendToUser(memberDetails.getUsername(), MESSAGE_PREFIX + "create", device);
    }

    @Override
    public void update(Device device, MemberDetails memberDetails) {
        this.websocket.convertAndSendToUser(memberDetails.getUsername(), MESSAGE_PREFIX + "update", device);
    }

    @Override
    public void delete(long deviceId, MemberDetails memberDetails) {
        this.websocket.convertAndSendToUser(
                memberDetails.getUsername(),
                MESSAGE_PREFIX + "delete", "{\"id\" : " + deviceId + "}"
        );
    }
}
