package com.greplfa.web.domain.member.dto;

import com.greplfa.web.domain.member.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Builder(access = AccessLevel.PRIVATE)
public class MemberCreateParameter {
    private String id;

    private String nickname;

    private String username;

    private String password;

    private String passwordConfirm;

    private String email;

    private Role role;

    public static MemberCreateParameter from(MemberRegisterForm form) {
        return MemberCreateParameter.builder()
                    .id(UUID.randomUUID().toString())
                    .nickname(form.getNickname())
                    .username(form.getUsername())
                    .password(form.getPassword())
                    .passwordConfirm(form.getPasswordConfirm())
                    .email(form.getEmail())
                    .role(Role.USER)
                .build();
    }

    public static MemberCreateParameter from(MemberRegisterForm form, Role role) {
        return MemberCreateParameter.builder()
                    .id(UUID.randomUUID().toString())
                    .nickname(form.getNickname())
                    .username(form.getUsername())
                    .password(form.getPassword())
                    .passwordConfirm(form.getPasswordConfirm())
                    .email(form.getEmail())
                    .role(role)
                .build();
    }

    public static MemberCreateParameter from(MemberRegisterForm form, String id, Role role) {
        return MemberCreateParameter.builder()
                .id(id)
                .nickname(form.getNickname())
                .username(form.getUsername())
                .password(form.getPassword())
                .passwordConfirm(form.getPasswordConfirm())
                .email(form.getEmail())
                .role(role)
                .build();
    }
}
