<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <link href="/styles/styles.css" rel="stylesheet">
</head>
<body>
<c:set var="Post" value="${sessionScope.post}"/>
<c:choose>
    <c:when test="${Post!=null}">
        <table>
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
        </table>
    </c:when>
</c:choose>
<h3>Update post:</h3>
<form method="post">
    <div class="field">
        <label>
            Title<br>
            <input type="text" name="postName"/>
        </label>
    </div>
    <div class="field">
        <label>
            Text<br>
            <input type="text" name="postText"/>
        </label>
    </div>
    <button type="submit">Update</button>
    <input type="hidden" name="action" value="update"/>
</form>
<form method="post">
    <input type="hidden" name="action" value="delete"/>
    <button type="submit">Delete</button>
</form>
    <button type="submit" class="redirect" data-target="/main">Return</button>
    <script>
        $('.redirect').on('click', function (event) {
            event.preventDefault();
            const url = $(this).data('target');
            location.replace(url)
        });
    </script>
</body>
</html>
