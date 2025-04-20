<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="uk">
<head>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1"/>
  <title>
    <c:out value="${pageTitle != null ? pageTitle : 'Online Shop'}"/>
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
  <nav class="navbar navbar-expand-lg navbar-light bg-light mb-4">
    <div class="container-fluid">
      <a class="navbar-brand" href="<c:url value='/'/>">Online Shop</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navMenu">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navMenu">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <li class="nav-item"><a class="nav-link" href="<c:url value='/customers'/>">Customers</a></li>
          <li class="nav-item"><a class="nav-link" href="<c:url value='/products'/>">Products</a></li>
          <li class="nav-item"><a class="nav-link" href="<c:url value='/orders'/>">Orders</a></li>
          <li class="nav-item"><a class="nav-link" href="<c:url value='/delivery-status'/>">Delivery Status</a></li>
        </ul>
        <ul class="navbar-nav">
          <li class="nav-item">
            <spring:url value="/logout" var="logoutUrl"/>
            <a class="nav-link" href="${logoutUrl}">Logout</a>
          </li>
        </ul>
      </div>
    </div>
  </nav>
  <div class="container">
  </div>