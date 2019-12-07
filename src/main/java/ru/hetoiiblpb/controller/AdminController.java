package ru.hetoiiblpb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hetoiiblpb.model.User;
import ru.hetoiiblpb.service.UserService;

import java.sql.SQLException;

@Controller
public class AdminController {
    private  final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/admin/allUsers")
    public String main(Model model) throws SQLException {
        model.addAttribute("users",userService.getAllUsers());
        return "allUsers";
    }



    @GetMapping("/admin/delete")
    private String edit(@RequestParam ("id") Long id,Model model) throws SQLException {
        userService.deleteUser(id);
        model.addAttribute("users",userService.getAllUsers());
        return "redirect:/admin/allUsers";
    }

    @GetMapping("/admin/edit")
    private String egitPage(@RequestParam ("id") Long id, Model model) throws SQLException {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("/admin/edit")
    private String editUser(@ModelAttribute User user, Model model) throws SQLException {
        userService.updateUser(user);
        model.addAttribute("users",userService.getAllUsers());
        return "redirect:/admin/allUsers";
    }

}
