package ru.otus.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import ru.otus.core.model.User;
import ru.otus.services.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    UserService userService;

    @GetMapping({"/", "/user/list"})
    public String userListView(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "userList.html";
    }
}
