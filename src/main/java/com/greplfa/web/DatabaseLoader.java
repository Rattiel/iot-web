package com.greplfa.web;

import com.greplfa.web.domain.member.Member;
import com.greplfa.web.domain.member.Role;
import com.greplfa.web.domain.member.dto.MemberCreateParameter;
import com.greplfa.web.domain.member.dto.MemberRegisterForm;
import com.greplfa.web.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Profile("init")
@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseLoader implements ApplicationRunner {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final static String ADMIN_ID = "ef41418b-68de-11ed-8e14-6e51dda814a4";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "1234";
    private static final String ADMIN_NICKNAME = "관리자";
    private static final String ADMIN_EMAIL = "whddlf0504@naver.com";

    @Transactional
    @Override
    public void run(ApplicationArguments args) {
        Optional<Member> memberOptional = memberRepository.findByUsername(ADMIN_USERNAME);

        if (memberOptional.isPresent()) {
            log.info("기존 관리자 계정 (아이디 : {}, 비밀번호 : {})", ADMIN_USERNAME, "?");
        } else {
            MemberRegisterForm adminMemberRegisterForm = MemberRegisterForm.builder()
                    .username(ADMIN_USERNAME)
                    .password(ADMIN_PASSWORD)
                    .passwordConfirm(ADMIN_PASSWORD)
                    .nickname(ADMIN_NICKNAME)
                    .email(ADMIN_EMAIL)
                    .build();

            Member admin = Member.of(MemberCreateParameter.from(adminMemberRegisterForm, ADMIN_ID, Role.ADMIN), passwordEncoder);

            memberRepository.save(admin);

            log.info("새로 생성된 관리자 계정 (아이디 : {}, 비밀번호 : {})", ADMIN_USERNAME, ADMIN_PASSWORD);
        }
    }
}
