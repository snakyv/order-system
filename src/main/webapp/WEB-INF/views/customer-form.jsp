<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="/WEB-INF/views/fragments/head.jsp" %>

<h2>Форма клієнта</h2>

<form:form method="post" modelAttribute="customer" action="${pageContext.request.contextPath}/customers/save">
  <table class="table">
    <tr>
      <td>Ім'я:</td>
      <td><form:input path="name" cssClass="form-control"/></td>
    </tr>
    <tr>
      <td>Email:</td>
      <td><form:input path="email" cssClass="form-control"/></td>
    </tr>
    <tr>
      <td>Телефон:</td>
      <td><form:input path="phone" cssClass="form-control"/></td>
    </tr>
    <tr>
      <td>Адреса:</td>
      <td><form:input path="address" cssClass="form-control"/></td>
    </tr>
    <tr>
      <td colspan="2">
        <button type="submit" class="btn btn-primary">Зберегти</button>
      </td>
    </tr>
  </table>
</form:form>

<%@ include file="/WEB-INF/views/fragments/footer.jsp" %>
