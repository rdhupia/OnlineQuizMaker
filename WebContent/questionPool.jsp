<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Question Pool</title>
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
				<h3>Questions Pool</h3>
				<form action="ProcessQuestionServlet" method="POST">
					
	                <table class="gradienttable">
	                	<tr>
	                		<th>SELECT</th>
	                		<th>QUESTION</th>
	                		<th>DIFFICULTY LEVEL</th>
	                	</tr>
	                	<c:set var="questions" value="${ questions }" scope="session" />
	                	<c:forEach var="ques" items="${ questions }">
	                    <tr>
	                        <td><input type="radio" name="quesId" value="${ ques.quesid }" ></td>
	                        <td>${ ques.question }</td>
	                        <td>${ ques.getDifficulty() }</td>
	                    </tr>
	                    </c:forEach>
	                </table>
	                
	                <input type="submit" name="viewQuestion" value="View Question" />
	                <input type="submit" name="editQuestion" value="Edit Question" />
	                <input type="submit" name="removeQuestion" value="RemoveQuestion" />
	                <input type="submit" name="addNewQuestion" value="Add New Question" />
	            </form>
			</div>
		</div>
	</c:if>
<!-- -----------------/ TEACHER------------------------------- -->
</body>
</html>