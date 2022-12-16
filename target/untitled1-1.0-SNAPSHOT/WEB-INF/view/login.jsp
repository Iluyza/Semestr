<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/styles/styles.css" rel="stylesheet">
</head>
<body>
<form method="post">
    <div class="field">
        <label>
            Nickname<br>
            <input type="text" name="userName"/>
        </label>
    </div>
    <div class="field">
        <label>
            Password<br>
            <input type="password" name="userPassword"/>
        </label>
    </div>
    <button type="submit">Sign Up</button>
</form>
<c:set var="Error" value="${sessionScope.LoginException}"/>
<c:choose>
    <c:when test="${Error!=null}">
        <h4 class="wrong">${Error}</h4>
    </c:when>
</c:choose>
</body>
</html>
