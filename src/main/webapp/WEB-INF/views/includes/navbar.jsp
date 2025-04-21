<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
  <div class="container">
    <a class="navbar-brand"
       href="${pageContext.request.contextPath}/">Order Management</a>

    <button class="navbar-toggler" type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarNav">
      <!-- лівий блок -->
      <ul class="navbar-nav me-auto">
        <li class="nav-item">
          <a class="nav-link"
             href="${pageContext.request.contextPath}/customers">Клієнти</a></li>
        <li class="nav-item">
          <a class="nav-link"
             href="${pageContext.request.contextPath}/products">Товари</a></li>
        <li class="nav-item">
          <a class="nav-link"
             href="${pageContext.request.contextPath}/orders">Замовлення</a></li>
        <li class="nav-item">
          <a class="nav-link"
             href="${pageContext.request.contextPath}/delivery-status">Статус доставки</a></li>
      </ul>

      <!-- правий блок -->
      <ul class="navbar-nav">
        <li class="nav-item">
          <spring:url value="/logout" var="logoutUrl"/>
          <a class="nav-link" href="${logoutUrl}">Logout</a>
        </li>
      </ul>
    </div>
  </div>
</nav>
