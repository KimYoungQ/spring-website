package com.springwebsite.member;

import com.springwebsite.form.SignUpForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MemberValidator implements Validator {

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
    }
}
