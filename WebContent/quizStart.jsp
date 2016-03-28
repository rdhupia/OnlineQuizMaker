<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page import="onlineQuiz.model.Quiz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Quiz Time!!!</title>
	<%@include file="header.jsp" %>
</head>
<body>
	<c:set var="currentUser" value="${sessionScope.loggedIn }" /> 
	<h1>It's Quiz Time !! </h1>
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
	<!-- Confirmation -->
				<h3>Start the Quiz ?</h3>
				<form action="QuizStartServlet" method="POST">
					
	                	<c:forEach var="quiz" items="${ quizzes }">
	                		<c:if test="${ quiz.quizid == param.quizId }" >
	                			<p>Are you sure you want to start this quiz?<p>
	                			 <p>QUIZ NAME        : ${ quiz.quizname } 
	                			<br>DESCRIPTION      : ${ quiz.quizdescription } 
	                			<br>DIFFICULTY SCORE : <fmt:formatNumber value="${ (((quiz.difficultqs*2)+ quiz.mediumqs) / ((quiz.difficultqs + quiz.easyqs + quiz.mediumqs)*2))*100 }" maxFractionDigits="0"/>
	                			<br>TOTAL QUESTIONS  : ${ quiz.difficultqs + quiz.easyqs + quiz.mediumqs } 
	                		</c:if>	    
	                    </c:forEach>
	                
	                <br><br>
	                <input type="submit" name="startQuiz" value="Bring it On!" />
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