<%@page contentType="text/html" pageEncoding="utf-8"%>
<%@page import="onlineQuiz.model.User"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Details</title>
</head>
<body>
  <h2>Account Details</h2>
  <form id="regForm" action="RegServlet" method="POST">
    <%
    	User user = (User)request.getAttribute("user");
    	String userRole;
    	String message;
    	Boolean isRegistered = (Boolean)request.getAttribute("registered");
    	if( isRegistered )
    		message = "You have successfully registered. Please Log In to continue.";
    	else
    		message = "";
    	
    	if( user.getRole() >= 4 && user.getRole() < 9 )
    		userRole = "Teacher";
    	else if( user.getRole() < 4 )
    		userRole = "Learner";
    	else
    		userRole = "Super Admin";
    		
    %>
    <p><%=message%></p>
    <table cellpadding=5 cellspacing=5>
    	<tr>
    		<td>First Name: </td>
    		<td><input type="text" name="firstName" value="<%=user.getFirstname()%>" readonly/></td>
    	</tr>
    	<tr>
    		<td>Last Name: </td>
    		<td><input type="text" name="lastName" value="<%=user.getLastname()%>" readonly/></td>
    	</tr>
    	<tr>
    		<td>E-Mail: </td>
    		<td><input type="text" name="email" value="<%=user.getEmail()%>" readonly /></td>
    	</tr>
    	<tr>
    		<td>Type of Account: </td>
    		<td><input type="text" name="userRole" value="<%=userRole%>" readonly /></td>
    	</tr>
    	<tr><td></td><td></td></tr>
    	<tr>
	    	<td></td>
	    	<td><a href="login.jsp" >Log In</a></td>
    	</tr>
    </table>
  </form>
</body>
</html>