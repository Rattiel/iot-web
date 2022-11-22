package com.greplfa.web.domain.iot.widget.common.exception;

import com.greplfa.web.domain.common.exception.EntityNotFoundException;
import com.greplfa.web.domain.common.exception.Fieldable;
import lombok.Getter;

@Getter
public class WidgetNotFoundException extends EntityNotFoundException implements Fieldable {
    private static final String REASON = "위젯이 존재하지 않습니다.";

    private final String field;

    public WidgetNotFoundException() {
        super(REASON, REASON);
        this.field = "uuid";
    }
}
