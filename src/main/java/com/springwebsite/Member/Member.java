package com.springwebsite.Member;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
public class Member {

    private Long member_idx;

    private String id;

    private String password;

    private String passwordConfirm;

    private String name;

    private String role;
}
