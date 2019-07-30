<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html>
<head>

<meta charset="ISO-8859-1">

<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<c:set var="contextRoot" value="${contextRoot.request.contextPath}"/>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->

<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
	integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
	crossorigin="anonymous"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"
	integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy"
	crossorigin="anonymous"></script>
	
<script src="//ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="js/vendor/jquery-3.3.1.min.js"><\/script>')</script>	

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<link rel="stylesheet" href="${contextRoot}/css/main.css">

<title><tiles:insertAttribute name="title" /></title>

</head>

<body>

	<nav class="navbar navbar-expand-lg navbar-light bg-dark navbar-dark py-1">
		<a class="navbar-brand" href="${contextRoot}/">Spring Boot Tutorial</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<a class="nav-link" href="${contextRoot}/">Home</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="${contextRoot}/about">About</a>
				</li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
			
				<sec:authorize access="!isAuthenticated()">
					<li class="nav-item">
						<a class="nav-link" href="${contextRoot}/login">Login</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="${contextRoot}/register">Register</a>
					</li>
				</sec:authorize>
			
				<sec:authorize access="isAuthenticated()">
					<li class="nav-item">
						<a class="nav-link" href="${contextRoot}/profile">Profile</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="javascript:$('#logoutForm').submit();">Logout</a>
					</li>					
				</sec:authorize>
				
				<sec:authorize access="hasRole('ROLE_ADMIN')">
					<li class="nav-item dropdown">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							Status
						</a>
						<div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
							<a class="dropdown-item" href="${contextRoot}/addstatus">Add Status</a>
							<a class="dropdown-item" href="${contextRoot}/viewstatus">View Status</a>
						</div>
					</li>	
				</sec:authorize>
			
			</ul>			
		</div>
	</nav>
	
	<c:url var="logoutLink" value="/logout" />
	<form id="logoutForm" action="${logoutLink}" method="post">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
	
	<div class="container main-container">
		<tiles:insertAttribute name="content" />
	</div>

</body>
</html>



