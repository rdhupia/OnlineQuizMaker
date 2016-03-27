<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri= "http://java.sun.com/jsp/jstl/core" %>
<%@page import="onlineQuiz.model.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<c:if test="${ empty sessionScope.loggedIn }">
	<c:redirect url="login.jsp" />
</c:if>
<a href="logout.jsp">Logout 
	<c:out value="${sessionScope.loggedIn.firstname}" />
</a>
