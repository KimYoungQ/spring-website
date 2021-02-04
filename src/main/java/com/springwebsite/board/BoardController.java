package com.springwebsite.board;

import com.springwebsite.member.Member;
import com.springwebsite.member.UserMember;
import com.springwebsite.navMenu.NavMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final NavMenuService navMenuService;
    private final BoardService boardService;

    @GetMapping("/main")
    public String main(Model model, @RequestParam int board_info_idx) {

        model.addAttribute("board_info_idx", board_info_idx);

        String boardInfoName = boardService.getBoardInfoName(board_info_idx);
        model.addAttribute("boardInfoName", boardInfoName);

        return "/board/main";
    }

    @GetMapping("/read")
    public String read(Model model, @RequestParam int board_info_idx, @RequestParam int content_idx) {

        model.addAttribute("board_info_idx", board_info_idx);
        model.addAttribute("content_idx", content_idx);


        return "/board/read";
    }

    @GetMapping("/write")
    public String write(Model model, @RequestParam int board_info_idx) {

        model.addAttribute(new Content());
        model.addAttribute("board_info_idx", board_info_idx);
        return "/board/write";
    }

    @PostMapping("/write")
    public String write(@Valid @ModelAttribute Content content, Errors errors,
                        @RequestParam int board_info_idx, Model model,
                        Principal principal,
                        RedirectAttributes attributes) {
        if(errors.hasErrors()) {
            model.addAttribute("board_info_idx", board_info_idx);
            return "/board/write";
        }


        content.setContent_board_idx(board_info_idx);
        String memberId = principal.getName();
        boardService.addContentInfo(content, memberId);
        attributes.addAttribute("board_info_idx", board_info_idx);
        attributes.addAttribute("content_idx", content.getContent_idx());

        return "redirect:/board/read";
    }
}
