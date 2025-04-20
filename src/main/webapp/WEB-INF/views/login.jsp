<%@ taglib prefix=" c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/fragments/head.jsp"/>

<div class="d-flex justify-content-center align-items-center vh-100">
  <div class="card p-4" style="width:300px">
    <h4 class="card-title text-center mb-3">Увійти</h4>
    <form method="post" action="<c:url value='/login'/>">
      <div class="mb-2">
        <input class="form-control" type="text" name="username" placeholder="Username" required/>
      </div>
      <div class="mb-3">
        <input class="form-control" type="password" name="password" placeholder="Password" required/>
      </div>
      <button class="btn btn-primary w-100" type="submit">Login</button>
    </form>
    <c:if test="${param.error != null}">
      <div class="text-danger mt-2">Невірний логін або пароль</div>
    </c:if>
    <c:if test="${param.logout != null}">
      <div class="text-success mt-2">Ви вийшли</div>
    </c:if>
  </div>
</div>

<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>