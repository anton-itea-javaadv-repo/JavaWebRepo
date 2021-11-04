package ua.itea.web.hw14.lesson14hw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequestMapping("/login")
public interface LoginController {
    @RequestMapping(method = RequestMethod.GET)
    String get(
            HttpSession session,
            @RequestParam(required = false) String logout);

    @RequestMapping(method = RequestMethod.POST)
    String post(
            HttpSession session,
            @RequestParam(required = false) String logout,
            @RequestParam String login,
            @RequestParam(required = false) String password);
}
