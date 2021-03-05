package com.springwebsite.member;

import com.springwebsite.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;


public class WithSecurityContextFacotry implements WithSecurityContextFactory<WithMockCustomUser> {

    @Autowired
    private MemberService memberService;

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser withMockCustomUser) {
        String id = withMockCustomUser.value();
        String password = "123123123";
        String name = "테스트용";

        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setId(id);
        signUpForm.setPassword("12345678");
        signUpForm.setName("테스트용");
        signUpForm.setEmail("test@email.com");
        Member member = memberService.createNewMember(signUpForm);

        UserDetails principal = memberService.loadUserByUsername(id);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), principal.getAuthorities());
        authentication.setDetails(new Member(member.getId(), member.getPassword()));
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}
