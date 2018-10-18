<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
</head>
<body>
	<div>Hello, <b>${username}</b>. Welcome to the website! <a href="<c:url value='/logout'/>">Logout</a></div>
	
	<sec:authorize access="hasRole('ROOT') or hasRole('MODER')">
		<a href="<c:url value='/playground'/>">Playground</a>
		<a href="<c:url value='/users'/>">Admin panel</a>
	</sec:authorize>
</body>
</html>