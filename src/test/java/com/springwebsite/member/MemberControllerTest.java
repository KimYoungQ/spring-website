package com.springwebsite.member;

import com.springwebsite.form.SignUpForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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
class MemberControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private MemberService memberService;


    @BeforeEach
    void beforeEach() {
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setId("testID");
        signUpForm.setPassword("123123123");
        signUpForm.setName("apple");
        memberService.createNewMember(signUpForm);
    }

    @AfterEach
    void afterEach() {
        memberService.deleteAll();
    }


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
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(authenticated());
    }

    @DisplayName("로그인")
    @Test
    void login_correct_input() throws Exception {
        mockMvc.perform(post("/login")
               .param("id", "testID")
                .param("password","123123123")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated());
    }

    @DisplayName("로그인 실패")
    @Test
    void login_wrong_input() throws Exception {
        mockMvc.perform(post("/login")
                .param("id", "notexist")
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

}