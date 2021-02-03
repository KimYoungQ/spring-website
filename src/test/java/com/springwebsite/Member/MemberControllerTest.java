package com.springwebsite.Member;

import com.springwebsite.Form.SignUpForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private MemberService memberService;

    @DisplayName("회원 가입 뷰 작동")
    @Test
    void signUpView() throws Exception {
        mockMvc.perform(get("/join"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("member/join"))
                .andExpect(model().attributeExists("member"));
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
                .andExpect(view().name("member/join"));
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
                .andExpect(view().name("redirect:/"));
    }

    @Test
    @Transactional
    void login() throws Exception {
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setId("tes1");
        signUpForm.setPassword("123123123");
        signUpForm.setName("12345678");
        memberService.createNewMember(signUpForm);

        mockMvc.perform(post("/login")
                .param("id", "tes1")
                .param("password", "123123123")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("tes1"));
    }

}