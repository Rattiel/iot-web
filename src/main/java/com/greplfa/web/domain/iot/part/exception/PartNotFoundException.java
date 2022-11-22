package com.greplfa.web.domain.iot.part.exception;

import com.greplfa.web.domain.common.exception.EntityNotFoundException;
import com.greplfa.web.domain.common.exception.Fieldable;
import lombok.Getter;

@Getter
public class PartNotFoundException extends EntityNotFoundException implements Fieldable {
    private static final String REASON = "파츠가 존재하지 않습니다.";

    private final String field;

    public PartNotFoundException() {
        super(REASON, REASON);
        this.field = "uuid";
    }

    public PartNotFoundException(String field) {
        super(REASON, REASON);
        this.field = field;
    }
}
