package com.greplfa.web.domain.iot.device.dto;

import com.greplfa.web.domain.iot.part.dto.PartFormData;

import java.util.List;

public interface DeviceUpdateFormData {
    String getName();

    List<PartFormData> getParts();
}
