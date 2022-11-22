package com.greplfa.web.domain.common.exception;

import lombok.Getter;

@Getter
public abstract class FieldException extends BusinessException implements Fieldable {
    private final String field;

    public FieldException(String msg, String field, String reason) {
        super(msg, reason);
        this.field = field;
    }
}
