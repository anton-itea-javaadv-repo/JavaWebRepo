<%@ page pageEncoding="UTF-8" %><%@include file="header.jsp"%>
<c:forEach var="product" items="${productMap}">
    <table>
        <tr><td>${product.key.name}</td><td></td></tr>
        <tr><td><a href="products?id=${product.key.id}"><img src="static/images/${product.key.id}.jpg" width="150" height="100%"/></a></td><td>${product.key.description}</td></tr>
        <tr><td>${product.key.price}</td><td><form action="cart" method="post">
            <input type="text" size="2" name="quantity" value="${product.value}"/>
            <input type="hidden" name="change" value="${product.key.id}"/>
            <input type="submit" value="Изменить"/></form></td></tr>
    </table><br/><br/>
</c:forEach>
<c:if test="${sessionScope.productsCartListSize == null || sessionScope.productsCartListSize <= 0}">
    В корзине пусто :(
</c:if>
<c:if test="${sessionScope.sumProductPrices != null && sessionScope.sumProductPrices > 0}">
    Общая сумма заказа: ${sessionScope.sumProductPrices}
</c:if>
<%@include file="footer.jsp"%>
