<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>

</head>
<body>
	<%@ include file="Header.jsp" %>
	<div>Credits: ${player.credits}</div>
	
	<a href="<c:url value='/playground/ticket'/>">Get ticket</a>
	<a href="<c:url value='/playground/play'/>">Play</a>
	<a href="<c:url value='/top'/>">Top players</a>	
	
	<div>
		${msg}
	</div>
	
	<div>
		Tickets:
		<c:forEach var="ticket" items="${player.tickets}">
			<div>${ticket}</div>
		</c:forEach>
	</div>
</body>
</html>