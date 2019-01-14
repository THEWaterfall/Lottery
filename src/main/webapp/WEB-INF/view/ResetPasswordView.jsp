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

			<c:if test="${errorMsg != null}">
				<div class="alert alert-danger">${errorMsg}</div>
			</c:if>
			
			<c:url var="resetUrl" value="/password/reset" />
			<form:form action="${resetUrl}" method="POST">
				<div class="form-group">
					<div class="input-group">
						<label class="input-group-append input-group-text"><i class="fa fa-lock"></i></label>
						<input class="form-control input-sm" type="email" name="email" placeholder="Enter your email" required />
					</div>
				</div>

				<div class="form-group">
					<input class="btn btn-block btn-primary" name="resetBtn" type="submit" value="Reset" />
				</div>
				<div class="form-group">
					<a class="btn btn-block btn-primary" href="<c:url value="/login" />">Cancel</a>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>