package com.greplfa.web.domain.iot.device;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greplfa.iot.sdk.device.DeviceInfo;
import com.greplfa.iot.sdk.device.part.PartInfo;
import com.greplfa.web.domain.iot.part.Part;
import com.greplfa.web.domain.iot.part.dto.PartUpdateParameter;
import com.greplfa.web.domain.iot.widget.device.DeviceWidget;
import com.greplfa.web.domain.member.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @JsonIgnore
    @Column(nullable = false, updatable = false, unique = true)
    private String uuid;

    @JsonIgnore
    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<DeviceWidget> widgetList;

    @Column
    private String name;

    @OneToMany(mappedBy = "device", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id")
    @Builder.Default
    private List<Part> parts = new ArrayList<>();

    @JsonIgnore
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Member owner;

    private static int count = 0;

    public static Device create(DeviceInfo deviceInfo, Member owner) {
        Device device = Device.builder()
                .uuid(deviceInfo.getContent().getDeviceId())
                .name(deviceInfo.getContent().getDeviceName())
                .owner(owner)
                .build();

        for (PartInfo partInfo : deviceInfo.getChannels()) {
            Part part = Part.create(partInfo, device, owner);
            device.addPart(part);
        }

        return device;
    }

    public void addPart(Part part) {
        this.parts.add(part);
    }

    public Device update(String name, List<PartUpdateParameter> partUpdateParameters) {
        if (name == null || name.isBlank()) {
            this.name = "장치";
        } else {
            this.name = name;
        }

        for (Part part : this.parts) {
            Optional<PartUpdateParameter> optionalParameter = partUpdateParameters.stream()
                    .filter(p -> p.getId().equals(part.getId())).findFirst();

            if (optionalParameter.isPresent()) {
                PartUpdateParameter parameter = optionalParameter.get();
                part.update(parameter.getLabel());
            }
        }

        return this;
    }
}