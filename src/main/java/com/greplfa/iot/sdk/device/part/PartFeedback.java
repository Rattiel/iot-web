package com.greplfa.iot.sdk.device.part;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartFeedback implements Serializable {
    @JsonProperty("ChannelId")
    private String channelId;

    @JsonProperty("ChannelName")
    private String channelName;

    @JsonProperty("ChannelOrder")
    private Long channelOrder;

    @JsonProperty("ChannelType")
    private String channelType;

    @JsonProperty("DevEui")
    private String devEui;

    @JsonProperty("DeviceId")
    private String deviceId;

    @JsonProperty("Username")
    private String username;

    @JsonProperty("UserId")
    private String userId;

    @JsonProperty("Time")
    private Date time;

    @JsonProperty("Data")
    private Float data;
}
