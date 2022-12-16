<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.1.js"></script>
<html>
<head>
    <title>Chat</title>
    <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <link href="/styles/styles.css" rel="stylesheet">
</head>
<body>
<h2>Chat with ${sessionScope.name}</h2>
<form method="post">
    <div class="field">
        <label>
            <input type="text" name="message"/>
        </label>
    </div>
    <button type="submit">Send</button>
</form>
<button type="submit" class="redirect" data-target="/main">Return</button>
<c:set var="list" value="${sessionScope.allMessages}"/>
<c:choose>
    <c:when test="${list!=null}">
        <table style="margin-right: 100px; margin-bottom: 30px; float: left">
            <c:forEach var="Message" items="${list}">
                <tr>
                    <td><c:out value="${Message.author}"/></td>
                </tr>
                <tr>
                    <td><c:out value="${Message.text}"/></td>
                </tr>
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
