<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row d-flex justify-content-center">

	<div class="col-md-8">

		<div class="card">
		
			<div class="card-header bg-primary">
				Add A Status Update
			</div>
			
			<div class="card-body">

				<form:form modelAttribute="statusUpdate">
					
					<div class="errors">
						<form:errors path="text" />
					</div>
				
					<div class="form-group">
						<form:textarea id="editor" path="text" name="text" class="form-control" rows="10"></form:textarea>
					</div>
					
					<button type="submit" class="btn btn-default">Add Status</button>				
				
				</form:form>

			</div>
		
		</div>
		<hr>
		<div class="card">
		
			<div class="card-header bg-primary">
				<fmt:formatDate value="${latestStatusUpdate.added}" pattern="EEEE d MMMM y 'at' H:mm:s"/>
			</div>
			
			<div class="card-body">
	
				${latestStatusUpdate.text}

			</div>			
		
		</div>
		
	</div>

</div>

    