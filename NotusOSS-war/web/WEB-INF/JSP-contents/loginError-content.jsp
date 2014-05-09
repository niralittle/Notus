<%--
    Document   : loginError-content
    Created on : 12 04 2014, 19:32:43
    Author     : Panchenko Dmytro
--%>
<%
	if (request.getAttribute("errMessage") == null) {
%>
<h2 style="color: red;">Invalid user name and/or password.</h2>
<%
	} else {
%>
<h2 style="color: red;">
	<%=request.getAttribute("errMessage")%>
</h2>
<%
	}
%>
