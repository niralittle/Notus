<%--
    Document   : index
    Created on : 5 04 2014, 17:01:02
    Author     : Igor Lytvynenko
--%>

<%
	if (request.getUserPrincipal() != null) {
%>
<h3>You logged in as, <%=request.getUserPrincipal()%></h3>

<form method="GET" action="logout">
	<input type="submit" value="Logout" />
</form>

<%
	} else {
%>

<p>
	<a href="login.jsp">Login</a> 
</p>

<% } %>

<p>_____________________________________________________</p>

<a href='adminPage.jsp'>Admin page</a>
