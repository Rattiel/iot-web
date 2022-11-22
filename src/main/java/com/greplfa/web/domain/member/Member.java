package com.greplfa.web.domain.member;

import com.greplfa.web.domain.member.dto.MemberCreateParameter;
import com.greplfa.web.domain.member.dto.MemberDetails;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 사용자 {@link Entity}
 */
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Member {
    @Id
    @Column(nullable = false, unique = true, updatable = false)
    private String id;

    @Column(nullable = false, unique = true, updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    public static Member of(MemberCreateParameter parameter, PasswordEncoder passwordEncoder) {
        return Member.builder()
                    .id(parameter.getId())
                    .username(parameter.getUsername())
                    .password(passwordEncoder.encode(parameter.getPassword()))
                    .nickname(parameter.getNickname())
                    .email(parameter.getEmail())
                    .role(parameter.getRole())
                .build();
    }

    public static Member from(MemberDetails member) {
        return Member.builder()
                    .id(member.getId())
                    .username(member.getUsername())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
