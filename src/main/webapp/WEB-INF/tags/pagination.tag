<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ attribute name="page" required="true" type="org.springframework.data.domain.Page"%>
<%@ attribute name="url" required="true" %>
<%@ attribute name="size" required="false" %>

<c:set var="size" value="${empty size ? 10 : size }" />
<c:set var="block" value="${empty param.b ? 0 : param.b}" />

<c:set var="startPage" value="${block * size + 1}" />
<c:set var="endPage" value="${(block + 1) * size}" />
<c:set var="endPage" value="${endPage > page.totalPages ? page.totalPages : endPage}" />

<c:if test="${page.totalPages != 1}">

	<div class="pagination">
	
		<c:if test="${block != 0}">
			<a href="${url}?b=${block - 1}&p=${startPage - size}">&lt;&lt;&nbsp;</a>
		</c:if>
		
		<c:forEach var="pageNumber" begin="${startPage}" end="${endPage}">
	
			<c:choose>
			
				<c:when test="${page.number == pageNumber - 1}">
					<strong><c:out value="${pageNumber}" /></strong>
				</c:when>
			
				<c:otherwise>
					<a href="${url}?p=${pageNumber}&b=${block}"><c:out value="${pageNumber}" /></a>
				</c:otherwise>
			
			</c:choose>
	
			<c:if test="${pageNumber != endPage}">
				&nbsp;|&nbsp;
			</c:if>
	
		</c:forEach>
	
		<c:if test="${endPage != page.totalPages}">
			<a href="${url}?b=${block + 1}&p=${endPage + 1}">&nbsp;&gt;&gt;</a>
		</c:if>
	
	</div>

</c:if>