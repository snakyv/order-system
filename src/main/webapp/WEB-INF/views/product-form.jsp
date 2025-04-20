<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="includes/navbar.jsp"/>
<%@ include file="/WEB-INF/views/fragments/head.jsp" %>

<html>
<head><title>Форма товару</title></head>
<body>
<h2>Товар</h2>
<form:form method="post" modelAttribute="product" action="/products/save">
    <table>
        <tr><td>Назва:</td><td><form:input path="name"/></td></tr>
        <tr><td>Опис:</td><td><form:input path="description"/></td></tr>
        <tr><td>Ціна:</td><td><form:input path="price"/></td></tr>
        <tr><td>Кількість:</td><td><form:input path="quantity"/></td></tr>
        <tr><td colspan="2"><input type="submit" value="Зберегти"/></td></tr>
    </table>
</form:form>
</body>
</html>
