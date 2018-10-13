<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
</head>
<body>
	<table>
		<tr>
			<th>ID</th>
			<th>Username</th>
			<th>Email</th>
			<th>Credits</th>
			<th>Roles</th>
			<th></th>
			<sec:authorize access="hasRole('ROOT')">
				<th></th>
				<th></th>
			</sec:authorize>
		</tr>
		
		<c:forEach var="user" items="${users}">
			<tr>
				<td>${user.id}</td>
				<td>${user.username}</td>
				<td>${user.email}</td>
				<td>${user.credits}</td>
				
				<td>
					<c:forEach var="role" items="${user.roles}">
						<span>${role.type} </span>
					</c:forEach>
				</td>
				
				<td>
					<a href="<c:url value='/users/save/${user.id}'/>">Save</a>
				</td>
				
				<sec:authorize access="hasRole('ROOT')">
					<td>
						<a href="<c:url value='/users/edit/${user.id}'/>">Edit</a>
					</td>
				
					<td>
						<a href="<c:url value='/users/remove/${user.id}'/>">Remove</a>
					</td>
				</sec:authorize>
			</tr>
		</c:forEach>
	</table>
	
	<a href="<c:url value='/users/add'/>">Add</a>
</body>
</html>