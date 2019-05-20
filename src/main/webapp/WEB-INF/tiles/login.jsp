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
		
			<form action="/examples/actions/confirmation.php" method="post">
		
				<h2 class="text-center">Please Log in</h2>
		
				<div class="form-group">
					<input name="username" type="text" class="form-control" placeholder="Username" required="required">
				</div>
		
				<div class="form-group">
					<input name="password" type="password" class="form-control" placeholder="Password" required="required">
				</div>
		
				<div class="form-group">
					<button type="submit" class="btn btn-primary btn-block">
						Log in
					</button>
				</div>
		
				<div class="clearfix">
					<label class="pull-left checkbox-inline">
						<input type="checkbox"> Remember me
					</label> 
					<a href="#" class="pull-right">
						Forgot Password?
					</a>
				</div>
		
			</form>
		
			<p class="text-center">
				<a href="#">Create an Account</a>
			</p>
		
		</div>

	</div>

</div>

    