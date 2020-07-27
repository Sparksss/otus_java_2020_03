package ru.otus.servlet;

import ru.otus.core.service.DBServiceUser;
import ru.otus.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ru.otus.core.model.User;

public class UsersServlet extends HttpServlet {

    private TemplateProcessor templateProcessor;
    private DBServiceUser dbServiceUser;

    public UsersServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser ) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> data = new HashMap<>();
        List<User> users = this.dbServiceUser.getAllUsers();
        data.put("users", users);
        String html = templateProcessor.getPage("users.html", data);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println(html);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }
}
