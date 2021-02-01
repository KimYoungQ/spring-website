package com.springwebsite.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserForm {

    @NotBlank(message = "ID를 입력해주세요.")
    @Length(min = 3, max = 20, message = "5~20자의 영소문자, 숫자와 특수기호(_)(-)만 사용가능합니다.")
    @Pattern(regexp = "^[a-z0-9_-]{3,20}$")
    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 8, max = 16, message = "8~16자의 영문 대 소문자, 숫자만 사용가능합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$")
    private String password;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 8, max = 16)
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$")
    private String passwordConfirm;


    @NotBlank(message = "이름을 입력해주세요.")
    @Length(min = 1, max = 20, message = "1~20자의 한글 및 영문 대소문자만 가능합니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]{1,20}$")
    private String name;
}
