<%-- 
    Document   : disconnectScenarioForInstEng-content
    Created on : Apr 30, 2014, 9:19:19 PM
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
   String taskName = "";
   String serviceLocation = "";
   String serviceDescription = "";
   Integer price = 0;

    if (request.getAttribute("taskName") != null) {
        taskName = (String) request.getAttribute("taskName");
    }
    if (request.getAttribute("serviceLocation") != null) {
        serviceLocation = (String) request.getAttribute("serviceLocation");
    }
    if (request.getAttribute("serviceDescription") != null) {
        serviceDescription = (String) request.getAttribute("serviceDescription");
    }
    if (request.getAttribute("price") != null) {
        price = (Integer) request.getAttribute("price");
    }

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
   <form action="TasksAssignment?type=personal" method="POST">
       <input type="submit" name="action" value="Back to Tasks" />
   </form>
   <table border="1" id="table">
    <tbody>
        <tr>
            <th>
                Task ID
            </th>
            <th>
                Service Order ID
            </th>
            <th>
                Task name
            </th>
            <th>
                Service Location
            </th>
            <th>
                Type of Service
            </th>
            <th>
                Price
            </th>
            <th>
                Cable
            </th>
            <th>
                Port
            </th>
        </tr>
        <tr>
            <td>
                <%=taskID%>
            </td>
            <td>
                <%=soID%>
            </td>
            <td>
                <%=taskName%>
            </td>
            <td>
                <%=serviceLocation%>
            </td>
            <td>
                <%=serviceDescription%>
            </td>
            <td>
                <%=price%>
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


<form action="DisconnectScenarioForInstEng" method="POST">
    <input type="hidden" name="taskid" value="<%=taskID%>"/>
    <input type="hidden" name="serviceorderid" value="<%=soID%>"/>
    <input type="hidden" name="cable" value="<%=cable%>"/>
    <input type="hidden" name="port" value="<%=port%>"/>
    <input type="submit" name="action" value="Disconnect Cable from Port" />
</form>
