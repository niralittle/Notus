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
        <% Integer taskID = (Integer) request.getAttribute("taskid");
           Port port = new Port();
           if (request.getAttribute("port") != null) {
                port = (Port) request.getAttribute("port");
           }
           Cable cable;
           if (request.getParameter("cable") != null) {
                cable = (Cable) request.getAttribute("cable");
           } else {
               cable = new Cable();
           }
           Integer soID = (Integer) request.getAttribute("soid");%>
           <form action="InstallationEngineerTasks" method="POST">
               <input type="submit" name="action" value="Back to Tasks" />
           </form>
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
                        Cable
                    </td>
                    <td>
                        Port
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
                        <%=cable.getCable()%>
                    </td>
                    <td>
                        <%=port.getPortNumber()%>
                    </td>
                </tr>
            </tbody>
           </table>
        <form action="SubmitTask" method="POST">
            <input type="hidden" name="taskid" value="<%=taskID%>"/>
            <input type="hidden" name="serviceorderid" value="<%=soID%>"/>
            <input type="hidden" name="cable" value="<%=cable%>"/>
            <input type="hidden" name="port" value="<%=port%>"/>
            <input type="submit" name="action" value="Create Router" />
            <input type="submit" name="action" value="Create Cable" />
            <input type="submit" name="action" value="Connect Cable to Port" />
        </form>
    </body>
</html>