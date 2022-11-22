package com.greplfa.web.domain.iot.part;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greplfa.iot.sdk.device.part.PartInfo;
import com.greplfa.iot.sdk.device.part.PartType;
import com.greplfa.web.domain.iot.device.Device;
import com.greplfa.web.domain.iot.widget.part.PartWidget;
import com.greplfa.web.domain.member.Member;
import lombok.*;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Part implements Comparable<Part> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, unique = true, nullable = false)
    private Long id;

    @JsonIgnore
    @Column(updatable = false, unique = true, nullable = false)
    private String uuid;
    
    @Enumerated(EnumType.STRING)
    @Column(updatable = false, nullable = false)
    private PartType type;

    @Setter
    @Column
    private String label;

    @Column
    private String status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false)
    private Device device;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(updatable = false, nullable = false)
    private Member owner;

    @JsonIgnore
    @OneToMany(mappedBy = "part", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<PartWidget> widgetList;

    @LastModifiedBy
    private LocalDateTime lastModified;

    public Object getStatus() {
        return status;
    }

    public static Part create(PartInfo partInfo, Device device, Member owner) {
        Part part = Part.builder()
                .device(device)
                .owner(owner)
                .uuid(partInfo.getChannelId())
                .label(partInfo.getChannelName())
                .status(String.valueOf(0))
                .build();

        if (partInfo.isChannelIsControl()) {
            if (partInfo.getChannelType().equals("boolean")) {
               part.type = PartType.BUTTON;
            } else if (partInfo.getChannelType().equals("int")){
                part.type = PartType.SLIDER;
            }
        } else {
            switch (partInfo.getChannelName()) {
                case "temp" -> part.type = PartType.TEMPERATURE_SENSOR;
                case "hum" -> part.type = PartType.HUMIDITY_SENSOR;
                case "co2" -> part.type = PartType.CO2_SENSOR;
            }
        }

        return part;
    }

    public Part feedback(Object status) {
        this.status = status.toString();
        return this;
    }

    public Part update(String label) {
        if (label == null || label.isBlank()) {
            this.label = "파츠";
        } else {
            this.label = label;
        }

        return this;
    }

    @Override
    public int compareTo(Part o) {
        return this.id.compareTo(o.id);
    }
}
