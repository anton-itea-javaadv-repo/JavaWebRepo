<%@ page import="ua.itea.dao.MySqlUserDao" %>
<%@ page import="java.util.Optional" %>
<%@ page import="java.util.Objects" %>
<%@ page import="ua.itea.domain.User" %>
<%@ page import="static ua.itea.util.StaticUtils.isEmpty" %>
<%@ page import="static ua.itea.util.StaticUtils.isEmailValid" %>
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
    User user = (User) session.getAttribute("authorized");

    boolean showForm = true;
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
            showForm = false;
            MySqlUserDao dao = new MySqlUserDao();
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
                    showForm = true;
                    isError = true;
                    errorText.append("<li>This login is already taken!</li>");
                } else {
                    dao.update(userReg, user.getLogin());
                    Optional<User> userOpn = dao.get(userReg.getLogin());
                    if (userOpn.isPresent()) {
                        user = userOpn.get();
                        session.setAttribute("authorized", user);
                    }
                    successText = "Data has been updated successfully.";
                }
            }
        }
    } else if (user != null) {
        name = user.getName();
        login = user.getLogin();
        gender = user.getGender();
        region = user.getRegion();
        comment = user.getComment();
    }

    if (user != null) {
        out.write(" | Hello, " + user.getName() + " <a href='?logout'>logout</a>");
    }

    if (successText != null) {
        out.write("<center><font color=\"green\">" + successText + "</font></center>");
    }

    if (showForm) {
%>
<center>
    <table>
        <tr>
            <td>
                <table>
                    <form action="" method="post">
                        <tr><td>Name:</td><td><input name="name" value="<%=(name!=null)?name:""%>"/></td></tr>
                        <tr><td>Email:</td><td><input type="email" name="login" value="<%=(login!=null)?login:""%>"/></td></tr>
                        <tr><td>Password:</td><td><input type="password" name="password" value="<%=(password!=null)?password:""%>"/></td></tr>
                        <tr><td>Repeat password:</td><td><input type="password" name="passwordRepeat" value="<%=(passwordRepeat!=null)?passwordRepeat:""%>"/></td></tr>
                        <tr><td>Gender:</td><td>M<input type="radio" name="gender" value="M"<%=("M".equals(gender))?" checked='true'":""%>/>F<input type="radio" name="gender" value="F"<%=("F".equals(gender))?" checked='true'":""%>/></td></tr>
                        <tr><td>Region:</td><td><select name="region">
                            <option value="DNR"<%=("DNR".equals(region))?" selected='true'":""%>>ДНР</option>
                            <option value="LNR"<%=("LNR".equals(region))?" selected='true'":""%>>ЛНР</option>
                            <option value="Crimea"<%=("Crimea".equals(region))?" selected='true'":""%>>Крым</option>
                        </select></td></tr>
                        <tr><td>Comment:</td><td><textarea rows=6 cols=22 name="comment"/><%=(comment!=null)?comment:""%></textarea></td></tr>
                        <tr><td>I agree to install<br/>an Amigo Browser:</td><td><input type="checkbox" name="browser" checked="true"/></td></tr>
                        <tr><td> </td><td><input type="submit" value="SEND"/></td></tr>
                    </form>
                </table>
            </td>
<%
        if (isError) {
            out.write("<td><font color=\"red\">" + errorText.toString() + "</td>");
        }
%>
        </tr>
    </table>
</center>
<%
    }
%>
</body>
</html>
