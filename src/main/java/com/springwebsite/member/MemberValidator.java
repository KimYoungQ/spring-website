package com.springwebsite.member;

import com.springwebsite.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class MemberValidator implements Validator {

    private final MemberService memberService;

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

        SignUpForm signUpForm = (SignUpForm) o;

        if(signUpForm.getPassword().equals(signUpForm.getPasswordConfirm()) == false) {
            errors.rejectValue("passwordConfirm", "wrong.value", "패스워드가 일치하지 않습니다.");
        }

        if(memberService.existById(signUpForm.getId()) != null) {
            errors.rejectValue("id", "invalid.id", "이미 존재하는 ID입니다.");
        }

        if(memberService.existByEmail(signUpForm.getEmail()) != null) {
            errors.rejectValue("email", "invalid.email", "이미 존재하는 이메일입니다.");
        }

    }
}
