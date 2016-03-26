 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="onlineQuiz.model.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
<body>

  <h2>Registration</h2>
  <form id="regForm" action="RegServlet" method="POST">
   <%
    	User user = (User)request.getAttribute("user");
   		String firstName = "";
   		String lastName = "";
   		String email = "";
   		String admin = "";
   		String learner = "";
   		
   		int role = 0;
   		if( user != null ) {
   			System.out.println("User is NOT NULL");
	    	firstName = user.getFirstname();
	    	lastName = user.getLastname();
	    	email = user.getEmail();
	    	role = user.getRole();
	    	if(role >= 4) {
	    		admin = "checked";
	    		learner = "";
	    	}
	    	else {
	    		admin = "";
	    		learner = "checked";
	    	}
   		}
   		
   		// Errors
    	String userExistsError = (String)request.getAttribute("userExistsError");
    	String pwdMatchError = (String)request.getAttribute("pwdMatchError");
    	String emailMatchError = (String)request.getAttribute("emailMatchError");
    	System.out.println(admin+"---"+learner);
    %>
    <table cellpadding=5 cellspacing=5>
    	<tr>
    		<td>First Name: </td>
    		<td><input type="text" name="firstName" value="<%=firstName%>"/></td>
    		<td></td>
    	</tr>
    	<tr>
    		<td>Last Name: </td>
    		<td><input type="text" name="lastName" value="<%=lastName%>" /></td>
    		<td></td>
    	</tr>
    	<tr>
    		<td>E-Mail: </td>
    		<td><input type="text" name="email" value="<%=email%>"></td>
    		<td></td>
    	</tr>
    	<tr>
    		<td>Confirm E-Mail: </td>
    		<td><input type="text" name="conEmail"></td>
    		<td>${emailMatchError}</td>
    	</tr>
    	<tr>
    		<td>Select Password: </td>
    		<td><input type="password" name="password"></td>
    		<td></td>
    	</tr>
    	<tr>
    		<td>Confirm Password: </td>
    		<td><input type="password" name="conPassword"></td>
    		<td>${pwdMatchError}</td>
    	</tr>
    	<tr>
    		<td>Type of Account: </td>
    		<td>
    			<input type="radio" name="role" value="4" <%=admin %>>Teacher (Create Quizzes)
    		</td>
    		<td></td>
    	</tr>
    	<tr>
    		<td></td>
    		<td>
    		    <input type="radio" name="role" value="1" <%=learner %>>Learner (Take Quizzes)
    	    </td>
    	    <td></td>
    	</tr>
    	<tr><td></td><td></td></tr>
    	<tr>
	    	<td></td>
	    	<td><input type="submit" name="registerSubmit" value="Register"></td>
    	</tr>
    </table>
  </form>
  <p>${userExistsError}</p>
</body>
</html>