package com.greplfa.web.domain.iot.device.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class DeviceCreateForm {
    @NotEmpty(message = "장치 고유 아이디를 입력해주세요.")
    private String uuid;

    private static final DeviceCreateForm _instance = new DeviceCreateForm();

    public static DeviceCreateForm of() {
        return _instance;
    }
}
