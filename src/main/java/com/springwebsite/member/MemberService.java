package com.springwebsite.member;

import com.springwebsite.form.SignUpForm;
import com.springwebsite.mail.EmailMessage;
import com.springwebsite.mail.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberDao memberDao;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TemplateEngine templateEngine;

    @Value("${server.port}")
    private int HOST;

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
                .email(signUpForm.getEmail())
                .emailVerified("0")
                .build();

        member.generateEmailCheckToken();
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

    public Member findByEmail(String email) {
        return memberDao.findByEmail(email);
    }

    public String existById(String id) {
        return memberDao.existById(id);
    }

    public String existByEmail(String email){
        return memberDao.existByEmail(email);
    }

    public String findWriterNameByContentWriterIndex(int content_idx) {
        return memberDao.findWriterNameByContentWriterIndex(content_idx);
    }

    public void sendSignUpConfirmEmail(Member newMember) {
        Context context = new Context();
        context.setVariable("link", "/check-email-token?token=" + newMember.getEmailCheckToken() +
                "&email=" + newMember.getEmail());
        context.setVariable("name", newMember.getName());
        context.setVariable("linkName", "이메일 인증하기");
        context.setVariable("message", "이메일 인증을 진행하실려면 아래의 링크를 클릭하세요.");
        context.setVariable("host", "http://localhost:" + HOST);

        String message = templateEngine.process("mail/simple-link", context);

        EmailMessage emailMessage = EmailMessage.builder()
                .to(newMember.getEmail())
                .subject("[스프링 웹사이트] 회원가입 인증 메일입니다.")
                .message(message)
                .build();

        emailService.sendEmail(emailMessage);
    }

    public Member processNewAccount(SignUpForm signUpForm) {
        Member newMember = createNewMember(signUpForm);
        sendSignUpConfirmEmail(newMember);

        return newMember;
    }

    public void updateEmailVerified(String emailVerified) {
        memberDao.updateEmailVerified(emailVerified);
    }

    public Boolean isEmailVerifiedById(String id) {
        return memberDao.isEmailVerifiedById(id);
    }
}
