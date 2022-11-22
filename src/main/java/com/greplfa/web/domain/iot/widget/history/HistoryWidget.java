package com.greplfa.web.domain.iot.widget.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.greplfa.web.domain.iot.part.Part;
import com.greplfa.web.domain.iot.widget.Widget;
import com.greplfa.web.domain.iot.widget.WidgetType;
import com.greplfa.web.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Getter
@SuperBuilder
@NoArgsConstructor
@Entity
public class HistoryWidget extends Widget {
    @JsonProperty("device")
    @Enumerated
    @Column(nullable = false, updatable = false)
    private Device historyDeviceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, updatable = false)
    private Part part;

    public static HistoryWidget create(Long deviceId, Part part, Member owner) {
        return HistoryWidget.builder()
                .historyDeviceId(new Device(deviceId))
                .type(WidgetType.HISTORY)
                .part(part)
                .owner(owner)
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Embeddable
    static class Device {
        @JsonProperty("id")
        private Long historyDeviceId;
    }
}
