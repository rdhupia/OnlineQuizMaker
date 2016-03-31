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
				<c:set var="readonly" value="" />
				<c:set var="easy" value="" />
		   		<c:set var="med" value="" />
		   		<c:set var="dif" value="" />
		   		<c:set var="disabled" value="" />
		   		<!--  VIEW ONLY -->
				<c:if test="${ action == 'view' }" >
					<c:set var="readonly" value="readonly" />
					<c:set var="easy" value="disabled" />
			   		<c:set var="med" value="disabled" />
			   		<c:set var="dif" value="disabled" />
			   		<c:set var="disabled" value="disabled" />
				</c:if>
				<h3>Quiz Question</h3>
				<hr>
				<c:if test="${ question.difficultylevel == 1 }">
			   		<c:set var="easy" value="checked" />
			    </c:if>
			    <c:if test="${ question.difficultylevel == 2 }">
			   		<c:set var="med" value="checked" />
			    </c:if>
			    <c:if test="${ question.difficultylevel == 3 }">
			   		<c:set var="dif" value="checked" />
			    </c:if>
			   
				
				<form action="SaveChangesServlet" method="POST">
					<table cellpadding=5 cellspacing=5>
	    				<tr>
	    					<td>Question</td>
	    					<td><textarea rows="4" cols="50" name="quesText" value="${ question.question }" <c:out value="${ readonly }" /> >${ question.question }</textarea></td>
						</tr>
						<tr>
	    					<td>Answer Explanation</td>
	    					<td><textarea rows="3" cols="50" name="ansExpl"  value="${ question.answerexplained }" <c:out value="${ readonly }" /> >${ question.answerexplained }</textarea></td>
						</tr>
						<tr>
	    					<td>Difficulty Level</td>
							<td><input type="radio" name="diff" value="1" <c:out value="${ easy }">name</c:out> />Easy</td>
						</tr>
						<tr>
	    					<td></td>
							<td><input type="radio" name="diff" value="2" <c:out value="${ med }">name</c:out>/>Medium</td>
						</tr>
						<tr>
	    					<td></td>
							<td><input type="radio" name="diff" value="3" <c:out value="${ dif }">name</c:out>/>Hard</td>
						</tr>
						<tr>
	    					<td>Hint</td>
							<td><input type="text" name="hint" value="${ question.hint }"  size="45" <c:out value="${ readonly }" /> /></td>
						</tr>
						
		<!-- ------- ANSWERS -------- -->
						<tr>
							<td>ANSWERS:</td>
							<td></td>
						</tr>
						
						<c:if test="${ type.typeid == 1 }" >
							<tr>
								<td></td>
								<td>This Question can have Multiple Choice Answers</td>
							</tr>
							<tr>
								<td>${ answers[0].correct == 0 ? "WRONG" : "CORRECT"}</td>
								<td><input type="text" name="ans1" value="${ answers[0].answer }"  size="45" <c:out value="${ readonly }" /> />
									<input type="hidden" name="ansId1" value="${answers[0].ansid}" />	</td>	
							</tr>
							<tr>
								<td>${ answers[1].correct == 0 ? "WRONG" : "CORRECT"}</td>
								<td><input type="text" name="ans2" value="${ answers[1].answer }"  size="45" <c:out value="${ readonly }" /> />
								<input type="hidden" name="ansId2" value="${answers[1].ansid}" />	</td>		
							</tr>
							<tr>
								<td>${ answers[2].correct == 0 ? "WRONG" : "CORRECT"}</td>
								<td><input type="text" name="ans3" value="${ answers[2].answer }"  size="45" <c:out value="${ readonly }" /> />
								<input type="hidden" name="ansId3" value="${answers[2].ansid}" />	</td>
							</tr>
							<tr>
								<td>${ answers[3].correct == 0 ? "WRONG" : "CORRECT"}</td>
								<td><input type="text" name="ans4" value="${ answers[3].answer }"  size="45" <c:out value="${ readonly }" /> />
								<input type="hidden" name="ansId4" value="${answers[3].ansid}" />	</td>		
							</tr>
							
						</c:if>
						
						<c:if test="${ type.typeid == 2 }" >
							<tr>
								<td></td>
								<td>This Question can have True or False Answer</td>
							</tr>
							<tr>
								<td>${ answers[0].correct == 0 ? "WRONG" : "CORRECT"}</td>
								<td><input type="text" name="ans1" value="${ answers[0].answer }"  size="45" <c:out value="${ readonly }" /> />
								<input type="hidden" name="ansId1" value="${answers[0].ansid}" />	</td>		
							</tr>
							<tr>
								<td>${ answers[1].correct == 0 ? "WRONG" : "CORRECT"}</td>
								<td><input type="text" name="ans2" value="${ answers[1].answer }"  size="45" <c:out value="${ readonly }" /> />
								<input type="hidden" name="ansId2" value="${answers[1].ansid}" />	</td>			
							</tr>
						</c:if>
						
						<c:if test="${ type.typeid == 3 }" >
							<tr>
								<td></td>
								<td>This Question can have One Word Input Answer</td>
							</tr>
								<tr>
								<td>${ answers[0].correct == 0 ? "WRONG" : "CORRECT"}</td>
								<td><input type="text" name="ans1" value="${ answers[0].answer }"  size="45" <c:out value="${ readonly }" /> />
								<input type="hidden" name="ansId1" value="${answers[0].ansid}" />	</td>		
							</tr>
						</c:if>
						
						<c:if test="${ type.typeid == 4 }" >
							<tr>
								<td></td>
								<td>This Question can have Multiple Correct Answers</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="correct" value="first" ${ answers[0].correct == 0 ? "unchecked" : "checked"} ${ disabled } /></td>
								<td><input type="text" name="ans1" value="${ answers[0].answer }"  size="45" <c:out value="${ readonly }" /> />
								<input type="hidden" name="ansId1" value="${answers[0].ansid}" />	</td>		
							</tr>
							<tr>
								<td><input type="checkbox" name="correct" value="second" ${ answers[1].correct == 0 ? "" : "checked"} ${ disabled } /></td>
								<td><input type="text" name="ans2" value="${ answers[1].answer }"  size="45" <c:out value="${ readonly }" /> />
								<input type="hidden" name="ansId2" value="${answers[1].ansid}" />	</td>		
							</tr>
							<tr>
								<td><input type="checkbox" name="correct" value="third" ${ answers[2].correct == 0 ? "" : "checked"} ${ disabled } /></td>
								<td><input type="text" name="ans3" value="${ answers[2].answer }"  size="45" <c:out value="${ readonly }" /> />
								<input type="hidden" name="ansId3" value="${answers[2].ansid}" />	</td>
							</tr>
							<tr>
								<td><input type="checkbox" name="correct" value="fourth" ${ answers[3].correct == 0 ? "" : "checked"} ${ disabled } /></td>
								<td><input type="text" name="ans4" value="${ answers[3].answer }"  size="45" <c:out value="${ readonly }" /> />
								<input type="hidden" name="ansId4" value="${answers[3].ansid}" />	</td>		
							</tr>
						</c:if>
						</table>					
						<input type="hidden" name="qType" value="${ type.typeid }" />
						<input type="hidden" name="quesId" value="${ question.quesid }" />
						<input type="hidden" name="asked" value="${ question.numoftimesasked }" />
						
						<div class="linkbutton">
							<a id="logout" href="questionPool.jsp">Go Back</a>
						</div>					
						<c:if test="${ action == 'edit' }" >
							<input type="submit" name="saveEdit" value="Save Changes" />
						</c:if>
	            </form>
	            
			</div>
		</div>
	</c:if>
<!-- -----------------/ TEACHER------------------------------- -->
</body>
</html>















