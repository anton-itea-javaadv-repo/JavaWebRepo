<%@ page pageEncoding="UTF-8" %><%@include file="header.jsp"%>
<c:if test="${sessionScope.authorized != null}">
    <font color="green">Доступ предоставлен.</font>
</c:if>
<c:if test="${sessionScope.showLoginForm}">
    <table>
        <form action="login" method="post">
            <tr><td>Логин:</td><td><input type="text" name="login"/></td></tr>
            <tr><td>Пароль:</td><td><input type="password" name="password"/></td></tr>
            <tr><td> </td><td><input type="submit" value="ВХОД"/></td></tr>
        </form>
    </table>
</c:if>
<c:if test="${sessionScope.showAccessDenied || sessionScope.showLocked}">
    <c:if test="${sessionScope.showAccessDenied}"><font color="red">Доступ запрещён.</font></c:if>
    <c:if test="${sessionScope.showAccessDenied && showLocked}"><br/></c:if>
    <c:if test="${sessionScope.showLocked}"><font color="red">Вы заблокированы на ${sessionScope.lockedSeconds} секунд.</font></c:if>
</c:if>
<%@include file="footer.jsp"%>
