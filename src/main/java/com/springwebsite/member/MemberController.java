package com.springwebsite.member;

import com.springwebsite.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.mail.javamail.JavaMailSender;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;

    @InitBinder("signUpForm")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(memberValidator);
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute(new SignUpForm());
        return "member/join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors) {
        if (errors.hasErrors()) {
            return "member/join";
        }

        Member member = memberService.processNewAccount(signUpForm);
        memberService.login(member);

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/check-email-token")
    public String checkEmailToken(String token, String email, Model model) {
        Member member = memberService.findByEmail(email);
        String view = "member/checkedEmail";
        if (member == null) {
            model.addAttribute("error", "wrong.email");
            return view;
        }

        if(!member.isValidToken(token)) {
            model.addAttribute("error", "wrong.token");
            return view;
        }

        String VerifiedEmail = "1";
        member.setEmailVerified(VerifiedEmail);
        memberService.updateEmailVerified(VerifiedEmail);
        memberService.login(member);
        model.addAttribute("name", member.getName());
        return view;

    }

    @GetMapping("/check-email")
    public String checkEmail(Principal principal, Model model) {
        String memberId = principal.getName();
        Member currentMember = memberService.findById(memberId);
        model.addAttribute("email", currentMember.getEmail());
        return "member/checkEmail";
    }

    @GetMapping("/resend-confirm-email")
    public String resendConfirmEmail(Principal principal, Model model) {
        String memberId = principal.getName();
        Member currentMember = memberService.findById(memberId);
        memberService.sendSignUpConfirmEmail(currentMember);
        return "redirect:/";
    }
}
