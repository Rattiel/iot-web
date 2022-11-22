package com.greplfa.web.domain.iot.part.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;

@Builder(access = AccessLevel.PRIVATE)
@Data
public class PartUpdateParameter {
    private Long id;

    private String label;

    public static PartUpdateParameter from(PartUpdateForm form) {
        return PartUpdateParameter.builder()
                .id(form.getId())
                .label(form.getLabel())
                .build();
    }
}
