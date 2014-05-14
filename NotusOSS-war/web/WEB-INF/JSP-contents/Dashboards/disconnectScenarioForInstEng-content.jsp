<%-- 
    Document   : disconnectScenarioForInstEng-content
    Created on : Apr 30, 2014, 9:19:19 PM
    Author     : Vladimir Ermolenko
--%>

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

    taskID = request.getAttribute("taskid") != null ?
        (Integer) request.getAttribute("taskid") : 0;

    port = (request.getAttribute("port") != null) ?
       (Port) request.getAttribute("port") : new Port();

    cable = (request.getAttribute("cable") != null) ?
       (Cable) request.getAttribute("cable") : new Cable();

    soID = (request.getAttribute("soid") != null) ?
        (Integer) request.getAttribute("soid") : 0;
%>
   <form action="TasksAssignment?type=personal" method="POST">
       <input type="submit" name="action" value="Back to Tasks" />
   </form>
   <table border="1" id="table">
    <tbody>
        <tr>
            <th> Task ID </th>
            <th> Service Order ID </th>
            <th> Task name </th>
            <th> Service Location </th>
            <th> Type of Service </th>
            <th> Price </th>
            <th> Cable </th>
            <th> Port </th>
        </tr>
        <tr>
            <td> <%=taskID%> </td>
            <td> <%=soID%> </td>
            <td> <%=taskName%> </td>
            <td> <%=serviceLocation%> </td>
            <td> <%=serviceDescription%> </td>
            <td> <%=price%> </td>
            <td> <%=cable.getCable()%> </td>
            <td> <%=port.getPortNumber()%> </td>
        </tr>
    </tbody>
</table>

<form action="DisconnectScenarioForInstEng" method="POST">
    <input type="hidden" name="taskid" value="<%=taskID%>"/>
    <input type="hidden" name="serviceorderid" value="<%=soID%>"/>
    <input type="hidden" name="cableid" value="<%=cable.getId()%>"/>
    <input type="hidden" name="portid" value="<%=port.getId()%>"/>
    <input type="submit" name="action" class="btn btn-info" value="Disconnect Cable from Port" />
</form>
