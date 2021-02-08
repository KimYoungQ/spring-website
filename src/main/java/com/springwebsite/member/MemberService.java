package com.springwebsite.member;

import com.springwebsite.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberDao memberDao;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberDao.findById(id);
        if (member == null) {
            throw new UsernameNotFoundException(id);
        }

        return new UserMember(member);
    }

    public Member createNewMember(SignUpForm signUpForm) {

        Member member = Member.builder()
                .id(signUpForm.getId())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .role("ROLE_USER")
                .name(signUpForm.getName())
                .build();

        memberDao.save(member);
        return member;
    }

    public void login(Member member) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserMember(member),
                member.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public void deleteAll() {
        memberDao.deleteAll();
    }

    public Member findById(String id) {
        return memberDao.findById(id);
    }

    public String existById(String id) {
        return memberDao.existById(id);
    }
}
