<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Evilbook Home</title>
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
			<div class="new-post">
				<form:form method="POST" action="/" modelAttribute="post">
	        		<form:textarea path="body" placeholder="Make a New Post"/>
	        		<form:errors path="body"/>
	        		<input type="submit" value="New Post"/>
	    		</form:form>
			</div>
			<div class="feed">
				<c:forEach items="${ posts }" var="post">
					<div class="post" id="${ post.id }">
						<img class="avatar" src="/img/placeholder-a.png" alt='<c:out value="${ post.user.bio.displayName }"/>' />
						<div class="chain">
							<span class="name"><c:out value="${ post.user.bio.displayName }"/></span>
							<c:out value="${ post.body }"/>
							<c:forEach items="${ post.comments }" var="comment">
								<div class="comment">
									<img class="avatar" src="/img/placeholder-a.png" alt='<c:out value="${ comment.user.bio.displayName }"/>' />
									<div class="body">
										<span class="name"><c:out value="${ comment.user.bio.displayName }"/></span>
										<p><c:out value="${ comment.body }"/></p>
									</div>
								</div>
							</c:forEach>
							<div class="new-comment">
								<form method="POST" action="/comment/${ post.id }">
									<textarea name="body" id="body" placeholder="Add a Comment"></textarea>
									<span>${ commentError }</span>
									<input type="submit" value="Add Comment"/>
								</form>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</main>
	</div>
</body>
</html>