<%-- 
    Document   : installationEngineerWorkflow-content
    Created on : Apr 22, 2014, 12:30:54 PM
    Author     : Vladimir Ermolenko
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="nc.notus.entity.Port"%>
<%@page import="nc.notus.entity.Cable"%>
<%@page import="nc.notus.entity.ServiceOrder"%>

<% Port port;
   Cable cable;
   Integer soID;
   Integer taskID;

    if (request.getAttribute("taskid") != null) {
        taskID = (Integer) request.getAttribute("taskid");
    } else {
       taskID = 0;
    }

   if (request.getAttribute("port") != null) {
        port = (Port) request.getAttribute("port");
   } else {
       port = new Port();
   }

   if (request.getAttribute("cable") != null) {
        cable = (Cable) request.getAttribute("cable");
   } else {
       cable = new Cable();
   }

   if (request.getAttribute("soid") != null) {
        soID = (Integer) request.getAttribute("soid");
    } else {
       soID = 0;
    }
%>
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