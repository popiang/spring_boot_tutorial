<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row d-flex justify-content-center">

	<div class="col-md-8">

		<div class="errors">
			<form:errors path="profile.about" />
		</div>

		<div class="card">
		
			<div class="card-header bg-primary">
				Edit Your Profile 'About'
			</div>
			
			<div class="card-body">

				<form:form modelAttribute="profile" method="POST">
				
					<div class="form-group">
						<form:textarea path="about" name="about" class="form-control" rows="10"></form:textarea>
					</div>
					
					<button type="submit" class="btn btn-default">Save Status</button>				
				
				</form:form>

			</div>
		
		</div>
		
	</div>

</div>

    