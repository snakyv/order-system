<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/views/fragments/head.jsp" %>

<h2>Форма товару</h2>

<form:form method="post" modelAttribute="product" action="${pageContext.request.contextPath}/products/save">
  <table class="table">
    <tr>
      <td>Назва:</td>
      <td><form:input path="name" cssClass="form-control"/></td>
    </tr>
    <tr>
      <td>Опис:</td>
      <td><form:input path="description" cssClass="form-control"/></td>
    </tr>
    <tr>
      <td>Ціна:</td>
      <td><form:input path="price" cssClass="form-control"/></td>
    </tr>
    <tr>
      <td>Кількість:</td>
      <td><form:input path="quantity" cssClass="form-control"/></td>
    </tr>
    <tr>
      <td colspan="2">
        <button type="submit" class="btn btn-primary">Зберегти</button>
      </td>
    </tr>
  </table>
</form:form>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
