package com.greplfa.web.domain.member.exception;

import com.greplfa.web.domain.common.exception.FieldException;

/**
 * 회원 가입시, 사용자 아이디가 이미 존재할시 발생하는 예외 
 */
public class MemberUsernameExistsException extends FieldException {
    private static final String REASON = "이미 존재하는 아이디 입니다.";

    public MemberUsernameExistsException() {
        super(REASON, "username", REASON);
    }
}
