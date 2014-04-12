<%--
    Document   : index
    Created on : 5 04 2014, 17:01:02
    Author     : Igor Lytvynenko
--%>

<h3><%=request.getUserPrincipal()%></h3>

<p>
    <a href="Login.jsp">Login</a> | <a href="Registration.jsp">Register</a>
</p>

<form method="GET" action="logout">
    <input type="submit" value="Logout" />
</form>

<a href='AdminOnly.jsp'>Admin page</a>
