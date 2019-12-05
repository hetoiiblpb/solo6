package ru.hetoiiblpb.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.hetoiiblpb.model.User;
import ru.hetoiiblpb.service.UserService;

import java.sql.SQLException;

@Controller
public class RegistrationController {
  private  final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model) throws SQLException {


        if (userService.isExistLogin(user.getLogin())) {
            model.addAttribute("message","This login exists!");
            return "registration";
        }
        userService.addUser(user);
        return "redirect:/login";
    }
}
