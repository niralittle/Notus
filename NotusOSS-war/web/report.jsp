<%-- 
    Document   : report view/download page
    Created on : 17.04.2014, 20:05:58
    Author     : Andrey Ilin
--%>
<jsp:include page="/WEB-INF/dashboardsTemplate.jsp">
	<jsp:param name="content" value="report-content"/>
        <jsp:param name="title" value='<%=request.getSession().getAttribute("title")%>'/>
</jsp:include>

