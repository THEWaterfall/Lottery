<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/bootstrap.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/css/styles.css'/>">
</head>
<body>
	<div class="container">
		<%@ include file="Header.jsp" %>
		<div class="playground-container">
			<div class="h4">Credits: ${player.credits}</div>
			
			<c:url var="moretickets" value='/playground/moretickets'/>
			<form:form action="${moretickets}" method="POST">
				<div class="form-group row">
					<div class="col-1">
						<a class="btn btn-success" href="<c:url value='/playground/ticket'/>">Get ticket</a>
					</div>
				
					<div class="input-group col-2">
						<div class="input-group-prepend ">
							<input class="btn btn-success" type="submit" value="Get more"/>
						</div>
						<input class="form-control" type="number" name="amount" min="1" required/>
					</div>
					
					<div class="col-1">
						<a class="btn btn-warning" href="<c:url value='/playground/play'/>">Play</a>
					</div>
				</div>
			</form:form>
			
			<c:if test="${msg != null}">
				<div class="alert alert-danger" id="no-tickets">
					${msg}
				</div>
			</c:if>
			
			<div>
				<div class="h5 mt-2">Tickets:</div>
				<ul class="list-group">
					<c:forEach var="ticket" items="${player.tickets}">
						<li class="list-group-item">${ticket}</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>