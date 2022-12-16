<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User list</title>
    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <link href="/styles/styles.css" rel="stylesheet">
</head>
<body>
<h2>User list</h2>
<input type="text" id="toSearch">
<button type="submit" class="redirectToUser" data-target="/list?search=">Search</button>
<button type="submit" class="redirect" data-target="/main">Return</button>
<c:set var="list" value="${sessionScope.userlist}"/>
<c:choose>
    <c:when test="${list!=null}">
        <table style="margin-right: 100px; margin-bottom: 30px; float: left">
            <c:forEach var="User" items="${list}">
                <tr>
                    <td><c:out value="${User.username}"/></td>
                </tr>
                <tr>
                    <td>
                        <button type="submit" class="redirect" data-target="/userpage?id=${User.id}">Move to user page
                        </button>
                    </td>
                </tr>
                <c:choose>
                    <c:when test="${sessionScope.permission!=null}">
                        <tr>
                            <td>
                                <form method="post">
                                    <button type="submit">Delete</button>
                                    <input type="hidden" name="id" value="${User.id}">
                                </form>
                            </td>
                        </tr>
                    </c:when>
                </c:choose>
            </c:forEach>
        </table>
    </c:when>
</c:choose>
<script>
    $('.redirect').on('click', function (event) {
        event.preventDefault();
        const url = $(this).data('target');
        location.replace(url)
    });
</script>
<script>
    $('.redirectToUser').on('click', function (event) {
        event.preventDefault();
        const url = $(this).data('target') + $("#toSearch").val();
        location.replace(url)
    });
</script>
</body>
</html>
