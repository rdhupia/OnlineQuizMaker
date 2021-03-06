<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Log In</title>
<link rel="stylesheet" type="text/css" href="css/custom.css" />
</head>
<c:if test="${ not empty sessionScope.loggedIn }">
	<c:redirect url="index.jsp" />
</c:if>
<body>
	<center><h1>Quiz Time</h1></center>
	<br><br><br>
    <center><h2>Log In</h2></center>
    <center>
        <form name="login" method="POST" action="LogInServlet">
            <table cellpadding=5 cellspacing=5>
			    <tr>
			        <td>Email:</td>
			        <td><input type="text" name="username" align="right" required/></td>
			    </tr>
			    <tr>
			        <td>Password:</td>
			        <td><input type="password" name="password" align="right" required/></td>
			    </tr>
			    <tr>
			        <td></td>
			        <td><center><input type="submit" name="login" value="Log In" /></center></td>
			    </tr>
			    <tr><td></td><td></td></tr>
			    <tr>
			    	<td></td>
			    	<td class="linkbutton"><a href="forgotPassword.jsp">Forgot Password</a></td>
			    </tr>
			    <tr>
			    	<td>New User: </td>
			    	<td class="linkbutton"><a href="register.jsp">Create Account</a></td>
			    </tr>
           </table>
       </form>
       <p>${ logInError }</p>
   </center>
</body>
</html>