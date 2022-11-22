package com.greplfa.web.domain.member.exception;

import com.greplfa.web.domain.common.exception.FieldException;

public class MemberEmailExistsException extends FieldException {
    private static final String REASON = "이미 존재하는 이메일 입니다.";

    public MemberEmailExistsException() {
        super(REASON, "email", REASON);
    }
}
