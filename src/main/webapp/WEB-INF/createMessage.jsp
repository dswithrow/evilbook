<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>New Message</title>
	<link rel="stylesheet" href="/css/style.css" />
</head>
<body>
	<div class="wrapper">
		<header>
			<h1>evilbook</h1>
			<h5>
				| <a href="/">Home</a>
				| <a href="/mail">
					<c:choose>
						<c:when test="${ newMessages > 0 }">
							<span class="bold">&#9733 Messages (${ newMessages })</span>
						</c:when>
						<c:otherwise>
							Messages
						</c:otherwise>
					</c:choose>
				</a>
				| <a href="/market">Marketplace</a> |
			</h5>
			<h6>
				<a href="/logout">Log Out</a> |
			</h6>
		</header>
		<div class="sidebar">
			<img class="profile-picture" src="/img/placeholder.png" alt='<c:out value="${ user.bio.displayName }"/>' />
			<div class="user-links">
				<span class="bold"><c:out value="${ user.bio.displayName }"/></span>
				<hr>
				<a href="/profile/${ user.id }">View Profile</a>
				<hr>
			</div>			
		</div>
		<main>
			<h4>New Message To <c:out value="${ recipient.bio.displayName }"/></h4>
			<form:form method="POST" action="/message/send/${ recipient.id }" modelAttribute="message">
				<c:choose>
					<c:when test="${ replyingTo == null }">
						<form:input type="text" path="subject" placeholder="Subject..."/>
		        		<form:errors path="subject"/>
		        		<form:textarea path="body" placeholder="Message..."/>
		        		<form:errors path="body"/>
					</c:when>
					<c:otherwise>
						<form:input type="text" path="subject" placeholder="Subject..." value='RE:<c:out value="${ replyingTo.subject }"/>'/>
		        		<form:errors path="subject"/>
		        		<form:textarea path="body" placeholder="Message..." value= '\"<c:out value="${ replyingTo.body }"/>\"' />
		        		<form:errors path="body"/>
					</c:otherwise>
				</c:choose>
        		<input type="submit" value="Send Message"/>
	    		</form:form>
		</main>
	</div>
</body>
</html>