<%@ page pageEncoding="UTF-8" %><%@include file="header.jsp"%>
<c:forEach items="${productList}" var="product">
<table>
<tr><td>${product.name}</td><td></td></tr>
<tr><td><a href="products?id=${product.id}"><img src="static/images/${product.id}.jpg" width="150" height="100%"/></a></td><td>${product.description}</td></tr>
<tr><td>${product.price}</td><td><form action="cart" method="post">
    <img class="plusminusinput" onclick="sum('a${product.id}', -1)" src="static/images/minus_sign.png" width="20" height="20"/><input id="a${product.id}" size="2" type="text" name="quantity" value="1"/><img class="plusminusinput" onclick="sum('a${product.id}', 1)" src="static/images/plus_sign.png" width="20" height="20"/><span width="6"></span>
    <input type="hidden" name="buy" value="${product.id}"/>
    <input onclick="sendBuyProduct('a${product.id}', '${product.id}')" type="button" value="Купить"/></form></td></tr>
</table><br/><br/>
</c:forEach>
<%@include file="footer.jsp"%>
