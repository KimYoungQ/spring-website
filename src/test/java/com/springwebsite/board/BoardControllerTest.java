package com.springwebsite.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springwebsite.member.Member;
import com.springwebsite.member.MemberDao;
import com.springwebsite.member.MemberService;
import com.springwebsite.member.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
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
    PasswordEncoder passwordEncoder;

    @Autowired
    MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("게시글 메인 뷰 작동")
    @Test
    void main() throws Exception {

        mockMvc.perform(get("/board/main")
                .param("board_info_idx", "1")
                .param("page", "1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("board/main"))
                .andExpect(model().attributeExists("board_info_idx"))
                .andExpect(model().attributeExists("boardInfoName"))
                .andExpect(model().attributeExists("contentList"))
                .andExpect(model().attributeExists("paging"))
                .andExpect(model().attributeExists("page"));
    }

    @DisplayName("게시글 조회")
    @WithMockCustomUser("testID")
    @Test
    void read() throws Exception {

        Content content = createContent("testID");
        boardService.addContentInfo(content, "testID");

        String str_content_idx = intToStringContentIdx(content);

        mockMvc.perform(get("/board/read")
                .param("board_info_idx", "1")
                .param("content_idx", str_content_idx)
                .param("page", "1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("board/read"))
                .andExpect(model().attributeExists("board_info_idx"))
                .andExpect(model().attributeExists("content_idx"))
                .andExpect(model().attributeExists("page"))
                .andExpect(model().attributeExists("content"));
    }

    @DisplayName("게시글 작성")
    @WithMockCustomUser("testID")
    @Test
    void write() throws Exception {

        mockMvc.perform(post("/board/write")
                .param("content_subject", "제목자리")
                .param("content_text", "내용자리")
                .param("board_info_idx", "1")
                .param("page", "1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/board/read"))
                .andExpect(model().attributeExists("board_info_idx"))
                .andExpect(model().attributeExists("content_idx"))
                .andExpect(model().attributeExists("page"))
                .andExpect(authenticated());
    }

    @DisplayName("게시글 수정")
    @WithMockCustomUser("testID")
    @Test
    void modify() throws Exception {
        Content content = createContent("testID");
        boardService.addContentInfo(content, "testID");

        String str_content_idx = intToStringContentIdx(content);

        mockMvc.perform(post("/board/modify")
                .param("board_info_idx", "1")
                .param("page", "1")
                .param("content_idx", str_content_idx)
                .param("content_subject", "제목자리")
                .param("content_text", "내용자리")
                .param("content_writer_name", "테스트")
                .param("content_date", "2021-02-08 12:12:12")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/board/read"))
                .andExpect(model().attributeExists("board_info_idx"))
                .andExpect(model().attributeExists("content_idx"))
                .andExpect(model().attributeExists("page"))
                .andExpect(authenticated());
    }

    @DisplayName("게시글 삭제")
    @WithMockCustomUser("testID")
    @Test
    void delete() throws Exception {
        Content content = createContent("testID");
        boardService.addContentInfo(content, "testID");

        String str_content_idx = intToStringContentIdx(content);

        mockMvc.perform(get("/board/delete")
                .param("board_info_idx", "1")
                .param("content_idx", str_content_idx)
                .param("page", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/board/main"))
                .andExpect(model().attributeExists("board_info_idx"))
                .andExpect(model().attributeExists("page"))
                .andExpect(authenticated());
    }

    private String intToStringContentIdx(Content content) {
        int content_idx = content.getContent_idx();
        return String.valueOf(content_idx);
    }

    private int findWriterIndexById(String id) {
        Member member = memberService.findById(id);
        int content_writer_idx = member.getMember_idx();
        return content_writer_idx;
    }

    private Content createContent(String id) {
        int content_writer_idx = findWriterIndexById(id);

        Content content = Content.builder()
                .content_subject("제목 테스트")
                .content_text("내용 테스트")
                .content_board_idx(1)
                .content_writer_idx(content_writer_idx)
                .build();
        return content;
    }
}