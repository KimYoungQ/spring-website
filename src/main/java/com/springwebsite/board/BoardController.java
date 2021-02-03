package com.springwebsite.board;

import com.springwebsite.NavMenu.NavMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
}
