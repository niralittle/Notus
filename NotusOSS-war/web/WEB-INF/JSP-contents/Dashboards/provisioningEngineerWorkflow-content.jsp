<%-- 
    Document   : provisioningEngineerWorkflow-content
    Created on : Apr 23, 2014, 12:20:17 PM
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
        <title>Provisioning Engineer Workflow</title>
    </head>
    <body>
        <% Integer taskID = (Integer) request.getAttribute("taskid");
           Integer soID = (Integer) request.getAttribute("soid");%>
           <form action="ProvisioningEngineerTasksServlet" method="POST">
               <input type="submit" name="action" value="Back to Tasks" />
           </form>
           <form action="CreateCircuit" method="POST">
           <table border="1" id="tablename1">
            <tbody>
                <tr>
                    <td>
                        Task ID
                    </td>
                    <td>
                        Service Order ID
                    </td>
                    <td>
                        Circuit
                    </td>
                </tr>
                <tr>
                    <td>
                        <%=taskID%>
                    </td>
                    <td>
                        <%=soID%>
                    </td>
                    <td>
                        <input type="text" name="circuit"/>
                    </td>
                </tr>
            </tbody>
           </table>
        
            <input type="hidden" name="taskid" value="<%=taskID%>"/>
            <input type="hidden" name="serviceorderid" value="<%=soID%>"/>
            <input type="submit" name="action" value="Create Circuit" />
        </form>
    </body>
</html>
