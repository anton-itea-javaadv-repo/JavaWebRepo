package ua.itea.web.hw14.lesson14hw.controller.impl;

import org.springframework.stereotype.Controller;
import ua.itea.web.hw14.lesson14hw.controller.LoginController;
import ua.itea.web.hw14.lesson14hw.dto.UserDto;
import ua.itea.web.hw14.lesson14hw.service.Authorizer;
import ua.itea.web.hw14.lesson14hw.service.UserService;
import ua.itea.web.hw14.lesson14hw.util.StaticUtils;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class LoginControllerImpl implements LoginController {
    public static final String COUNT_LOGINS = "countLogins";
    public static final String TIME_OF_LOCK = "timeOfLock";
    public static final String AUTHORIZED = "authorized";
    public static final String SHOW_LOGIN_FORM = "showLoginForm";
    public static final String SHOW_ACCESS_DENIED = "showAccessDenied";
    public static final String SHOW_LOCKED = "showLocked";
    public static final String LOCKED_SECONDS = "lockedSeconds";

    private final UserService userService;
    private final Authorizer auth;

    public LoginControllerImpl(UserService userService, Authorizer auth) {
        this.userService = userService;
        this.auth = auth;
    }

    @Override
    public String get(HttpSession session, String logout) {
        if (StaticUtils.checkLogout(session, logout)) {
            // Обход ошибки инвалидации сессии за счёт редиректа на login.
            return "redirect:login";
        }

        if (session.getAttribute(COUNT_LOGINS) == null) {
            session.setAttribute(COUNT_LOGINS, 0);
        }
        if (session.getAttribute(SHOW_LOGIN_FORM) == null) {
            session.setAttribute(SHOW_LOGIN_FORM, true);
        }
        if (session.getAttribute(SHOW_ACCESS_DENIED) == null) {
            session.setAttribute(SHOW_ACCESS_DENIED, false);
        }
        if (session.getAttribute(SHOW_LOCKED) == null) {
            session.setAttribute(SHOW_LOCKED, false);
        }
        if (session.getAttribute(LOCKED_SECONDS) == null) {
            session.setAttribute(LOCKED_SECONDS, 0L);;
        }

        Long timeOfLock = (Long) session.getAttribute(TIME_OF_LOCK);
        if (timeOfLock == null) {
            session.setAttribute(TIME_OF_LOCK, 0L);
        } else {
            long lockedSeconds;
            if (timeOfLock != 0) {
                session.setAttribute(SHOW_LOGIN_FORM, false);
                lockedSeconds = ((timeOfLock + 10000) - System.currentTimeMillis()) / 1000;
                if (lockedSeconds > 0) {
                    session.setAttribute(SHOW_LOCKED, true);
                    session.setAttribute(LOCKED_SECONDS, lockedSeconds);
                } else {
                    session.setAttribute(TIME_OF_LOCK, 0L);
                    session.setAttribute(COUNT_LOGINS, 0);
                    session.setAttribute(SHOW_LOGIN_FORM, true);
                    session.setAttribute(SHOW_ACCESS_DENIED, false);
                    session.setAttribute(SHOW_LOCKED, false);
                }
            }
        }

        return "login";
    }

    @Override
    public String post(HttpSession session, String logout, String login, String password) {
        if (StaticUtils.checkLogout(session, logout)) {
            // Обход ошибки инвалидации сессии за счёт редиректа на login.
            return "redirect:login";
        }

        Integer countLogins;
        Long timeOfLock;
        countLogins = (Integer) session.getAttribute(COUNT_LOGINS);
        timeOfLock = (Long) session.getAttribute(TIME_OF_LOCK);
        if (countLogins == null) {
            countLogins = 0;
        }
        if (timeOfLock == null) {
            timeOfLock = 0L;
        }

        UserDto user = (UserDto) session.getAttribute(AUTHORIZED);

        boolean showLoginForm = true;
        boolean showAccessDenied = false;
        boolean showLocked = false;
        String userName = null;
        if (user != null) {
            showLoginForm = false;
        } else if (login != null) {
            userName = auth.isAuthorized(login, password);
            if (userName != null) {
                showLoginForm = false;
                Optional<UserDto> userOpn = userService.get(login);
                if (userOpn.isPresent()) {
                    user = userOpn.get();
                    session.setAttribute(AUTHORIZED, user);
                }
            } else {
                countLogins++;
                if (countLogins == 3) {
                    timeOfLock = System.currentTimeMillis();
                    showLoginForm = false;
                }
                showAccessDenied = true;
            }
        }
        session.setAttribute(COUNT_LOGINS, countLogins);
        session.setAttribute(TIME_OF_LOCK, timeOfLock);
        session.setAttribute(SHOW_LOGIN_FORM, showLoginForm);
        session.setAttribute(SHOW_ACCESS_DENIED, showAccessDenied);
        session.setAttribute(SHOW_LOCKED, showLocked);

        return "login";
    }
}
