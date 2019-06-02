<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row d-flex justify-content-center">

	<div class="col-md-8">

		<div class="card">
		
			<div class="card-header bg-primary">
				Edit A Status Update
			</div>
			
			<div class="card-body">

				<form:form modelAttribute="statusUpdate">
				
					<form:input path="id" type="hidden"/>
					
					<div class="errors">
						<form:errors path="text" />
					</div>
				
					<div class="form-group">
						<form:textarea path="text" name="text" class="form-control" rows="10"></form:textarea>
					</div>
					
					<button type="submit" class="btn btn-default">Save Status</button>				
				
				</form:form>

			</div>
		
		</div>
		
	</div>

</div>

    