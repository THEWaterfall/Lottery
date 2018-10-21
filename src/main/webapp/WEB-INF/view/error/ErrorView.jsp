<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/bootstrap.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/styles.css'/>">
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.4.1/css/all.css">
</head>
<body>
	<div class="container">	
		<div class="error-container">
			<div class="row justify-content-center">
				<div class="col-2">
					<i class="fas fa-exclamation-circle fa-3x"></i> 
				</div>
			</div>
			
			<div class="row justify-content-center">
				<div class="col-9">
					<span class="alert alert-danger d-block text-center">${errorMsg}</span>
				</div>
			</div>
			
			<hr>
			
			<div class="row justify-content-center">
				<div class="col-9">
					<a class="btn btn-block btn-primary" href="<c:url value='/playground'/>">Return</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>