<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="includes/navbar.jsp"/>
<%@ include file="/WEB-INF/views/fragments/head.jsp" %>

<html>
<head>
  <title>Список товарів</title>
</head>
<body>
<h2>Товари</h2>
<a href="/products/new">Додати товар</a>
<table border="1">
  <tr>
    <th>ID</th><th>Назва</th><th>Опис</th><th>Ціна</th><th>Кількість</th><th>Дії</th>
  </tr>
  <c:forEach var="p" items="${products}">
    <tr>
      <td>${p.id}</td>
      <td>${p.name}</td>
      <td>${p.description}</td>
      <td>${p.price}</td>
      <td>${p.quantity}</td>
      <td>
        <a href="/products/edit/${p.id}">Редагувати</a> |
        <a href="/products/delete/${p.id}">Видалити</a>
      </td>
    </tr>
  </c:forEach>
</table>
</div>

<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</body>
</html>
