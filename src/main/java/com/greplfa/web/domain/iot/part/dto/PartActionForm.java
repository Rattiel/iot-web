package com.greplfa.web.domain.iot.part.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class PartActionForm {
    @NotNull
    private Object option;
}
