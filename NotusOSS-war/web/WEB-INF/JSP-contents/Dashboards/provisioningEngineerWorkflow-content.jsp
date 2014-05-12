<%-- 
    Document   : provisioningEngineerWorkflow-content
    Created on : Apr 23, 2014, 12:20:17 PM
    Author     : Panchenko Dmytro 
--%>

<%@page import="nc.notus.entity.Port"%>
<%@page import="nc.notus.entity.Task"%>
<%@page import="nc.notus.entity.Cable"%>
<%@page import="nc.notus.entity.ServiceOrder"%>

<h3 style="color:red;">
    <%=request.getAttribute("errMessage") == null ? "" :
        request.getAttribute("errMessage")%>
</h3>
<h3 style="color:green;">
    <%=request.getAttribute("success") == null ? "" :
        request.getAttribute("success")%>
</h3>
	
<form action="TasksAssignment?type=personal" method="POST">
    <input type="submit" name="action" value="Back to Tasks" />
</form>
	
<%
    if ("NEW".equalsIgnoreCase((String) request.getAttribute("wfScenario"))) {
            Task task = (Task) request.getAttribute("task");
            String serviceType = (String) request.getAttribute("serviceType");

            Integer taskID = task.getId();
            Integer soID = task.getServiceOrderID();
%>

<form action="ProvisionEngineerTasksServlet" method="POST">
    <table border="1" id="table">
        <caption>NEW SCENARIO TASK</caption>
        <tbody>
            <tr>
                <th>Task ID</th>
                <th>Service Order ID</th>
                <th>Service type</th>
                <th>Circuit</th>
            </tr>
            <tr>
                <td><%=taskID%></td>
                <td><%=soID%></td>
                <td><%=serviceType%></td>
                <td><input required type="text"  name="circuit"  /></td>
            </tr>
        </tbody>
    </table>

    <input type="hidden" name="taskid" value="<%=taskID%>" /> 
    <input type="hidden" name="serviceorderid" value="<%=soID%>" />
    <input type="submit" name="action" value="Create Circuit" />
</form>
<%
    } else if ("DISCONNECT".equalsIgnoreCase((String) request.getAttribute("wfScenario"))) {
        Task task = (Task) request.getAttribute("task");
        Integer taskID = task.getId();
        Integer soID = task.getServiceOrderID();
%>
	
<form method="Post" action="ProvisionEngineerTasksServlet">
    <table border="1" id="table">
        <caption>DISCONNECT SCENARIO TASK</caption>
        <tr>
            <th>Task Number</th>
            <th>Service Order ID</th>
            <th>Action</th>
        </tr>
        <tr>
            <td><%=task.getId()%></td>
            <td><%=task.getServiceOrderID()%></td>
            <td><input type="submit" class="btn btn-info" value="Delete Circuit from SI" name="action" /></td>
        </tr>
    </table>
    <input type="hidden" name="d_taskid" value="<%=task.getId()%>"  />
    <input type="hidden" name="d_orderid" value="<%=task.getServiceOrderID()%>" />
</form>

<%
    }
%> 
