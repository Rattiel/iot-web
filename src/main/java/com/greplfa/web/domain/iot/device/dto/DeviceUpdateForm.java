package com.greplfa.web.domain.iot.device.dto;

import com.greplfa.web.domain.iot.part.dto.PartFormData;
import com.greplfa.web.domain.iot.part.dto.PartUpdateForm;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DeviceUpdateForm {
    private String name;

    @NotNull
    private List<@Valid PartUpdateForm> parts;

    public static DeviceUpdateForm form(DeviceUpdateFormData data) {
        List<PartUpdateForm> partUpdateForms = new ArrayList<>();

        for(PartFormData partData : data.getParts()) {
            PartUpdateForm from = PartUpdateForm.form(partData);
            partUpdateForms.add(from);
        }

        return DeviceUpdateForm.builder()
                .name(data.getName())
                .parts(partUpdateForms)
                .build();
    }
}
