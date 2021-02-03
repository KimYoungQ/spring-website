package com.springwebsite.config;

import com.springwebsite.Member.MemberService;
import com.springwebsite.Member.UserMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        UsernamePasswordAuthenticationToken authToken = (UsernamePasswordAuthenticationToken) authentication;
//        //유저가 입력한 정보를 이이디비번으으로만든다.(로그인한 유저아이디비번정보를담는다)
//
//        //UserDetailsService에서 유저정보를 불러온다.
//        UserMember userInfo = (UserMember)memberService.loadUserByUsername(authToken.getName());
//
//        System.out.println("해당 아이디 있는 지 확인");
//        if (userInfo == null) {
//            throw new UsernameNotFoundException(authToken.getName());
//        }
//
//        System.out.println("아이디 비밀번호 맞는지 확인");
//        if (matchPassword(userInfo.getPassword(), authToken.getCredentials())) {
//            throw new BadCredentialsException("not matching username or password");
//        }
//
//        List<GrantedAuthority> authorities = (List<GrantedAuthority>) userInfo.getAuthorities();
//        return new UsernamePasswordAuthenticationToken(userInfo, authorities);

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserMember user = (UserMember)memberService.loadUserByUsername(username);

        if(!matchPassword(password, user.getPassword())) {
            throw new BadCredentialsException(username);
        }

        if(!user.isEnabled()) {
            throw new BadCredentialsException(username);
        }

        return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());

    }

    private boolean matchPassword(String password, String Password) {
        boolean matches = passwordEncoder.matches(password, Password);
        return matches;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
