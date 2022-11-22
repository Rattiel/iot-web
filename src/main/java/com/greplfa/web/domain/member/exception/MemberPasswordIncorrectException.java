package com.greplfa.web.domain.member.exception;

import com.greplfa.web.domain.common.exception.FieldException;

public class MemberPasswordIncorrectException extends FieldException {
    private static final String REASON = "비밀번호가 일치 하지 않습니다.";

    public MemberPasswordIncorrectException() {
        super(REASON, "passwordConfirm", REASON);
    }
}
