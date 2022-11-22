package com.greplfa.web.domain.member.controller;

import com.greplfa.web.domain.common.exception.FieldException;
import com.greplfa.web.domain.common.exception.Fieldable;
import com.greplfa.web.domain.member.dto.MemberCreateParameter;
import com.greplfa.web.domain.member.dto.MemberDetails;
import com.greplfa.web.domain.member.dto.MemberRegisterForm;
import com.greplfa.web.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class MemberController  {
    private final MemberService memberService;

    @GetMapping("/login")
    public String renderLoginPage(
            @AuthenticationPrincipal MemberDetails member
    ) {
        if (member != null) {
            return "redirect:/";
        }

        return "member/login";
    }

    @GetMapping("/register")
    public String renderRegisterPage(
            @ModelAttribute("form") MemberRegisterForm registerForm,
            @AuthenticationPrincipal MemberDetails member
    ) {
        if (member != null) {
            return "redirect:/";
        }

        return "member/register";
    }

    @PostMapping("/register")
    public String requestRegister(
            @Valid @ModelAttribute("form") MemberRegisterForm registerForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "member/register";
        }

        try {
            memberService.register(MemberCreateParameter.from(registerForm));
        } catch (FieldException e) {
            bindFieldError(bindingResult, e);

            return "member/register";
        }

        return "redirect:/login";
    }

    private void bindFieldError(BindingResult bindingResult, Fieldable e) {
        FieldError fieldError = new FieldError("form", e.getField(), e.getReason());
        bindingResult.addError(fieldError);
    }
}
