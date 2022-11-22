package com.greplfa.web.domain.iot.device.helper;

import com.greplfa.web.domain.iot.device.Device;
import com.greplfa.web.domain.member.dto.MemberDetails;

public interface DeviceMessageSender {
    void create(Device device, MemberDetails memberDetails);

    void update(Device device, MemberDetails memberDetails);

    void delete(long deviceId, MemberDetails memberDetails);
}
