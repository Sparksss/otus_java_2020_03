package ru.otus.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.core.model.User;
import ru.otus.repository.UserRepository;
import ru.otus.repository.UserRepositoryImpl;

import java.util.List;

@Controller
public class UserController {
    @GetMapping({"/", "/user/list"})
    public String userListView() {
        UserRepository repository = new UserRepositoryImpl();
        List<User> users = repository.findAll();
        return "userList.html";
    }
}
