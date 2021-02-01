package com.springwebsite.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute(new UserForm());
        return "user/join";
    }
}
