package com.springwebsite.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserForm {

    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[a-z0-9_-]{3,20}$" , message = "다시 입력해주세요")
    private String id;

    @Length(min = 8, max = 16)
    @Pattern(regexp = "^[a-zA-Z0-9]{8,16}$", message = "다시 입력해주세요")
    private String password;

    @Length(min = 8, max = 16)
    @Pattern(regexp = "^[a-zA-Z0-9]{8,16}$")
    private String passwordConfirm;

    @Length(min = 1, max = 20)
    @Pattern(regexp = "^[가-힣a-zA-Z]{1,20}$" ,message = "1~20자 영어 대소문자, 한글만 가능합니다.")
    private String name;
}
