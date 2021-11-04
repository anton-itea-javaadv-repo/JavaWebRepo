package ua.itea.web.hw14.lesson14hw.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public interface IndexController {
    @RequestMapping("/")
    String home();
}
