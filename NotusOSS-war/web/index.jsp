<%-- 
    Document   : index
    Created on : 5 квіт 2014, 17:01:02
    Author     : Igor
--%>

<%-- just test --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>

        <%=request.getUserPrincipal()%>

        <p>
            <a href="login.jsp"><input type="button" value="Login" name="submit"/></a>
        </p>
        <form method="get" action="logout">
            <input type="submit" value="Logout" />
        </form>
        <a href='adminOnly.jsp'>admin page</a>
    </body>
</html>
