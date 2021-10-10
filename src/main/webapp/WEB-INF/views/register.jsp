<%@ page pageEncoding="UTF-8" %><%@ page isELIgnored ="false" %><%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta charset="UTF-8" />
    <link href="static/css/style.css" rel="stylesheet" />
</head>

<body>
    <a href="login">Authorization</a> | <a href="register">Registration</a>
    <c:if test="${userName != null}">
        | Hello, ${userName} <a href='?logout'>logout</a>
    </c:if>
    <c:if test="${successText != null}">
        <center><font color="green">${successText}</font></center>
    </c:if>
    <c:if test="${showRegistrationForm}">
    <center>
        <table>
            <tr>
                <td>
                    <table>
                        <form action="" method="post">
                            <tr><td>Name:</td><td><input name="name" value="${user!=null && user.getName()!=null?user.getName():""}"/></td></tr>
                            <tr><td>Email:</td><td><input type="email" name="login" value="${user!=null && user.getLogin()!=null?user.getLogin():""}"/></td></tr>
                            <tr><td>Password:</td><td><input type="password" name="password" value="${user!=null && user.getPassword()!=null?user.getPassword():""}"/></td></tr>
                            <tr><td>Repeat password:</td><td><input type="password" name="passwordRepeat" value="${user!=null && user.getPasswordRepeat()!=null?user.getPasswordRepeat():""}"/></td></tr>
                            <tr><td>Gender:</td><td>M<input type="radio" name="gender" value="M"${user!=null && "M".equals(user.getGender())?" checked='true'":""}/>F<input type="radio" name="gender" value="F"${user!=null && "F".equals(user.getGender())?" checked='true'":""}/></td></tr>
                            <tr><td>Region:</td><td><select name="region">
                                <option value="DNR"${user!=null && "DNR".equals(user.getRegion())?" selected='true'":""}>ДНР</option>
                                <option value="LNR"${user!=null && "LNR".equals(user.getRegion())?" selected='true'":""}>ЛНР</option>
                                <option value="Crimea"${user!=null && "Crimea".equals(user.getRegion())?" selected='true'":""}>Крым</option>
                            </select></td></tr>
                            <tr><td>Comment:</td><td><textarea rows=6 cols=22 name="comment">${user!=null && user.getComment()!=null?user.getComment():""}</textarea></td></tr>
                            <tr><td>I agree to install<br/>an Amigo Browser:</td><td><input type="checkbox" name="browser" checked="true"/></td></tr>
                            <tr><td> </td><td><input type="submit" value="SEND"/></td></tr>
                        </form>
                    </table>
                </td>
                <c:if test="${isError}">
                    <td><font color="red">${errorText.toString()}</font></td>
                </c:if>
            </tr>
        </table>
    </center>
    </c:if>
</body>
</html>
