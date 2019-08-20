<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style type="text/css">
	.register-form {
		max-width: 500px;
    	margin: 50px auto;
	}
    .register-form form {
    	margin-bottom: 15px;
        background: #f7f7f7;
        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        padding: 30px;
    }
    .register-form h2 {
        margin: 0 0 15px;
    }
    .form-control, .btn {
        min-height: 38px;
        border-radius: 2px;
    }
    .btn {        
        font-size: 15px;
        font-weight: bold;
    }
</style>

<c:url var="loginUrl" value="/login" />

<div class="row justify-content-center">

	<div class="col-md-6">

		<div class="register-form">
			<c:set var="rpError">
				<form:errors path="siteUser" class="errors" />
			</c:set>
			
			<form:form modelAttribute="siteUser" method="post">
		
				<h2 class="text-center">Create An Account</h2>
				
				<div class="input-group">
					<form:input path="firstname" type="text" class="form-control" placeholder="First Name" required="required" />
					<span class="input-group-btn" style="width: 10px"></span>
					<form:input path="lastname" type="text" class="form-control" placeholder="Last Name" required="required" />
				</div>
				
				<form:errors path='firstname' class="errors" />
				<form:errors path='lastname' class="errors" />
				
				<div class="form-group m-10-top m-0-bottom">
					<form:input path="email" type="text" class="form-control" placeholder="Email" required="required" />
				</div>

				<form:errors path='email' class="errors" />
		
				<div class="form-group m-10-top m-0-bottom">
					<form:input path="plainPassword" type="password" class="form-control" placeholder="Password" required="required" />
				</div>

				<form:errors path='plainPassword' class="errors" />
				
				<div class="form-group m-10-top m-0-bottom">
					<form:input path="repeatPassword" type="password" class="form-control" placeholder="Repeat Password" required="required" />
				</div>		
				
				<c:if test="${not empty rpError}">
					<form:errors cssClass="errors">${rpError}</form:errors>	
				</c:if>
				
				<div class="form-group m-10-top">
					<button type="submit" class="btn btn-primary btn-block">
						Register
					</button>
				</div>
		
			</form:form>
		
		</div>

	</div>

</div>
