package ua.itea.web.hw14.lesson14hw.controller.impl;

import org.springframework.stereotype.Controller;
import ua.itea.web.hw14.lesson14hw.controller.IndexController;

@Controller
public class IndexControllerImpl implements IndexController {
    public String home() {
        return "index";
    }
}
