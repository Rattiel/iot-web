package com.greplfa.web.domain.iot.device.exception;

import com.greplfa.web.domain.common.exception.EntityNotFoundException;
import com.greplfa.web.domain.common.exception.Fieldable;
import lombok.Getter;

@Getter
public class DeviceNotFoundException extends EntityNotFoundException implements Fieldable {
    private static final String REASON = "장치가 존재하지 않습니다.";

    private final String field;

    public DeviceNotFoundException() {
        super(REASON, REASON);
        this.field = "uuid";
    }

    public DeviceNotFoundException(String field) {
        super(REASON, REASON);
        this.field = field;
    }
}
