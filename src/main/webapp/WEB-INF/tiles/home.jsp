<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="search" value="/search" />

<div class="row d-flex justify-content-center status_row">
	<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
		<div class="homepage-status">${statusUpdate.text}</div>
	</div>
</div>

<div class="row d-flex justify-content-center">
	<div class="col-md-8 col-md-offset-2">

		<form action="${search}" method="POST">

			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		
			<div class="input-group input-group-lg">
				<input type="text" name="s" style="line-height:48px" placeholder="Search Hobbies"  class="form-control">
				
				<div class="input-group-append">
					<button id="button-addon5" type="submit" class="btn btn-primary">
						Find People
					</button>
				</div>
			</div>
		
		</form>
		
	</div>
</div>