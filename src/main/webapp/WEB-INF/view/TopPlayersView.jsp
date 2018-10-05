<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
	<c:set var="counter" value="0" scope="page"/>
	
	<table>
		<tr>
			<th>#</th>
			<th>Username</th>
			<th>Credits</th>
		</tr>
		
		<tr>
			<c:forEach var="user" items="${users}">
				<c:set var="counter" value="${counter+1}" scope="page"/>
				<td>${counter}</td>
				<td>${user.username}</td>
				<td>${user.credits}</td>
			</c:forEach>
		</tr>
	</table>
	
	<a href="<c:url value='/' />">Back</a>
</body>
</html>