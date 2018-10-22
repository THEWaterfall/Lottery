<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/bootstrap.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/styles.css'/>">
</head>
<body>
	<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/bootstrap.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/styles.css'/>">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css">
</head>
<body>
	<div class="container">
		<div class="add-container">
			<form:form modelAttribute="user">
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Username</label>
					<div class="col-md-10">
						<form:input class="form-control" type="text" path="username" disabled="true"/>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Password</label>
					<div class="col-md-10">
						<form:input class="form-control" type="password" path="password" disabled="true"/>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Email</label>
					<div class="col-md-10">
						<form:input class="form-control" type="email" path="email" disabled="true"/>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Credits</label>
					<div class="col-md-10">
						<div class="input-group">
							<form:input class="form-control" type="number" min="0" path="credits" disabled="true"/>
							<div class="input-group-append input-group-text"><i class="fas fa-money-check-alt"></i></div>
						</div>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Roles</label>
					<div class="col-md-10">
						<div class="selection">
							<form:select class="form-control" path="roles" items="${roles}" itemValue="id" itemLabel="type" size="3" disabled="true"/>
						</div>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Firstname</label>
					<div class="col-md-10">
						<form:input class="form-control" type="text" path="profile.firstname" disabled="true"/>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Lastname</label>
					<div class="col-md-10">
						<form:input class="form-control" type="text" path="profile.lastname" disabled="true"/>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Gender</label>
					<div class="col-md-10">
						<div class="form-check">
							<form:radiobutton class="form-check-input" path="profile.gender" value="M" disabled="true"/>
							<label class="form-check-label">Male</label>
						</div>
						
						<div class="form-check">
							<form:radiobutton class="form-check-input" path="profile.gender" value="F" disabled="true"/>
							<label class="form-check-label">Female</label>
						</div>
					</div>
				</div>
				
				<div class="form-group row">
					<label class="col-md-2 col-form-label">Birthday</label>
					<div class="col-md-10">
						<form:input class="form-control" type="date" path="profile.birthday" disabled="true"/>
					</div>
				</div>
				
				<form:input type="hidden" path="profile.id"/>
				<div class="form-group row">
					<div class="col-md-2">
						<a class="btn btn-block btn-primary" href="<c:url value='/users'/>">Back</a>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>
</body>
</html>