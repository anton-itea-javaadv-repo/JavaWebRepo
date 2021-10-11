package ua.itea.hw08.controller;

import ua.itea.hw08.dao.Dao;
import ua.itea.hw08.dao.MySqlUserDao;
import ua.itea.hw08.domain.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static ua.itea.hw08.controller.LoginController.*;
import static ua.itea.hw08.util.StaticUtils.*;

public class RegistrationController extends HttpServlet {
    public static final String SHOW_REGISTRATION_FORM = "showRegistrationForm";
    public static final String REQUESTED_CHANGE = "requestedChange";
    public static final String IS_ERROR = "isError";
    public static final String ERROR_TEXT = "errorText";
    public static final String SUCCESS_TEXT = "successText";

    public static final String USER = "user";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute(SHOW_REGISTRATION_FORM) == null) {
            session.setAttribute(SHOW_REGISTRATION_FORM, true);
        }
        if (session.getAttribute(IS_ERROR) == null) {
            session.setAttribute(IS_ERROR, false);
        }
        if (session.getAttribute(ERROR_TEXT) == null) {
            session.setAttribute(ERROR_TEXT, "");
        }

        User user = (User) session.getAttribute(USER);
        Boolean requestedChange = (Boolean) session.getAttribute(REQUESTED_CHANGE);
        if (user == null && (requestedChange == null || !requestedChange)) {
            user = (User) session.getAttribute(AUTHORIZED);
            if (user != null) {
                user.setPassword(null);
                user.setPasswordRepeat(null);
            }
            session.setAttribute(USER, user);
        }

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/registration.jsp");
        rd.forward(request, response);

        if (session.getAttribute(SUCCESS_TEXT) != null) {
            session.setAttribute(SUCCESS_TEXT, null);
            session.setAttribute(SHOW_REGISTRATION_FORM, true);
            session.setAttribute(IS_ERROR, false);
            session.setAttribute(ERROR_TEXT, "");
            session.setAttribute(USER, null);
            session.setAttribute(REQUESTED_CHANGE, null);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AUTHORIZED);

        boolean showRegistrationForm = true;
        boolean isError = false;
        String successText = "";
        StringBuilder errorText = new StringBuilder("<ul>");
        String name = request.getParameter("name");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String passwordRepeat = request.getParameter("passwordRepeat");
        String gender = request.getParameter("gender");
        String region = request.getParameter("region");
        String comment = request.getParameter("comment");
        String browser = request.getParameter("browser");
        if (name != null || login != null || password != null || passwordRepeat != null ||
                gender != null || region != null || comment != null || browser != null)
        //Если хоть один параметр пришёл
        {
            session.setAttribute(REQUESTED_CHANGE, true);
            if (isEmpty(name)) {
                isError = true;
                errorText.append("<li>Имя пусто!</li>");
            } else if (isEmpty(login)) {
                isError = true;
                errorText.append("<li>Логин пуст!</li>");
            } else if (!isEmailValid(login)) {
                isError = true;
                errorText.append("<li>Логин не верен!</li>");
            }
            if (isEmpty(password)) {
                isError = true;
                errorText.append("<li>Пароль пуст!</li>");
            } else if (isEmpty(passwordRepeat)) {
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
                    errorText.append("<li>Пароли должны состоять не менее чем из 8 символов и должны содержать минимум по одной цифре, маленькой и большой латинской букве!</li>");
                }
            }
            if (isEmpty(gender)) {
                isError = true;
                errorText.append("<li>Пол пуст!</li>");
            } else if (!("F".equals(gender) || "M".equals(gender))) {
                isError = true;
                errorText.append("<li>Пол не верен!</li>");
            }
            if (isEmpty(region)) {
                isError = true;
                errorText.append("<li>Регион пуст!</li>");
            } else if (!("DNR".equals(region) || "LNR".equals(region) || "Crimea".equals(region))) {
                isError = true;
                errorText.append("<li>Регион не верен!</li>");
            }
            if (isEmpty(comment)) {
                isError = true;
                errorText.append("<li>Комментарий пуст!</li>");
            }
            if (!(browser == null || "on".equals(browser))) {
                isError = true;
                errorText.append("<li>Поле browser не верно!</li>");
            }
            errorText.append("</ul>");

            User userReg = new User();
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
                Dao dao = new MySqlUserDao();
                if (user == null) {
                    dao.save(userReg);
                    session.setAttribute(USER, null);
                    successText = "Регистрация успешна.";
                } else {
                    Optional<User> userExistsOpn = dao.get(userReg.getLogin());
                    if (userExistsOpn.isPresent() && !Objects.equals(userReg.getLogin(), user.getLogin())) {
                        showRegistrationForm = true;
                        isError = true;
                        errorText.append("<li>Такой логин уже занят!</li>");
                    } else {
                        dao.update(userReg, user.getLogin());
                        Optional<User> userOpn = dao.get(userReg.getLogin());
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

        doGet(request, response);
    }
}
