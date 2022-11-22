package com.greplfa.web.domain.member.service;

import com.greplfa.web.domain.member.dto.MemberCreateParameter;
import com.greplfa.web.domain.member.exception.MemberEmailExistsException;
import com.greplfa.web.domain.member.exception.MemberPasswordIncorrectException;
import com.greplfa.web.domain.member.exception.MemberUsernameExistsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface MemberService extends UserDetailsService {
    /**
     * 사용자 인증시 사용되는 메소드
     * @param username 사용자 아이디
     * @return 사용자 인증 정보
     * @throws UsernameNotFoundException 데이터베이스에 입력받은 아이디가 없을때 발생
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    /**
     * 일반 회원 가입할때 사용되는 메소드
     * @throws MemberUsernameExistsException 아이디가 이미 존재할시 발생
     */
    void register(MemberCreateParameter parameter) throws MemberPasswordIncorrectException, MemberUsernameExistsException, MemberEmailExistsException;
}
