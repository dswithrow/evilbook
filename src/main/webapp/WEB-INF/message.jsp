<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title><c:out value="${ message.subject }"/></title>
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
			<h2><c:out value="${ message.subject }"/></h2>
			<div class="message">
				<img class="avatar" src="/img/placeholder-a.png" alt='<c:out value="${ message.sender.bio.displayName }"/>' />
				<div class="body">
					<span class="name"><c:out value="${ message.sender.bio.displayName }"/></span>
					<h3><c:out value="${ message.subject }"/></h3>
					<c:out value="${ message.body }"/>
				</div>
			</div>
			<c:if test="${ isRecipient }">
				<a href="/mail/reply/${ message.id }">Reply</a>
			</c:if>
		</main>
	</div>
</body>
</html>