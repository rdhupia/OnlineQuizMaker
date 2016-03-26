<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="onlineQuiz.model.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	User user;
	int role = -1;
	// Allow access only if session exists
	if(session.getAttribute("user") == null) {
		System.out.println("Session not set, redirect to login.jsp");
		response.sendRedirect("login.jsp");
	} else {
		user = (User)session.getAttribute("user");
		role = user.getRole();
	}
	
	String userName = null;
	String sessionID = null;
	
	Cookie[] cookies =request.getCookies();
	if(cookies != null) {
		for( Cookie cookie: cookies )
			if( cookie.getName().equals("name"))
				userName = cookie.getValue();
	}
%>
<title>Online Quiz Maker</title>
</head>
<body>
	<h1>Welcome <%=userName %> to your Home Page</h1>
	<%
		if( role < 4 ) {
			%>
	<p>Take a Quiz</p>		
	<%	}
		else if( role >= 4 && role < 9) {	
	%>
	<p>Create a Quiz</p>
	<% }
	%>
	
</body>
</html>