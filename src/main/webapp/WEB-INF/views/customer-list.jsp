<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="includes/navbar.jsp"/>
<%@ include file="/WEB-INF/views/fragments/head.jsp" %>

<html>
<head>
    <title>Список клієнтів</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid #333;
            padding: 6px;
        }
        th {
            background-color: #f2f2f2;
        }
        a {
            margin: 0 4px;
        }
    </style>
</head>
<body>
<h2>Клієнти</h2>
<a href="/customers/new">Додати клієнта</a>
<table>
    <tr>
        <th>ID</th><th>Ім'я</th><th>Email</th><th>Телефон</th><th>Адреса</th><th>Дії</th>
    </tr>
    <c:forEach var="c" items="${customers}">
        <tr>
            <td>${c.id}</td>
            <td>${c.name}</td>
            <td>${c.email}</td>
            <td>${c.phone}</td>
            <td>${c.address}</td>
            <td>
                <a href="/customers/edit/${c.id}">Редагувати</a> |
                <a href="/customers/delete/${c.id}">Видалити</a>
            </td>
        </tr>
    </c:forEach>
</table>
</div>

<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</body>
</html>
