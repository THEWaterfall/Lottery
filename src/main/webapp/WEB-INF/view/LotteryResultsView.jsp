<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
</head>
<body>
	<c:if test="${won == true}">
		<div>
			Congratulation! 
			You have just won ${winningPrize}
		</div>
	</c:if>
	
	<c:if test="${lost == true}">
		You have won nothing for now.
	</c:if>
	
	<div>
		<a href="<c:url value='/playground'/>">Back</a>
	</div>
</body>
</html>