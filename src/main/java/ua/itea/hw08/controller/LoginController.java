package ua.itea.hw08.controller;

import ua.itea.hw08.dao.Dao;
import ua.itea.hw08.dao.MySqlUserDao;
import ua.itea.hw08.domain.User;
import ua.itea.hw08.service.Authorizator;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Optional;

import static ua.itea.hw08.util.StaticUtils.checkLogout;

public class LoginController extends HttpServlet {
    public static final String COUNT_LOGINS = "countLogins";
    public static final String TIME_OF_LOCK = "timeOfLock";
    public static final String AUTHORIZED = "authorized";
    public static final String SHOW_LOGIN_FORM = "showLoginForm";
    public static final String SHOW_ACCESS_DENIED = "showAccessDenied";
    public static final String SHOW_LOCKED = "showLocked";
    public static final String LOCKED_SECONDS = "lockedSeconds";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkLogout(request);
        HttpSession session = request.getSession();
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

        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/views/login.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        checkLogout(request);
        HttpSession session = request.getSession();

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

        User user = (User) session.getAttribute(AUTHORIZED);

        String login = request.getParameter("login");
        String password = request.getParameter("password");
        boolean showLoginForm = true;
        boolean showAccessDenied = false;
        boolean showLocked = false;
        String userName = null;
        if (user != null) {
            showLoginForm = false;
        } else if (login != null) {
            Authorizator auth = new Authorizator();
            userName = auth.isAuthorized(login, password);
            if (userName != null) {
                showLoginForm = false;
                Dao dao = new MySqlUserDao();
                Optional<User> userOpn = dao.get(login);
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
        doGet(request, response);
    }
}
