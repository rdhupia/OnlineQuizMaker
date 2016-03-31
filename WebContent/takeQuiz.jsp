<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Quiz Time!!!</title>
	<%@include file="header.jsp" %>
</head>
<c:set var="currentUser" value="${sessionScope.loggedIn }" /> 
<body>
	<h1> ${ currentQuiz.quizname } Quiz </h1><hr>
<!-- -----------------LEARNER------------------------------- -->
	<c:if test="${ currentUser.getRole() < 4 }">
		<div class="main_content">
	<!-- Menu -->
			<div class="question">
				<c:if test="${ empty quizQuestions }" >
					<c:set var="quizQuestions" scope="session" value="${ questions }" />											<!-- ************************************************* -->
				</c:if>
				<c:if test="${ empty quizAnswers }" >
					<c:set var="quizAnswers" scope="session" value="${ answers }" />	<!-- ************************************************* -->
				</c:if>
				<p>QTYPE: ${question.getQuestionType()}</p>
			
				<p>Question-${ index+1 }</p>
				<p class="ques">${ quizQuestions[index].question }</p>
				<input type="button" class="hint" onclick="alert('${ quizQuestions[index].hint }');" value="Grab a Hint" /><br><br>
				
				<form action="TakeQuizServlet" method="POST">
					<input type="hidden" name="newIndex" value="${ index + 1 }" />
					<input type="hidden" name="typeOfQ" value="${ quizQuestions[index].getQuestionType() }" />
					<input type="hidden" name="recordId" value="${ recordId }" />
					<input type="hidden" name="quesId" value="${ quizQuestions[index].quesid }" />
					<c:choose>
						<c:when test="${ quizQuestions[index].getQuestionType() == 1 || quizQuestions[index].getQuestionType() == 2}" >
							<c:forEach var="answer" items="${ quizAnswers[index] }" >
								<input type="radio" name="ansId" value="${ answer.ansid }" >${ answer.answer }<br>
							</c:forEach>
						</c:when>
						<c:when test="${ quizQuestions[index].getQuestionType() == 3 }" >
								Answer: <input type="text" name="ans" placeholder="One word answer"><br>
						</c:when>
						<c:when test="${ quizQuestions[index].getQuestionType() == 4 }" >
							<c:forEach var="answer" items="${ quizAnswers[index] }" >
								<input type="checkbox" name="ansIds" value="${ answer.ansid }" >${ answer.answer }<br>
							</c:forEach>
						</c:when>
					</c:choose>
					<br>
					<input type="submit" name="next" value="Submit Answer" />
				</form>
				
			</div><br>
			<div id="progress-bar" class="all-rounded">
	    		<div id="progress-bar-percentage" class="all-rounded" style="width: ${((index+1)/totalQuestions)*100}%"><span>${ index+1 } / ${ totalQuestions }</span></div>
			</div>
		</div>
	</c:if>
<!-- -----------------/ LEARNER------------------------------- -->

</body>
</html>