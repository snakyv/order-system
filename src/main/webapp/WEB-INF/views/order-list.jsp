<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/fragments/head.jsp"/>
<jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>

<body>
<div class="container mt-4">
  <h2>Замовлення</h2>

  <!-- Фільтрація -->
  <form action="<c:url value='/orders'/>" method="get" class="row g-2 mb-3">
    <!-- ... ваши поля фильтра ... -->
  </form>

  <!-- Кнопки действия -->
  <div class="mb-3">
    <a href="<c:url value='/orders/new'/>" class="btn btn-success">Створити замовлення</a>
    <a href="<c:url value='/orders/export'/>" class="btn btn-info">Скачати Excel‑звіт</a>
    <a href="<c:url value='/orders/report/pdf'/>" class="btn btn-danger">Скачати PDF‑звіт</a>
  </div>

  <!-- Таблица заказов -->
  <table class="table table-striped table-bordered">
    <thead class="table-light">
    <tr>
      <th>ID</th><th>Клієнт</th><th>Дата</th><th>Статус</th>
      <th>Товари</th><th>Сума</th><th>Дії</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="o" items="${orders}">
      <tr>
        <td>${o.id}</td>
        <td>${o.customer.name}</td>
        <td>${o.orderDate}</td>
        <td>${o.status}</td>
        <td>
          <ul class="list-unstyled mb-0">
            <c:forEach var="item" items="${o.orderItems}">
              <li>${item.product.name} — ${item.quantity} шт. (${item.subtotal})</li>
            </c:forEach>
          </ul>
        </td>
        <td>${o.total}</td>
        <td>
          <a href="<c:url value='/orders/edit/${o.id}'/>" class="btn btn-sm btn-warning">Редагувати</a>
          <a href="<c:url value='/orders/delete/${o.id}'/>" class="btn btn-sm btn-danger">Видалити</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</body>
