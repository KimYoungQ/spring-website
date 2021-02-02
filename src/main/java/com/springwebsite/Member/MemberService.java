package com.springwebsite.Member;

import com.springwebsite.Form.SignUpForm;
import com.springwebsite.Mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        Member newMember = Member.builder()
                .id(signUpForm.getId())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .name(signUpForm.getName())
                .role("ROLE_USER")
                .build();

        memberDao.save(newMember);
        return newMember;
    }
}
