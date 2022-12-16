<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>User page</title>
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.6.1.js"></script>
    <link href="/styles/styles.css" rel="stylesheet">
</head>
<body>
<h1><c:out value="${sessionScope.Username}"/></h1>
<c:set var="list" value="${sessionScope.feed}"/>
<c:set var="follow" value="${sessionScope.toFollow}"/>
<c:choose>
    <c:when test="${follow!=null}">
        <form method="post">
            <button type="submit">${follow}</button>
            <input type="hidden" name="action" value="follow"/>
        </form>
        <button type="submit" class="redirect" data-target="/chat?name=${sessionScope.Username}">Open chat</button>
    </c:when>
    <c:when test="${follow==null}">
        <form method="post">
            <button type="submit">Sign off</button>
            <input type="hidden" name="action" value="delete"/>
        </form>
    </c:when>
</c:choose>
<button type="submit" class="redirect" data-target="/main">Return</button>
<c:choose>
    <c:when test="${list!=null}">
        <table style="margin-right: 100px; margin-bottom: 30px; float: left">
            <c:forEach var="Post" items="${list}">
                <tr>
                    <td><c:out value="${Post.owner}"/></td>
                </tr>
                <tr>
                    <td><c:out value="${Post.name}"/></td>
                </tr>
                <tr>
                    <td><c:out value="${Post.text}"/></td>
                </tr>
                <tr>
                    <td>
                        <c:out value="${Post.likes}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form method="post">
                            <button type="submit">Like</button>
                            <input type="hidden" name="action" value="like"/>
                            <input type="hidden" name="idOfPost" value="${Post.id}"/>
                        </form>
                    </td>
                </tr>
                <c:set var="isEditable" value="${Post.isEditable}"/>
                <c:choose>
                    <c:when test="${isEditable=='true'}">
                        <tr>
                            <td>
                                <button type="submit" class="redirect" data-target="/post?id=${Post.id}">edit</button>

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
</body>
</html>
