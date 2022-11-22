package com.greplfa.iot.sdk.device;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeviceContent {
    @JsonProperty("Owner")
    private String owner;

    @JsonProperty("DeviceId")
    private String deviceId;

    @JsonProperty("DeviceEUI")
    private String deviceEUI;

    @JsonProperty("DeviceDescription")
    private String deviceDescription;

    @JsonProperty("DeviceName")
    private String deviceName;

    @JsonProperty("DeviceManId")
    private String deviceManId;
}