<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.6.1.js"></script>
    <link href="/styles/styles.css" rel="stylesheet">
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.User==null}">
        <button type="submit" class="redirect" data-target="/login">Login</button>
        <button type="submit" class="redirect" data-target="/register">Register</button>
    </c:when>
</c:choose>
<c:choose>
    <c:when test="${sessionScope.User!=null}">
        <form method="post">
            <button type="submit">Sign off</button>
            <input type="hidden" name="action" value="signoff">
        </form>
        <button type="submit" class="redirect" data-target="/feed">Feed</button>
        <button type="submit" class="redirect" data-target="/userpage">User page</button>
        <button type="submit" class="redirect" data-target="/createpost">Create post</button>
        <button type="submit" class="redirect" data-target="/list">User list</button>
    </c:when>
</c:choose>
<script>
    $('.redirect').on('click', function (event) {
        event.preventDefault();
        window.location.href = $(this).data('target');
    });
</script>
</body>
</html>
