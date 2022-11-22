package com.greplfa.web.domain.iot.device.exception;

import com.greplfa.web.domain.common.exception.FieldException;
import lombok.Getter;

@Getter
public class DeviceUnavailableException extends FieldException {
    public DeviceUnavailableException(String msg, String reason) {
        super(msg, "uuid", reason);
    }
}
