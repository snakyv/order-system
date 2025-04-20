<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="includes/navbar.jsp"/>
<%@ include file="/WEB-INF/views/fragments/head.jsp" %>

<html>
<head><title>Статус доставки</title></head>
<body>
<h2>Статус доставки</h2>

<form action="<c:url value='/delivery-status/save'/>" method="post">
    <table>
        <!-- если редактирование, передаём id -->
        <c:if test="${not empty status}">
            <input type="hidden" name="id" value="${status.id}" />
        </c:if>

        <tr>
            <td>Замовлення:</td>
            <td>
                <select name="orderId">
                    <c:forEach var="o" items="${orders}">
                        <option value="${o.id}"
                                <c:if test="${status.order.id == o.id}">selected</c:if>
                        >#${o.id} — ${o.customer.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Статус:</td>
            <td>
                <input type="text"
                       name="status"
                       value="${status.status}" />
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button type="submit">Зберегти</button>
            </td>
        </tr>
    </table>
</form>
</body>
</html>
