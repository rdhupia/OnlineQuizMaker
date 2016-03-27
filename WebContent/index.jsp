<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@page import="onlineQuiz.model.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Online Quiz Maker</title>
</head>
<%@include file="header.jsp" %>
<c:set var="currentUser" value="${sessionScope.loggedIn }" /> 

<body>
	<h1>Welcome ${ currentUser.firstname } to your Home Page</h1>
	<c:if test="${ currentUser.getRole() < 4 }">
		<p>Take a Quiz</p>
	</c:if>
	<c:if test="${ currentUser.getRole() >= 4 }">		
		<p>Create a Quiz</p>
	</c:if>
	
</body>
</html>