<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="pop" %>

<div class="row d-flex justify-content-center">

	<div class="col-md-8">

		<c:url var="url" value="/viewstatus" />
	
		<pop:pagination page="${page}" url="${url}" size="4" />
	
		<c:forEach items="${page.content}" var="statusUpdate">

			<c:url var="editLink" value="/editstatus?id=${statusUpdate.id}" />
			<c:url var="deleteLink" value="/deletestatus?id=${statusUpdate.id}" />

			<div class="card">

				<div class="card-header bg-primary">
					<fmt:formatDate value="${statusUpdate.added}"
						pattern="EEEE d MMMM y 'at' H:mm:s" />
				</div>

				<div class="card-body">

					<div>
						${statusUpdate.text}
					</div>
					
					<div class="edit-links">
						<a href="${editLink}">edit</a> | <a onclick="return confirm('Are you sure want to delete this status update?');" href="${deleteLink}">delete</a>
					</div>
					
				</div>

			</div><br>

		</c:forEach>

	</div>

</div>