<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <meta name="description" content="Notus Internet provider"/>
    <title><%=request.getParameter("title")%> | Notus</title>
    <script src="assets/registerValidator.js"></script>
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/main.css">

</head>
<body>
    <div id="page_content">
        <div id="wrapper">
            <div id="main">
            	<jsp:include page="/WEB-INF/jspf/dashboardsHeader.jsp"/> 
                 <h2 id="Dash-title"><%=request.getParameter("title")%></h2>
                <div id="main-content">
                   
                    <% pageContext.include("/WEB-INF/JSP-contents/" + request.getParameter("content") + ".jsp");%>
                </div>
            </div>
        </div>
    </div>

</body>
</html>
