<%-- 
    Document   : report view/download page
    Created on : 17.04.2014, 20:05:58
    Author     : Andrey Ilin
--%>
<jsp:include page="/WEB-INF/template.jsp">
	<jsp:param name="content" value="/report-content"/>
        <jsp:param name="title" value='<%=request.getAttribute("title")%>'/>
</jsp:include>

