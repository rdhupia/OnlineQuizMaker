<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@page import="onlineQuiz.model.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Online Quiz Maker</title>
	<%@include file="header.jsp" %>
</head>
<c:set var="currentUser" value="${sessionScope.loggedIn }" /> 

<body>
	<h1>Quiz Completed</h1>
<!-- -----------------LEARNER------------------------------- -->
	<c:if test="${ currentUser.getRole() < 4 }">
		<div class="main_content">
	<!-- Menu -->
			<div class="menu_simple">
				<ul>
					<li><a href="myaccount.jsp">MY ACCOUNT</a></li>
					<li><a class="current "href="index.jsp">TAKE A QUIZ</a></li>
					<li><a href="records.jsp">MY QUIZZES</a></li>
				</ul>
			</div>
			<div class="sub_content">
	<!-- Subject Choice -->
				<h3 style="text-align:center">Result</h3>
				<p>Your Score: <span>${ score }</span></p>
				<p>${ comment.text }</p>
				<h3 style="text-align:center">Review</h3>
				<table class="gradienttable">
						<tr>
							<th>Question</th>
							<th>Your Answers</th>
							<th></th>
						</tr>
					<c:forEach var="ansChosen" items="${ answerschosen }" varStatus="status" >
						<tr>
							<td>${ quesAsked[status.index].question  }</td>
							<td>${ ansChosen.answer }</td>
							<td>${ ansChosen.correct == 1 ? "RIGHT" : "WRONG" }</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</c:if>
<!-- -----------------/ LEARNER------------------------------- -->
<!-- -----------------TEACHER------------------------------- -->
	<c:if test="${ currentUser.getRole() >= 4 }">		
		<div class="main_content">
			<div class="menu_simple">
				<ul>
					<li><a href="#">Link 1</a></li>
					<li><a href="#">Link 2</a></li>
					<li><a href="#">Link 3</a></li>
					<li><a href="#">Link 4</a></li>
					<li><a href="#">Link 5</a></li>
				</ul>
			</div>
			<div class="sub_content">
				<p>stuff</p>
				<p>he last </p>
				<p>: SELECT USERID, EMAIL, FIRSTNAME, LASTNAME, PASSWORD, ROLE FROM Users WHERE (EMAIL = ?)	bind => [learner@learner.com]</p>
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