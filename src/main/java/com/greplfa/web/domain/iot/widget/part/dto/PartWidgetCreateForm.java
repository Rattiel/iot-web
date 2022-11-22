package com.greplfa.web.domain.iot.widget.part.dto;

import com.greplfa.web.domain.iot.part.dto.PartOption;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
@Setter
@Getter
public class PartWidgetCreateForm {
    @NotNull(message = "파츠를 선택해주세요.")
    private Long partId;

    private List<PartOption> partOptions;

    public static PartWidgetCreateForm from(List<PartOption> partOptions) {
        return PartWidgetCreateForm.builder()
                .partOptions(partOptions)
                .build();
    }

    public PartWidgetCreateForm update(List<PartOption> partOptions) {
        this.partOptions = partOptions;

        return this;
    }
}
