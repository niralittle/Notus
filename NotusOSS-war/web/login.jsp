<%--
    Document   : Login
    Created on : 12 04 2014, 19:32:43
    Author     : Katya Atamanchuk <nira@niralittle.name>
--%>

<% if (request.getUserPrincipal() != null) {
      response.sendRedirect("index.jsp");
   } %>

<jsp:include page="/WEB-INF/template.jsp">
	<jsp:param name="content" value="login-content"/>
	<jsp:param name="title" value="Login"/>
</jsp:include>

