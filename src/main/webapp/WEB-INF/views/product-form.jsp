<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html lang="uk">
<head>
  <jsp:include page="/WEB-INF/views/fragments/head.jsp"/>
</head>

<body>
<jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>

<div class="container mt-4">
  <h2>Форма товару</h2>

  <form:form method="post"
             modelAttribute="product"
             action="${pageContext.request.contextPath}/products/save">

    <div class="mb-3">
      <label class="form-label">Назва</label>
      <form:input path="name" cssClass="form-control"/>
    </div>

    <div class="mb-3">
      <label class="form-label">Опис</label>
      <form:textarea path="description" cssClass="form-control"/>
    </div>

    <div class="mb-3">
      <label class="form-label">Ціна</label>
      <form:input path="price" cssClass="form-control"/>
    </div>

    <div class="mb-3">
      <label class="form-label">Кількість</label>
      <form:input path="quantity" cssClass="form-control"/>
    </div>

    <button class="btn btn-primary" type="submit">Зберегти</button>
    <a class="btn btn-secondary" href="${pageContext.request.contextPath}/products">Скасувати</a>
  </form:form>
</div>

<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</body>
</html>
