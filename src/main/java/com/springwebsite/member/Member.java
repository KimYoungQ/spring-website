package com.springwebsite.member;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    private int member_idx;

    private String id;

    private String password;

    private String passwordConfirm;

    private String name;

    private String role;

    public Member(String id, String password) {
        this.id = id;
        this.password =password;
    }
}
