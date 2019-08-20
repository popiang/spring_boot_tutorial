<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:url var="profilePhoto" value="/profilephoto/${userID}" />
<c:url var="editProfileAbout" value="/edit-profile-about" />
<c:url var="saveInterest" value="/saveinterest" />
<c:url var="deleteInterest" value="/deleteinterest"/>

<div class="row d-flex justify-content-center">

	<div class="col-md-10">

		<div id="photo-upload-status"></div>
	
		<div id="interestDiv">
			<ul id="interestList">
			
				<c:choose>
					<c:when test="${empty profile.interests}">
						<li>Add interest here</li>	
					</c:when>
					<c:otherwise>
						<c:forEach var="interest" items="${profile.interests}">
							<li>${interest}</li>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			
				
			</ul>
		</div>	

		<div class="profile-about">
		
			<div class="profile-img">
				<div>
					<img id="profile-image" src="${profilePhoto}">
				</div>
				<div class="text-center">
					<c:if test="${ownProfile == true}">
						<a href="#" id="upload-photo">Upload Photo</a>
					</c:if>
				</div>
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
			<c:if test="${ownProfile == true}">
				<a href="${editProfileAbout}">edit</a>
			</c:if>
		</div>

		<c:url value="/upload-profile-photo" var="uploadPhotoLink" />
		<form method="post" id="photo-upload-form" enctype="multipart/form-data" action="${uploadPhotoLink}">
		
			<input type="file" accept="image/*" name="file" id="photo-file-input">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		
		</form>

	</div>

</div>

<script type="text/javascript">

// set the upload status text to display
function setUploadStatusText(text) {
	
	// set the text
	$("#photo-upload-status").text(text);
	
	// remote the text after 2 seconds
	window.setTimeout(function() {
		$("#photo-upload-status").text("");
	}, 2000);
	
}

// to invoke upon successfull photo upload
function uploadSuccess(data) {
	
	$("#profile-image").attr("src", "${profilePhoto}?t=" + new Date());
	
	$("#photo-file-input").val("");
	
	setUploadStatusText(data.message)
	
}

// invoked during form submission
function uploadPhoto(event) {
	
	// ajax call during photo upload
	$.ajax({
		url: $(this).attr("action"),
		type: 'POST',
		data: new FormData(this),
		processData: false,
		contentType: false,
		success: uploadSuccess,
		error: function() {
			setUploadStatusText("Server unreachable!");
		}
	});
	
	event.preventDefault();
	
}

function saveInterest(text) {
	editInterest(text, "${saveInterest}");
}

function deleteInterest(text) {
	editInterest(text, "${deleteInterest}");
}

function editInterest(text, actionUrl) {

	var token = $("meta[name='_csrf']").attr("content");
	var tokenHeader = $("meta[name='_csrf_header']").attr("content");

	$.ajaxPrefilter(function(options, originalOptions, jqHXR) {
		jqHXR.setRequestHeader(tokenHeader, token);
	});
	
	$.ajax({
		url: actionUrl,
		data: {
			"name": text
		},
		type: "POST",
		success: function() {
			alert("OK");
		},
		error: function() {
			alert("Error");
		}
	});
}

$(document).ready(function() {

	$('#interestList').tagit({
		
		afterTagAdded : function(event, ui) {
			if(ui.duringInitialization != true) {
				saveInterest(ui.tagLabel);
			}
		},
		
		afterTagRemoved : function(event, ui) {
			deleteInterest(ui.tagLabel);
		},
		
		caseSensitive : false,
		allowSpaces : true,
		tagLimit : 10,
		readOnly: '${ownProfile}' == 'false'
	});
	
	// trigger when upload photo link is clicked
	$('#upload-photo').click(function(event) {
		event.preventDefault();
		$('#photo-file-input').trigger('click');
	})
	
	// trigger when photo input element received data and then submitting the form 
	$('#photo-file-input').change(function() {
		$('#photo-upload-form').submit();
	})
	
	// during submitting the form, invoking uploadPhoto function
	$('#photo-upload-form').on('submit', uploadPhoto);

});

</script>









    