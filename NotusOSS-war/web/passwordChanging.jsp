<%-- 
    Document   : passwordChange
    Created on : Apr 24, 2014
    Author     : Panchenko Dmytro
--%>

<jsp:include page="/WEB-INF/dashboardsTemplate.jsp">
    <jsp:param name="content" value="/Dashboards/passwordChanging-content"/>
    <jsp:param name="title" value='<%=request.isUserInRole("ADMINISTRATOR") == true ? "Block user" : "Change user password"%>'/>
</jsp:include>
