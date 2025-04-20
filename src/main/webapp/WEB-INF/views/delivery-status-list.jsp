<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="includes/navbar.jsp"/>
<%@ include file="/WEB-INF/views/fragments/head.jsp" %>

<html>
<head><title>Список статусів доставки</title></head>
<body>
<h2>Статуси доставки</h2>

<p>
    <a href="<c:url value='/delivery-status/new'/>">
        Додати статус доставки
    </a>
</p>

<table border="1">
    <tr>
        <th>ID</th><th>Замовлення</th><th>Статус</th><th>Дії</th>
    </tr>
    <c:forEach var="s" items="${statuses}">
        <tr>
            <td>${s.id}</td>
            <td>${s.order.id} — ${s.order.customer.name}</td>
            <td>${s.status}</td>
            <td>
                <a href="<c:url value='/delivery-status/edit/${s.id}'/>">
                    Редагувати
                </a>
            </td>
        </tr>
    </c:forEach>
</table>
</div>

<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</body>
</html>
