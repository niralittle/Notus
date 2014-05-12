<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.Task"%>


<%
    if (request.getAttribute("success") != null) {
        out.print("<div id='installationMessage'>" + request.getAttribute("success") + "</div>");
        request.setAttribute("success", null);
    }
    if (request.getAttribute("task") != null) {
        Task task = (Task) request.getAttribute("task");
        String serviceType = (String) request.getAttribute("serviceType");
        String price = (String) request.getAttribute("price");
%>

<form action="TasksAssignment?type=personal" method="POST">
    <input type="submit" name="action" value="Back to Tasks" />
</form>

<form method="Post" action="Support">
    <table id="table">
        <caption>NEW SCENARIO TASK</caption>
        <tr>
            <th>Task Number</th>
            <th>Service Order ID</th>
            <th>Service type</th>
            <th>Price</th>
        </tr>
        <tr>
            <td><input type="text" name="taskid" value="<%=task.getId()%>"
                       readonly="readonly" /></td>
            <td><input type="text" name="serviceorderid"
                       value="<%=task.getServiceOrderID()%>" readonly="readonly" /></td>
        	<td><%=serviceType %></td>
                <td><%=price %></td>
        </tr>
    </table>
    <input type="submit" value="Send bill" class="btn btn-info" name="action" />
</form>
 <% } %>
