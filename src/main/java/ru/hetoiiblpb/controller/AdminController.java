package ru.hetoiiblpb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.hetoiiblpb.model.UserDTO;
import ru.hetoiiblpb.service.UserService;

@Controller
public class AdminController {
    private  final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/admin/allUsers")
    public String main(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "allUsers";
    }


    @GetMapping("/admin/delete")
    private String edit(@RequestParam("id") Long id, Model model) {
        userService.deleteUser(id);
        model.addAttribute("users", userService.getAllUsers());
        return "redirect:/admin/allUsers";
    }

    @GetMapping("/admin/edit")
    private String egitPage(@RequestParam("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("/admin/edit")
    private String editUser(@ModelAttribute UserDTO userDTO, Model model) {
        System.out.println(userDTO);
        userService.updateUser(userDTO);
        return "redirect:/admin/allUsers";
    }

    @PostMapping("/admin/add")
    public String addUser(UserDTO userDTO, Model model) {


        if (userService.isExistLogin(userDTO.getLogin())) {
            model.addAttribute("message", "This login exists!");
        }
        userService.addUser(userDTO);
        return "redirect:/admin/allUsers";
    }

}
