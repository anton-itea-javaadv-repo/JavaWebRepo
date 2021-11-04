package ua.itea.web.hw14.lesson14hw.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@RequestMapping("/registration")
public interface RegistrationController {
    @RequestMapping(method = RequestMethod.GET)
    String get(HttpSession session);

    @RequestMapping(method = RequestMethod.POST)
    String post(
            HttpSession session,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String login,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String passwordRepeat,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String comment,
            @RequestParam(required = false) String browser);
}
