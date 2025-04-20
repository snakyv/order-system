<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="uk">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>
    <c:out value="${pageTitle != null ? pageTitle : 'Order Management'}"/>
  </title>

  <!-- Bootstrap CSS из WebJars -->
  <link
    rel="stylesheet"
    href="<c:url value='/webjars/bootstrap/5.3.2/css/bootstrap.min.css'/>"
  />

  <!-- Ваши стили -->
  <link
    rel="stylesheet"
    href="<c:url value='/resources/static/css/site.css'/>"
  />
</head>
<body>
  <!-- навигация -->
  <jsp:include page="/WEB-INF/views/includes/navbar.jsp"/>

  <!-- общий контейнер -->
  <div class="container mt-4">
  </div>
