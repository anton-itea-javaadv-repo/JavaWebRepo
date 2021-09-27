<%@ page import="ua.itea.Authorizator" %>
<%! private int countLogins = 0; %>
<%! private long timeOfLock = 0; %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta charset="UTF-8" />
    <link href="style.css" rel="stylesheet" />
</head>

<body>
    <a href="index.jsp">Authorization</a> | <a href="regform.jsp">Registration</a>
<%
    if (request.getParameter("logout") != null) {
        session.setAttribute("authorized", null);
    }
    String loginFromSession = (String) session.getAttribute("authorized");

    String login = request.getParameter("login");
    String password = request.getParameter("password");
    boolean showForm = true;
    boolean showAccessDenied = false;
    boolean showLocked = false;
    String userName = null;
    if (loginFromSession != null) {
        userName = loginFromSession;
        showForm = false;
    } else if (login != null) {
        Authorizator auth = new Authorizator();
        userName = auth.isAuthorized(login, password);
        if (userName != null) {
            showForm = false;
            session.setAttribute("authorized", userName);
        } else {
            countLogins++;
            if (countLogins == 3) {
                timeOfLock = System.currentTimeMillis();
                showForm = false;
            }
            showAccessDenied = true;
        }
    }
    long rez = 0;
    if (timeOfLock != 0) {
        showForm = false;
        rez = ((timeOfLock + 10000) - System.currentTimeMillis())/1000;
        if (rez > 0) {
            showLocked = true;
        } else {
            timeOfLock = 0;
            countLogins = 0;
            showForm = true;
        }
    }
    if (showForm) {
%>
<form id="loginForm" action="index.jsp" method="post">
    <div class="field">
        <label>Enter your login:</label>
        <div class="input"><input type="text" name="login" value="" id="login" /></div>
    </div>

    <div class="field">
        <a href="#" id="forgot">Forgot your password?</a>
        <label>Enter your password:</label>
        <div class="input"><input type="password" name="password" value="" id="password" /></div>
    </div>

    <div class="submit">
        <button type="submit">Enter</button>
        <label id="remember"><input name="" type="checkbox" value="" /> Remember me</label>
    </div>
</form>
<%
    } else if (!(showAccessDenied || showLocked)) {
        out.write(" | Hello, " + userName + " <a href='?logout'>logout</a>");
    }
    if (userName != null) {
        out.write("<center><font color=\"green\">Access granded.</text>");
    }

    if (showAccessDenied || showLocked) {
        out.write("<center>");
        if (showAccessDenied) {
            out.write("<font color=\"red\">Access denied.</text>");
        }
        if (showAccessDenied && showLocked) {
            out.write("<br>");
        }
        if (showLocked) {
            out.write("<font color=\"red\">You locked for " + rez + " seconds.</text>");
        }
        out.write("</center>");
    }
%>
</body>
</html>