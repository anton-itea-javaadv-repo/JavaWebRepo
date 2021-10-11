<%@ page pageEncoding="UTF-8" %>
                </div>
                <div id="sidebar">
                    <table border=1>
                    <tr>
                    <td width="252" align="left">
                    <font color=white><c:choose>
    <c:when test="${sessionScope.authorized != null}">Вы авторизировались как ${sessionScope.authorized.name} <a href="login?logout">Выйти</a></c:when><c:otherwise>
    Вы не авторизованы
</c:otherwise>
</c:choose><br />
                    В вашей корзине 0 товаров.
                    </font>
                    </td>
                    </tr>
                    </table>
                    <h2>Боковое меню</h2>
                    <ul>
                        <li><a href="products.php">Категория 1</a></li>
                        <li><a href="products.php">Категория 2</a></li>
                        <li><a href="products.php">Категория 3</a></li>
                        <li><a href="registration">Регистрация</a></li>
                        <li><a href="login">Вход</a></li>
                        <li><a href="cart.php">Корзина</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="footer">
    <p>Copyright (c) by Бондаренко Антон</p>
</div>
<!-- end #footer -->
</body>
</html>
