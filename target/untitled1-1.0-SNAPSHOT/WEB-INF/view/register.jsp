<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
<html>
<head>
    <title>Register</title>
    <link href="/styles/styles.css" rel="stylesheet">
    <link rel="stylesheet" href="assets/css/fontawesome.css">
    <link rel="stylesheet" href="assets/css/templatemo-liberty-market.css">
    <link rel="stylesheet" href="assets/css/owl.css">
    <link rel="stylesheet" href="assets/css/animate.css">
    <link rel="stylesheet"href="https://unpkg.com/swiper@7/swiper-bundle.min.css"/>
</head>
<body>
<div id="js-preloader" class="js-preloader">
    <div class="preloader-inner">
        <span class="dot"></span>
        <div class="dots">
            <span></span>
            <span></span>
            <span></span>
        </div>
    </div>
</div>
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
            <input type="password" name="userPassword" />
        </label>
    </div>
    <div class="field">
        <label>
            Email<br>
            <input type="text" name="userEmail"/>
        </label>
    </div>
    <button type="submit">Sign Up</button>
</form>
<c:set var="Error" value="${sessionScope.RegisterException}"/>
<c:choose>
    <c:when test="${Error!=null}">
        <h4 class="wrong">${Error}</h4>
    </c:when>
</c:choose>
</body>
</html>
