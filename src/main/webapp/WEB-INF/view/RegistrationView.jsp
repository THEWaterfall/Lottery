<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/bootstrap.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/styles.css'/>">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css">
	<script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>
	<div class="container">
		<div class="add-container">
			<form:form method="POST" modelAttribute="user">
			
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Username</label>
					<div class="col-md-10">
						<form:input class="form-control" type="text" path="username"/>
						<form:errors class="text-danger" path="username"/>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Password</label>
					<div class="col-md-10">
						<form:input class="form-control" type="password" path="password"/>
						<form:errors class="text-danger"  path="password"/>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Email</label>
					<div class="col-md-10">
						<form:input class="form-control" type="email" path="email"/>
						<form:errors class="text-danger" path="email"/>
					</div>
				</div>
				
				
				<form:input type="hidden" path="credits" value="0"/>
				<form:input type="hidden" path="roles" value="3"/>

				<hr/>
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Firstname</label>
					<div class="col-md-10">
						<form:input class="form-control" type="text" path="profile.firstname"/>
						<form:errors class="text-danger" path="profile.firstname"/>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Lastname</label>
					<div class="col-md-10">
						<form:input class="form-control	" type="text" path="profile.lastname"/>
						<form:errors class="text-danger" path="profile.lastname"/>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Gender</label>
					<div class="col-md-10">
						<div class="form-check">
							<form:radiobutton class="form-check-input" path="profile.gender" value="M"/> 
							<label class="form-check-label">Male</label>
						</div>
						
						<div class="form-check">	
							<form:radiobutton class="form-check-input" path="profile.gender" value="F"/>
							<label class="form-check-label">Female</label>
						</div>
						<form:errors class="text-danger" path="profile.gender"/>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Birthday</label>
					<div class="col-md-10">
						<form:input class="form-control" type="date" path="profile.birthday"/>
						<form:errors class="text-danger" path="profile.birthday"/>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-2">
						<input class="btn btn-block btn-success" type="submit" value="Register" />
						<a class="btn btn-sm btn-block btn-danger" href="<c:url value='/users'/>">Cancel</a>
					</div>
				
					<div class="col-md-10">
						<div class="g-recaptcha" data-sitekey="${sitekey}"></div>
						<c:if test="${captchaError != null}">
							Captcha error
						</c:if>
					</div>
				</div>
				
			</form:form>
		</div>
	</div>
</body>
</html>