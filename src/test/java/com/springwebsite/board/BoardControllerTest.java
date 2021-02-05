package com.springwebsite.board;

import com.springwebsite.form.SignUpForm;
import com.springwebsite.member.Member;
import com.springwebsite.member.MemberDao;
import com.springwebsite.member.WithMockCustomUser;
import org.apache.ibatis.jdbc.Null;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    MockMvc  mockMvc;

    @Autowired
    BoardService boardService;

    @Autowired
    BoardDao boardDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberDao memberDao;

    @BeforeEach
    void beforeEach() {

    }

    @AfterEach
    void afterEach() {
    }

    @DisplayName("네비게이션바 클릭 게시판 이동")
    @Test
    void main() throws Exception {
        mockMvc.perform(get("/board/main")
                .param("board_info_idx", "1")
                .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/board/main"))
                .andExpect(model().attributeExists("boardInfoName"));
    }

    @DisplayName("게시글 조회")
    @WithMockCustomUser("testID")
    @Test
    void read() throws Exception {
        Member member = memberDao.findById("testID");
        int content_writer_idx = member.getMember_idx();

        Content content = Content.builder()
                .content_subject("제목 테스트")
                .content_text("내용 테스트")
                .content_board_idx(1)
                .content_writer_idx(content_writer_idx)
                .build();
        boardDao.addContentInfo(content);
        int content_idx = content.getContent_idx();
        String str_content_idx = String.valueOf(content_idx);

        mockMvc.perform(get("/board/read")
                .param("board_info_idx", "1")
                .param("content_idx", str_content_idx)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("/board/read"));
    }


}