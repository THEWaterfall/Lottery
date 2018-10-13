<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

</head>
<body>
	<form:form method="POST" modelAttribute="user">
		
		<div>
			<label>Username</label>
			<form:input type="text" path="username"/>
			<form:errors path="username"/>
		</div>
		
		<div>
			<label>Password</label>
			<form:input type="password" path="password"/>
			<form:errors path="password"/>
		</div>
		
		<div>
			<label>Email</label>
			<form:input type="email" path="email"/>
			<form:errors path="email"/>
		</div>
		
		<div>
			<label>Credits</label>
			<form:input type="number" path="credits"/>
			<form:errors path="credits"/>
		</div>
		
		<div>
			<label>Roles</label>
			<form:select path="roles" items="${roles}" itemValue="id" itemLabel="type" size="3"/>
			<form:errors path="roles"/>
		</div>
		
		<div>
			<label>Firstname</label>
			<form:input type="text" path="profile.firstname"/>
			<form:errors path="profile.firstname"/>
		</div>
		
		<div>
			<label>Lastname</label>
			<form:input type="text" path="profile.lastname"/>
			<form:errors path="profile.lastname"/>
		</div>
		
		<div>
			<label>Gender</label>
			<form:radiobutton path="profile.gender" value="M"/> Male
			<form:radiobutton path="profile.gender" value="F"/> Female
			<form:errors path="profile.gender"/>
		</div>
		
		<div>
			<label>Birthday</label>
			<form:input type="date" path="profile.birthday"/>
			<form:errors path="profile.birthday"/>
		</div>
		
		<div>
			<input type="submit" value="Add" />
		</div>
	</form:form>
	<a href="<c:url value='/users'/>">Cancel</a>
</body>
</html>