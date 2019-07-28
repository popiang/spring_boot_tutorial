<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:url var="img" value="/img" />
<c:url var="editProfileAbout" value="/edit-profile-about" />

<div class="row d-flex justify-content-center">

	<div class="col-md-10 col-md-offset-1">

		<div class="profile-about">
		
			<div class="profile-img">
				<img src="${img}/default-profile.png">
			</div>
			
			<div class="profile-text">

				<c:choose>
					<c:when test="${profile.about == null}">
						Click 'edit' to add information about yourself in the profile
					</c:when>
					<c:otherwise>
						${profile.about}
					</c:otherwise>
				</c:choose>

			</div>
		
		</div>
		
		<div class="profile-about-edit">
			<a href="${editProfileAbout}">edit</a>
		</div>

	</div>

</div>

    