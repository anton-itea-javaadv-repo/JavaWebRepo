<%@ page pageEncoding="UTF-8" %><%@include file="header.jsp"%>
<c:if test="${sessionScope.showRegistrationForm}">
    <table>
        <tr>
            <td>
                <table>
                    <form action="" method="post">
                        <tr><td>Имя:</td><td><input name="name" value="${sessionScope.user!=null && sessionScope.user.name!=null?sessionScope.user.name:""}"/></td></tr>
                        <tr><td>Email:</td><td><input type="email" name="login" value="${sessionScope.user!=null && sessionScope.user.login!=null?sessionScope.user.login:""}"/></td></tr>
                        <tr><td>Пароль:</td><td><input type="password" name="password" value="${sessionScope.user!=null && sessionScope.user.password!=null?sessionScope.user.password:""}"/></td></tr>
                        <tr><td>Повтор пароля:</td><td><input type="password" name="passwordRepeat" value="${sessionScope.user!=null && sessionScope.user.passwordRepeat!=null?sessionScope.user.passwordRepeat:""}"/></td></tr>
                        <tr><td>Пол:</td><td>М<input type="radio" name="gender" value="M"${sessionScope.user!=null && "M".equals(sessionScope.user.gender)?" checked='true'":""}/>Ж<input type="radio" name="gender" value="F"${sessionScope.user!=null && "F".equals(sessionScope.user.gender)?" checked='true'":""}/></td></tr>
                        <tr><td>Регион:</td><td><select name="region">
                            <option value="DNR"${sessionScope.user!=null && "DNR".equals(sessionScope.user.region)?" selected='true'":""}>ДНР</option>
                            <option value="LNR"${sessionScope.user!=null && "LNR".equals(sessionScope.user.region)?" selected='true'":""}>ЛНР</option>
                            <option value="Crimea"${sessionScope.user!=null && "Crimea".equals(sessionScope.user.region)?" selected='true'":""}>Крым</option>
                        </select></td></tr>
                        <tr><td>Комментарий:</td><td><textarea rows=6 cols=22 name="comment">${sessionScope.user!=null && sessionScope.user.comment!=null?sessionScope.user.comment:""}</textarea></td></tr>
                        <tr><td>Я согласен на<br/>установку<br/>Amigo Browser:</td><td><input type="checkbox" name="browser" checked="true"/></td></tr>
                        <tr><td> </td><td><input type="submit" value="ОТПРАВИТЬ"/></td></tr>
                    </form>
                </table>
            </td>
            <c:if test="${sessionScope.isError}">
                <td><font color="red">${sessionScope.errorText.toString()}</font></td>
            </c:if>
        </tr>
    </table>
</c:if>
<c:if test="${sessionScope.successText != null}">
    <font color="green">${sessionScope.successText}</font>
</c:if>
<%@include file="footer.jsp"%>