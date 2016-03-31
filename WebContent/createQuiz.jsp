<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="onlineQuiz.model.Quiz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Online Quiz Maker</title>
	<%@include file="header.jsp" %>
</head>
<body>
	<c:set var="currentUser" value="${sessionScope.loggedIn }" /> 
	<h1>Create a New Quiz !! </h1>
	
<!-- -----------------TEACHER------------------------------- -->
	<c:if test="${ currentUser.getRole() >= 4 }">		
		<div class="main_content">
		<!-- Menu -->
			<div class="menu_simple">
				<ul>
					<li><a href="#">MY ACCOUNT</a></li>
					<li><a class="current" href="quizzes.jsp">EDIT QUIZ</a></li>
					<li><a href="createQuiz.jsp">CREATE QUIZ</a></li>
					<li><a href="records.jsp">RESULTS</a></li>
				</ul>
			</div>
		<!-- Subject Choice -->
			<div class="sub_content">
				<h3>Create a Quiz</h3>
				
				<c:if test="${ empty subjects }" >
					<input type="submit" name="create" value="Create New Quiz" />
				</c:if>
				
				<c:if test="${ not empty subjects }" >
					<form action="CreateQuizServlet" method="POST">
					
						Quiz Name: <input type="text" name="quizName" required /><br><br>
						Quiz Description <input type="text" name="desc" required size="45"/> <br><br>
						Quiz Format:<br><br>
						Difficult Questions <input type="text" name="diff" required /> <br><br>
						Medium Questions <input type="text" name="med" required /> <br><br>
						Easy Questions <input type="text" name="easy" required /> <br><br>
						<label for="subjectChoice">Choose a Subject</label>
						<select name="subjectChoice" id="subjectChoice">
							<c:forEach var="subject" items="${subjects}">
							  <option value="${ subject.subjectid }"><c:out value="${ subject.subjectname }"/></option>
							</c:forEach>
							  <option value="new">New Subject</option>
						</select>
						Subject (if not in the list) <br><br>
						Name:<input type="text" name="newSub"  /> <br><br>
						Description:<input type="text" name="newSubDesc"  /> <br><br>
						<input type="hidden" name="userId" value="${ currentUser.userid }" />
						<input type="submit" name="save" value="Create New Quiz" />
						
					</form>
				</c:if>
				
			</div>
		</div>
	</c:if>
<!-- -----------------/ TEACHER------------------------------- -->
<!-- ----------------- SUPERADMIN ------------------------------- -->
	<c:if test="${ currentUser.getRole() == 9 }">		
		<h3>Add a Subject</h3>
	</c:if>
<!-- -----------------/ SUPERADMIN ------------------------------- -->
					
</body>
</html>