package com.greplfa.web.domain.member;

import lombok.Getter;

@Getter
public enum Role {
    DEVELOPER("ROLE_DEVELOPER", "개발자"),
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "사용자");

    Role(String id, String name) {
        this.id = id;
        this.name = name;
    }

    private final String id;
    private final String name;
}
