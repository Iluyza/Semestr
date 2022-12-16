<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create post</title>
    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <link href="/styles/styles.css" rel="stylesheet">
</head>
<body>
<form method="post">
    <div class="field">
        <label>
            Title<br>
            <input type="text" name="title"/>
        </label>
    </div>
    <div class="field">
        <label>
            Text<br>
            <input type="text" name="text"/>
        </label>
    </div>
    <button type="submit">Create post</button>
</form>
<button type="submit" class="redirect" data-target="/main">Return</button>
<c:set var="Error" value="${sessionScope.PostCreateException}"/>
<c:choose>
    <c:when test="${Error!=null}">
        <h4 class="wrong">${Error}</h4>
    </c:when>
</c:choose>
<script>
    $('.redirect').on('click', function (event) {
        event.preventDefault();
        const url = $(this).data('target');
        location.replace(url)
    });
</script>
</body>
</html>
