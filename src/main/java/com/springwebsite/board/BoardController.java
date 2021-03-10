package com.springwebsite.board;

import com.springwebsite.member.MemberService;
import com.springwebsite.setting.Paging;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/main")
    public String main(Model model, @RequestParam int board_info_idx, @RequestParam(value = "page", defaultValue = "1") int page) {

        model.addAttribute("board_info_idx", board_info_idx);

        String boardInfoName = boardService.getBoardInfoName(board_info_idx);
        model.addAttribute("boardInfoName", boardInfoName);

        List<Content> contentList = boardService.getContentList(board_info_idx, page);
        model.addAttribute("contentList", contentList);

        Paging paging = boardService.getContentCount(board_info_idx, page);
        model.addAttribute("paging", paging);

        model.addAttribute("page", page);

        return "board/main";
    }

    @GetMapping("/read")
    public String read(Model model, @RequestParam int board_info_idx, @RequestParam int content_idx, @RequestParam int page,
                       Principal principal) {

        model.addAttribute("board_info_idx", board_info_idx);
        model.addAttribute("content_idx", content_idx);
        model.addAttribute("page", page);
        model.addAttribute("principal", principal);

        Content content = boardService.getContentInfo(content_idx);
        model.addAttribute("content", content);

        boolean matchResult = matchCurrentUserAndWriterUser(content_idx, principal);
        model.addAttribute("matchResult", matchResult);

        return "board/read";
    }

    private boolean matchCurrentUserAndWriterUser(int content_idx, Principal principal) {

        String principalName = principal.getName();
        int writerIndex = boardService.getContextWriterIndexByContentIndex(content_idx);
        String writerName = memberService.findWriterNameByContentWriterIndex(writerIndex);

        return principalName.equals(writerName);
    }

    @GetMapping("/write")
    public String write(Model model, @RequestParam int board_info_idx, @RequestParam int page) {

        model.addAttribute(new Content());
        model.addAttribute("board_info_idx", board_info_idx);
        model.addAttribute("page", page);
        return "board/write";
    }

    @PostMapping("/write")
    public String write(@Valid @ModelAttribute Content content, Errors errors,
                        @RequestParam int board_info_idx, @RequestParam int page, Model model,
                        Principal principal,
                        RedirectAttributes attributes) throws IOException {
        if(errors.hasErrors()) {
            model.addAttribute("board_info_idx", board_info_idx);
            model.addAttribute("page", page);
            return "board/write";
        }


        content.setContent_board_idx(board_info_idx);
        String memberId = principal.getName();
        boardService.addContentInfo(content, memberId);
        attributes.addAttribute("board_info_idx", board_info_idx);
        attributes.addAttribute("content_idx", content.getContent_idx());
        attributes.addAttribute("page", page);

        return "redirect:/board/read";
    }

    @GetMapping("/modify")
    public String modify(Model model, @RequestParam int board_info_idx, @RequestParam int content_idx, @RequestParam int page,
                         @ModelAttribute Content content) {

        model.addAttribute("board_info_idx", board_info_idx);
        model.addAttribute("content_idx", content_idx);
        model.addAttribute("page", page);

        Content contentInfo = boardService.getContentInfo(content_idx);
        model.addAttribute("content", contentInfo);

        return "board/modify";
    }

    @PostMapping("/modify")
    public String modify(@Valid @ModelAttribute Content content, Errors errors,
                         RedirectAttributes attributes,
                         @RequestParam int board_info_idx, @RequestParam int content_idx, @RequestParam int page,
                         Principal principal,
                         Model model) throws IOException {
        if (errors.hasErrors()) {
            model.addAttribute("board_info_idx", board_info_idx);
            model.addAttribute("content_idx", content_idx);
            model.addAttribute("page",  page);
            model.addAttribute("content", content);
            return "board/modify";
        }

        content.setContent_board_idx(board_info_idx);
        attributes.addAttribute("board_info_idx", board_info_idx);
        attributes.addAttribute("content_idx", content_idx);
        attributes.addAttribute("page", page);

        String memberId = principal.getName();
        boardService.modifyContentInfo(content, memberId);
        return "redirect:/board/read";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int board_info_idx, @RequestParam int content_idx, @RequestParam int page,
                         @ModelAttribute Content content,
                         Model model,
                         RedirectAttributes attributes) {
        model.addAttribute(content);
        model.addAttribute("board_info_idx", board_info_idx);
        model.addAttribute("content_idx", content_idx);
        boardService.deleteContentInfo(content_idx);

        attributes.addAttribute("board_info_idx", board_info_idx);
        attributes.addAttribute("page", page);

        return "redirect:/board/main";
    }
}
