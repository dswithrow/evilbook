<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title><c:out value="${ user.bio.displayName }"/></title>
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
			<div class="profile">
				<h2><c:out value="${ user.bio.displayName }"/></h2>
				<form:form method="POST" action="/profile/${ user.id }/edit" modelAttribute="bio">
					<table>
						<tr>
		        			<td class="bold">
			        			<form:label path="displayName">Display Name</form:label>
			        			<form:errors path="displayName"/>
		        			</td>
		        			<td><form:input type="text" path="displayName" value="${ user.bio.displayName }"/></td>
		        		</tr>
						<tr>
		        			<td class="bold">
			        			<form:label path="about">About</form:label>
			        			<form:errors path="about"/>
		        			</td>
		        			<td><form:textarea type="text" path="about" value="${ user.bio.about }"/></td>
		        		</tr>
						<tr>
		        			<td class="bold">
		        				<form:label path="occupation">Occupation</form:label>
			        			<form:errors path="occupation"/>
		        			</td>
		        			<td><form:textarea type="text" path="occupation" value="${ user.bio.occupation }"/></td>
		        		</tr>
						<tr>
		        			<td class="bold">
		        				<form:label path="nemesis">Nemesis</form:label>
			        			<form:errors path="nemesis"/>
							</td>
		        			<td><form:textarea path="nemesis" value="${ user.bio.nemesis }"/></td>
		        		</tr>
		        		<tr>
		        			<td></td>
		        			<td><input type="submit" value="Update"/></td>
		        		</tr>
					</table>
				</form:form>
			</div>
		</main>
	</div>
</body>
</html>