<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/bootstrap.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/styles.css'/>">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css">
</head>
<body>
	<div class="container">
		<div class="login-container">
			<c:if test="${param.error != null}">
				<div class="alert alert-danger">Wrong password or username</div>
			</c:if>

			<c:if test="${param.logout != null}">
				<div class="alert alert-success">You have successfully logged out</div>
			</c:if>
			
			<c:url var="loginUrl" value="/login" />
			<form:form action="${loginUrl}" method="POST">
				<div class="form-group">
					<div class="input-group">
						<label class="input-group-append input-group-text"><i class="fa fa-user"></i></label>
						<input class="form-control" type="text" id="username" name="username" placeholder="Enter username" required />
					</div>
				</div>

				
				<div class="form-group">
					<div class="input-group">
						<label class="input-group-append input-group-text"><i class="fa fa-lock"></i></label>
						<input class="form-control input-sm" type="password" name="password" placeholder="Enter password" required />
					</div>
				</div>

				<div class="form-group">
					<input class="btn btn-block btn-primary" name="loginBtn" type="submit" value="Login" />
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>