 <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@page import="onlineQuiz.model.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
<link rel="stylesheet" type="text/css" href="css/custom.css" />
</head>

<body>

  <center><h2>Registration</h2></center>
  <center>
  <form id="regForm" action="RegServlet" method="POST">
   <c:if test="${ role >= 4 } ">
   		<c:set var="admin" value="checked" />
   		<c:set var="learner" value="" />
   </c:if>
   <c:if test="${ role < 4 } ">
   		<c:set var="admin" value="" />
   		<c:set var="learner" value="checked" />
   </c:if>
   
    <table cellpadding=5 cellspacing=5>
    	<tr>
    		<td>First Name: </td>
    		<td><input type="text" name="firstName" value="${ user.firstname }"/></td>
    		<td></td>
    	</tr>
    	<tr>
    		<td>Last Name: </td>
    		<td><input type="text" name="lastName" value="${ user.lastname }" /></td>
    		<td></td>
    	</tr>
    	<tr>
    		<td>E-Mail: </td>
    		<td><input type="text" name="email" value="${ user.email }"></td>
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
    			<input type="radio" name="role" value="4" <c:out value="${ admin }">name</c:out> >Teacher (Create Quizzes)
    		</td>
    		<td></td>
    	</tr>
    	<tr>
    		<td></td>
    		<td>
    		    <input type="radio" name="role" value="1" <c:out value="${ learner }">name</c:out> >Learner (Take Quizzes)
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
  </center>
</body>
</html>