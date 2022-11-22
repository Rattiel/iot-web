package com.greplfa.iot.sdk.device;

import com.greplfa.iot.sdk.device.part.PartInfo;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class DeviceInfo {

    private DeviceContent content;

    private List<PartInfo> channels;
}
