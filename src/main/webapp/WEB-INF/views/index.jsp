<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="includes/navbar.jsp"/>
<%@ include file="/WEB-INF/views/fragments/head.jsp" %>

<html>
<head><title>Головна</title></head>
<body>
<h2>Ласкаво просимо до Order Management</h2>
<ul>
  <li><a href="<c:url value='/customers'/>">Клієнти</a></li>
  <li><a href="<c:url value='/products'/>">Товари</a></li>
  <li><a href="<c:url value='/orders'/>">Замовлення</a></li>
  <li><a href="<c:url value='/delivery-status'/>">Статус доставки</a></li>
</ul>
</body>
</html>
