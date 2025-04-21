<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="uk">
<head>
  <jsp:include page="/WEB-INF/views/fragments/head.jsp"/>
</head>

<body>
<jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>

<div class="container mt-4">
  <h2>Товари</h2>

  <a class="btn btn-success mb-3"
     href="${pageContext.request.contextPath}/products/new">Додати товар</a>

  <table class="table table-striped table-bordered align-middle">
    <thead class="table-light">
    <tr>
      <th>ID</th><th>Назва</th><th>Опис</th><th>Ціна</th><th>Кількість</th><th style="width:150px;">Дії</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="p" items="${products}">
      <tr>
        <td>${p.id}</td>
        <td>${p.name}</td>
        <td>${p.description}</td>
        <td>${p.price}</td>
        <td>${p.quantity}</td>
        <td>
          <a class="btn btn-sm btn-primary"
             href="${pageContext.request.contextPath}/products/edit/${p.id}">Редагувати</a>
          <a class="btn btn-sm btn-danger"
             href="${pageContext.request.contextPath}/products/delete/${p.id}"
             onclick="return confirm('Видалити товар?');">Видалити</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</body>
</html>
