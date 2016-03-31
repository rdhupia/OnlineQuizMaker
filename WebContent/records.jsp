<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="onlineQuiz.model.User"%>
<%@page import="onlineQuiz.model.Record"%>
<%@page import="onlineQuiz.model.Quiz"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Quiz History</title>
<%@include file="header.jsp"%>

</head>
<c:set var="currentUser" value="${sessionScope.loggedIn }" />

<body>
	<h1>Quiz records</h1>
	<!-- -----------------LEARNER------------------------------- -->
	<c:if test="${ currentUser.getRole() < 4 }">
		<div class="main_content">
			<!-- Menu -->
			<div class="menu_simple">
				<ul>
					<li><a href="#">MY ACCOUNT</a></li>
					<li><a href="index.jsp">TAKE A QUIZ</a></li>
					<li><a class="current" href="records.jsp">MY QUIZZES</a></li>
				</ul>
			</div>
			<div class="sub_content">
				<!-- User's History -->
				<h3>History</h3>
				<!-- if/else to display their results if they have any -->
				<c:choose>
					<c:when test="${not empty records}">
						<table cellpadding="5" class="gradienttable">
							<tr>
								<th id="name">Quiz Name</th>
								<th id="date">Date Taken</th>
								<th id="score">Scores</th>
							</tr>
							<c:forEach items="${records}" var="item">
								<c:forEach items="${quiz}" var="itemname">
									<tr>
										<c:if test="${item.getQuizId() == itemname.getQuizid()}">
											<td><c:out value="${itemname.getQuizname()}" /></td>
											<td><c:out value="${item.getDateofquiz()}" /></td>
											<td><c:out value="${item.getScore()}" /></td>
										</c:if>
									</tr>
								</c:forEach>
							</c:forEach>
						</table>
						<p>Your Average score has been ${ score }</p>
					</c:when>
					<c:when test="${ empty records && pressed == 1}">
						<c:out
							value="${sessionScope.loggedIn.firstname}, you haven't taken any quizzes!" />
					</c:when>
					<c:otherwise>
						<form name="displayResults" method="get" action="RecordsServlet">
							<input type="hidden" name="userId"
								value="${ currentUser.userid }" /> <input type="submit"
								value="Results">
						</form>
						<br />
						<c:out
							value="${sessionScope.loggedIn.firstname}, press 'Results' button to display your results!" />
					</c:otherwise>
				</c:choose>
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
					<li><a href="quizzes.jsp">EDIT QUIZ</a></li>
					<li><a href="createQuiz.jsp">CREATE QUIZ</a></li>
					<li><a class="current" href="records.jsp">RESULTS</a></li>
				</ul>
			</div>
			<div class="sub_content">
				<!-- User's History -->
				<h3>Statistics</h3>
				<!-- if/else to display their results if they have any -->
				<c:choose>
					<c:when test="${not empty records}">
						<table cellpadding="5" class="gradienttable">
							<tr>
								<th>Student Name</th>
								<th id="name">Quiz Name</th>
								<th id="date">Date Taken</th>
								<th id="score">Scores</th>
							</tr>
							<c:forEach items="${records}" var="item">
								<c:forEach items="${quiz}" var="itemname" varStatus="count">
									<tr>
										<c:if test="${item.getQuizId() == itemname.getQuizid()}">
											<td><c:out value="${names[count.index]}" /></td>
											<td><c:out value="${itemname.getQuizname()}" /></td>
											<td><c:out value="${item.getDateofquiz()}" /></td>
											<td><c:out value="${item.getScore()}" /></td>
										</c:if>
									</tr>
								</c:forEach>
							</c:forEach>
						</table>
						
					</c:when>
					<c:when test="${ empty records && pressed == 1}">
						<c:out
							value="${sessionScope.loggedIn.firstname}, you haven't taken any quizzes!" />
					</c:when>
					<c:otherwise>
						<form name="displayResults" method="get" action="RecordsServlet">
							<input type="hidden" name="userId" value="${ currentUser.userid }" />
							<input type="hidden" name="userrole" value="${ currentUser.role }" /> 
							<input type="submit" name="getResults" value="Results">
						</form>
						<br />
						<c:out
							value="${sessionScope.loggedIn.firstname}, press 'Results' button to display your results!" />
					</c:otherwise>
				</c:choose>
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