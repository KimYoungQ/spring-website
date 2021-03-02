package com.springwebsite.member;

import com.springwebsite.form.SignUpForm;
import com.springwebsite.mail.EmailMessage;
import com.springwebsite.mail.EmailService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @MockBean
    EmailService emailService;


    @DisplayName("회원 가입 뷰 작동")
    @Test
    void signUpView() throws Exception {
        mockMvc.perform(get("/join"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/join"))
                .andExpect(model().attributeExists("signUpForm"))
                .andExpect(unauthenticated());
    }

    @DisplayName("회원 가입 처리 - 입력값 오류")
    @Test
    void signUpTest_wrong_input() throws Exception {
        mockMvc.perform(post("/join")
                .param("id", "qWEQgqeㅂㅈㄷ")
                .param("password", "12345678")
                .param("passwordConfirm", "12345678")
                .param("name", "이름")
                .param("email", "qweasd")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("member/join"))
                .andExpect(unauthenticated());
    }

    @DisplayName("회원 가입 처리 - 입력값 정상")
    @Test
    void signUpTest_correct_input() throws Exception {
        mockMvc.perform(post("/join")
                .param("id", "testid")
                .param("password", "12345678")
                .param("passwordConfirm", "12345678")
                .param("name", "이름")
                .param("email", "kim@gmail.com")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(authenticated());

        Member member = memberDao.findByEmail("kim@gmail.com");
        assertNotNull(member);
        assertNotEquals(member.getPassword(), "12345678");
        assertNotNull(member.getEmailCheckToken());
        assertTrue(memberDao.existByEmail("kim@gmail.com") != null);
        then(emailService).should().sendEmail(any(EmailMessage.class));
    }

    @DisplayName("로그인")
    @Test
    void login_correct_input() throws Exception {
        String id = "testID";
        String password = "123123123";

        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setId(id);
        signUpForm.setPassword(password);
        signUpForm.setName("테스트용");
        signUpForm.setEmail("test@email.com");

        Member newMember = memberService.createNewMember(signUpForm);

        mockMvc.perform(post("/login")
               .param("id", newMember.getId())
                .param("password", password)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated());
    }

    @DisplayName("로그인 실패")
    @Test
    void login_wrong_input() throws Exception {
        String id = "testID";
        String password = "123123123";

        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setId(id);
        signUpForm.setPassword(password);
        signUpForm.setName("테스트용");
        signUpForm.setEmail("test@email.com");

        Member newMember = memberService.createNewMember(signUpForm);

        mockMvc.perform(post("/login")
                .param("id", "testID")
                .param("password","1123123123")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());

    }

    @DisplayName("로그아웃")
    @Test
    void logout() throws Exception {
        mockMvc.perform(post("/logout")
               .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(unauthenticated());
    }

    @DisplayName("인증 메일 확인 - 입력값 오류")
    @Test
    void checkEmailToken_wrong_input() throws Exception {
        mockMvc.perform(get("/check-email-token")
                .param("token", "sdfqwrast")
                .param("email", "email@email.com"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("error"))
                .andExpect(view().name("member/checkedEmail"))
                .andExpect(unauthenticated());
    }

    @DisplayName("인증 메일 확인 - 입력값 정상")
    @Test
    void checkEmailToken_correct_input() throws Exception {
        String id = "testID";
        String password = "123123123";

        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setId(id);
        signUpForm.setPassword(password);
        signUpForm.setName("테스트용");
        signUpForm.setEmail("test@email.com");

        Member newMember = memberService.createNewMember(signUpForm);

        mockMvc.perform(get("/check-email-token")
                .param("token", newMember.getEmailCheckToken())
                .param("email", newMember.getEmail()))
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("error"))
                .andExpect(model().attributeExists("name"))
                .andExpect(view().name("member/checkedEmail"))
                .andExpect(authenticated().withUsername("testID"));
    }
}