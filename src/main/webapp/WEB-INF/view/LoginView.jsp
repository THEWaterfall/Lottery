<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
</head>
<body>
	<c:if test="${param.error != null}">
		<div>Wrong password or username</div>
	</c:if>

	<c:if test="${param.logout != null}">
		<div>You have successfully logged out</div>
	</c:if>
	
	<c:url var="loginUrl" value="/login" />
	<form:form action="${loginUrl}" method="POST">
		<div>
			<label>Username</label>
			<input type="text" name="username" required />
		</div>
		
		<div>
			<label>Password</label>	
			<input type="password" name="password" required />
		</div>
		
		<div>
			<input type="submit" value="Login" />
		</div>
	</form:form>
</body>
</html>