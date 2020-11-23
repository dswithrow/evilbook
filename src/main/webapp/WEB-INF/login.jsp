<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Log In or Sign Up Today!</title>
	<link rel="stylesheet" href="/css/style.css" />
</head>
<body>
	<div class="wrapper">
		<header>
			<h1>evilbook</h1>
		</header>
		<div class="registration-form">
		    <h4>Register New Account</h4>
		    <p class="error"><form:errors path="user.*"/></p>
	        <form:form method="POST" action="/registration" modelAttribute="user">
	        	<table>
	        		<tr>
	        			<td><form:label path="username">Username</form:label></td>
	        			<td><form:input type="text" path="username"/></td>
	        		</tr>
	        		<tr>
	        			<td><form:label path="email">Email</form:label></td>
	        			<td><form:input type="email" path="email"/></td>
	        		</tr>
	        		<tr>
	        			<td><form:label path="password">Password</form:label></td>
	        			<td><form:password path="password"/></td>
	        		</tr>
	        		<tr>
	        			<td><form:label path="passwordConfirmation">Password Confirmation:</form:label></td>
	        			<td><form:password path="passwordConfirmation"/></td>
	        		</tr>
	        		<tr>
	        			<td></td>
	        			<td><input type="submit" value="Register!"/></td>
	        		</tr>
	        	</table>
	    	</form:form>
		</div>
		<div class="login-form">
			<h4>Log In</h4>
			<p class="error"><c:out value="${error}" /></p>
			<form method="post" action="/login">
				<table>
					<tr>
						<td><label for="alias">Email or Username</label></td>
						<td><input type="text" id="alias" name="alias"/></td>
					</tr>
					<tr>
						<td><label for="password">Password</label></td>
						<td><input type="password" id="password" name="password"/></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="submit" value="Login!"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>