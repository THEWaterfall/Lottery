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
		<div class="addticket-container">
			<form:form method="POST" modelAttribute="ticket">
				<div class="form-group row">
				
					
					<div class="from-check col-md-2 offset-md-2">
						<label class="text-primary">Choose 5 white balls</label> <br>
						
						<label class="form-check-label text-secondary">Quick pick</label>
						<form:checkbox class="form-check-input ml-2" path="whiteBallsQuickPick" />
						
					
					</div>
					
					<div class="col-md-2">
						<form:select class="form-control" path="chosenWhiteBalls" items="${whiteBalls}" />
						<form:errors class="text-danger" path="chosenWhiteBalls" />
					</div>
					
			
					<div class="col-md-2">
						<form:select class="form-control" path="chosenRedBalls" items="${redBalls}" />
						<form:errors class="text-danger" path="chosenRedBalls" />
					</div>
			
			
					
					<div class="form-check col-md-2">
						<label class="text-primary">Choose 1 red ball</label> <br>
						
						<form:checkbox class="form-check-input" path="redBallsQuickPick" />
						<label class="form-check-label text-secondary">Quick pick</label>
					</div>
					
				</div>
				
				<div class="form-group row">
					<div class="col-md-2 offset-md-4">
						<input class="btn btn-block btn-success" type="submit" value="Save" />
					</div>
					<div class="col-md-2">
						<a class="btn btn-block btn-danger" href="<c:url value='/playground'/>">Cancel</a>
					</div>
				</div>
				
			</form:form>
			
		</div>
	</div>
</body>
</html>