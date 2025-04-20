<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="includes/navbar.jsp"/>
<%@ include file="/WEB-INF/views/fragments/head.jsp" %>

<html>
<head><title>Форма замовлення</title></head>
<body>
<h2>Замовлення</h2>
<form action="/orders/save" method="post">
  <table>
    <tr>
      <td>Клієнт:</td>
      <td>
        <select name="customerId">
          <c:forEach var="customer" items="${customers}">
            <option value="${customer.id}">${customer.name}</option>
          </c:forEach>
        </select>
      </td>
    </tr>
    <tr>
      <td>Статус:</td>
      <td><input type="text" name="status"/></td>
    </tr>
  </table>

  <h3>Товари:</h3>
  <table>
    <tr><th>✔</th><th>Назва</th><th>Ціна</th><th>Кількість</th></tr>
    <c:forEach var="product" items="${products}">
      <tr>
        <td><input type="checkbox" name="productIds" value="${product.id}"/></td>
        <td>${product.name}</td>
        <td>${product.price}</td>
        <td><input type="number" name="quantities" value="1" min="1"/></td>
      </tr>
    </c:forEach>
  </table>

  <br/>
  <input type="submit" value="Створити замовлення"/>
</form>
</body>
</html>
