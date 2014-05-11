<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.Task"%>


<%--<%=request.getParameter("wfScenario") %> --%>

<%
            if (request.getAttribute("success") != null) {
                out.print("<p>" + request.getAttribute("success") + "</p>");
                request.setAttribute("success", null);
            }
            if (request.getAttribute("task") != null) {
                Task task = (Task) request.getAttribute("task");
                String serviceType = (String) request.getAttribute("serviceType");
%>

<form action="TasksAssignment?type=personal" method="POST">
		<input type="submit" name="action" value="Back to Tasks" />
</form>

<form method="Post" action="Support">
    <table id="table">
        <caption>NEW SCENARIO TASK</caption>
        <tr>
            <td>Task Number</td>
            <td>Service Order ID</td>
            <td>Service type</td>
            <td>Price</td>
        </tr>
        <tr>
            <td><input type="text" name="taskid" value="<%=task.getId()%>"
                       readonly="readonly" /></td>
            <td><input type="text" name="serviceorderid"
                       value="<%=task.getServiceOrderID()%>" readonly="readonly" /></td>
        	<td><%=serviceType %>
        </tr>
    </table>
    <input type="submit" value="Send bill" name="action" />
</form>
<%
            }
%>
