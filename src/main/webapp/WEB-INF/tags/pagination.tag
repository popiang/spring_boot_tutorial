<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ attribute name="page" required="true" type="org.springframework.data.domain.Page"%>
<%@ attribute name="url" required="true" %>

<div class="pagination">

	<c:forEach var="pageNumber" begin="1" end="${page.totalPages}">

		<c:choose>
			<c:when test="${page.number == pageNumber - 1}">
				<strong><c:out value="${pageNumber}" /></strong>
			</c:when>
			<c:otherwise>
				<a href="${url}?p=${pageNumber}"><c:out value="${pageNumber}" /></a>
			</c:otherwise>
		</c:choose>

		<c:if test="${pageNumber != page.totalPages}">
			&nbsp|&nbsp
		</c:if>

	</c:forEach>

</div>