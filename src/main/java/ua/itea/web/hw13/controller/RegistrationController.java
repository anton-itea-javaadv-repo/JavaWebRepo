package ua.itea.web.hw13.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ua.itea.web.hw13.dto.UserDto;
import ua.itea.web.hw13.service.UserService;
import ua.itea.web.hw13.util.StaticUtils;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Optional;

import static ua.itea.web.hw13.controller.LoginController.AUTHORIZED;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    public static final String SHOW_REGISTRATION_FORM = "showRegistrationForm";
    public static final String REQUESTED_CHANGE = "requestedChange";
    public static final String IS_ERROR = "isError";
    public static final String ERROR_TEXT = "errorText";
    public static final String SUCCESS_TEXT = "successText";

    public static final String USER = "user";

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String get(HttpSession session) {
        if (session.getAttribute(SUCCESS_TEXT) != null) {
            session.setAttribute(SUCCESS_TEXT, null);
            session.setAttribute(SHOW_REGISTRATION_FORM, true);
            session.setAttribute(IS_ERROR, false);
            session.setAttribute(ERROR_TEXT, "");
            session.setAttribute(USER, null);
            session.setAttribute(REQUESTED_CHANGE, null);
        }

        if (session.getAttribute(SHOW_REGISTRATION_FORM) == null) {
            session.setAttribute(SHOW_REGISTRATION_FORM, true);
        }
        if (session.getAttribute(IS_ERROR) == null) {
            session.setAttribute(IS_ERROR, false);
        }
        if (session.getAttribute(ERROR_TEXT) == null) {
            session.setAttribute(ERROR_TEXT, "");
        }

        UserDto user = (UserDto) session.getAttribute(USER);
        Boolean requestedChange = (Boolean) session.getAttribute(REQUESTED_CHANGE);
        if (user == null && (requestedChange == null || !requestedChange)) {
            user = (UserDto) session.getAttribute(AUTHORIZED);
            if (user != null) {
                user.setPassword(null);
                user.setPasswordRepeat(null);
            }
            session.setAttribute(USER, user);
        }

        return "registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String post(
            HttpSession session,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String login,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) String passwordRepeat,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String comment,
            @RequestParam(required = false) String browser) {
        UserDto user = (UserDto) session.getAttribute(AUTHORIZED);

        boolean showRegistrationForm = true;
        boolean isError = false;
        String successText = "";
        StringBuilder errorText = new StringBuilder("<ul>");

        if (name != null || login != null || password != null || passwordRepeat != null ||
                gender != null || region != null || comment != null || browser != null)
        //Если хоть один параметр пришёл
        {
            session.setAttribute(REQUESTED_CHANGE, true);
            if (StaticUtils.isEmpty(name)) {
                isError = true;
                errorText.append("<li>Имя пусто!</li>");
            } else if (StaticUtils.isEmpty(login)) {
                isError = true;
                errorText.append("<li>Логин пуст!</li>");
            } else if (!StaticUtils.isEmailValid(login)) {
                isError = true;
                errorText.append("<li>Логин не верен!</li>");
            }
            if (StaticUtils.isEmpty(password)) {
                isError = true;
                errorText.append("<li>Пароль пуст!</li>");
            } else if (StaticUtils.isEmpty(passwordRepeat)) {
                isError = true;
                errorText.append("<li>Повтор пароля пуст!</li>");
            } else if (!password.equals(passwordRepeat)) {
                isError = true;
                errorText.append("<li>Введённые пароли не совпадают!</li>");
            } else {
                boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
                boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
                boolean hasDigit = password.chars().anyMatch(Character::isDigit);
                if (!(hasUpperCase && hasLowerCase && hasDigit) || password.length() < 8) {
                    isError = true;
                    errorText.append("<li>Пароли должны состоять не менее чем из 8 символов и должны " +
                            "содержать минимум по одной цифре, маленькой и большой латинской букве!</li>");
                }
            }
            if (StaticUtils.isEmpty(gender)) {
                isError = true;
                errorText.append("<li>Пол пуст!</li>");
            } else if (!("F".equals(gender) || "M".equals(gender))) {
                isError = true;
                errorText.append("<li>Пол не верен!</li>");
            }
            if (StaticUtils.isEmpty(region)) {
                isError = true;
                errorText.append("<li>Регион пуст!</li>");
            } else if (!("DNR".equals(region) || "LNR".equals(region) || "Crimea".equals(region))) {
                isError = true;
                errorText.append("<li>Регион не верен!</li>");
            }
            if (StaticUtils.isEmpty(comment)) {
                isError = true;
                errorText.append("<li>Комментарий пуст!</li>");
            }
            if (!(browser == null || "on".equals(browser))) {
                isError = true;
                errorText.append("<li>Поле browser не верно!</li>");
            }
            errorText.append("</ul>");

            UserDto userReg = new UserDto();
            userReg.setName(name);
            userReg.setLogin(login);
            userReg.setPassword(password);
            userReg.setPasswordRepeat(passwordRepeat);
            userReg.setGender(gender);
            userReg.setRegion(region);
            userReg.setComment(comment);
            userReg.setBrowser(browser);
            session.setAttribute(USER, userReg);

            if (!isError) {
                showRegistrationForm = false;
                if (user == null) {
                    userService.save(userReg);
                    session.setAttribute(USER, null);
                    successText = "Регистрация успешна.";
                } else {
                    Optional<UserDto> userExistsOpn = userService.get(userReg.getLogin());
                    if (userExistsOpn.isPresent() && !Objects.equals(userReg.getLogin(), user.getLogin())) {
                        showRegistrationForm = true;
                        isError = true;
                        errorText.append("<li>Такой логин уже занят!</li>");
                    } else {
                        userService.update(userReg, user.getLogin());
                        Optional<UserDto> userOpn = userService.get(userReg.getLogin());
                        if (userOpn.isPresent()) {
                            user = userOpn.get();
                            user.setPassword(null);
                            user.setPasswordRepeat(null);
                            session.setAttribute(AUTHORIZED, user);
                            session.setAttribute(USER, user);
                            showRegistrationForm = true;
                        }
                        successText = "Данные были успешно обновлены.";
                    }
                }
            }
        } else {
            session.setAttribute(REQUESTED_CHANGE, false);
        }

        session.setAttribute(SHOW_REGISTRATION_FORM, showRegistrationForm);
        session.setAttribute(IS_ERROR, isError);
        session.setAttribute(ERROR_TEXT, errorText);
        session.setAttribute(SUCCESS_TEXT, successText);

        return "registration";
    }
}
