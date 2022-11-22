package com.greplfa.iot.sdk.device.part;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PartInfo implements Serializable {
    @JsonProperty("Device")
    private String device;

    @JsonProperty("ChannelId")
    private String channelId;

    @JsonProperty("ChannelType")
    private String channelType;

    @JsonProperty("ChannelName")
    private String channelName;

    @JsonProperty("ChannelDescription")
    private String channelDescription;

    @JsonProperty("ChannelOrder")
    private Long channelOrder;

    @JsonProperty("ChannelIsControl")
    private boolean channelIsControl;
}
