package com.greplfa.web.domain.iot.widget.device.dto;

import com.greplfa.web.domain.iot.device.dto.DeviceOption;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
@Setter
@Getter
public class DeviceWidgetCreateForm {
    @NotNull(message = "장치를 선택해주세요.")
    private Long deviceId;

    private List<DeviceOption> deviceOptions;

    public static DeviceWidgetCreateForm from(List<DeviceOption> deviceOptions) {
        return DeviceWidgetCreateForm.builder()
                .deviceOptions(deviceOptions)
                .build();
    }

    public DeviceWidgetCreateForm update(List<DeviceOption> deviceOptions) {
        this.deviceOptions = deviceOptions;

        return this;
    }
}
