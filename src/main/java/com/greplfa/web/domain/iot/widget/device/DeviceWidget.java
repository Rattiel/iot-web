package com.greplfa.web.domain.iot.widget.device;

import com.greplfa.web.domain.iot.device.Device;
import com.greplfa.web.domain.iot.widget.Widget;
import com.greplfa.web.domain.iot.widget.WidgetType;
import com.greplfa.web.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class DeviceWidget extends Widget {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, updatable = false)
    private Device device;

    public static DeviceWidget create(Device device, Member owner) {
        return DeviceWidget.builder()
                .device(device)
                .type(WidgetType.DEVICE)
                .owner(owner)
                .build();
    }
}
