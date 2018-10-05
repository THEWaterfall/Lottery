<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>

</head>
<body>
	<form:form method="POST" modelAttribute="ticket">
		<div>
			<label>Choose 5 white balls</label>
			<form:select path="chosenWhiteBalls" items="${whiteBalls}" />
			<form:checkbox path="whiteBallsQuickPick" />
			<form:errors path="chosenWhiteBalls" />
		</div>
		
		<div>
			<label>Choose 1 red ball</label>
			<form:select path="chosenRedBalls" items="${redBalls}" />
			<form:checkbox path="redBallsQuickPick" />
			<form:errors path="chosenRedBalls" />
		</div>
		
		<div>
			<input type="submit" value="Save" />
		</div>
	</form:form>
</body>
</html>