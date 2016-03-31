<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Question</title>
<%@include file="header.jsp" %>
</head>
<body>
	<c:set var="currentUser" value="${sessionScope.loggedIn }" /> 
<!-- -----------------TEACHER------------------------------- -->
	<c:if test="${ currentUser.getRole() >= 4 }">		
	<h1>${ currentQuiz.quizname } </h1>
		<div class="main_content">
	 		<div class="question">
	<!-- Question -->
				
				<h3>Add Question</h3>
				<hr>
				<form action="SaveChangesServlet" method="POST">
					<table cellpadding=5 cellspacing=5>
	    				<tr>
	    					<td>Question</td>
	    					<td><textarea rows="4" cols="50" name="quesText" required /></textarea></td>
						</tr>
						<tr>
	    					<td>Answer Explanation</td>
	    					<td><textarea rows="3" cols="50" name="ansExpl" value="none" required /></textarea></td>
						</tr>
						<tr>
	    					<td>Difficulty Level</td>
							<td><input type="radio" name="diff" value="1" required />Easy</td>
						</tr>
						<tr>
	    					<td></td>
							<td><input type="radio" name="diff" value="2" />Medium</td>
						</tr>
						<tr>
	    					<td></td>
							<td><input type="radio" name="diff" value="3" />Hard</td>
						</tr>
						<tr>
	    					<td>Hint</td>
							<td><input type="text" name="hint" /></td>
						</tr>
						<tr>
	    					<td>Answer Type</td>
							<td><input type="radio" name="ansType" value="1" required />Multiple Choice Answer</td>
						</tr>
						<tr>
	    					<td></td>
							<td><input type="radio" name="ansType" value="2" />True or False answer</td>
						</tr>
						<tr>
	    					<td></td>
							<td><input type="radio" name="ansType" value="3" />One Word Input Answer</td>
						</tr>
						<tr>
	    					<td></td>
							<td><input type="radio" name="ansType" value="4" />Muli-select Answers</td>
						</tr>
						
		<!-- ------- ANSWERS -------- -->
						
						</table>					
						
						<div class="linkbutton">
							<a id="logout" href="questionPool.jsp">Go Back</a>
						</div>					
						
							<input type="submit" name="addQuestion" value="Add Question" />
						
	            </form>
	            
			</div>
		</div>
	</c:if>
<!-- -----------------/ TEACHER------------------------------- -->
</body>
</html>















