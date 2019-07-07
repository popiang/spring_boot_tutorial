<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<style type="text/css">
	.login-form {
		width: 340px;
    	margin: 50px auto;
	}
    .login-form form {
    	margin-bottom: 15px;
        background: #f7f7f7;
        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);
        padding: 30px;
    }
    .login-form h2 {
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

<div class="row d-flex justify-content-center">

	<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">

		<div class="login-form">
		
			<form:form modelAttribute="siteUser" method="post">
		
				<h2 class="text-center">Create An Account</h2>
												
				<div class="form-group m-0-bottom">
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
				
				<form:errors path="repeatPassword" class="errors" />		
		
				<div class="form-group m-10-top">
					<button type="submit" class="btn btn-primary btn-block">
						Register
					</button>
				</div>
		
			</form:form>
		
		</div>

	</div>

</div>

    