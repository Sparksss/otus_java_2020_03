package ru.otus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.User;
import ru.otus.services.UserService;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/user/list"})
    public String userListView(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "userList.html";
    }

    @GetMapping("/user/create")
    public String userCreateView(Model model) {
        model.addAttribute("user", new User());
        return "userCreate.html";
    }

    @PostMapping("/user/save")
    public String addUser(@RequestParam String name) {
        userService.save(new User(name));
        return "redirect:" + "/user/list";
    }

    @GetMapping("/user/delete")
    public String delete(@RequestParam int id) {
        userService.deleteUser(id);
        return "redirect:" + "/user/list";
    }

    @GetMapping("/user/edit")
    public String edit(@RequestParam int id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "userEdit.html";
    }

    @PostMapping("/user/update")
    public String update(@RequestParam int id, @RequestParam String name) {
        userService.update(id, name);
        return "redirect:" + "/user/list";
    }

}
