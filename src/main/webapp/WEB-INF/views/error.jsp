<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="includes/navbar.jsp"/>
<%@ include file="/WEB-INF/views/fragments/head.jsp" %>

<html>
<head>
    <title>Помилка</title>
</head>
<body>
<h1 style="color:red;">Сталася помилка</h1>
<p>Код помилки: <b>${statusCode}</b></p>
<p>Спробуйте повернутись на <a href="/customers">головну сторінку</a>.</p>
</body>
</html>
