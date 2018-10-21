<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/bootstrap.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/styles.css'/>">
</head>
<body>
	<c:set var="counter" value="0" scope="page"/>
	<div class="container">
		<%@ include file="Header.jsp" %>
		
		<div class="table-container">
			<table class="table table-hover table-dark">
				<tr>
					<th>#</th>
					<th>Username</th>
					<th>Credits</th>
				</tr>
				
				<c:forEach var="user" items="${users}">
					<tr>
						<c:set var="counter" value="${counter+1}" scope="page"/>
						<td>${counter}</td>
						<td>${user.username}</td>
						<td>${user.credits}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
	</div>
</body>
</html>