<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
</head>
<body>
	<div class="header">
		<nav class="navbar navbar-expand-sm navbar-dark bg-dark">
		<a class="navbar-brand" href="https://github.com/THEWaterfall/">Waterfall Inc.</a>
			<div class="collapse navbar-collapse">
			
					<ul class="navbar-nav mr-auto">
						<sec:authorize access="hasRole('ROOT') or hasRole('MODER')">	
							<li class="nav-item">
								<a class="nav-link" href="<c:url value='/users'/>">Admin panel</a>
							</li>
						</sec:authorize>
						
						<li class="nav-item">
							<a class="nav-link" href="<c:url value='/playground'/>">Playground</a>
						</li>
						
						<li>
							<a class="nav-link" href="<c:url value='/top'/>">Ladder</a>
						</li>
					</ul>
				<c:url var="logout" value='/logout'/> 
				<div class="navbar-text">Hello, <b id="username">${username}</b>. Welcome to the website! <a href="<c:url value='/logout'/>">Logout</a></div>
			</div>
		</nav>
	</div>
</body>
</html>