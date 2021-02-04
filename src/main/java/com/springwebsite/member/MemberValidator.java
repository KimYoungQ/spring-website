package com.springwebsite.member;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MemberValidator implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return Member.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Member member = (Member) o;
    }
}
