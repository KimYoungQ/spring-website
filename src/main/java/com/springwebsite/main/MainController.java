package com.springwebsite.main;

import com.springwebsite.board.Board;
import com.springwebsite.board.Content;
import com.springwebsite.navMenu.NavMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    private final NavMenuService navMenuService;

    @GetMapping("/")
    public String main(Model model) {

        ArrayList<List<Content>> MainContentList = new ArrayList<List<Content>>();

        for(int i=1; i<=4; i++) {
            List<Content> contentList = mainService.getContentList(i);
            MainContentList.add(contentList);
        }

        model.addAttribute("mainContentList", MainContentList);

        List<Board> boardList = navMenuService.getNavMenuList();
        model.addAttribute("boardList", boardList);

        return "index";
    }
}
