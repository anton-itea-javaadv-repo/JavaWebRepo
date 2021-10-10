<%@ page pageEncoding="UTF-8" %><%@ page isELIgnored ="false" %><%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %><!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta charset="UTF-8" />
    <link href="static/css/style.css" rel="stylesheet" />
</head>

<body>
    <a href="login">Authorization</a> | <a href="register">Registration</a>
<c:choose>
    <c:when test="${showLoginForm}">
    <form id="loginForm" action="login" method="post">
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
    </c:when>
    <c:when test="${!(showAccessDenied || showLocked)}"> | Hello, ${userName} <a href='?logout'>logout</a></c:when>
</c:choose>
    <c:if test="${userName != null}"><center><font color="green">Access granted.</font></c:if>
    <c:if test="${showAccessDenied || showLocked}">
    <center>
        <c:if test="${showAccessDenied}"><font color="red">Access denied.</font></c:if>
        <c:if test="${showAccessDenied && showLocked}"><br/></c:if>
        <c:if test="${showLocked}"><font color="red">You locked for ${lockedSeconds} seconds.</font></c:if>
    </center>
    </c:if>
</body>
</html>