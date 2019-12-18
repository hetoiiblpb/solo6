package ru.hetoiiblpb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/dash")
    public String dash() {
        return "dashboard";
    }


}