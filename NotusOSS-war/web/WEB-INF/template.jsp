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
    <meta name="description" content="Notus Internet provider"/>
    <title><%=request.getParameter("title")%> | Notus</title>
    <link rel="icon" href="assets/favicon.ico"/>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans+Caption' rel='stylesheet' type='text/css'>
    <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/main.css">
</head>
<body>
    <div id="page_content">
        <div id="wrapper">
            <div id="main">
                <jsp:include page="/WEB-INF/jspf/header.jsp"/> 
                <h2 id="title"><%=request.getParameter("title")%></h2>
                <div id="main-content">
                    <% pageContext.include("/WEB-INF/JSP-contents/" + request.getParameter("content") + ".jsp");%>
                </div>
            </div>

            <footer>
                <div style="margin-top: 30px; padding: 20px; 
                height:auto; background-image: url(assets/cables.gif);
                background-position: top right;  background-repeat: no-repeat;">
                    <h5> Communications fast as wind.<br>
                    &copy; Notus 2014</h5> 
                </div>
            </footer>
        </div>
    </div>

</body>
</html>
