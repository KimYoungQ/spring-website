package com.springwebsite.main;

import com.springwebsite.board.Board;
import com.springwebsite.board.Content;
import com.springwebsite.member.Member;
import com.springwebsite.member.MemberService;
import com.springwebsite.member.UserMember;
import com.springwebsite.navMenu.NavMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final NavMenuService navMenuService;
    private final MemberService memberService;

    @GetMapping("/")
    public String main(Model model, Principal principal) {

        ArrayList<List<Content>> MainContentList = new ArrayList<List<Content>>();

        for(int i=1; i<=4; i++) {
            List<Content> contentList = mainService.getContentList(i);
            MainContentList.add(contentList);
        }

        model.addAttribute("mainContentList", MainContentList);

        List<Board> boardList = navMenuService.getNavMenuList();
        model.addAttribute("boardList", boardList);

        if(principal != null) {
            String memberId = principal.getName();
            Boolean emailVerifiedById = memberService.isEmailVerifiedById(memberId);
            model.addAttribute("memberId", memberId);
            model.addAttribute("emailVerified", emailVerifiedById);
        }

        return "index";
    }
}
