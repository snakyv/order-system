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
