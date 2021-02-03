package com.springwebsite.Member;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private Long member_idx;

    private String id;

    private String password;

    private String passwordConfirm;

    private String name;

    private String role;
}
