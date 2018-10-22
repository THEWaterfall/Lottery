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
	
			<div class="row">
				<div class="col">
					<c:if test="${won == true}">
						<div class="alert alert-success text-center">
							<div class="mb-2"><i class="fas fa-trophy fa-2x"></i></div>
							<h4 class="alert-heading">Congratulation!</h4>
						 	You have just won ${winningPrize} credits
						</div>
					</c:if>
					
					<c:if test="${lost == true}">
						<div class="alert alert-dark text-center">
							<div class="mb-2"><i class="fas fa-frown fa-2x"></i></div>
							You have won nothing for now.
						</div>	
					</c:if>
				</div>
			</div>
			
			<hr>
			
			<div class="row justify-content-center">
				<div class="col-4">
					<a class="btn btn-block btn-primary" href="<c:url value='/playground'/>">Back</a>
				</div>
			</div>
			
		</div>
	</div>
</body>
</html>