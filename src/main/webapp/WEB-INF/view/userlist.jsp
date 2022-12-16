<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User list</title>
  <script type="text/javascript" src="/js/jquery-3.6.1.min.js"></script>
    <link href="/styles/styles.css" rel="stylesheet">
</head>
<body>

<script>
  $('.redirect').on('click', function (event) {
    event.preventDefault();
    const url = $(this).data('target');
    location.replace(url)
  });
</script>
</body>
</html>
