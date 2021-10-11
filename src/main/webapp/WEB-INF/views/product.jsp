<%@ page pageEncoding="UTF-8" %><%@include file="header.jsp"%>
<c:forEach items="${productList}" var="product">
<table>
<tr><td>${product.name}</td><td></td></tr>
<tr><td><img src="static/images/${product.id}.jpg" width="150" height="100%"/></td><td>${product.description}</td></tr>
<tr><td>${product.price}</td><td>Buy</td></tr>
</table><br/><br/>
</c:forEach>
<%@include file="footer.jsp"%>
