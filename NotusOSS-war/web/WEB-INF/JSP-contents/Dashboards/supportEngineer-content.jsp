<%@page import="java.util.List"%>
<%@page import="nc.notus.entity.Task"%>

<a href="passwordChanging.jsp">Change user password</a> 

<%
	if (request.getAttribute("success") != null) {
		out.print("\n\n"+request.getAttribute("success") + "\n\n");
		request.setAttribute("success", null);
	}
	if(request.getAttribute("task") != null ) {
		Task task = (Task) request.getAttribute("task");
%>

<form method="Post" action="Support">
	<table>
	<caption>NEW SCENARIO TASK</caption>
		<tr>
			<td>Task Number</td>
			<td>Service Order ID</td>
		</tr>
		<tr>
			<td><input type="text" name="taskid" value="<%=task.getId()%>"
				readonly="readonly" /></td>
			<td><input type="text" name="serviceorderid"
				value="<%=task.getServiceOrderID()%>" readonly="readonly" /></td>
		</tr>	
	</table>
	<input type="submit" value="Send bill" name="action" />
</form>
<%
	}
%>
