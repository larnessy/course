package com.course.controller;

import com.course.model.entity.security.User;
import com.course.service.security.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute @Valid User user,
                             RedirectAttributes redirectAttributes,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("msg", "Длина имени и пароля"
                    + "должны быть длиннее 5 и короче 200 символов");
        } else if (userService.saveUser(user)) {
            redirectAttributes.addFlashAttribute("msg", "Register Successfully");
        } else {
            redirectAttributes.addFlashAttribute("msg", "User with this name already exists");
        }

        return "redirect:/register";
    }
}
