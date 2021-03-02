package com.springwebsite.member;

import lombok.*;

import java.util.UUID;

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

    private String email;

    private String emailCheckToken;

    private String emailVerified;

    public Member(String id, String password) {
        this.id = id;
        this.password =password;
    }

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }
}
