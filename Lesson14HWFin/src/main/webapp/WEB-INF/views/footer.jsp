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
                    Товаров в вашей корзине: <span id="cartSize">${(sessionScope.productsCartListSize==null?"0":""+sessionScope.productsCartListSize)}</span>
                    </font>
                    </td>
                    </tr>
                    </table>
                    <h2>Боковое меню</h2>
                    <ul>
                        <li><a href="products?category=net">Сетевухи</a></li>
                        <li><a href="products?category=vga">Видухи</a></li>
                        <li><a href="products?category=cpu">Процы</a></li>
                        <li><a href="registration">Регистрация</a></li>
                        <li><a href="login">Вход</a></li>
                        <li><a href="cart">Корзина</a></li>
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
<script type="text/javascript">
    function sum(id, data) {
        var val1 = document.getElementById(id).value;
        if ((+val1 + +data) > 0) {
            document.getElementById(id).value = +val1 + +data;
        }
    }
    function say(id, prodname) {
        var val1 = document.getElementById(id).value;
        console.log("Купили " + prodname + " в количестве " + +val1 + " штуки");
    }
    function sendBuyProduct(id, prodid) {
        var val1 = document.getElementById(id).value;
        $.ajax({
            url: 'cart',         /* Куда пойдет запрос */
            method: 'post',             /* Метод передачи (post или get) */
            data: "buy="+prodid+"&quantity="+ +val1,     /* Параметры передаваемые в запросе. */
            success: function(data){   /* функция которая будет выполнена после успешного запроса.  */
                document.getElementById("cartSize").innerHTML=data;            /* В переменной data содержится ответ от index.php. */
            }
        })
    }
    function sendChangeProduct(id, prodid, divid, remove) {
        var val1 = document.getElementById(id).value;
        var quantity = +val1;
        if (remove) {
            quantity = 0;
        }
        $.ajax({
            url: 'cart',         /* Куда пойдет запрос */
            method: 'post',             /* Метод передачи (post или get) */
            data: "change="+prodid+"&quantity="+quantity,     /* Параметры передаваемые в запросе. */
            success: function(data){   /* функция которая будет выполнена после успешного запроса.  */
                var dataObj = JSON.parse(data);
                document.getElementById("cartSize").innerHTML=dataObj[0];
                document.getElementById("totalsum").innerHTML=dataObj[1];
                if (dataObj[2]) {
                    document.getElementById(divid).innerHTML="";
                }
            }
        })
    }
</script>
</html>
