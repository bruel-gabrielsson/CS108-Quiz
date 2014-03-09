<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="servlets.*" %>
<%@ page import="app.*" %>
<%@ page import="models.*" %>

<%
	App app = (App)session.getAttribute("app");
	String username = app.current_user.user_name;
	String loginStatus = app.current_user.user_name;
%>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title> Admin panel for <%=  %></title>
</head>
<body>
<!--HEADER BAR-->
	<%@ include file="header.jspf" %>


</body>
</html>