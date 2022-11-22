package com.greplfa.iot.sdk.device.part;

import lombok.Getter;

@Getter
public enum PartType {
    SLIDER, // -1, 0, 1
    BUTTON, // 0 or 1
    CO2_SENSOR, // float
    TEMPERATURE_SENSOR,// float
    HUMIDITY_SENSOR // float
}
