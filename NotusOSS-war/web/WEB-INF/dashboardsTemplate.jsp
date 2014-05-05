<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="description" content="Notus Internet provider"/>
    <title><%=request.getParameter("title")%> | Notus</title>
    <link rel="stylesheet" href="assets/main.css">
    <script src="assets/registerValidator.js"></script>
</head>
<body>
    <div id="page_content">
        <div id="wrapper">
            <div id="main">
            	<jsp:include page="/WEB-INF/jspf/dashboardsHeader.jsp"/> 
                 <h1 id="Dash-title"><%=request.getParameter("title")%></h1>
                <div id="main-content">
                   
                    <% pageContext.include("/WEB-INF/JSP-contents/" + request.getParameter("content") + ".jsp");%>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
