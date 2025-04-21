<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ─── meta / title ─────────────────────────────────────────────────────── -->
<meta charset="UTF-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>

<title>
    <c:out value="${pageTitle != null ? pageTitle : 'Order Management'}"/>
</title>

<!-- ─── CSS ──────────────────────────────────────────────────────────────── -->
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.2/css/bootstrap.min.css"/>
<link rel="stylesheet"
      href="${pageContext.request.contextPath}/css/site.css"/>
