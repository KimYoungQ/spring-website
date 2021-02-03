package com.springwebsite.Main;

import com.springwebsite.board.Board;
import com.springwebsite.NavMenu.NavMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String main() {
        return "index";
    }
}
