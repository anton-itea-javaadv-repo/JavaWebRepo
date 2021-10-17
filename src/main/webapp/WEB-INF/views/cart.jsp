<%@ page pageEncoding="UTF-8" %><%@include file="header.jsp"%>
<c:forEach var="product" items="${productMap}">
    <div id="div${product.key.id}">
    <table>
        <tr><td>${product.key.name}</td><td></td></tr>
        <tr><td><a href="products?id=${product.key.id}"><img src="static/images/${product.key.id}.jpg" width="150" height="100%"/></a></td><td>${product.key.description}</td></tr>
        <tr><td>${product.key.price}</td><td><form action="cart" method="post">
            <img class="plusminusinput" onclick="sum('a${product.key.id}', -1)" src="static/images/minus_sign.png" width="20" height="20"/><input id="a${product.key.id}" size="2" type="text" name="quantity" value="${product.value}"/><img class="plusminusinput" onclick="sum('a${product.key.id}', 1)" src="static/images/plus_sign.png" width="20" height="20"/><span width="6"></span>
            <input type="hidden" name="change" value="${product.key.id}"/>
            <input onclick="sendChangeProduct('a${product.key.id}', '${product.key.id}', 'div${product.key.id}', false)" type="button" value="Изменить"/>
            <input onclick="sendChangeProduct('a${product.key.id}', '${product.key.id}', 'div${product.key.id}', true)" type="button" value="Удалить"/>
        </form></td></tr>
    </table><br/><br/>
    </div>
</c:forEach>
<c:if test="${sessionScope.productsCartListSize == null || sessionScope.productsCartListSize <= 0}">
    В корзине пусто :(
</c:if>
<c:if test="${sessionScope.sumProductPrices != null && sessionScope.sumProductPrices > 0}">
    Общая сумма заказа: <span id="totalsum">${sessionScope.sumProductPrices}</span>
</c:if>
<%@include file="footer.jsp"%>
