package com.greplfa.web.domain.common.exception;

public abstract class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException(String msg, String reason) {
        super(msg, reason);
    }
}
