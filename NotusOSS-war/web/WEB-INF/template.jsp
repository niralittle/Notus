<%-- 
    Document   : template
    Created on : 12 04 2014, 11:33:14
    Author     : Katya Atamanchuk
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="description" content="Wind Internet provider"/>
    <title><%=request.getParameter("title")%></title>
    <link rel="icon" href="assets/favicon.ico"/>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans+Caption' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="assets/main.css">
</head>
<body>
	<jsp:include page="/WEB-INF/jspf/header.jspf"/>

	<h1 id="title"><%=request.getParameter("title")%></h1> 
        
        <% pageContext.include("/WEB-INF/JSP-contents/" + request.getParameter("content") + ".jsp");%>

	<jsp:include page="/WEB-INF/jspf/footer.jspf"/>

</body>
</html>
