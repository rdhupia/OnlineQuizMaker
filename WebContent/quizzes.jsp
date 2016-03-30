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
	<h1>Welcome to your Home Page, ${ currentUser.firstname } !! </h1>
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
	<!-- Quizzes List -->
				<h3>Take a Quiz</h3>
				<form action="quizStart.jsp" method="GET">
					
	                <table class="gradienttable">
	                	<tr>
	                		<th>SELECT</th>
	                		<th>QUIZ</th>
	                		<th>DESCRIPTION</th>
	                		<th># OF Qs</th>
	                		<th>DIFFICULTY SCORE</th>
	                	</tr>
	                	<c:set var="quizzes" scope="session" value="${ quizzes }" />
	                	<c:forEach var="quiz" items="${ quizzes }">
	                    <tr>
	                        <td><input type="radio" name="quizId" value="${ quiz.quizid }" ></td>
	                        <td>${ quiz.quizname }</td>
	                        <td>${ quiz.quizdescription }</td>
	                        <td>${ quiz.difficultqs + quiz.easyqs + quiz.mediumqs }</td>
	                        <td><fmt:formatNumber value="${ (((quiz.difficultqs*2)+ quiz.mediumqs) / ((quiz.difficultqs + quiz.easyqs + quiz.mediumqs)*2))*100 }" 
												  maxFractionDigits="0"/></td>
	                    </tr>
	                    </c:forEach>
	                </table>
	                
	                <input type="submit" name="selectQuiz" value="Choose" />
	            </form>
				<p style="font-size:12px">Difficulty scores:<br> (0-30) Cake walk<br> (30-50) A Thinker<br> (50-75) Tough Cookie<br> (75-100) Bonkers!!! </p>
				<p>${ quizNotFound }</p>
			</div>
		</div>
	</c:if>
<!-- -----------------/ LEARNER------------------------------- -->
<!-- -----------------TEACHER------------------------------- -->
	<c:if test="${ currentUser.getRole() >= 4 }">		
		<div class="main_content">
		<!-- Menu -->
			<div class="menu_simple">
				<ul>
					<li><a href="#">MY ACCOUNT</a></li>
					<li><a class="current" href="quizzes.jsp">MY QUIZ MAKER</a></li>
					<li><a href="#">RESULTS</a></li>
				</ul>
			</div>
		<!-- Subject Choice -->
			<div class="sub_content">
				<h3>Create or Modify a Quiz</h3>
				<!-- Quizzes List -->
				
				<form action="quizStart.jsp" method="GET">
					
	                <table class="gradienttable">
	                	<tr>
	                		<th>SELECT</th>
	                		<th>QUIZ NAME</th>
	                		<th>SUBJECT</th>
	                		<th># OF Qs</th>
	                		<th>DIFFICULTY SCORE</th>
	                	</tr>
	                	<c:set var="quizzes" scope="session" value="${ quizzes }" />
	                	<c:forEach var="quiz" items="${ quizzes }">
	                    <tr>
	                        <td><input type="radio" name="quizId" value="${ quiz.quizid }" ></td>
	                        <td>${ quiz.quizname }</td>
	                        <td>${ subjects[quiz.subjectId-1].subjectname }</td>
	                        <td>${ quiz.difficultqs + quiz.easyqs + quiz.mediumqs }</td>
	                        <td><fmt:formatNumber value="${ (((quiz.difficultqs*2)+ quiz.mediumqs) / ((quiz.difficultqs + quiz.easyqs + quiz.mediumqs)*2))*100 }" 
												  maxFractionDigits="0"/></td>
	                    </tr>
	                    </c:forEach>
	                </table>
	                
	                <input type="submit" name="editQuiz" value="Edit Quiz" />
	                <input type="submit" name="deleteQuiz" value="Delete Quiz" />
	                <input type="submit" name="viewQuiz" value="View Quiz" />
	                <input type="submit" name="addQuiz" value="Add New Quiz" />
	            </form>
				<p style="font-size:12px">Difficulty scores:<br> (0-30) Cake walk<br> (30-50) A Thinker<br> (50-75) Tough Cookie<br> (75-100) Bonkers!!! </p>
				<p>${ quizNotFound }</p>
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