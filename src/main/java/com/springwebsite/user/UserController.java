package com.springwebsite.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute(new UserForm());
        return "user/join";
    }

    @PostMapping("/join")
    public String join(@Valid @ModelAttribute UserForm userForm, Errors errors) {
        if (errors.hasErrors()) {
            return "user/join";
        }

        return "redirect:/";
    }
}
