<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/bootstrap.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/styles.css'/>">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="<c:url value='/static/js/clickable-row.js'/>"></script>
</head>
<body>
	

	<div class="container">
	<%@ include file="Header.jsp" %>
		<div class="usertable-container">
			<table class="table table-hover table-dark">
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
				<c:url var="profile" value="/users/profile/${user.id}"/>
					<tr class="clickable-row" data-href="${profile}">
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
							<a class="btn btn-sm btn-success" style="width:65px" href="<c:url value='/users/save/${user.id}'/>">Save</a>
						</td>
						
						<sec:authorize access="hasRole('ROOT')">
							<td>
								<a class="btn btn-sm btn-primary" style="width:65px" href="<c:url value='/users/edit/${user.id}'/>">Edit</a>
							</td>
						
							<td>
								<a class="btn btn-sm btn-danger" style="width:65px" href="<c:url value='/users/remove/${user.id}'/>">Remove</a>
							</td>
						</sec:authorize>
					</tr>
				</c:forEach>
			</table>
			<a class="btn btn-primary" style="width:80px" href="<c:url value='/users/add'/>">Add</a>
		</div>
		
		
	</div>
	
</body>
</html>