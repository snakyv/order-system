<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="includes/navbar.jsp"/>
<%@ include file="/WEB-INF/views/fragments/head.jsp" %>

<html>
<head>
    <title>Форма клієнта</title>
    <style>
        table { border-collapse: collapse; }
        td { padding: 6px; }
    </style>
</head>
<body>
<h2>Клієнт</h2>
<form:form method="post" modelAttribute="customer" action="/customers/save">
    <table>
        <tr>
            <td>Ім'я:</td>
            <td><form:input path="name"/></td>
        </tr>
        <tr>
            <td>Email:</td>
            <td><form:input path="email"/></td>
        </tr>
        <tr>
            <td>Телефон:</td>
            <td><form:input path="phone"/></td>
        </tr>
        <tr>
            <td>Адреса:</td>
            <td><form:input path="address"/></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Зберегти"/></td>
        </tr>
    </table>
</form:form>
</body>
</html>
