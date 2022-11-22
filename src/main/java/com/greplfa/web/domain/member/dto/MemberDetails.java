package com.greplfa.web.domain.member.dto;

import com.greplfa.web.domain.member.Member;
import com.greplfa.web.domain.member.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

/**
 * {@link UserDetails}를 상속 받은 사용자 인증때 사용되는 Dto 클래스
 * @see UserDetails
 */
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class MemberDetails implements UserDetails {
    private String id;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Role role;
    private Set<SimpleGrantedAuthority> authorities;

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static MemberDetails from(Member member) {
        return MemberDetails.builder()
                    .id(member.getId())
                    .username(member.getUsername())
                    .password(member.getPassword())
                    .nickname(member.getNickname())
                    .email(member.getEmail())
                    .role(member.getRole())
                    .authorities(Set.of(new SimpleGrantedAuthority(member.getRole().getId())))
                .build();
    }
}
