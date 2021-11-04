package ua.itea.web.hw14.lesson14hw.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@RequestMapping("/cart")
public interface CartController {
    @RequestMapping(method = RequestMethod.GET)
    String doGet(HttpSession session, ModelMap model);

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    String doPost(
            HttpSession session,
            ModelMap model,
            @RequestParam(name = "buy", required = false) Integer id,
            @RequestParam(name = "change", required = false) Integer changeId,
            @RequestParam(name = "quantity") String quantityStr);
}
