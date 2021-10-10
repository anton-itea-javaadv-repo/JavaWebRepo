package ua.itea.controller;

import ua.itea.dao.Dao;
import ua.itea.dao.MySqlUserDao;
import ua.itea.domain.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

import static ua.itea.controller.LoginServlet.*;
import static ua.itea.util.StaticUtils.*;

public class RegisterServlet extends HttpServlet {
    public static final String SHOW_REGISTRATION_FORM = "showRegistrationForm";
    public static final String REQUESTED_CHANGE = "requestedChange";
    public static final String IS_ERROR = "isError";
    public static final String ERROR_TEXT = "errorText";
    public static final String SUCCESS_TEXT = "successText";

    public static final String USER = "user";


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkLogout(request);
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

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/register.jsp");
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
        checkLogout(request);
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
                errorText.append("<li>Name is empty!</li>");
            } else if (isEmpty(login)) {
                isError = true;
                errorText.append("<li>Login is empty!</li>");
            } else if (!isEmailValid(login)) {
                isError = true;
                errorText.append("<li>Login is invalid!</li>");
            }
            if (isEmpty(password)) {
                isError = true;
                errorText.append("<li>Password is empty!</li>");
            } else if (isEmpty(passwordRepeat)) {
                isError = true;
                errorText.append("<li>Password repeat is empty!</li>");
            } else if (!password.equals(passwordRepeat)) {
                isError = true;
                errorText.append("<li>Passwords entered does not match!</li>");
            } else {
                boolean hasUpperCase = password.chars().anyMatch(Character::isUpperCase);
                boolean hasLowerCase = password.chars().anyMatch(Character::isLowerCase);
                boolean hasDigit = password.chars().anyMatch(Character::isDigit);
                if (!(hasUpperCase && hasLowerCase && hasDigit) || password.length() < 8) {
                    isError = true;
                    errorText.append("<li>Passwords should contain uppercase, lowercase letter and a digit<br/>and should contain 8 symbols or more!</li>");
                }
            }
            if (isEmpty(gender)) {
                isError = true;
                errorText.append("<li>Gender is empty!</li>");
            } else if (!("F".equals(gender) || "M".equals(gender))) {
                isError = true;
                errorText.append("<li>Gender is invalid!</li>");
            }
            if (isEmpty(region)) {
                isError = true;
                errorText.append("<li>Region is empty!</li>");
            } else if (!("DNR".equals(region) || "LNR".equals(region) || "Crimea".equals(region))) {
                isError = true;
                errorText.append("<li>Region is invalid!</li>");
            }
            if (isEmpty(comment)) {
                isError = true;
                errorText.append("<li>Comment is empty!</li>");
            }
            if (!(browser == null || "on".equals(browser))) {
                isError = true;
                errorText.append("<li>Browser is invalid!</li>");
            }
            errorText.append("</ul>");
            if (!isError) {
                showRegistrationForm = false;
                Dao dao = new MySqlUserDao();
                User userReg = new User();
                userReg.setName(name);
                userReg.setLogin(login);
                userReg.setPassword(password);
                userReg.setGender(gender);
                userReg.setRegion(region);
                userReg.setComment(comment);
                if (user == null) {
                    dao.save(userReg);
                    successText = "Registration succeeded.";
                } else {
                    Optional<User> userExistsOpn = dao.get(userReg.getLogin());
                    if (userExistsOpn.isPresent() && !Objects.equals(userReg.getLogin(), user.getLogin())) {
                        showRegistrationForm = true;
                        isError = true;
                        errorText.append("<li>This login is already taken!</li>");
                    } else {
                        dao.update(userReg, user.getLogin());
                        Optional<User> userOpn = dao.get(userReg.getLogin());
                        if (userOpn.isPresent()) {
                            user = userOpn.get();
                            session.setAttribute(AUTHORIZED, user);
                        }
                        successText = "Data has been updated successfully.";
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

        User userToShow = new User();
        userToShow.setName(name);
        userToShow.setLogin(login);
        userToShow.setPassword(password);
        userToShow.setPasswordRepeat(passwordRepeat);
        userToShow.setGender(gender);
        userToShow.setRegion(region);
        userToShow.setComment(comment);
        userToShow.setBrowser(browser);

        session.setAttribute(USER, userToShow);
        doGet(request, response);
    }
}
