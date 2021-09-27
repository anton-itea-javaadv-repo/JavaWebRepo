<%@ page import="ua.itea.Authorizator" %>
<a href="withauthorizator.jsp">Authorization</a> | <a href="form03.jsp">Registration</a>
<%
    if (request.getParameter("logout") != null) {
        session.setAttribute("authorized", null);
    }
    String loginFromSession = (String) session.getAttribute("authorized");
    boolean showForm = true;
    if (loginFromSession != null) {
        showForm = false;
    } else {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Authorizator auth = new Authorizator();
        String userName = auth.isAuthorized(login, password);
        if (userName != null) {
            showForm = false;
            session.setAttribute("authorized", userName);
            loginFromSession = userName;
            out.write("<center><font color='green'>Access granded</font>");
        }
    }
    if (showForm) {
%>
<center>
<table>
<form action="withauthorizator.jsp" method="post">
    <tr><td>Login:</td><td><input type="text" name="login"/></td></tr>
    <tr><td>Password:</td><td><input type="password" name="password"/></td></tr>
    <tr><td> </td><td><input type="submit" value="SEND"/></td></tr>
</form>
</table>
</center>
<%
    } else {
        out.write("Hello, " + loginFromSession + " <a href='?logout'>logout</a>");
    }
%>