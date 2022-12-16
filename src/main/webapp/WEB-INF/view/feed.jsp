<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Feed</title>
    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <link href="/styles/styles.css" rel="stylesheet">
</head>
<body>
<h2>My feed</h2>
<button type="submit" class="redirect" data-target="/main">Return</button>
<c:set var="list" value="${sessionScope.feed}"/>
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
