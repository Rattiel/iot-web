package com.greplfa.web.domain.iot.part.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PartUpdateForm {
    @NotNull
    private Long id;

    private String label;

    public static PartUpdateForm form(PartFormData data) {
        return PartUpdateForm.builder()
                .id(data.getId())
                .label(data.getLabel())
                .build();
    }
}
