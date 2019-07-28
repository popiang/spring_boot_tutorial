<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row d-flex justify-content-center" >

	<div class="col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2 text-center">
	
		<c:out value="${message}" />
		
		<!--  
		
		Exception: <c:out value="${exception}" />
		Failed URL: <c:out value="${url}" />
		Exception message: <c:out value="${exception.message}" />
		
		<c:forEach var="line" items="${exception.stackTrace}">
			<c:out value="${line}" />
		</c:forEach>
	
		-->
	
	</div>

</div>
