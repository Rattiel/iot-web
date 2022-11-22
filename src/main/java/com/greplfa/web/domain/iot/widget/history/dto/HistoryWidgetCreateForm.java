package com.greplfa.web.domain.iot.widget.history.dto;

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
public class HistoryWidgetCreateForm {
    @NotNull(message = "파츠를 선택해주세요.")
    private Long partId;

    private List<PartOption> partOptions;

    public static HistoryWidgetCreateForm from(List<PartOption> partOptions) {
        return HistoryWidgetCreateForm.builder()
                .partOptions(partOptions)
                .build();
    }

    public HistoryWidgetCreateForm update(List<PartOption> partOptions) {
        this.partOptions = partOptions;

        return this;
    }
}
