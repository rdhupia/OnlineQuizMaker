<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Remove Question</title>
<%@include file="header.jsp" %>
</head>
<body>
	<c:set var="currentUser" value="${sessionScope.loggedIn }" /> 
<!-- -----------------TEACHER------------------------------- -->
	<c:if test="${ currentUser.getRole() >= 4 }">		
	<h1>${ currentQuiz.quizname } </h1>
		<div class="main_content">
	<!-- Menu -->
			<!-- Menu -->
			<div class="menu_simple">
				<ul>
					<li><a href="#">MY ACCOUNT</a></li>
					<li><a class="current" href="quizzes.jsp">EDIT QUIZ</a></li>
					<li><a href="#">CREATE QUIZ</a></li>
					<li><a href="#">RESULTS</a></li>
				</ul>
			</div>
			<div class="sub_content">
	<!-- Questions -->
				<!-- Quizzes List -->
				<h3>Remove this Question permanently?</h3>
				<form action="ProcessQuestionServlet" method="POST">
					<p>Question: ${ question.question }</p>
	                <p>This action will permanently delete this question and all the answers (right and wrong) associated with this question.</p>	                
	                <input type="hidden" name="quesId" value="${ question.quesid }" />
	                <input type="hidden" name="quizId" value="${ currentQuiz.quizid }" />
	                <input type="submit" name="deleteQuestion" value="Delete Permanently" />
	            </form>
			</div>
		</div>
	</c:if>
<!-- -----------------/ TEACHER------------------------------- -->
</body>
</html>