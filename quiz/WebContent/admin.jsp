<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="servlets.*" %>
<%@ page import="app.*" %>
<%@ page import="models.*" %>
<%@ page import="java.util.ArrayList" %>

<%
	App app = (App)session.getAttribute("app");
	User user = app.current_user;
	if (user == null || !user.isAdmin()){
		// Redirect to home page if user isn't an admin
		response.sendRedirect("/home.jsp");
	}
%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title> Admin panel for <%= app.current_user.user_name %></title>
</head>

<body>
<!--HEADER BAR-->
	<%@ include file="header.jspf" %>


</body>
</html>