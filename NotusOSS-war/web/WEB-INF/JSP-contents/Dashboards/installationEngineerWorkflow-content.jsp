<%-- 
    Document   : installationEngineerWorkflow-content
    Created on : Apr 22, 2014, 12:30:54 PM
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


<% if (request.getAttribute("actionStatus") != null) { %>
    <%=request.getAttribute("actionStatus")%>
<% } %>
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

<form action="SubmitTask" method="POST">
    <input type="hidden" name="taskid" value="<%=taskID%>"/>
    <input type="hidden" name="serviceorderid" value="<%=soID%>"/>
    <input type="hidden" name="cable" value="<%=cable%>"/>
    <input type="hidden" name="port" value="<%=port%>"/>
    <input type="hidden" name="taskName" value="<%=taskName%>"/>
    <input type="hidden" name="serviceLocation" value="<%=serviceLocation%>"/>
    <input type="hidden" name="serviceDescription" value="<%=serviceDescription%>"/>
    <input type="hidden" name="price" value="<%=price%>"/>
<% if(port == null) { %>
    <input type="submit" name="action" value="Create Router" />
<% }
   if(cable.getCable() == null) { %>
    <input type="submit" name="action" value="Create Cable" />
<% }
   if(cable.getCable() != null && port != null) { %>
    <input type="submit" name="action" value="Connect Cable to Port" />
<% }  %>
</form>