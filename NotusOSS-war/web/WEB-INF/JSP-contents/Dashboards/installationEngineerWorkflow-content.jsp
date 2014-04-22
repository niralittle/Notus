<%-- 
    Document   : installationEngineerWorkflow-content
    Created on : Apr 22, 2014, 12:30:54 PM
    Author     : Vladimir Ermolenko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="nc.notus.entity.Port"%>
<%@page import="nc.notus.entity.Cable"%>
<%@page import="nc.notus.entity.ServiceOrder"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Installation Engineer Workflow</title>
    </head>
    <body>
        <% Integer taskID = (Integer) request.getAttribute("taskID");%>
        <% Port port = (Port) request.getAttribute("port");%>
        <% Cable cable = (Cable) request.getAttribute("cable");%>
        <% ServiceOrder so = (ServiceOrder) request.getAttribute("so");%>
        <a href="installationEngineer.jsp"Back to Tasks</a>
        Task ID
        <%=taskID%>
        Service Order ID
        <%=so.getId()%>
        Cable
        <%=cable.getCable()%>
        Port
        <%=port.getPortNumber()%>
        <form action="SubmitTask" method="POST">
            <input type="submit" name="action" value="Create Router" />
            <input type="submit" name="action" value="Create Cable" />
            <input type="submit" name="action" value="Connect Cable to Port" />
        </form>
    </body>
</html>
